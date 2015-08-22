# Range #

**module:** utils<br>
<b>package:</b> jaitools.numeric<br>
<br>
<h2>Description</h2>

Yet another numeric Range class. It is used with the RangeLookup operator but is also available for general use.<br>
<br>
Range is a generic class that can be used with types that extend Number and implement Comparable. A Range is defined by its endpoints. If the min (lower bound) end-point is the same as the max (upper-bound) end-point then the Range represents a point, also referred to as a degenerate interval.<br>
<br>
An end-point can be positioned at a finite value, in which case it is said to be <i>closed</i>, or at positive or negative infinity, in which case it is said to be <i>open</i>. For finite end-points, a Range can include or exclude the end-point. Included end-points correspond to square-brackets in interval notation, while excluded end-points correspond to round brackets.<br>
<br>
<h2>Performing interval comparisons</h2>

Comparisons between Range objects are handled by the RangeComparator class. Hayes (2003) describes 18 possible pair-wise comparisons between intervals (see his Fig. 3). The RangeComparator.Result enum has a constant for each of these possible relationships...<br>
<br>
<table><thead><th><b>Name</b></th><th><b>Notation</b></th><th><b>Description</b></th></thead><tbody>
<tr><td>LLLL       </td><td><<<<           </td><td>completely less than</td></tr>
<tr><td>LLLE       </td><td><<<=           </td><td>extends to min of </td></tr>
<tr><td>LLLG       </td><td><<<>           </td><td>starts below and extends within</td></tr>
<tr><td>LLEG       </td><td><<=>           </td><td>starts below and extends to max of</td></tr>
<tr><td>LEGG       </td><td><=>>           </td><td>starts with and extends beyond</td></tr>
<tr><td>LLEE       </td><td><<==           </td><td>starts below and has max at point location of</td></tr>
<tr><td>EEGG       </td><td>==>>           </td><td>extends from point location of</td></tr>
<tr><td>LEEG       </td><td><==>           </td><td>is exactly equal to finite interval</td></tr>
<tr><td>EEEE       </td><td>====           </td><td>is exactly equal to point</td></tr>
<tr><td>LLGG       </td><td><<>>           </td><td>strictly encloses </td></tr>
<tr><td>LGLG       </td><td><><>           </td><td>is strictly enclosed by</td></tr>
<tr><td>LGGG       </td><td><>>>           </td><td>starts within and extends beyond</td></tr>
<tr><td>LGEG       </td><td><>=>           </td><td>starts within and extends to max of</td></tr>
<tr><td>LELG       </td><td><=<>           </td><td>starts with and ends within</td></tr>
<tr><td>EGEG       </td><td>=>=>           </td><td>is a point at max of</td></tr>
<tr><td>LELE       </td><td><=<=           </td><td>is a point at min of</td></tr>
<tr><td>EGGG       </td><td>=>>>           </td><td>extends from max of</td></tr>
<tr><td>GGGG       </td><td>>>>>           </td><td>is completely greater than</td></tr></tbody></table>

<h2>Handling NaN values</h2>

The following rules apply to Float and Double NaN values...<br>
<br>
<ul><li>Float and Double NaN are considered equal</li></ul>

<ul><li>Point intervals can have value NaN</li></ul>

<ul><li>Proper intervals treat NaN at their lower end-point as negative infinity and at their upper end-point as positive infinity</li></ul>

<ul><li>A test value of NaN can be equal to a point interval</li></ul>

<ul><li>A test value of NaN will always be treated as outside a proper interval</li></ul>

<h2>Examples of use</h2>
<pre><code>// Create a range representing the interval [5, 10)<br>
// ie. 5 is included in the range, 10 is excluded<br>
Range&lt;Integer&gt; r1 = new Range&lt;Integer&gt;(5, true, 10, false);<br>
<br>
// Create a range representing the interval (Inf, 10]<br>
// this time using the static create function<br>
Range&lt;Integer&gt; r2 = Range.create(null, false, 10, true);<br>
<br>
// Get the relationship between these two intervals from the<br>
// 'point of view' of r1<br>
RangeComparator.Result comp = r1.compareTo(r2);<br>
System.out.println(String.format("r1 %s r2", comp.getDesc()));<br>
<br>
// A point (degenerate) interval<br>
Range&lt;Integer&gt; r3 = Range.create(10);<br>
comp = r3.compareTo(r2);<br>
System.out.println(String.format("r3 %s r2", comp.getDesc()));<br>
<br>
</code></pre>

The output from the above code is...<br>
<pre><code>r1 is strictly enclosed by r2<br>
r3 is a point at max of r2<br>
</code></pre>

As well as these fine-grain comparisons the Range class also provides higher-level comparison methods <b>contains</b> and <b>intersects</b> as illustrated in this example...<br>
<br>
<pre><code>// the interval [-5.0, 5.0)<br>
Range&lt;Float&gt; r1 = Range.create(-5.0f, true, 5.0f, false);<br>
<br>
float[] testValues = {-5.0f, 0f, 5.0f};<br>
<br>
for (float val : testValues) {<br>
    String s;<br>
    if (r1.contains(val)) {<br>
        s = String.format("%.1f is within r1", val);<br>
    } else {<br>
        s = String.format("%.1f is outside r1", val);<br>
    }<br>
    System.out.println(s);<br>
}<br>
<br>
// test for intersection of r1 and the intervals <br>
// [0.0, Inf) and [5.0, Inf)<br>
float[] lowerBounds = {0f, 5.0f};<br>
for (float lower : lowerBounds) {<br>
    String s = String.format("r1 %s with [%.1f, Inf)",<br>
        (r1.intersects(Range.create(lower, true, null, false)) ? <br>
            "intersects" : "does not intersect"),<br>
        lower);<br>
<br>
    System.out.println(s);<br>
}<br>
<br>
</code></pre>

The output is...<br>
<br>
<pre><code>-5.0 is within r1<br>
0.0 is within r1<br>
5.0 is outside r1<br>
r1 intersects with [0.0, Inf)<br>
r1 does not intersect with [5.0, Inf)<br>
</code></pre>

<h2>Reference</h2>
Brian Hayes (2003) A lucid interval. <i>American Scientist</i> <b>91</b>(6):484-488.<br>
<br>
Available at: <a href='http://www.cs.utep.edu/interval-comp/hayes.pdf'>http://www.cs.utep.edu/interval-comp/hayes.pdf</a>