package au.com.polly.mci;

import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * Created by dave on 21/03/2014.
 */
@RunWith(JUnit4.class)
public class RGBTest
{

public static junit.framework.Test suite() { return new JUnit4TestAdapter( RGBTest.class ); }


private static byte[] v = new byte[]{ (byte)0xE3, (byte)0xC7, (byte)0x42 };
private static byte[] u = new byte[]{ (byte)0xE3, (byte)0xC7, (byte)0x42 };
private static byte[] w = new byte[]{ (byte)0xE2, (byte)0xC6, (byte)0x43 };
private static byte[] x = new byte[]{ (byte)0xE2, (byte)0xC7, (byte)0x42 };
private static byte[] y = new byte[]{ (byte)0xE3, (byte)0xC6, (byte)0x42 };
private static byte[] z = new byte[]{ (byte)0xE3, (byte)0xC7, (byte)0x43 };


@Test
public void testIntegerConstructor()
{
    RGB pixel = new RGB( 0xE3C742 );
    assertNotNull( pixel );
    assertEquals( (byte)0xE3, pixel.getRed() );
    assertEquals( (byte)0xC7, pixel.getGreen() );
    assertEquals( (byte)0x42, pixel.getBlue() );
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

}
