# MaskedConvolve operator #

**module:** maskedconvole <br>
<b>package:</b> jaitools.media.jai.maskedconvolve<br>
<br>
<h2>Description</h2>

Performs kernel-based convolution like JAI's Convolve operator, but adds control over which source image pixels are included in the convolution. This is defined in an<br>
<a href='http://java.sun.com/products/java-media/jai/forDevelopers/jai-apidocs/javax/media/jai/ROI.html'>ROI</a> object that the user provides with the source image.<br>
<br>
Two control options are provided:<br>
<br>
<ol><li><b>source masking</b> in which the ROI is used to constrain which source image pixels contribute to the kernel calculation</li></ol>

<ol><li><b>destination masking</b> in which the ROI constrains the positioning of the convolution kernel such that a destination image pixel will be given a value of 0 if the corresponding source pixel is not contained in the ROI.</li></ol>

The two options may be used together. If neither masking option is required it is preferable to use the standard JAI Convolve operator for faster processing.<br>
<br>
If there is no convolution result for a destination image pixel, either because it was not included in a destination mask or had no kernel values included in a source mask, it will be set to a flag value. This can be specified using the <b>nilValue</b> parameter (default is 0).<br>
<br>
<h2>Example of use</h2>
<pre><code>RenderedImage img = ...<br>
<br>
// a kernel to calculate the sum of values<br>
// in a (roughly) circular neighbourhood with<br>
// 2 pixel radius<br>
float[] kernelData = new float[]{<br>
     0, 0, 1, 0, 0,<br>
     0, 1, 1, 1, 0,<br>
     1, 1, 1, 1, 1,<br>
     0, 1, 1, 1, 0,<br>
     0, 0, 1, 0, 0,<br>
};<br>
       <br>
KernelJAI kernel = new KernelJAI(5, 5, kernelData);<br>
ROI roi = new ROI(img, thresholdValue);<br>
<br>
ParameterBlockJAI pb = new ParameterBlockJAI("maskedconvolve");<br>
pb.setSource("source0", op0);<br>
pb.setParameter("kernel", kernel);<br>
pb.setParameter("roi", roi);<br>
<br>
// no need to set masksource and maskdest params if we want to<br>
// use their default values (TRUE)<br>
<br>
BorderExtender extender = BorderExtender.createInstance(BorderExtender.BORDER_ZERO);<br>
RenderingHints hints = new RenderingHints(JAI.KEY_BORDER_EXTENDER, extender);<br>
<br>
RenderedOp dest = JAI.create("maskedconvolve", pb, hints);<br>
</code></pre>