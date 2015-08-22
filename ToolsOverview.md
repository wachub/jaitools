#summary Overview of JAITools components

# Tools #

![http://wiki.jaitools.googlecode.com/git/images/toolbox_200.gif](http://wiki.jaitools.googlecode.com/git/images/toolbox_200.gif)

## Image classes ##
  * DiskMemImage - a writable tiled image that uses disk and memory caching for its data

## Operators ##
  * KernelStats - calculates one or more statistics for a pixel's neighbourhood
  * MaskedConvolve - convolution of a subset of image pixels
  * RangeLookup - a lookup transformation defined by source image value ranges
  * [Regionalize](Regionalize.md) - identifies regions of uniform value with user-specified tolerance
  * ZonalStats - summary statistics for data image values within zone image regions

## Operator helper classes ##
  * KernelFactory - creates KernelJAI objects with a variety of configurations
  * KernelUtil - various methods to modify and examine kernel objects

## Image scripting ##
  * [Jiffle](Jiffle.md) - a scripting language and interpreter for image creation and analysis

## Support classes ##
  * [Range](UtilsRange.md) - implements numeric intervals and comparisons
  * [SampleStats](UtilsSampleStats.md) - summary statistics
  * [StreamingSampleStats](UtilsStreamingSampleStats.md) - smmary statistics for very long sequences of data
  * [DiskMemTileCache](UtilsTileCache.md) - a JAI TileCache implementation that uses disk and memory storage