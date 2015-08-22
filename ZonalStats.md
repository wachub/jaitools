#summary summary statistics within image zones

# ZonalStats #

**module:** zonalstats <br>
<b>package:</b> org.jaitools.media.jai.zonalstats<br>
<br>
<h2>Description</h2>

This operator calculates a range of summary statistics for values in zones, or regions, of a data image. The zones are defined using a separate zone image which must of integral type (e.g. TYPE_INT) and cover all data image locations.<br>
<br>
By default, the zone image is taken to have the same origin, and at least as many rows and columns, as the data image. If this is not the case an AffineTransform can be provided to transform from data image coordinates to zone image coordinates, allowing differences in image origins and dimensions to be accommodated.<br>
<br>
Unlike the KernelStats operator, which outputs its results as image bands, ZonalStats works in the manner of standard JAI statistics operators such as "Histogram", simply passing pixel values through from the source data image to the destination image and storing the results of its calculations as a ZonalStats object attached to the destination image as a property. The property name can be reliably referred to via the ZONAL_STATS_PROPERTY_NAME constant.<br>
<br>
<h2>Implementation details</h2>

This operator uses <a href='UtilsStreamingSampleStats.md'>StreamingSampleStats</a> for its calculations, allowing it to handle very large images since sample data are not retained in memory, except when calculating the exact median. Sample variance and sample standard deviation are calculated using a streaming algorithm described by <a href='http://code.google.com/p/jaitools/wiki/ZonalStats#References'>Knuth (1997)</a>.<br>
<br>
Calculation of the exact median (Statistic.MEDIAN) will exhaust memory if the image is too large. An alternative median estimator is provided (Statistic.APPROX_MEDIAN) to handle such cases. This uses the <i>remedian</i> algorithm of <a href='http://code.google.com/p/jaitools/wiki/ZonalStats#References'>Rousseeuw and Bassett (1990)</a> which performs well under general conditions but can produce biased estimates for highly ordered sequences of data values (e.g. monotonically increasing or decreasing).<br>
<br>
<h2>Example of use</h2>

<pre><code> RenderedImage myData = ...<br>
 RenderedImage myZones = ...<br>
 <br>
 ParameterBlockJAI pb = new ParameterBlockJAI("ZonalStats");<br>
 pb.setSource("dataImage", myData);<br>
 pb.setSource("zoneImage", myZones);<br>
 <br>
 Statistic[] stats = {<br>
     Statistic.MIN,<br>
     Statistic.MAX,<br>
     Statistic.MEAN,<br>
     Statistic.SDEV<br>
 };<br>
 <br>
 pb.setParameter("stats", stats);<br>
 RenderedOp op = JAI.create("zonalstats", pb);<br>
<br>
 ZonalStats zs = (ZonalStats) op.getProperty(ZonalStatsDescriptor.ZONAL_STATS_PROPERTY);<br>
 System.out.println("                               exact    approx");<br>
 System.out.println(" band zone      min      max   median   median     sdev");<br>
 System.out.println("-----------------------------------------------------------");<br>
<br>
 final int band = 0;<br>
 for (int z : zs.getZones()) {<br>
     System.out.printf(" %4d %4d", band, z);<br>
<br>
     ZonalStats zoneSubset = zs.band(0).zone(z);<br>
     for (Statistic s : statistics) {<br>
         System.out.printf(" %8.4f", zoneSubset.statistic(s).results().get(0).getValue());<br>
     }<br>
     System.out.println();<br>
 }<br>
<br>
</code></pre>

Note in the example above we are using chaining methods of the ZonalStats class, band(b), zone(z), statistic(s), to select subsets of results for printing. Each of these chaining methods returns a new ZonalStats object containing the desired subset as a list of Result objects (the data are referenced from the original ZonalStats object rather than being copied). There will be a Result object for each combination of data image band, zone image zone, and requested statistic.<br>
<br>
An alternative to using the chaining methods is to iterate through the list of Result objects in the parent ZonalStats object directly as shown here:<br>
<br>
<pre><code> ZonalStats allStats = ...<br>
 for (Result r : allStats.results()) {<br>
     if (r.getStatistic() == Statistic.MEAN) {<br>
         System.out.printf("%4d %4d %8.4f\n", <br>
         r.getImageBand(), r.getZone(), r.getValue());<br>
     }<br>
 }<br>
<br>
</code></pre>

<h2>References</h2>
Donald E Knuth (1997) The art of computer programming. Vol. 2 (3rd ed.)<br>
<br>
PJ Rousseeuw and GW Bassett (1990) <i>The remedian: a robust averaging method for large data sets.</i>  Journal of the American Statistical Society 85:97-104