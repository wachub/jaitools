

# Including individual JAITools modules in your project #

If you are using [Maven](http://maven.apache.org/) as your build tool you can get the JAITools jars downloaded automagically by including them as dependencies in your **pom.xml** file.  For example:

```
<dependencies>
    <!-- The contour image operator -->
    <dependency>
        <groupId>org.jaitools</groupId>
        <artifactId>jt-contour</artifactId>
        <version>1.3.0</version>
    </dependency>
    <!-- The zonalstats image operator -->
    <dependency>
        <groupId>org.jaitools</groupId>
        <artifactId>jt-zonalstats</artifactId>
        <version>1.3.0</version>
    </dependency>
</dependencies>
```

# Using the JAITools uber-jar #

If you prefer, you can use the jt-all module which combines all JAITools modules other than the demo applications in a single jar:

```
<dependencies>
    <!-- Provides all JAITools modules -->
    <dependency>
        <groupId>org.jaitools</groupId>
        <artifactId>jt-all</artifactId>
        <version>1.3.0</version>
    </dependency>
</dependencies>
```

The modules are available from [Maven Central](http://repo1.maven.org/maven2/com/googlecode/jaitools/), and source and javadoc jars are available for all modules.

# Manual installation #

To add JAITools jars to your project manually, simple download the binary, source and javadoc jars from Maven Central at: http://repo1.maven.org/maven2/com/googlecode/jaitools/

# Available modules #

## General modules ##

| **Module** | **Description** |
|:-----------|:----------------|
| jt-demo    | Demo programs   |
| jt-jiffle  | The jiffle image scripting language |
| jt-kernel  | Factory and utility classes to work with KernelJAI objects |
| jt-utils   | Support classes including DiskMemTileCache, DiskMemImage and !ROIGeometry |

## Image operator modules ##

| **Module** | **Description** |
|:-----------|:----------------|
| jt-contour | Interpolates vector contours from a raster image |
| jt-kernelstats | Calculates neighbourhood statistics |
| jt-maskedconvolve | Extends the standard !JAI convolve operator with source and destination masking |
| jt-rangelookup | An image lookup operator that can handle both integral and floating point data types and work with value ranges |
| jt-regionalize | Identifies regions of uniform value, with a specified tolerance, in a source image |
| jt-vectorize | Traces the boundaries of regions with uniform value in an image and returns them as vector polygons |
| jt-vectorbinarize | Binarizes a source image and creates a destination image backed by a JTS Geometry rather than a raster |
| jt-zonalstats | Calculates statistics for values in a source image, optionally grouped by zones in a control image |

# Snapshot releases #

If you are brave (or have no choice) you can use the JAITools snapshot jars which are built from the latest code on trunk and the active development branch in the subversion repository. To do this you need to specify the JAITools snapshot repository in your project's POM:

```
    <repositories>
        <repository> 
            <id>jaitools-snapshots</id> 
            <name>jaitools snapshots</name> 
            <url>http://oss.sonatype.org/content/repositories/snapshots/</url> 
            <snapshots> 
                <enabled>true</enabled> 
            </snapshots> 
        </repository>
    </repositories>

    <!-- other stuff...  -->

    <dependencies>
        <!-- JAITools utilities -->
        <dependency>
            <groupId>org.jaitools</groupId>
            <artifactId>jt-utils</artifactId>
            <version>1.3-SNAPSHOT</version>
        </dependency>
    </dependencies>
```