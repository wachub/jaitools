/*
 * Copyright 2009 Michael Bedward
 *
 * This file is part of jai-tools.
 *
 * jai-tools is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * jai-tools is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with jai-tools.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package jaitools.tilecache;

import java.awt.Point;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.SampleModel;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.jai.ParameterList;
import javax.media.jai.ParameterListDescriptor;
import javax.media.jai.TileCache;

/**
 * @author Michael Bedward
 */
public class DiskBasedTileCache implements TileCache {

    public static final long DEFAULT_MEMORY_CAPACITY = 64L * 1024L * 1024L;

    // @todo use JAI ParameterList or some other ready-made class for this ?
    private static class ParamDesc {
        String key;
        Class<?> clazz;
        Object defaultValue;

        ParamDesc(String key, Class<?> clazz, Object defaultValue) {
            this.key = key; this.clazz = clazz; this.defaultValue = defaultValue;
        }

        boolean typeOK (Object value) {
            return value.getClass().isAssignableFrom(clazz);
        }
    }

    /**
     * Key for the parameter controlling initial memory capacity of the
     * tile cache. This determines the maximum number of tiles that can
     * be resident concurrently. The value must be numeric and will be
     * treated as Long.
     */
    public static final String INITIAL_MEMORY_CAPACITY = "memcapacity";

    /**
     * Key for the parameter controlling whether newly added tiles become
     * resident in memory. The value must be Boolean.
     */
    public static final String MAKE_NEW_TILES_RESIDENT = "newtilesres";

    private static Map<String, ParamDesc> paramDescriptors;
    static {
        ParamDesc desc;
        paramDescriptors = new HashMap<String, ParamDesc>();

        desc = new ParamDesc(INITIAL_MEMORY_CAPACITY, Long.class, DEFAULT_MEMORY_CAPACITY);
        paramDescriptors.put( desc.key, desc );

        desc = new ParamDesc(MAKE_NEW_TILES_RESIDENT, Boolean.class, Boolean.TRUE);
        paramDescriptors.put( desc.key, desc );
    }

    private long memCapacity;
    private boolean newTilesResident;

    // map of all tiles whether currently in memory or cached on disk
    private Map<Object, DiskCachedTile> tiles;
    
    // set of those tiles currently resident in memory
    private Map<Object, SoftReference<Raster>> residentTiles;

    // current (approximate) memory used for resident tiles
    private long curSize;
    

    /**
     * Constructor.
     *
     * @param params an optional map of parameters (may be empty or null)
     */
    public DiskBasedTileCache(Map<String, Object> params) {
        if (params == null) {
            params = Collections.emptyMap();
        }

        tiles = new HashMap<Object, DiskCachedTile>();
        residentTiles = new HashMap<Object, SoftReference<Raster>>();
        curSize = 0L;

        Object o;
        ParamDesc desc;

        desc= paramDescriptors.get(INITIAL_MEMORY_CAPACITY);
        o = params.get(desc.key);
        if (o != null) {
            if (desc.typeOK(o)) {
                memCapacity = (Long)o;
            }
        } else {
            memCapacity = (Long)desc.defaultValue;
        }

        desc = paramDescriptors.get(MAKE_NEW_TILES_RESIDENT);
        o = params.get(desc.key);
        if (o != null) {
            if (desc.typeOK(o)) {
                newTilesResident = (Boolean)o;
            }
        } else {
            newTilesResident = (Boolean)desc.defaultValue;
        }
    }

    public void add(RenderedImage owner, int tileX, int tileY, Raster data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void add(RenderedImage owner,
                int tileX,
                int tileY,
                Raster data,
                Object tileCacheMetric) {
        try {
            DiskCachedTile tile = new DiskCachedTile(owner, tileX, tileY, data, tileCacheMetric);

        } catch (IOException ex) {
            Logger.getLogger(DiskBasedTileCache.class.getName())
                    .log(Level.SEVERE, "Unable to cache this tile on disk", ex);
        }
    }

    public void remove(RenderedImage owner, int tileX, int tileY) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Raster getTile(RenderedImage owner, int tileX, int tileY) {
        Raster r = null;

        // is the tile resident ?
        Object key = DiskCachedTile.getTileKey(owner, tileX, tileY);
        if (residentTiles.containsKey(key)) {
            r = residentTiles.get(key).get();
            if (r == null) {
                // tile has been garbage collected
                residentTiles.remove(key);

            } else {
                return r;
            }
        }

        // tile needs to be loaded from disk and added
        // to the memory store
        DiskCachedTile tile = tiles.get(key);
        if (tile != null) {
            r = tile.getTile();
            makeResident(key, r);
        }

        return r;
    }

    public Raster[] getTiles(RenderedImage owner) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeTiles(RenderedImage owner) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addTiles(RenderedImage owner,
                     Point[] tileIndices,
                     Raster[] tiles,
                     Object tileCacheMetric) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Raster[] getTiles(RenderedImage owner, Point[] tileIndices) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void flush() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void memoryControl() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Implemented as an empty method (it was deprecated as of JAI 1.1)
     * @param arg0
     * @deprecated
     */
    @Deprecated
    public void setTileCapacity(int arg0) {
    }

    /**
     * Implemented as a dummy method that always returns 0
     * (it was deprecated as of JAI 1.1)
     * @deprecated
     */
    @Deprecated
    public int getTileCapacity() {
        return 0;
    }

    public void setMemoryCapacity(long memoryCapacity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public long getMemoryCapacity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setMemoryThreshold(float memoryThreshold) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public float getMemoryThreshold() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setTileComparator(Comparator comparator) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Comparator getTileComparator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Add a raster to those resident in memory
     */
    private void makeResident(Object tileKey, Raster r) {
        SampleModel sm = r.getSampleModel();
        long typeLen = DataBuffer.getDataTypeSize(sm.getTransferType());
        long size = (long)sm.getNumDataElements() * typeLen;

        if (size > memCapacity) {
            // @todo something better than this...
            throw new RuntimeException("tile size greater than memory capacity");
        }

        while (curSize + size > memCapacity) {
            removeResidentTile();
        }

        // TODO FINISH ME 1
    }

    /**
     * Remove the tile with the longest time since last access from memory
     */
    private void removeResidentTile() {

    }

}