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
 *
 *
 * Tuck lengthy and complex code here. All static helper functions.
 *
 * @author Dave Young
 */
public class MCIUtil
{


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

//    System.out.println( "MCIUtil::obtainFile()> Invoked with filename=\"" + filename + "\", directory=\"" + directory + "\"" );
//    System.out.flush();

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

//    System.out.println( "MCIUtil::obtainFile()> returns with result=\"" + result.getAbsolutePath() + "\"" );
//    System.out.flush();

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
    // current working directory with /images appended.
    // --------------------------------------------------
    if ( System.getProperties().containsKey( "image.dir" ) )
    {
        candidates.add( System.getProperty( "image.dir" ).trim() );
    }

    if ( ( System.getenv( "IMAGEDIR" ) != null ) && ( System.getenv( "IMAGEDIR" ).length() > 0 ) )
    {
        candidates.add( System.getenv( "IMAGEDIR" ).trim()  );
    }

    if ( System.getProperties().containsKey( "user.dir" ) )
    {
        candidates.add( System.getProperty( "user.dir" ).trim() + File.separator + "images" );
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

//    System.out.println( "MCIUtil::determineImageDirectory()> returns with result=\"" + result + "\"" );

    return result;
}


}

