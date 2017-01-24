package com.erian.microgrid.webapp;
/**
 * 
 * function description： Constants used in app i.e. api urls 
 *
 * @author <a href="mailto:zhuyb@ntu.edu.sg">zhuyuanbo </a>
 * @version v 1.0 
 * Create:  Sep 8, 2016 11:40:23 AM
 */
public final class Constants {

    /**
     * prefix of REST API
     */
    public static final String URI_API = "/api";

    public static final String URI_DEVICE_API = "/devices";

    public static final String URI_DEVICES = "/devices1";
    
    public static final String BASE_URL = "http://172.21.76.189/MicrogridApi";
    
    private Constants() {
        throw new InstantiationError( "Must not instantiate this class" );
    }
    
}
