package au.com.polly.mci;

/**
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

    public RGB( byte[] values )
    {
        this(values[0], values[1], values[2]);
    }

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

    public boolean equals( Object other )
    {
        boolean result = false;
        if ( other instanceof RGB )
        {
            RGB oRGB = (RGB)other;
            result = getRed() == oRGB.getRed() && getBlue() == oRGB.getBlue() && getGreen() == oRGB.getGreen();
        }

        return result;
    }

}
