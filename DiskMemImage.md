# DiskMemImage #

**module:** utils <br>
<b>package:</b> jaitools.tiledimage<br>
<br>
<h2>Description</h2>

This class serves the same purpose as JAI's standard TiledImage class: namely, a writable image that can accommodate a variety of data types, an optional ColorModel, and data arranged in tiles. However, unlike TiledImage, DiskMemImage uses disk as well as memory storage to allow very large images to be handled in limited memory.<br>
<br>
By default, each instance of DiskMemImage uses its own <a href='UtilsTileCache.md'>DiskMemTileCache</a> to store image data on disk and hold the most recently accessed tiles in memory. The amount of memory used for tile storage can be controlled. You can also request that multiple DiskMemImages share a common tile cache.<br>
<br>
DiskMemImage implements the WritableRenderedImage and PlanarImage interfaces.<br>
<br>
<h2>Examples of use</h2>

This code illustrates creating a DiskMemImage...<br>
<pre><code>// RGB ColorModel<br>
ColorModel cm = ColorModel.getRGBdefault();<br>
<br>
// Sample  model specifying the size of image tiles<br>
int tileW = 128;<br>
SampleModel sm = cm.createCompatibleSampleModel(tileW, tileW);<br>
<br>
// Create an image 512 x 512 pixels with the given sample and <br>
// color models<br>
DiskMemImage img = new DiskMemImage(0, 0, 512, 512, 0, 0, sm, cm);<br>
</code></pre>

Graphics2D methods can be used to draw into a DiskMemImage...<br>
<pre><code>DiskMemImage img = ...<br>
// create a graphics object (this will be an instance<br>
// of jaitools.tiledimage.DiskMemImageGraphics)<br>
Graphics2D gr = img.createGraphics();<br>
<br>
// do some drawing with Graphics2D methods<br>
gr.setColor(Color.BLUE);<br>
Shape shp = ... <br>
gr.draw(shp);<br>
</code></pre>

Image data can also be accessed at the tile level...<br>
<pre><code>DiskMemImage img = ...<br>
<br>
// access to read<br>
Raster reading = img.getTile(tileX, tileY);<br>
<br>
// access for writing<br>
WritableRaster writing = img.getWritableTile(tileX, tileY);<br>
... <br>
// release the tile when finished<br>
img.releaseWritableTile(tileX, tileY);<br>
</code></pre>

You can specify that multiple images use a common tile cache like this...<br>
<pre><code>// two images using a common DiskMemTileCache<br>
DiskMemImage img1 = ...<br>
img1.useCommonTileCache( true );<br>
<br>
DiskMemImage img2 = ...<br>
img2.useCommonTileCache( true );<br>
<br>
// some image operations that result in tile caching<br>
...<br>
<br>
// query some attributes of the common cache<br>
int numTiles = DiskMemImage.getCommonCache().getNumTiles();<br>
long memInUse = DiskMemImage.getCommonCache().getCurrentMemory();<br>
</code></pre>// two images using a common DiskMemTileCache
DiskMemImage img1 = ...
img1.useCommonTileCache( true );

DiskMemImage img2 = ...
img2.useCommonTileCache( true );

// some image operations that result in tile caching
...

// query some attributes of the common cache
int numTiles = DiskMemImage.getCommonCache().getNumTiles();
long memInUse = DiskMemImage.getCommonCache().getCurrentMemory();
}}}</code></pre>