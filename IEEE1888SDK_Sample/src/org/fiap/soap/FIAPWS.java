

/**
 * FIAPWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.1  Built on : Oct 19, 2009 (10:59:00 EDT)
 */

    package org.fiap.soap;

    /*
     *  FIAPWS java interface
     */

    public interface FIAPWS {
          

        /**
          * Auto generated method signature
          * 
                    * @param queryRQ28
                
         */

         
                     public org.fiap.soap.QueryRS query(

                        org.fiap.soap.QueryRQ queryRQ28)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param queryRQ28
            
          */
        public void startquery(

            org.fiap.soap.QueryRQ queryRQ28,

            final org.fiap.soap.FIAPWSCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param dataRQ30
                
         */

         
                     public org.fiap.soap.DataRS data(

                        org.fiap.soap.DataRQ dataRQ30)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param dataRQ30
            
          */
        public void startdata(

            org.fiap.soap.DataRQ dataRQ30,

            final org.fiap.soap.FIAPWSCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    