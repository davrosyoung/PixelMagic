package au.com.polly.mci;


import java.util.Objects;

/**
 * Represents the red, green and blue components of a 24 bit colour pixel.
 * Primarily used to verify that an image is composed of unique colours!
 *
 * Created by dave on 21/03/2014.
 */
public class RGB
{
    protected final static int R=0;
    protected final static int G=1;
    protected final static int B=2;
    byte[] v = new byte[ 3 ];

    public RGB( byte red, byte green, byte blue )
    {
        v[ R ] = red;
        v[ G ] = green;
        v[ B ] = blue;
    }

    /**
     *
     * @param values three member byte array with elements containing R, G & B colour component values respectively.
     */
    public RGB( byte[] values )
    {
        this(values[0], values[1], values[2]);
    }

    /**
     *
     * @param hexValue 24 bit value with first octet containing RED, the second GREEN, the last octet BLUE
     */
    public RGB( int hexValue )
    {
        this((byte) ((hexValue >>>16 )& 0xff), (byte) ((hexValue >>>8) & 0xff), (byte) (hexValue & 0xff));
    }

    public byte getRed()
    {
        return this.v[ R ];
    }

    public byte getGreen()
    {
        return this.v[ G ];
    }

    public byte getBlue()
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

}
