package au.com.polly.mci;

import java.util.ArrayList;
import java.util.Collections;
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
public class LinearIntensityPalette implements Palette
{
    List<RGB> palette;

protected LinearIntensityPalette( int numberColours )
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
            Collections.sort( sortedPalette );

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

}
