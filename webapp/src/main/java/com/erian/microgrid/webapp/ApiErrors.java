
package com.erian.microgrid.webapp;

/**
 *
 * @author Rambo Zhu<asybzhu@gmail.com>
 */
public final class ApiErrors {
	
    private static final String PREFIX = "errors.";

    public static final String INVALID_REQUEST = PREFIX + "INVALID_REQUEST";
    
    private ApiErrors() {
        throw new InstantiationError( "Must not instantiate this class" );
    }
}
