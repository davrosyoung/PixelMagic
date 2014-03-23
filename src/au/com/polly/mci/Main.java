package au.com.polly.mci;

import java.io.File;
import java.util.Map;
import java.util.Properties;

/**
 * First class written, just outputs environment and property values.
 * POC class was used as the application with the main routine to
 * create the image file.
 *
 *
 * @author Dave Young
 */
public class Main {

    public static void main(String[] args) {
	    Map<String,String> environment = System.getenv();
        for( String key : environment.keySet() )
        {
            String value = environment.get( key );
            System.out.println( "env{" + key + "}=\"" + value + "\"" );
        }

        Properties props = System.getProperties();
        for( Object key : props.keySet() )
        {
            Object value = props.get( key );
            String text = value != null ? "\"" + value.toString() + "\"" : "<NULL>";
            System.out.println( "property{" + key + "}=" + text );
        }
        System.out.println( "File.separator=\"" + File.separator + "\"" );
        System.out.println( "File.pathSeparator=\"" + File.pathSeparator + "\"" );
        System.out.flush();
        System.exit( 0 );
    }
}
