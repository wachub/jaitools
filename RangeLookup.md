# RangeLookup operator #

**module:** rangelookup <br>
<b>package:</b> jaitools.media.jai.rangelookup<br>
<br>
<h2>Description</h2>

This is a variation on the JAI Lookup operation.<br>
It works with a RangeLookupTable object in which each entry maps<br>
a source image value range to a destination image value.<br>
<br>
The lower and upper bounds of the range may be open or closed. If closed<br>
they may include their defining value or exclude it.<br>
<br>
The RangeLookupTable has two constructors. The first takes no arguments: a table created with this constructor will throw an IllegalStateException if it encounters a source image value that is not contained within any of the ranges in the table. The second constructor takes an argument for default value: if set to a non-null value, this value will be written to the destination image whenever a source image value is not contained within any of the ranges in the table. If set to null, the table will behave as if created with the no-argument constructor.<br>
<br>
<h2>RangeLookup vs Lookup</h2>

The per-band offset values that can be defined for Lookup do not exist for RangeLookup.<br>
<br>
The Range class used by the RangeLookup operator allows you to define a point range, ie. one where the lower and upper bounds are the same. With this, you can peform Lookup-style single value lookups with float and double source image data.<br>
<br>
<h2>Limitations</h2>

RangeLookup doesn't handle TYPE_USHORT: an UnsupportedOperationException will be thrown.<br>
<br>
Presently, RangeLookup simply performs the same source-range <code>-&gt;</code> destination value lookups on all bands of the source image. It also doesn't support the transformation between single and multi-band images that can be done with Lookup.<br>
<br>
<h2>Example of use</h2>

<pre><code>/*<br>
 * Perform a lookup as follows:<br>
 *   Src Value     Dest Value<br>
 *     x &lt; 5            1<br>
 *   5 &lt;= x &lt; 10        2<br>
 *  10 &lt;= x &lt;= 20       3<br>
 *  any other value    99<br>
 */<br>
RenderedImage myIntImg = ...<br>
<br>
RangeLookupTable&lt;Integer&gt; table = new RangeLookupTable&lt;Integer&gt;(99);<br>
<br>
Range&lt;Integer&gt; r = new Range&lt;Integer&gt;(null, true, 5, false);  // x &lt; 5<br>
table.add(r, 1);<br>
<br>
r = new Range&lt;Integer&gt;(5, true, 10, false);  // 5 &lt;= x &lt; 10<br>
table.add(r, 2);<br>
<br>
r = new Range&lt;Integer&gt;(10, true, 20, true);  // 10 &lt;= x &lt;= 20<br>
table.add(r, 2);<br>
<br>
ParameterBlockJAI pb = new ParameterBlockJAI("rangelookup");<br>
pb.setSource("source0", myIntImg);<br>
pb.setParameter("table", table);<br>
RenderedImage luImg = JAI.create("rangelookup", pb);<br>
</code></pre>