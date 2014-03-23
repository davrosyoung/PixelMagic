package au.com.polly.mci;

import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Created by dave on 21/03/2014.
 */
@RunWith(JUnit4.class)
public class ImageVerifierTest
{
public static junit.framework.Test suite() { return new JUnit4TestAdapter( ImageVerifierTest.class ); }
// the following should really be read from a property file!!
private static String uniqueSixteenPixelFilename = "unique4x4.png";
private static String duplicateSixteenPixelFilename = "duplicate4x4.png";
private static String uniqueSixteenPixelPath;       // full path to image file
private static String duplicateSixteenPixelPath;    // full path to image file.

@BeforeClass
static public void setup()
{
    String imageDirectory = MCIUtil.determineImageDirectory();
    System.out.println( "Obtained imageDirectory=\"" + imageDirectory + "\"" );
    assertNotNull( imageDirectory );
    uniqueSixteenPixelPath = imageDirectory + File.separator + uniqueSixteenPixelFilename;
    duplicateSixteenPixelPath = imageDirectory + File.separator + duplicateSixteenPixelFilename;
}

@Test
public void testUniqueSixteenPixelImage()
{
    BufferedImage image = null;
    File imageFile = new File( uniqueSixteenPixelPath );

    assertTrue( "Could not find \"" + imageFile.getAbsolutePath() + "\"" ,imageFile.exists() );
    assertTrue( "Could not read \"" + imageFile.getAbsolutePath() + "\"", imageFile.canRead() );
    assertTrue( "\"" + imageFile.getAbsolutePath() + "\" is not a file!!", imageFile.isFile() );
    try {
        image = ImageIO.read(imageFile);
    } catch( IOException ioe ) {
        fail( "Did not expect " + ioe.getClass().getName() + " - " + ioe.getMessage() + " for file \"" + imageFile.getAbsolutePath() + "\"" );
    }
    ImageVerifier verifier = new ImageVerifier( image );
    verifier.interrogate();
    assertTrue( verifier.isEachPixelUnique() );

}


@Test
public void testDuplicateSixteenPixelImage()
{
    BufferedImage image = null;
    File imageFile = new File( duplicateSixteenPixelPath );
    assertTrue( "Could not find \"" + imageFile.getAbsolutePath() + "\"" ,imageFile.exists() );
    assertTrue( "Could not read \"" + imageFile.getAbsolutePath() + "\"", imageFile.canRead() );
    assertTrue( "\"" + imageFile.getAbsolutePath() + "\" is not a file!!", imageFile.isFile() );
    try {
        image = ImageIO.read(imageFile);
    } catch( IOException ioe ) {
        fail( "Did not expect " + ioe.getClass().getName() + " - " + ioe.getMessage() + " for file \"" + imageFile.getAbsolutePath() + "\"" );
    }
    ImageVerifier verifier = new ImageVerifier( image );
    verifier.interrogate();
    assertFalse( verifier.isEachPixelUnique() );
}


}
