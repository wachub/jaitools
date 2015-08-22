# KernelFactory #

**module:** kernel <br>
<b>package:</b> jaitools.media.jai.kernel<br>
<br>
<h2>Description</h2>

KernelFactory is a class with static methods to make it easier to create KernelJAI objects with the non-zero kernel elements arranged as one of:<br>
<ul><li>circle<br>
</li><li>annulus<br>
</li><li>rectangle</li></ul>

Kernels representing more complex shapes can also be created from java.awt.Shape objects.<br>
<br>
<h2>Examples of use</h2>

Here we create a kernel representing a raster circle with a radius of 3 pixels, as set by the first argument. The second argument specifies that elements within the circle will have a value of 1.0 (within the circle) or 0.0 (outside the circle). The third argument sets the value of the centre (key) kernel element.<br>
<br>
<pre><code>KernelJAI circle = KernelFactory.createCircle(3, Kernel.ValueType.BINARY, 1.0);<br>
<br>
// Let's have a look at it...<br>
System.out.println(KernelUtil.kernelToString(circle, true);<br>
</code></pre>

Which produces this...<br>
<br>
<pre><code>[[0001000]<br>
 [0111110]<br>
 [0111110]<br>
 [1111111]<br>
 [0111110]<br>
 [0111110]<br>
 [0001000]]<br>
</code></pre>

<hr />

Next, an example of creating a kernel for distance-weighted operations...<br>
<br>
Once again we create a circular kernel, this time with a radius of 3 pixels. The value of each element in the kernel will be the inverse of the distance to the kernel centre. We set the centre element's value is set to 1.0<br>
<br>
<pre><code>KernelJAI distWt = KernelFactory.createCircle(3, Kernel.ValueType.INVERSE_DISTANCE, 1.0);<br>
System.out.println(KernelUtil.kernelToString(distWt, true);<br>
</code></pre>

And this is the output...<br>
<br>
<pre><code>[[0.0000 0.0000 0.0000 0.3333 0.0000 0.0000 0.0000]<br>
 [0.0000 0.3536 0.4472 0.5000 0.4472 0.3536 0.0000]<br>
 [0.0000 0.4472 0.7071 1.0000 0.7071 0.4472 0.0000]<br>
 [0.3333 0.5000 1.0000 1.0000 1.0000 0.5000 0.3333]<br>
 [0.0000 0.4472 0.7071 1.0000 0.7071 0.4472 0.0000]<br>
 [0.0000 0.3536 0.4472 0.5000 0.4472 0.3536 0.0000]<br>
 [0.0000 0.0000 0.0000 0.3333 0.0000 0.0000 0.0000]]<br>
</code></pre>

<hr />

You can also make an annulus (doughnut) kernel. In this example we create a kernel with an outer radius of 3 pixels, and an inner radius (the hole in the doughnut) of 2 pixels.<br>
<br>
<pre><code>KernelJAI annulus = KernelFactory.createAnnulus(3, 2, Kernel.ValueType.BINARY, 0.0);<br>
System.out.println(KernelUtil.kernelToString(annulus, true);<br>
</code></pre>

Which prints this output...<br>
<br>
<pre><code>[[0001000]<br>
 [0110110]<br>
 [0100010]<br>
 [1000001]<br>
 [0100010]<br>
 [0110110]<br>
 [0001000]]<br>
</code></pre>

<hr />

You can create a kernel from any closed, polygonal object that implements java.awt.Shape. In this example we create a narrow elliptical kernel, with the long axis of the ellipse having an upper-left to lower-right (NW - SE) orientation. Such a kernel might be part of a landscape fire model...<br>
<br>
<pre><code>// create an ellipse with a horizontal long axis<br>
Shape ell = new Ellipse2D.Float(0, 0, 10, 5);<br>
<br>
// create an AffineTransform to rotate the ellipse 45 degrees clock-wise<br>
AffineTransform at = AffineTransform.getRotateInstance(Math.PI/4);<br>
<br>
// create the kernel with its key element at the upper-left corner (0,0)<br>
KernelJAI ellKern = KernelFactory.createFromShape(<br>
    ell, at, KernelFactory.ValueType.BINARY, 0, 0, 0f);<br>
<br>
// now print it to convince ourselves that we have what we want<br>
System.out.println(KernelUtil.kernelToString(ellKern, true));<br>
</code></pre>

And here is the thing of beauty that results...<br>
<br>
<pre><code>[[0000000000]<br>
 [0011100000]<br>
 [0111111000]<br>
 [0111111100]<br>
 [0011111100]<br>
 [0001111110]<br>
 [0001111110]<br>
 [0000011110]<br>
 [0000001110]<br>
 [0000000000]]<br>
</code></pre>

Note that the kernel's key element, which we set as the upper-left corner, is disjoint from the elements within the ellipse. This is perfectly valid (and hopefully what we wanted).