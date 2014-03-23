package au.com.polly.mci;

import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * Exercise the RGB class and ensure it behaves as expected.
 *
 * @author Dave Young
 */
@RunWith(JUnit4.class)
public class RGBTest
{

public static junit.framework.Test suite() { return new JUnit4TestAdapter( RGBTest.class ); }


private static int[] v = new int[]{ 0xE3, 0xC7, 0x42 };
private static int[] u = new int[]{ 0xE3, 0xC7, 0x42 };
private static int[] w = new int[]{ 0xE2, 0xC6, 0x43 };
private static int[] x = new int[]{ 0xE2, 0xC7, 0x42 };
private static int[] y = new int[]{ 0xE3, 0xC6, 0x42 };
private static int[] z = new int[]{ 0xE3, 0xC7, 0x43 };


@Test
public void testIntegerConstructor()
{
    RGB pixel = new RGB( 0xE3C742 );
    assertNotNull( pixel );
    assertEquals( 0xE3, pixel.getRed() );
    assertEquals( 0xC7, pixel.getGreen() );
    assertEquals( 0x42, pixel.getBlue() );

    pixel = new RGB( 0x000000 );
    assertNotNull( pixel );
    assertEquals( 0x00, pixel.getRed() );
    assertEquals( 0x00, pixel.getGreen() );
    assertEquals( 0x00, pixel.getBlue() );
}

@Test
public void testThreeArgConstructor() {
    RGB pixel = new RGB( v[0], v[1], v[2] );
    assertNotNull( pixel );
    assertEquals( v[0], pixel.getRed());
    assertEquals( v[1], pixel.getGreen());
    assertEquals( v[2], pixel.getBlue());
}

@Test
public void testArrayConstructor()
{
    RGB pixel = new RGB( v );
    assertNotNull( pixel );
    assertEquals( v[0], pixel.getRed());
    assertEquals( v[1], pixel.getGreen());
    assertEquals( v[2], pixel.getBlue());
}

@Test
public void testEquals()
{
    RGB alpha = new RGB( v );
    RGB beta = new RGB( u );

    assertNotNull( alpha );
    assertNotNull( beta );
    assertEquals( alpha, beta );

    beta = new RGB( w );
    assertNotNull( alpha );
    assertNotNull( beta );
    assertNotEquals( alpha, beta );


    beta = new RGB( x );
    assertNotNull( alpha );
    assertNotNull( beta );
    assertNotEquals( alpha, beta );


    beta = new RGB( y );
    assertNotNull( alpha );
    assertNotNull( beta );
    assertNotEquals( alpha, beta );


    beta = new RGB( z );
    assertNotNull( alpha );
    assertNotNull( beta );
    assertNotEquals(alpha, beta);
}

@Test
public void testCompareTo()
{
    RGB alpha = new RGB( 0x000000 );
    RGB beta = new RGB( 0x000000 );

    assertEquals( alpha, beta );
    assertEquals( 0, alpha.compareTo( beta ) );
    assertEquals( 0, beta.compareTo( alpha ) );

    alpha = new RGB( 0x000007 );
    assertNotEquals( alpha, beta );
    assertEquals( 1, alpha.compareTo( beta ) );
    assertEquals( -1, beta.compareTo( alpha ) );

    beta = new RGB( 0x000700 );
    assertNotEquals( alpha, beta );
    assertEquals( -1, alpha.compareTo( beta ) );
    assertEquals( 1, beta.compareTo( alpha ) );

    alpha = new RGB( 0x07, 0x00, 0x00 );
    assertNotEquals( alpha, beta );
    assertEquals( 1, alpha.compareTo( beta ) );
    assertEquals( -1, beta.compareTo( alpha ) );

    alpha = new RGB( 0x07, 0x07, 0x07 );
    beta = new RGB( 0x07, 0x07, 0x07 );
    assertEquals( alpha, beta );
    assertEquals( 0, alpha.compareTo( beta ) );
    assertEquals( 0, beta.compareTo( alpha ) );

    beta = new RGB( 0x08, 0x07, 0x06 );
    assertNotEquals( alpha, beta );
    assertEquals( -1, alpha.compareTo( beta ) );
    assertEquals( 1, beta.compareTo( alpha ) );


    beta = new RGB( 0x06, 0x07, 0x08 );
    assertNotEquals( alpha, beta );
    assertEquals( 1, alpha.compareTo( beta ) );
    assertEquals( -1, beta.compareTo( alpha ) );

}

}
