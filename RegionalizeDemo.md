#summary Demo program for the Regionalize operator

# Regionalize demo #

**module:** demo <br>
<b>package:</b> org.jaitools.demo.regionalize<br>
<br>
This program demonstrates the <a href='Regionalize.md'>Regionalize</a> operator which identifies areas of uniform value, within some specified tolerance, in a source image.<br>
<br>
The input image has a chessboard pattern, where each square contains pixels with value 0.0 (black) or 1.0 (white).<br>
<br>
<img src='http://wiki.jaitools.googlecode.com/git/images/chessboard.gif' />

First we regionalize this, requiring all pixels in a region to be orthogonally connected. Here's a code snippet from the program...<br>
<br>
<pre><code>       /*<br>
        * Regionalize the source chessboard image,<br>
        * specifying orthogonal connectedness by setting the<br>
        * diagonal parameter to false<br>
        */<br>
       ParameterBlockJAI pb = new ParameterBlockJAI("regionalize");<br>
       pb.setSource("source0", image);<br>
       pb.setParameter("diagonal", false);<br>
       RenderedOp orthoImg = JAI.create("Regionalize", pb);<br>
</code></pre>

This results in each of the 64 chessboard squares being defined as a separate region, as shown in this image...<br>
<br>
<img src='http://wiki.jaitools.googlecode.com/git/images/orthogonal_regions.gif' />

Note that the discontinuities in colour evident here are due to the region numbering scheme. Each region is given an integer ID, starting with 1. Because the source was a tiled image, the ID values are allocated as each tile is processed (although a region in the source image that spans tile boundaries will not be cut into separate regions in the output image).<br>
<br>
Now we regionalize the chessboard image again, this time allowing regions to contain diagonally connected pixels. Here's the relevant snippet from the demo program...<br>
<br>
<pre><code>       /*<br>
        * Repeat the regionalization of the source image<br>
        * allowing diagonal connections within regions<br>
        */<br>
<br>
       pb = new ParameterBlockJAI("regionalize");<br>
       pb.setSource("source0", image);<br>
       pb.setParameter("diagonal", true);<br>
       RenderedOp diagImg = JAI.create("regionalize", pb);<br>
</code></pre>

And here is the output image showing that we now have only two regions: one corresponding to the black chessboard squares and the other corresponding to the white squares...<br>
<br>
<img src='http://wiki.jaitools.googlecode.com/git/images/diagonal_regions.gif' />

