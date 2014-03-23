package au.com.polly.mci;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * Palette where the entries are ordered by hue, then by saturation.
 * Nice in theory, but doesn't produce great results. Probably need to
 * weight the saturation more against the hue.
 *
 * @todo weight the hue against the saturation when determining order.
 *
 * @author Dave Young
 *
 */
public class HueSaturationPalette implements Palette
{
List<RGB> palette;

/**
 *
 * @param numberColours sort of irrelevant, only 32768 colours supported at the moment!!
 *
 *
 * Please use the PaletteFactory!! This method is only
 * intended to be invoked by the PaletteFactory.
 */
protected HueSaturationPalette(int numberColours)
{
    RGB colour;
    ArrayList<RGB> rawPalette = new ArrayList<RGB>();
    long a0,a1,a2;

    switch( numberColours ) {
        case 32768:
            a0 = System.currentTimeMillis();
            for ( int r = 0; r <= 0xF8; r += 8)
            {
                for ( int g = 0; g <= 0xF8; g += 8 )
                {
                    for( int b = 0; b <= 0xF8; b+= 8 )
                    {
                        colour = new RGB( r, g, b );
                        rawPalette.add( colour );
                    }
                }
            }
            a1 = System.currentTimeMillis();

            System.out.println( "We have " + rawPalette.size() + " palette entries." );
            // it is ok to perform a shallow copy, we don't want to make copies
            // of the RGB entries, just the order in which they are held
            // in the list...
            // ---------------------------------------------------------------
            List<RGB> sortedPalette = (ArrayList)rawPalette.clone();

            // now the magic bit...
            // --------------------------------
            Collections.sort( sortedPalette, new HueSaturationComparator() );

            a2 = System.currentTimeMillis();
            System.out.println( "Took " + ( a1 - a0 ) + "ms to create colours, " + ( a2 - a1 ) + "ms to sort them" );
            this.palette = sortedPalette;
            break;
        default:
            throw new IllegalArgumentException( "Only 32768 colour palette supported" );
    }
}

/**
 * Fulfill our contract to the Palette interface
 *
 * @param idx index into the palette
 * @return colour
 */
public RGB get(int idx)
{
    return this.palette.get( idx );
}

/**
 * Inner class providing HSL colour comparison with conversion code
 * borrowed from Rob Camick.
 *
 * Tucked this within inner class as the HSL colour space is not
 * relevant to the rest of this project. It is all contained within here.
 */
protected class HueSaturationComparator implements Comparator<RGB>
{

    public int compare( RGB a, RGB b )
    {
        int result = 0;
        HSL hslAlpha = new HSL( a.getRed(), a.getGreen(), a.getBlue() );
        HSL hslBeta = new HSL( b.getRed(), b.getGreen(), b.getBlue() );

        if ( hslAlpha.getHue() == hslBeta.getHue() )
        {
            result = hslAlpha.getSaturation() > hslBeta.getSaturation() ? 1 : -1;
        } else {
            result = hslAlpha.getHue() > hslBeta.getHue() ? 1 : -1;
        }

        return result;
    }

    /**
     * Inner class representing a HSL colour value.
     */
    protected class HSL
    {
        float h;
        float s;
        float l;

        public HSL( float h, float s, float l )
        {
            this.h = h;
            this.s = s;
            this.l = l;
        }

        /**
         *
         * @param red
         * @param green
         * @param blue
         * @return HSL colour representation
         *
         *
         * tip of the hat to Rob Camick
         * url: http://svn.codehaus.org/griffon/builders/gfxbuilder/tags/GFXBUILDER_0.1/gfxbuilder-core/src/main/com/camick/awt/HSLColor.java
         */
        protected HSL( int red, int green, int blue )
        {
            float r = (float)red;
            float g = (float)green;
            float b = (float)blue;

            //	Minimum and Maximum RGB values are used in the HSL calculations

            float min = Math.min(r, Math.min(g, b));
            float max = Math.max(r, Math.max(g, b));

            //  Calculate the Hue

            this.h = 0;

            if (max == min)
                this.h = 0;
            else if (max == r)
                this.h = ((60 * (g - b) / (max - min)) + 360) % 360;
            else if (max == g)
                this.h = (60 * (b - r) / (max - min)) + 120;
            else if (max == b)
                this.h = (60 * (r - g) / (max - min)) + 240;

            //  Calculate the Luminance

            this.l = (max + min) / 2;

            //  Calculate the Saturation

            this.s = 0;

            if (max == min)
                this.s = 0;
            else if (l <= .5f)
                this.s = (max - min) / (max + min);
            else
                this.s = (max - min) / (2 - max - min);

            this.s *= 100.0;
            this.l *= 100.0;
        }

        protected float getHue()
        {
            return this.h;
        }

        protected float getSaturation()
        {
            return this.s;
        }

        protected float getLuminance()
        {
            return this.l;
        }


    } // end-CLASS( HSV)
} //end-CLASS (HSVComparator)


/**
 * !!!!ONLY TO BE INVOKED BY UNIT TESTS!!!!!
 *
 *
 * @return
 */
protected List<RGB> __unit_test_backdoor_getUnderlyingList()
{
    return this.palette;
}

} // end-CLASS( HueSaturationPalette )

