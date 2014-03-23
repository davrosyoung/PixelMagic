package au.com.polly.mci;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by dave on 23/03/2014.
 *
 *
 * Palette where we start with the darkest colours and end up with the
 * brightest.
 *
 * 0x000000
 * 0x000001
 * 0x000100
 * 0x010000
 * 0x000101
 * 0x010001
 * 0x010100
 * 0x010101
 * ...
 * 0xFFFFFF
 *
 */
public class HueSaturationPalette implements Palette
{
    List<RGB> palette;

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

public RGB get(int idx)
{
    return this.palette.get( idx );
}


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
} // end-CLASS( HueSaturationPalette )

