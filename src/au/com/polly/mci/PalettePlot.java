// #(@) java application to create tiled image across r,g,b colour spectrum.
package au.com.polly.mci;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;

/**
 * <p>
 * Use a pregenerated palette to output a different colour to each pixel.
 * </p>
 * <p>
 *     Usage: takes output filename from the command line.
 * </p>
 *
 * This program code is not intended for serious commercial use, but for
 * demonstration purposes only. Please use and enjoy, but at your own peril!
 *
 *
 * @Author Dave Young
 */
public class PalettePlot
{
private final static String DEFAULT_IMAGE_FILE_NAME = "POC.png";
private final static int WIDTH = 32 * 8;
private final static int HEIGHT = 32 * 4;

public static void main( String[] argv )
{
    int exitCode = 0;
    File imageFile = null;
    BufferedImage image = new BufferedImage( WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB );
    WritableRaster rasta = image.getRaster();
    long then = System.currentTimeMillis();
    Palette palette = new PaletteFactory().getHueSaturationPalette();

    // for each pixel column.....
    // -----------------------------
    for( int i = 0; i < WIDTH; i++ )
    {

        // for each pixel row....
        // ------------------------
        for ( int j = 0; j < HEIGHT; j++ )
        {
            int idx = ( j * WIDTH ) + i;
            RGB colour = palette.get( idx );

            rasta.setPixel( i, j, new int[] { colour.getRed(), colour.getGreen(), colour.getBlue() } );
        }
    }
    long now = System.currentTimeMillis();
    System.out.println( "Took " + ( now - then ) + "ms to render image." );

    String imageDirectory = MCIUtil.determineImageDirectory();
    try {
        // If an image filename was specified on the command line, then use that,
        // otherwise use the default image filename..
        // -----------------------------------------------------------------------
        imageFile = MCIUtil.obtainFile( argv.length > 0 ? argv[0] : DEFAULT_IMAGE_FILE_NAME , imageDirectory );
        ImageIO.write(image, "png", imageFile );
        System.out.println( "OK> Just wrote out image to \"" + imageFile.getAbsolutePath() + "\"" );
    } catch( Exception e ) {
        System.err.println( "OOOhhh Errr!!" + e.getClass().getName() + ":" + e.getMessage() );
        if ( imageFile != null )
        {
            System.err.println( "file  - \"" + imageFile.getAbsolutePath() + "\"" );
        }
        System.err.flush();
        exitCode = 1;
    }

    System.exit( exitCode );    // allows application invoker to determine status by process return value. ($? in unix shell).
}




}
