# StreamingSampleStats #

**module:** utils<br>
<b>package:</b> jaitools.numeric<br>
<br>
<h2>Description</h2>

The StreamingSampleStats class offers a similar range of summary statistics as the <a href='UtilsSampleStats.md'>SampleStats</a> class, but works with a (potentially very long) stream of double-valued data.<br>
<br>
<h2>Example of use</h2>
<pre><code>StreamingSampleStats strmStats = new StreamingSampleStats();<br>
<br>
// set the statistics that will be calculated<br>
Statistic[] stats = {<br>
    Statistic.MEAN,<br>
    Statistic.SDEV,<br>
    Statistic.RANGE,<br>
    Statistic.APPROX_MEDIAN<br>
};<br>
strmStats.setStatistics(stats);<br>
<br>
// some process that generates a long stream of data<br>
while (somethingBigIsRunning) {<br>
    double value = ...<br>
    strmStats.addSample(value);<br>
}<br>
<br>
// report the results<br>
for (Statistic s : stats) {<br>
    System.out.println(String.format("%s: %.4f", s, strmStats.getStatisticValue(s)));<br>
}<br>
<br>
</code></pre>