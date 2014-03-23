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
public class PaletteFactoryTest
{

public static junit.framework.Test suite() { return new JUnit4TestAdapter( PaletteFactoryTest.class ); }




@Test
public void testObtainingLinearPalette()
{
    PaletteFactory paletteFactory = new PaletteFactory();
    Palette paletteAlpha = paletteFactory.getLinearOrderedPalette();
    Palette paletteBeta = paletteFactory.getLinearOrderedPalette();

    assertNotNull( paletteAlpha );
    assertNotNull( paletteBeta );

    assertEquals( paletteAlpha, paletteBeta );

    for ( int i = 0; i < 100; i++ )
    {
        RGB blah = paletteAlpha.get( i );
        System.out.printf( "color(%03d): r=%02x g=%02x b=%02x\n", i, blah.getRed(), blah.getGreen(), blah.getBlue() );
    }

    RGB colour = paletteAlpha.get( 0 );
    assertEquals( 0x00, colour.getRed() );
    assertEquals( 0x00, colour.getGreen() );
    assertEquals( 0x00, colour.getBlue() );

    colour = paletteAlpha.get( 1 );
    assertEquals( 0, colour.getRed() );
    assertEquals( 0, colour.getGreen() );
    assertEquals( 8, colour.getBlue() );

    colour = paletteAlpha.get( 2 );
    assertEquals( 0, colour.getRed() );
    assertEquals( 8, colour.getGreen() );
    assertEquals( 0, colour.getBlue() );

    colour = paletteAlpha.get( 3 );
    assertEquals( 8, colour.getRed() );
    assertEquals( 0, colour.getGreen() );
    assertEquals( 0, colour.getBlue() );

    colour = paletteAlpha.get( 4 );
    assertEquals( 0, colour.getRed() );
    assertEquals( 0, colour.getGreen() );
    assertEquals( 16, colour.getBlue() );

    colour = paletteAlpha.get( 32766 );
    assertEquals( 0xF8, colour.getRed() );
    assertEquals( 0xF8, colour.getGreen() );
    assertEquals( 0xF0, colour.getBlue() );

    colour = paletteAlpha.get( 32767 );
    assertEquals( 0xF8, colour.getRed() );
    assertEquals( 0xF8, colour.getGreen() );
    assertEquals( 0xF8, colour.getBlue() );


}



}
