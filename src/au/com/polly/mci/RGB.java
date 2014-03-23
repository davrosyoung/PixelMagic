package au.com.polly.mci;


import java.util.Objects;

/**
 * Represents the red, green and blue components of a 24 bit colour pixel.
 * Primarily used to verify that an image is composed of unique colours!
 *
 * Because of the silliness of signed bytes in java, its no more expensive and
 * a lot simpler to just store the bytes in int member fields!!
 *
 * Created by dave on 21/03/2014.
 */
public class RGB implements Comparable<RGB>
{
    protected final static int R=0;
    protected final static int G=1;
    protected final static int B=2;
    int[] v = new int[ 3 ];

    public RGB( int red, int green, int blue )
    {
        v[ R ] = red;
        v[ G ] = green;
        v[ B ] = blue;
    }

    /**
     *
     * @param values three member byte array with elements containing R, G & B colour component values respectively.
     */
    public RGB( int[] values )
    {
        this(values[0], values[1], values[2]);
    }

    /**
     *
     * @param hexValue 24 bit value with first octet containing RED, the second GREEN, the last octet BLUE
     */
    public RGB( int hexValue )
    {
        this( (hexValue >>>16 ) & 0xff, (hexValue >>> 8) & 0xff, hexValue & 0xff );
    }

    public int getRed()
    {
        return this.v[ R ];
    }

    public int getGreen()
    {
        return this.v[ G ];
    }

    public int getBlue()
    {
        return this.v[ B ];
    }

    /**
     *
     * @param other the other RGB colour pixel to compare against this one.
     * @return
     */
    @Override
    public boolean equals( Object other )
    {
        boolean result = false;
        if ( other instanceof RGB )
        {
            RGB oRGB = (RGB)other;
            result = ( getRed() == oRGB.getRed() ) && ( getBlue() == oRGB.getBlue() ) && ( getGreen() == oRGB.getGreen() );
        }

        return result;
    }

    /**
     * Two RGB objects with exactly the same pixel component values should
     * return the very same hashcode!!
     *
     * @return
     */
    @Override
    public int hashCode()
    {
        int result;

        result = ( this.v[ R ] << 16 ) + ( this.v[ B ] << 8 ) + this.v[ G ];

        return result;
    }

    /**
     *
     * @param other the other colour to compare this colour to.
     * @return -1 if less than, 1 if greater than, 0 if equal to
     *
     * Provides a means of ordering colours from darkest to lightest, with
     * an arbitrary decision to rank red higher than green and green higher than
     * blue if the sum of intensities is otherwise equal.
     *
     */
    public int compareTo( RGB other )
    {
       int sum = getBlue() + getGreen() + getRed();
       int otherSum = other.getBlue() + other.getGreen() + other.getRed();
       int result = 0;

       if ( sum == otherSum )
       {
           // only bother comparing actual colour component intensities if
           // the sum of the intensities is the same!!
           // --------------------------------------------------------
            if ( getRed() != other.getRed() )
            {
                result = getRed() > other.getRed() ? 1 : -1;
            } else {
                // only force a 1,-1 result if the green values are different,
                // otherwise, by virtue of the fact that the sum of the
                // components is equal and R & G are equal, so will be blue!!
                // -------------------------------------------------------
                if ( getGreen() != other.getGreen() )
                {
                    result = getGreen() > other.getGreen() ? 1 : -1;
                }
            }
       } else {
           // sum of the intensities will do for ordering purposes...
           // -------------------------------------------------------
           result = sum > otherSum ? 1 : -1;
       }

       return result;
    }

}
