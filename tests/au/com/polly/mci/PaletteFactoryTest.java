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


/**
 * Sanity check on ensuring that the linearly orderred palette is
 * what we expect and that if we ask for a palette twice, we get
 * the same instance!!
 */
@Test
public void testObtainingLinearPalette()
{
    PaletteFactory paletteFactory = new PaletteFactory();
    Palette paletteAlpha = paletteFactory.getLinearOrderedPalette();
    Palette paletteBeta = paletteFactory.getLinearOrderedPalette();

    assertNotNull( paletteAlpha );
    assertNotNull( paletteBeta );

    assertEquals( paletteAlpha, paletteBeta );  // field-wise comparison
    assertTrue( paletteAlpha == paletteBeta );  // same reference

    assertEquals( 32768, ((LinearIntensityPalette)paletteAlpha).__unit_test_backdoor_getUnderlyingList().size() );
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

/**
 * Sanity check on obtaining a hue saturation palette.
 * Complex beast to check value for unless you have a good
 * working knowledge of the HSL colour space.
 */
@Test
public void setGettingHueSaturationPalette()
{
    PaletteFactory paletteFactory = new PaletteFactory();
    Palette palette = paletteFactory.getHueSaturationPalette();

    assertNotNull( palette );

    assertEquals( 32768, ((HueSaturationPalette)palette).__unit_test_backdoor_getUnderlyingList().size() );
}

}
