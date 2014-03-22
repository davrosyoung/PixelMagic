package au.com.polly.mci;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.awt.*;
import java.util.Map;
import java.util.Set;

/**
 * Created by dave on 21/03/2014.
 * Read an image and ensure that each and every pixel is a different colour...
 */
public class ImageVerifier
{
Map<Integer,Integer> colours = new HashMap<Integer,Integer>();
boolean duplicate = false;
BufferedImage image = null;
boolean interrogated = false;

/**
 *
 * @param image BufferedImage
 *
 * Obtains a verifier which can be used to check an image to ensure that each
 * pixel has a different value.
 */
public ImageVerifier( BufferedImage image )
{
    this.image = image;
    this.interrogated = false;
    this.interrogate();
}

/**
 * Will only run once for each image. Performs the actual analysis.
 */
public void interrogate()
{
    if ( interrogated )
    {
        return;
    }

    int width = image.getWidth();
    int height = image.getHeight();
    colours = new HashMap<Integer, Integer>();
    duplicate = false;
    for ( int x = 0; x < width; x++ ) {
        for (int y = 0; y < height; y++)
        {
            int pixel = image.getRGB( x, y );
            int count;
//            System.out.printf( "(%03d,%03d)=%X\n",x,y,pixel);
            if ( colours.containsKey( pixel ) )
            {
                count = colours.get( pixel ) + 1;
                duplicate = true;
            } else {
                count = 1;
            }
            colours.put( pixel, count );
        }
    }

}

/**
 *
 * @return whether or not each pixel in the image has a unqiue value or not.
 */
public boolean isEachPixelUnique() {
    return !duplicate;
}

protected List<RGB> getRGBColours() {
    return null;
}

public static void main(String[] argv)
{
    File file;
    BufferedImage image;
    ImageVerifier verifier;
    int exitCode = 0;

    if ( argv.length > 0 )
    {
        for ( int i = 0; i < argv.length; i++ )
        {
            String imageDirectory = MCIUtil.determineImageDirectory();

            try {
            file = MCIUtil.obtainFile( argv[ i ], imageDirectory );

                if ( file.exists() && file.canRead() && file.isFile() )
                {
                        image = ImageIO.read( file );
                        verifier = new ImageVerifier( image );
                        verifier.interrogate();
                        if ( verifier.isEachPixelUnique() )
                        {
                            System.out.println( "OK> Image \"" + argv[ i ] + "\" contains unique pixels" );
                        } else {
                            System.out.println( "FAIL> Image \"" + argv[ i ] + "\" contains duplicate pixels." );
                            exitCode = 1;
                        }
                        System.out.flush();
                } else {
                    System.err.println( "Unable to open file \"" + argv[ i ] + "\" for processing." );
                    System.err.flush();
                    exitCode = 1;
                }
            } catch( IOException ioe ) {
                System.err.println( "ERROR processing \"" + argv[ i ] + "\"" );
                System.err.println( ioe.getClass().getName() + " -  " + ioe.getMessage() );
                System.err.flush();
                exitCode = 1;
            }
        }
    }

    System.exit( exitCode );

    System.out.println( "Hello world" );
    System.exit( 0 );
}

}
