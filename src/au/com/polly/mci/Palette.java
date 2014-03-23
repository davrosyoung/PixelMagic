package au.com.polly.mci;

import java.util.List;

/**
 * Implemented by classes which enable an RGB colour to be selected by
 * a simple index into the palette. Specifics of the palette to be
 * documented within the appropriate implementation class.
 *
 * @author Dave Young
 */
public interface Palette
{
public RGB get( int idx );
}
