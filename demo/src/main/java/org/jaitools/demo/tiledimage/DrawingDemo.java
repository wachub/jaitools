/* 
 *  Copyright (c) 2009, Michael Bedward. All rights reserved. 
 *   
 *  Redistribution and use in source and binary forms, with or without modification, 
 *  are permitted provided that the following conditions are met: 
 *   
 *  - Redistributions of source code must retain the above copyright notice, this  
 *    list of conditions and the following disclaimer. 
 *   
 *  - Redistributions in binary form must reproduce the above copyright notice, this 
 *    list of conditions and the following disclaimer in the documentation and/or 
 *    other materials provided with the distribution.   
 *   
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR 
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */   

package org.jaitools.demo.tiledimage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.image.ColorModel;
import java.awt.image.SampleModel;

import org.jaitools.swing.ImageFrame;
import org.jaitools.tiledimage.DiskMemImage;


/**
 * Demonstrates drawing into a <code>DiskMemImage</code> using
 * Graphics2D methods. See comments in the source code for more
 * details.
 *
 * @see org.jaitools.tiledimage.DiskMemImage
 * @see org.jaitools.tiledimage.DiskMemImageGraphics
 * 
 * @author Michael Bedward
 * @since 1.0
 * @version $Id$
 */
public class DrawingDemo {

    /**
     * Run the demo application.
     * @param args ignored
     */
    public static void main(String[] args) {
        DrawingDemo me = new DrawingDemo();
        me.demo();
    }

    private void demo() {
        /*
         * First we create a DiskMemImage with an RGB color model.
         * The image will be tiled: we specify a tile size of 128x128 pixels
         * when creating the sample model. The image size is 256x256 pixels
         * (2x2 tiles).
         */
        ColorModel cm = ColorModel.getRGBdefault();
        SampleModel sm = cm.createCompatibleSampleModel(128, 128);
        DiskMemImage img = new DiskMemImage(0, 0, 256, 256, 0, 0, sm, cm);

        /*
         * The createGraphics methods returns an instance of
         * DiskMemImageGraphics which provides a bridge to 
         * Graphics2D drawing methods
         */
        Graphics2D gr = img.createGraphics();
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /*
         * Here we do some common operations to demonstrate
         * that they work
         */
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

        /*
         * We display the results using the ImageFrame widget
         */
        ImageFrame frame = new ImageFrame(img, "Image of 4 square tiles");
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
}
