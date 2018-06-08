package com.zjy;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {
    public static String getProperty(String name) {
        Properties prop = new Properties();
        InputStream is = ClassLoader.getSystemResourceAsStream("application.properties");
        String value = StringUtils.EMPTY;
        try {
            prop.load(is);
            value = prop.getProperty(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
}
