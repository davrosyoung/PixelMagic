package au.com.polly.mci;

/**
 * Hide the horrible complexities behind obtaining different
 * sorts of colour palettes behind convenience methods....
 */
public class PaletteFactory
{
    static Palette linearPalette = null;
    static Object linearPaletteLock = new java.util.Date();
    
    static Palette hueSaturationPalette = null;
    static Object hueSaturationPaletteLock = new java.util.Date();


    /**
     *
     * @return A nicely ordered colour palette holding RGB values spread
     * between zero and 0xF8 in steps of eight.
     */
    public Palette getLinearOrderedPalette()
    {
        // be thread-safe when creating the linear intensity palette,
        // it can take a while!!
        // ---------------------------------------------------------
        synchronized( linearPaletteLock )
        {
            if ( linearPalette == null )
            {
                linearPalette = new LinearIntensityPalette( 32768 );
            }
        }

        return linearPalette;
    }


    /**
     *
     * @return A palette of colours mangled using the HSl colour space.
     */
    public Palette getHueSaturationPalette()
    {
        // be thread-safe when creating the hue saturation palette,
        // it can take a while!!
        // ---------------------------------------------------------
        synchronized( hueSaturationPaletteLock )
        {
            if ( hueSaturationPalette == null )
            {
                hueSaturationPalette = new HueSaturationPalette( 32768 );
            }
        }

        return hueSaturationPalette;
    }
}
