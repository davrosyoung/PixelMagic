package au.com.polly.mci;

import junit.framework.JUnit4TestAdapter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

/**
 * Battery of tests for the proof of concept program
 *
 *
 */
@RunWith(JUnit4.class)
public class PocTest
{

public static junit.framework.Test suite() { return new JUnit4TestAdapter( PocTest.class ); }


@Test
/**
 * Test that we can use binary and to effectively mask out the three
 * least significant bits of a hex value as expected.
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

}
