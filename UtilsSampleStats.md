# SampleStats #

**module:** utils<br>
<b>package:</b> jaitools.numeric<br>
<br>
<h2>Description</h2>

The SampleStats class provides static methods to calculate summary statistics for a sample of double-valued data. It is used by <a href='Jiffle.md'>Jiffle</a> and the KernelStats operator and is also available for general use.<br>
<br>
The following methods are provided:<br>
<br>
<ul><li>max - maximum sample value<br>
</li><li>mean - arithmetic mean<br>
</li><li>median - median sample value<br>
</li><li>min - minimum sample value<br>
</li><li>mode - most frequently occuring sample value<br>
</li><li>range - maximum - minimum<br>
</li><li>sdev - sample standard deviation<br>
</li><li>sum - sum of sample values<br>
</li><li>variance - sample variance</li></ul>

Each method has two parameters: a Double array containing the sample values; and a boolean flag specifying whether NaN values should be ignored or not. If NaNs are not ignored, all methods will return NaN if any of the sample values are NaN.