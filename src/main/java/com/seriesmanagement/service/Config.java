package com.seriesmanagement.service;

import com.seriesmanagement.SeriesManagementApplication;

import javax.ws.rs.core.Application;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class Config extends Application {

    private static final String PROPERTIES_PATH = "/var/www/html/json/seriesmanagement.properties";
    private static Properties properties = null;

    /**
     * define all provider classes
     *
     * @return set of classes
     */
    @Override
    public Set<Class<?>> getClasses() {

        HashSet providers = new HashSet<Class<?>>();
        providers.add(CategoryController.class);
        providers.add(SeriesController.class);
        providers.add(EpisodeController.class);
        providers.add(UserController.class);
        return providers;
    }

    /**
     * Gets the value of a property
     *
     * @param property the+++
     *  key of the property to be read
     * @return the value of the property
     */

    public static String getProperty(String property) {
        if (Config.properties == null) {
            setProperties(new Properties());
            readProperties();
        }
        String value = Config.properties.getProperty(property);
        if (value == null) return "";
        return value;
    }

    /**
     * reads the properties file
     */
    private static void readProperties() {

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(PROPERTIES_PATH);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {

            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }

        }

    }

    /**
     * Sets the properties
     *
     * @param properties the value to set
     */

    private static void setProperties(Properties properties) {
        Config.properties = properties;
    }
}
