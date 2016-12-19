package com.andya.ilogostest;

import org.xml.sax.SAXException;

import java.sql.SQLException;

/**
 * Created by AndyA on 17.12.2016.
 */
public class CustomExceptionRootMessage {

    public static Throwable getCause(Throwable e) {
        Throwable cause = null;
        Throwable result = e;

        while(null != (cause = result.getCause())  && (result != cause) ) {
            result = cause;
        }
        return result;
    }
}
