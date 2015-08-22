﻿#summary Demonstrates basic use of the DiskMemImage class

# DiskMemImage example #

**module:** demo
**package:** jaitools.demo.tiledimage

## Drawing into a DiskMemImage ##

In this program we create a DiskMemImage and do some simple drawing using !Graphics2D routines.

First an image is created with an !RGB colour model...
```
ColorModel cm = ColorModel.getRGBdefault();
SampleModel sm = cm.createCompatibleSampleModel(128, 128);
DiskMemImage img = new DiskMemImage(0, 0, 256, 256, 0, 0, sm, cm);
```

Next we get a graphics object for this image...
```
Graphics2D gr = img.createGraphics();
```

Now we can draw into the image using familiar Graphics2D methods...
```
gr.setBackground(Color.ORANGE);
gr.clearRect(0, 0, 256, 256);

Shape shp = new Rectangle(64, 64, 128, 128);
gr.setColor(Color.BLUE);
gr.setStroke(new BasicStroke(3.0f));
gr.draw(shp);

gr.setColor(Color.RED);
gr.fillRect(96, 96, 64, 64);

gr.setColor(Color.BLACK);
Font font = gr.getFont();
gr.setFont(font.deriveFont(24f));
gr.drawString("Hello World !", 48, 32);

/*
 * Draw lines on tile boundaries
 */
gr.setStroke(new BasicStroke(1.0f));
gr.setColor(Color.GRAY);
gr.drawLine(128, 0, 128, 255);
gr.drawLine(0, 128, 255, 128);
```

And display it with an ImageFrame...
```
ImageFrame frame = new ImageFrame(img, "Image of 4 square tiles");
frame.setVisible(true);
```

![http://wiki.jaitools.googlecode.com/git/images/drawingdemo.gif](http://wiki.jaitools.googlecode.com/git/images/drawingdemo.gif)