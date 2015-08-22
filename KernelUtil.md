# KernelUtil #

**module:** kernel <br>
<b>package:</b> jaitools.media.jai.kernel<br>
<br>
<h2>Description</h2>

This class contains some handy, time-saving methods to manipulate and examine kernels.<br>
<br>
<h2>Examples of use</h2>

<pre><code>// create a copy of a kernel with the element values<br>
// standardized such that they sum to 1.0<br>
KernelJAI newKernel = KernelUtil.standardize(existingKernel);<br>
<br>
// now print the element values to the console (the boolean<br>
// arg specifies whether we want a multiline string returned<br>
System.out.println(KernelUtil.kernelToString(newKernel, true));<br>
</code></pre>