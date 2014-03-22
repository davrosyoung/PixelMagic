package au.com.polly.mci;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dave on 21/03/2014.
 */
public class MCIUtil {

    public static byte firstOctet(int victim) {
        return (getOctet(victim, 0));
    }

    public static byte secondOctet(int victim) {
        return (getOctet(victim, 0));
    }

    public static byte thirdOctet(int victim) {
        return (getOctet(victim, 0));
    }

    public static byte fourthOctet(int victim) {
        return (getOctet(victim, 0));
    }

    public static byte getOctet(int victim, int idx) {
        return 0;
    }


    public static void setPixel(BufferedImage image, int x, int y, RGB colour) {
        image.setRGB(x, y, colour.hashCode());
    }


    public static RGB getPixel(BufferedImage image, int x, int y) {
        RGB result = new RGB(image.getRGB(x, y));
        return result;
    }

/**
 *
 * @param i pixel column or row within image.
 * @return 5 bit value between 0x00 and 0xFF in multiples of 8.
 *
 */
public static int extractFiveBitValueAndScale( int i )
{
    // extract bits 2,3,4,5,6 and double to obtain multiple of 8 between 0x00 and 0xF8
    int result = ( i & 0x7C ) << 1;
    return result;
}

/**
 * Interrogate the specified filename and directory to obtain a file with the
 * correct path. Ignore the directory if the filename is absolute.
 *
 * @param filename name of the desired image file, either absolute or relative.
 * @param directory if specified, the location that the image file should be placed.
 * @return
 * @throws java.io.FileNotFoundException if a relative filename is specified but the specified directory
 * does not exist.
 */
public static File obtainFile( String filename, String directory ) throws FileNotFoundException, AccessDeniedException, NotDirectoryException
{
    File result = null;
    boolean absolute = false;
    String path = null;

    if ( filename == null )
    {
        throw new NullPointerException( "NULL filename specified" );
    }

    if ( filename.trim().length() == 0 )
    {
        throw new IllegalArgumentException( "Empty filename specified" );
    }

    absolute = filename.startsWith( File.separator );

    if ( ! absolute )
    {
        if ( directory == null )
        {
            throw new NullPointerException( "NULL directory specified for relative filename \"" + filename + "\"" );
        }

        if ( directory.trim().length() == 0 )
        {
            throw new IllegalArgumentException( "Empty directory specified for relative filename \"" + filename + "\"" );
        }

        File dirFile = new File( directory );
        if ( ! dirFile.exists() )
        {
            throw new FileNotFoundException( "Directory \"" + directory + "\" does not exist." );
        }

        if ( !dirFile.isDirectory() )
        {
            throw new NotDirectoryException( "Path \"" + directory + "\" does NOT specify a directory." );
        }

        if ( ! dirFile.canRead() || ! dirFile.canWrite() )
        {
            throw new AccessDeniedException( "Unable to read and/or write in directory \"" + directory + "\"" );
        }

        if ( directory.endsWith( File.separator ) )
        {
            path = directory + filename;
        } else {
            path = directory + File.separator + filename;
        }
    } else {
        path = filename;
    }

    result = new File( path );


    return result;
}


/**
 * Interrogate the system properties and environment for the best directory to place
 * image files... DIFFICULT TO TEST!?!
 *
 *
 * @return non-null value means we obtained a directory to place images into!!
 */
public static String determineImageDirectory()
{
    List<String> candidates = new ArrayList<String>();
    File directory;
    String result = null;

    // investigate the following;
    // system property image.dir
    // environment variable IMAGEDIR
    // ------------------------------
    if ( System.getProperties().containsKey( "image.dir" ) )
    {
        candidates.add( System.getProperty( "image.dir" ).trim() );
    }

    if ( ( System.getenv( "IMAGEDIR" ) != null ) && ( System.getenv( "IMAGEDIR" ).length() > 0 ) )
    {
        candidates.add( System.getenv( "IMAGEDIR" ).trim()  );
    }

    for ( String path : candidates )
    {
        directory = new File( path );
        if ( ( directory.isDirectory() ) && ( directory.canWrite() ) )
        {
            result = directory.getAbsolutePath();
            break;
        }
     }

    return result;
}


}

