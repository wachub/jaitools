# Regionalize #

**module:** regionalize <br>
<b>package:</b> jaitools.media.jai.regionalize<br>
<br>
<h2>Description</h2>

This operator identifies regions of uniform value in a source image, allocates each region a unique ID value, and produces an output image within which the value of each pixel is the ID of its region.<br>
<br>
<h2>Algorithm</h2>

The operator scans the source image left to right, top to bottom. When it reaches a pixel that has not been allocated to a region yet it uses the pixel as the starting point for a flood-fill search (similar to flood-filling in a paint program). The value of the starting pixel is recorded as the <i>reference value</i> for the new region. The search works its way outwards from the starting pixel, testing other pixels for inclusion in the region. A pixel will be included if: <code>|value - reference value| &lt;= tolerance</code> where <code>tolerance</code> is a user-specified parameter.<br>
<br>
If the <code>diagonal</code> parameter is set to <code>true</code>, the flood-fill search will include pixels that can only be reached via a diagonal step; if <code>false</code>, only orthogonal steps are taken. So, if the source image was a chessboard pattern, setting diagonal to <code>true</code> would result in two regions being identified: one for all the black squares and the other for all the white squares; whereas <code>false</code> would result in each individual square being defined as a region.<br>
<br>
The search continues until no further pixels can be added to the region. The region is then allocated a unique integer ID and summary statistics (bounds, number of pixels, reference value) are recorded for it.<br>
<br>
<h2>Outputs</h2>

The operator produces an output image of TYPE_INT with the value of each pixel being the ID of the region that it belongs to.  Summary data for the regions, in the form of a RegionData object, is stored as a property of this image and can be retrieved using RegionalizeDescriptor.REGION_DATA_PROPERTY as the property name.<br>
<br>
The RegionData object has a single public method, getData(), which returns an unmodifiable List of Region objects (see the example below).<br>
<br>
<h2>Limitations</h2>

At present this operator can only deal with a single band (the default is band 0).<br>
<br>
Because JAI uses deferred execution, the summary data in the RegionData object will not be created until the destination has been rendered.<br>
<br>
The regions identified by this operator are not guaranteed to be optimal in any sense. If different starting points were used, rather than those found with the present row-col scan, and the tolerance parameter was greater than 0, then a different distribution of regions could result. It may be worth extending the operator to allow for different starting points (e.g. random, evenly spaced, specified).<br>
<br>
<h2>Example of use</h2>

<pre><code>RenderedImage myImg = ...<br>
<br>
ParameterBlockJAI pb = new ParameterBlockJAI("regionalize");<br>
pb.setSource("source0", myImg);<br>
pb.setParameter("band", 0);<br>
pb.setParameter("diagonal", false);<br>
pb.setParameter("tolerance", 0.1d);<br>
RenderedOp op = JAI.create("Regionalize", pb);<br>
<br>
// have a look at the image<br>
jaitools.utils.ImageFrame frame = new jaitools.utils.ImageFrame();<br>
frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);<br>
frame.displayImage(op, "Regions");<br>
<br>
// print the summary data<br>
// (note: at present it is essential to render the image, such as<br>
//  by displaying it, before getting the region data)<br>
RegionData regData =<br>
   (RegionData)op.getProperty(RegionalizeDescriptor.REGION_DATA_PROPERTY);<br>
<br>
List&lt;Region&gt; regions = regData.getData();<br>
Iterator&lt;Region&gt; iter = regions.iterator();<br>
System.out.println("ID\tValue\tSize\tMin X\tMax X\tMin Y\tMax Y");<br>
<br>
while (iter.hasNext()) {<br>
    Region r = iter.next();<br>
    System.out.println( String.format("%d\t%.2f\t%d\t%d\t%d\t%d\t%d",<br>
        r.getId(),<br>
        r.getRefValue(),<br>
        r.getNumPixels(),<br>
        r.getMinX(),<br>
        r.getMaxX(),<br>
        r.getMinY(),<br>
        r.getMaxY() ));<br>
}<br>
<br>
</code></pre>