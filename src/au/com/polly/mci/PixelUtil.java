package au.com.polly.mci;

import java.awt.image.BufferedImage;

/**
 * Created by dave on 21/03/2014.
 */
public class PixelUtil
{

public static byte firstOctet( int victim )
{
    return( getOctet( victim, 0 ) );
}

public static byte secondOctet( int victim )
{
    return( getOctet( victim, 0 ) );
}

public static byte thirdOctet( int victim )
{
    return( getOctet( victim, 0 ) );
}

public static byte fourthOctet( int victim )
{
    return( getOctet( victim, 0 ) );
}

public static byte getOctet( int victim, int idx )
{
    return 0;
}


public static void setPixel( BufferedImage image, int x, int y, RGB colour )
{
    image.setRGB( x, y, colour.hashCode() );
}


public static RGB getPixel( BufferedImage image, int x, int y )
{
    RGB result = new RGB( image.getRGB( x, y ) );
    return result;
}


}
