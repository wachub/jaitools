# DiskMemTileCache demo #

**module:** demo <br>
<b>package:</b> jaitools.demo.tilecache<br>
<br>
This program demonstrates basic use of the <a href='UtilsTileCache.md'>DiskMemTileCache</a> class.<br>
<br>
The program begins by creating a new tile cache with a very small memory capacity:<br>
<pre><code>/*<br>
 * We set the memory capacity of the cache to be too small for all tiles<br>
 * to be in memory concurrently in order to demonstrate cache actions.<br>
 */<br>
Map&lt;String, Object&gt; cacheParams = new HashMap&lt;String, Object&gt;();<br>
cacheParams.put(DiskMemTileCache.KEY_INITIAL_MEMORY_CAPACITY, 1L * 1024 * 1024);<br>
<br>
/*<br>
 * Create a new instance of DiskMemTileCache and add this as an observer<br>
 * so that we are notified about cache actions<br>
 */<br>
DiskMemTileCache cache = new DiskMemTileCache(cacheParams);<br>
cache.setDiagnostics(true);<br>
cache.addObserver(this);<br>
</code></pre>

The program then creates images with simple JAI operations. We specify that we want to use the custom tile cache by setting a rendering hint...<br>
<br>
<pre><code>Map&lt;RenderingHints.Key, Object&gt; imgParams = new HashMap&lt;RenderingHints.Key, Object&gt;();<br>
...  // other hints set<br>
imgParams.put(JAI.KEY_TILE_CACHE, cache);<br>
<br>
RenderingHints hints = new RenderingHints(imgParams);<br>
</code></pre>

We could also have set the cache as JAI's default tile cache like this...<br>
<pre><code>JAI.getDefaultInstance().setTileCache( cache );<br>
</code></pre>

When image tiles are added to the cache with its initial small memory capacity, the cache will swap tiles out to disk storage when it needs to free memory for each new tile. The program output (printed to the console) indicates this:<br>
<pre><code>Requesting tiles. Cache has only enough memory capacity<br>
for 2 tiles<br>
<br>
Tile at java.awt.Point[x=0,y=0] added to cache and placed into memory<br>
Tile at java.awt.Point[x=256,y=0] added to cache and placed into memory<br>
Tile at java.awt.Point[x=256,y=0] removed from memory<br>
Tile at java.awt.Point[x=0,y=128] added to cache and placed into memory<br>
Tile at java.awt.Point[x=0,y=0] removed from memory<br>
Tile at java.awt.Point[x=128,y=0] added to cache and placed into memory<br>
Tile at java.awt.Point[x=0,y=128] removed from memory<br>
Tile at java.awt.Point[x=256,y=128] added to cache and placed into memory<br>
Tile at java.awt.Point[x=128,y=0] removed from memory<br>
Tile at java.awt.Point[x=128,y=128] added to cache and placed into memory<br>
6 tiles cached; 2 resident in memory<br>
</code></pre>

Next the example is repeated after increasing the memory capacity of the cache like this:<br>
<pre><code>cache.setMemoryCapacity(5L * 1024 * 1024);<br>
</code></pre>

And the program output now shows that all tiles are stored in memory:<br>
<pre><code>Repeating the tile request after increasing the<br>
memory capacity of the cache<br>
<br>
Tile at java.awt.Point[x=0,y=0] placed into memory<br>
Tile at java.awt.Point[x=0,y=0] accessed<br>
Tile at java.awt.Point[x=128,y=0] placed into memory<br>
Tile at java.awt.Point[x=128,y=0] accessed<br>
Tile at java.awt.Point[x=256,y=0] placed into memory<br>
Tile at java.awt.Point[x=256,y=0] accessed<br>
Tile at java.awt.Point[x=0,y=128] placed into memory<br>
Tile at java.awt.Point[x=0,y=128] accessed<br>
Tile at java.awt.Point[x=128,y=128] accessed<br>
Tile at java.awt.Point[x=256,y=128] accessed<br>
6 tiles cached; 6 resident in memory<br>
</code></pre>