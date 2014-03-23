// #(@) java application to create tiled image across r,g,b colour spectrum.
package au.com.polly.mci;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;

/**
 * <p>
 * Proof of concept java application. Outputs an image with 32768 pixels,
 * each of which is unique in colour with the colour palette spread out
 * uniformly across the RGB colour cube. The problem is how to represent
 * a 3 dimensional cube asthaetically pleasing manner. Iterating over two
 * colour axes (take your pick out of R,G & B) is simple enough. Introducing
 * a third colour axis in a pleasing way is not as trivial. This application
 * takes the approach of splitting the image into a left and right half.
 * Each half is further split into 32x32 tiles, each 4x4 pixels in size.
 * Each "tile" contains a uniform value of R & G with the B colour component
 * being "manually" distributed so that no harsh boundaries of the blue component
 * clash between tile boundaries.</p>
 * <p>
 *    The left half contains blue saturation between 0% (0x00) and 49% (0x7F).
 *    The right half contains blue saturation between 50% (0x87) and 99% (0xFF).
 *    In this manner, the underlying colour gradient between R=0,G=0 and R=0xFF,G=0xFF
 *    is not overwhelmed.
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
public class POC
{
private final static String DEFAULT_IMAGE_FILE_NAME = "POC.png";
private final static int WIDTH = 32 * 8;
private final static int HEIGHT = 32 * 4;


/**
 * Blue colour component mask within a 4x4 pixel tile to avoid
 * harsh boundaries between tiles. This is the less saturated component.
 * For the left hand half of the image.
 */
private final static int[][] leftMask = new int[][] {
    new int[] { 0x2F, 0x5F, 0x4F, 0x0F },
    new int[] { 0x3F, 0x7F, 0x6F, 0x17 },
    new int[] { 0x1F, 0x67, 0x77, 0x37 },
    new int[] { 0x07, 0x47, 0x57, 0x27 }
};

/**
 * Blue colour component mask within a 4x4 pixel tile to avoid
 * harsh boundaries between tiles. This is the more saturated component.
 * For the right hand half of the image
 */
private final static int[][] rightMask = new int[][] {
    new int[] { 0x9F, 0xDF, 0xD7, 0x87 },
    new int[] { 0xBF, 0xFF, 0xE7, 0xA7 },
    new int[] { 0xAF, 0xEF, 0xF7, 0xB7 },
    new int[] { 0x8F, 0xCF, 0xC7, 0x97 }
};


public static void main( String[] argv )
{
    int exitCode = 0;
    File imageFile = null;
    BufferedImage image = new BufferedImage( WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB );
    WritableRaster rasta = image.getRaster();
    long then = System.currentTimeMillis();

    // for each pixel column.....
    // -----------------------------
    for( int i = 0; i < WIDTH; i++ )
    {

        int r = determineRedComponent( i );

        // for each pixel row....
        // ------------------------
        for ( int j = 0; j < HEIGHT; j++ )
        {
            int g = determineGreenComponent( j );
            int b = determineBlueComponent( i, j );
            rasta.setPixel( i, j, new int[] { r, g, b } );
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


/**
 *
 * @param column pixel column within the image ( 0 <= column < WIDTH)
 * @return red component value between 0 and 255.
 */
protected static int determineRedComponent( int column )
{
    assert( column > 0 );
    assert( column < WIDTH );

    // utility function extracts appropriate five bits
    // from the pixel column value and scales it appropriately.
    // -----------------------------------------------------
    return MCIUtil.extractFiveBitValueAndScale( column );
}


/**
 *
 * @param row pixel row within the image ( 0 <= row < HEIGHT )
 * @return red component value between 0 and 255.
 */
protected static int determineGreenComponent( int row )
{
    assert( row > 0 );
    assert( row < HEIGHT );

    // utility function extracts appropriate five bits
    // from the pixel row value and scales it appropriately.
    // -----------------------------------------------------
    return MCIUtil.extractFiveBitValueAndScale( row );
}

/**
 *
 * @param i pixel column within image.
 * @param j pixel row within image.
 * @return blue colour component value between 0 and 255.
 *
 * Use one of the masks specified to obtain visually pleasing value for the blue
 * colour component for the specified pixel address.
 */
protected static int determineBlueComponent( int i, int j )
{
    // sanity check...
    // --------------------------------------------------
    assert( i >= 0 && i < WIDTH && j >= 0 && j < HEIGHT );

    int column = i & 0x03;      // blue mask is only 4x4 so use the bottom 2 bits of i & j for
    int row = j & 0x03;         // indexing...

    // interrogate either the left or right mask dependant upon the pixel horizontal address (i).
    // we have already reduced row and column to 0,0 ... 3,3, so just pluck out the value for blue.
    // ------------------------------------------------------------------------------------------
    int result = ( i < ( WIDTH / 2 ) ) ? leftMask[ row ][ column ] : rightMask[ row ][ column ];

    return result;
}


}
