/*
 * Copyright 2009 Michael Bedward
 *
 * This file is part of jai-tools.

 * jai-tools is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.

 * jai-tools is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with jai-tools.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package jaitools.demo;

import java.awt.image.RenderedImage;

/**
 * Interface for classes that request images created by
 * the DemoImageProvider utility class
 *
 * @see DemoImageProvider
 * @author Michael Bedward
 */
public interface ImageReceiver {

    /**
     * Receive an image created by DemoImageProvider
     * @param image the image
     */
    public void receiveImage(RenderedImage image);
}

