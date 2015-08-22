#summary Calculates one or more statistics for values in a pixel's neighbourhood

# KernelStats #

**module:** kernelstats <br>
<b>package:</b> org.jaitools.media.jai.kernelstats<br>
<br>
<h2>Description</h2>

This operator calculates summary statistics for pixel neighbourhoods in a single band of the source image. The neighbourhood is defined with a KernelJAI object (see also KernelFactory).<br>
<br>
The following statistics can be calculated:<br>
<br>
<ul><li>Mean<br>
</li><li>Median<br>
</li><li>Minimum<br>
</li><li>Maximum<br>
</li><li>Range (max - min)<br>
</li><li>Sample standard deviation<br>
</li><li>Sample variance<br>
</li><li>Sum</li></ul>

Multiple statistics can be requested in a single invocation of the operator. Each statistic is returned as a band in the destination image.<br>
<br>
<h2>Kernel values and the neighbourhood</h2>

In this operator, the kernel values are converted to boolean such that non-zero values (regardless of sign) are true and zero values are false. The resulting boolean kernel defines which pixels in a source pixel's neighbourhood are included. The data for summary statistics calculations is the untransformed source image values of the neighbourhood pixels. This differs from convolution (e.g. JAI Convolve; JAITools MaskedConvolve) where source image values are multiplied with the corresponding kernel element values.<br>
<br>
<h2>Data type of the result image</h2>

The data type of the result depends upon the source image data type and the requested statistics. If any of mean, median, sdev or variance are requested the destination image will be TYPE_DOUBLE. If only min, max or range are requested the destination image will have the same data type as the source image.<br>
<br>
The reason that median generates a TYPE_DOUBLE image, even when working with a source image with integral data, is because of the tie-breaking rule implemented for this statistic. Where there are an even number of sample values and the mid-point of the sorted data falls between two differing values, the median is defined as the mean of these two values.<br>
<br>
<h2>Masking</h2>

KernelStats offers the same masking options as MaskedConvolve.<br>
<br>
<h2>Limitations</h2>

KernelStats only deals with a single source image band. This can be specified with the "band" parameter (the default is band 0).<br>
<br>
<h2>Example of use</h2>

Here we define a circular neighbourhood with a simple binary kernel (value=1 for included elements, 0 for outside elements) and calculate mean and sample standard deviation...<br>
<br>
<pre><code>KernelJAI kernel = KernelFactory.createCircle(10);<br>
ParameterBlockJAI pb = new ParameterBlockJAI("KernelStats");<br>
<br>
pb.setSource("source0", img);<br>
pb.setParameter("kernel", kernel);<br>
<br>
Statistic[] stats = new Statistic[]<br>
{<br>
    Statistic.MEAN,<br>
    Statistic.SDEV,<br>
};<br>
pb.setParameter("stats", stats);<br>
<br>
BorderExtender extender = BorderExtender.createInstance(BorderExtender.BORDER_REFLECT);<br>
RenderingHints hints = new RenderingHints(JAI.KEY_BORDER_EXTENDER, extender);<br>
<br>
RenderedImage multibandImg = JAI.create("KernelStats", pb, hints);<br>
<br>
// if we require one image per statistic we can use ImageUtils.getBandsAsImages<br>
List&lt;RenderedImage&gt; statsImages = ImageUtils.getBandsAsImages(multibandImg);<br>
</code></pre>