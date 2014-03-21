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
private final static int WIDTH = 32 * 8;
private final static int HEIGHT = 32 * 4;

public static void main( String[] argv )
{
    File imageFile = null;
    BufferedImage image = new BufferedImage( WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB );
    WritableRaster rasta = image.getRaster();
    long then = System.currentTimeMillis();
    for( int i = 0; i < WIDTH; i++ )
    {
        for ( int j = 0; j < HEIGHT; j++ )
        {
            int r = i & 0xf8;       // r only changes value every 8th column, just use the top 5 bits.
            int g = j & 0xfc;       // g only changes value every 4th row, just take the top 6 bits.
            int b = ( ( j & 0x03 ) << 6 ) + ( ( i & 0x07 ) << 2 ); // b varies within a 8x4 grid....
                                    // multiple the bottom 2 bits of the row by 8 and add the bottom
                                    // three bits of the column to it.

//            System.out.printf( "b=%03X\n", b );


            rasta.setPixel( i, j, new int[] { r, g, b } );
        }
    }
    long now = System.currentTimeMillis();
    System.out.println( "Took " + ( now - then ) + "ms to calculate." );

    String imageDirectory = null;

    if ( System.getenv().containsKey( "IMAGEDIR" ) )
    {
        imageDirectory = System.getenv( "IMAGEDIR" );
    } else {
        imageDirectory = System.getProperty( "image.dir" );
    }
    String path;

    try {
        if (argv.length > 0)
        {
            path = imageDirectory + File.separator + argv[ 0 ];
            try {
                imageFile = new File( path );
            } catch( Exception e ) {
                System.err.println( "Invalid file name \"" + path + "\"" );
            }
        }
        if ( imageFile == null )
        {
            path = imageDirectory + File.separator + DEFAULT_IMAGE_FILE_NAME;
            imageFile = new File( path );
        }
        ImageIO.write(image, "png", imageFile );
    } catch( Exception e ) {
        System.err.println( "OOOhhh Errr!!" + e.getClass().getName() + ":" + e.getMessage() + " - \"" + imageFile.getAbsolutePath() + "\"" );
        System.err.flush();
    }
    System.exit( 0 );
}

}
