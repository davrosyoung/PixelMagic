package au.com.polly.mci;

import junit.framework.JUnit4TestAdapter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


import static org.junit.Assert.assertEquals;

/**
 * Battery of tests for the proof of concept program
 * and the MCI utilities class.
 *
 *
 */
@RunWith(JUnit4.class)
public class MCIUtilTest
{

public static junit.framework.Test suite() { return new JUnit4TestAdapter( MCIUtilTest.class ); }

private static String imageDirectoryPath;

@BeforeClass
public static void setup()
{
    imageDirectoryPath = MCIUtil.determineImageDirectory();

    System.out.println( "obtained imageDirectoryPath=[" + imageDirectoryPath + "]" );
    System.out.flush();
}


@Test
/**
 * Test that we can use binary and to effectively mask out the three
 * least significant bits of a hex value as expected. Just checking
 * that we know our java binary math syntax!!
 */
public void testFiveMostSignificantBits()
{
    assertEquals( 0x00, 0x01 & 0xf8 );
    assertEquals( 0x00, 0x02 & 0xf8 );
    assertEquals( 0x00, 0x03 & 0xf8 );
    assertEquals( 0x00, 0x04 & 0xf8 );
    assertEquals( 0x00, 0x05 & 0xf8 );
    assertEquals( 0x00, 0x06 & 0xf8 );
    assertEquals( 0x00, 0x07 & 0xf8 );
    assertEquals( 0x08, 0x08 & 0xf8 );
    assertEquals( 0x08, 0x09 & 0xf8 );
    assertEquals( 0x08, 0x0A & 0xf8 );
    assertEquals( 0x08, 0x0B & 0xf8 );
    assertEquals( 0x08, 0x0C & 0xf8 );
    assertEquals( 0x08, 0x0D & 0xf8 );
    assertEquals( 0x08, 0x0E & 0xf8 );
    assertEquals( 0x08, 0x0F & 0xf8 );
    assertEquals( 0x10, 0x10 & 0xf8 );
    assertEquals( 0x10, 0x11 & 0xf8 );
    assertEquals( 0x10, 0x17 & 0xf8 );
    assertEquals( 0x18, 0x18 & 0xf8 );
    assertEquals( 0x18, 0x19 & 0xf8 );
    assertEquals( 0x18, 0x1F & 0xf8 );
    assertEquals( 0x20, 0x20 & 0xf8 );
    assertEquals( 0x20, 0x21 & 0xf8 );
    assertEquals( 0x20, 0x26 & 0xf8 );
    assertEquals( 0x20, 0x27 & 0xf8 );
    assertEquals( 0x28, 0x28 & 0xf8 );
    assertEquals( 0xF8, 0xFF & 0xf8 );
    assertEquals( 0xF8, 0xF8 & 0xf8 );

}

/**
 * Test that the binary shift operators work as we expect them to...
 * ... we can use these operators to multiple or divide by powers of two.
 */
@Test
public void testBinaryShift()
{
    assertEquals( 0x00, 0x01 >>> 1 );
    assertEquals( 0x00, 0x03 >>> 2 );
    assertEquals( 0x01 , 0x03 >> 1 );
    assertEquals( 0x02 , 0x01 << 1 );
    assertEquals( 0x06 , 0x03 << 1 );
}

/**
 * Show that specifying a null filename results in a null pointer exception
 */
@Test(expected = NullPointerException.class )
public void testObtainNullFile()
{
    assertNotNull("Unable to obtain a working directory!!", imageDirectoryPath);
    try {
        MCIUtil.obtainFile(null, imageDirectoryPath);
    } catch( IOException fnfe ) {
        fail( "Did not expect " + fnfe.getClass().getName() + " - " + fnfe.getMessage() );
    }

}


/**
 * Show that specifying a null directory with a relative filename results in
 * a null pointer exception
 */
@Test(expected = NullPointerException.class )
public void testObtainRelativeDirectoryWithNullDirectory()
{
    assertNotNull("Unable to obtain a working directory!!", imageDirectoryPath);
    try {
        MCIUtil.obtainFile( null, imageDirectoryPath );
    } catch( IOException fnfe ) {
        fail( "Did not expect " + fnfe.getClass().getName() + " - " + fnfe.getMessage() );
    }

}


/**
 * Show that specifying a null directory with a valid absolute filename does
 * NOT result in an exception
 */
@Test
public void testObtainAbsoluteFileWithNullDirectory()
{
    File result = null;
    assertNotNull("Unable to obtain a working directory!!", imageDirectoryPath);
    try {
        result = MCIUtil.obtainFile( imageDirectoryPath + File.separator + "blah.png", null );
    } catch( IOException ioe ) {
        fail("Did not expect " + ioe.getClass().getName() + " - " + ioe.getMessage());
    }

    assertNotNull( result );
    assertEquals( imageDirectoryPath + File.separator + "blah.png", result.getAbsolutePath() );
}


/**
 * Ensure that an attempt to create a file in a non-extent directory fails as expected.
 */
@Test
public void testObtainFileFromNonexistentDirectory()
{
    assertNotNull("Unable to obtain a working directory!!", imageDirectoryPath);
    try {
        MCIUtil.obtainFile( "blah.png", "/WhatThe");
    } catch( IOException ioe ) {
        if ( ! ( ioe instanceof FileNotFoundException ) )
        {
            fail( "Did not expect " + ioe.getClass().getName() + " - " + ioe.getMessage() );
        }

    }

}

/**
 * Ensure that an empty filename fails as expected.
 */
@Test(expected = IllegalArgumentException.class)
public void testObtainFileEmptyFilename()
{
    assertNotNull("Unable to obtain a working directory!!", imageDirectoryPath);
    try {
        MCIUtil.obtainFile( "", imageDirectoryPath );
    } catch( IOException ioe ) {
        fail( "Did not expect " + ioe.getClass().getName() + " - " + ioe.getMessage() );
    }

}


/**
 * Ensure that if we specify an absolute filename, that we get the expected pathname.
 */
@Test
public void testObtainValidAbsoluteFilename()
{
    File result = null;
    assertNotNull("Unable to obtain a working directory!!", imageDirectoryPath);
    try {
        result = MCIUtil.obtainFile( imageDirectoryPath + File.separator + "blah.png", imageDirectoryPath );
    } catch( IOException ioe ) {
        fail("Did not expect " + ioe.getClass().getName() + " - " + ioe.getMessage());
    }

    assertNotNull( result );
    assertEquals( imageDirectoryPath + File.separator + "blah.png", result.getAbsolutePath() );
}

/**
 * Ensure that if we specify a relative filename, that we get the expected pathname.
 */
@Test
public void testObtainValidRelativeFilename()
{
    File result = null;
    assertNotNull("Unable to obtain a working directory!!", imageDirectoryPath);
    try {
        result = MCIUtil.obtainFile("blah.png", imageDirectoryPath);
    } catch( IOException ioe ) {
        fail("Did not expect " + ioe.getClass().getName() + " - " + ioe.getMessage());
    }
    assertNotNull( result );
    assertEquals( imageDirectoryPath + File.separator + "blah.png", result.getAbsolutePath() );

}

/**
 * Demonstrate that given an eight bit value, we take bits 2,3,4,5,6 to obtain a five bit value
 * and then we scale it (double it) such that;
 * 0x00 .. 0x03 remain as zero
 * 0x04 .. 0x07 becomes 0x80
 * 0x08 .. 0x0C becomes 0x10
 * 0x0d .. 0x0f becomes 0x18
 * ...
 * 0x78 .. 0x7C becomes 0xf0
 * 0x7D .. 0x7F becomes 0xf8
 *
 * so we are;
 * ignoring the bottom two bits (effectively dividing by 4)
 * ignoring the top bit.
 * taking the remaining bits and promoting them up by one bit.
 */
@Test
public void testExtractFiveBitValueAndScale()
{
    assertEquals( 0x00, MCIUtil.extractFiveBitValueAndScale( 0x00 ) );
    assertEquals( 0x00, MCIUtil.extractFiveBitValueAndScale( 0x01 ) );
    assertEquals( 0x00, MCIUtil.extractFiveBitValueAndScale( 0x03 ) );
    assertEquals( 0x08, MCIUtil.extractFiveBitValueAndScale( 0x07 ) );
    assertEquals( 0x18, MCIUtil.extractFiveBitValueAndScale( 0x0F ) );
    assertEquals( 0x18, MCIUtil.extractFiveBitValueAndScale( 0x0E ) );
    assertEquals( 0x38, MCIUtil.extractFiveBitValueAndScale( 0x1F ) );
    assertEquals( 0x38, MCIUtil.extractFiveBitValueAndScale( 0x1E ) );
    assertEquals( 0x38, MCIUtil.extractFiveBitValueAndScale( 0x1D ) );
    assertEquals( 0x38, MCIUtil.extractFiveBitValueAndScale( 0x1C ) );
    assertEquals( 0x30, MCIUtil.extractFiveBitValueAndScale( 0x1A ) );
    assertEquals( 0xF8, MCIUtil.extractFiveBitValueAndScale( 0x7D ) );
    assertEquals( 0xF8, MCIUtil.extractFiveBitValueAndScale( 0x7E ) );
    assertEquals( 0xF8, MCIUtil.extractFiveBitValueAndScale( 0x7F ) );
    assertEquals( 0x00, MCIUtil.extractFiveBitValueAndScale( 0x80 ) );
    assertEquals( 0x08, MCIUtil.extractFiveBitValueAndScale( 0x87 ) );

}

}
