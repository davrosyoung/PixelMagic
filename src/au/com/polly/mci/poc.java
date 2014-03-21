package au.com.polly.mci;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

/**
 * Created by dave on 21/03/2014.
 */
public class poc
{
private final static String DEFAULT_IMAGE_FILE_NAME = "poc.png";


public static void main( String[] argv )
{
    File imageFile = null;
    BufferedImage image = new BufferedImage( 256, 256, BufferedImage.TYPE_INT_RGB );
    WritableRaster rasta = image.getRaster();
    long then = System.currentTimeMillis();
    for( int i = 0; i < 256; i++ )
    {
        for ( int j = 0; j < 256; j++ )
        {
 //           int r = ( i >>> 3 ) << 3;
 //           int g = ( j >>> 3 ) << 3;
 //           int b = ( j % 32 ) << 3;

            int r = i & 0xf8;
            int g = j & 0xf8;
            int b = ( j & 0x17 ) << 3;

            rasta.setPixel( i, j, new int[] { r, g, b } );
        }
    }
    long now = System.currentTimeMillis();
    System.out.println( "Took " + ( now - then ) + "ms to calculate." );

    try {
        if (argv.length > 0)
        {
            try {
                imageFile = new File( argv[ 0 ] );
            } catch( Exception e ) {
                System.err.println( "Invalid file name \"" + argv[ 0 ] + "\"" );
            }
        }
        if ( imageFile == null )
        {
            imageFile = new File( DEFAULT_IMAGE_FILE_NAME );
        }
        ImageIO.write(image, "png", imageFile );
    } catch( Exception e ) {
        System.err.println( "OOOhhh Errr!!" + e.getClass().getName() + ":" + e.getMessage() + " - \"" + imageFile.getAbsolutePath() + "\"" );
        System.err.flush();
    }
    System.exit( 0 );
}

}
