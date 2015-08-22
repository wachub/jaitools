**summary Demonstrates drawing into a tiled image**

# DiskMemImage drawing demo #

**module:** demo <br>
<b>package:</b> org.jaitools.demo.tiledimage<br>
<br>
This simple program demonstrates creating a DiskMemImage and drawing into it using !Graphics2D methods. The program displays the results to screen...<br>
<br>
<img src='http://wiki.jaitools.googlecode.com/git/images/drawingdemo.gif' />

This code snippet contains the juicy bits...<br>
<pre><code>/*<br>
* First we create a DiskMemImage with an RGB color model.<br>
* The image will be tiled: we specify a tile size of 128x128 pixels<br>
* when creating the sample model. The image size is 256x256 pixels<br>
* (2x2 tiles).<br>
*/<br>
ColorModel cm = ColorModel.getRGBdefault();<br>
SampleModel sm = cm.createCompatibleSampleModel(128, 128);<br>
DiskMemImage img = new DiskMemImage(0, 0, 256, 256, 0, 0, sm, cm);<br>
<br>
/*<br>
* The createGraphics methods returns an instance of<br>
* jaitools.tiledimage.DiskMemImageGraphics which provides a<br>
* bridge to Graphics2D drawing methods<br>
*/<br>
Graphics2D gr = img.createGraphics();<br>
<br>
/*<br>
* Here we do some common operations to demonstrate<br>
* that they work<br>
*/<br>
gr.setBackground(Color.ORANGE);<br>
gr.clearRect(0, 0, 256, 256);<br>
<br>
Shape shp = new Rectangle(64, 64, 128, 128);<br>
gr.setColor(Color.BLUE);<br>
gr.setStroke(new BasicStroke(3.0f));<br>
gr.draw(shp);<br>
<br>
gr.setColor(Color.RED);<br>
gr.fillRect(96, 96, 64, 64);<br>
<br>
gr.setColor(Color.BLACK);<br>
Font font = gr.getFont();<br>
gr.setFont(font.deriveFont(24f));<br>
gr.drawString("Hello World !", 48, 32);<br>
<br>
</code></pre>
