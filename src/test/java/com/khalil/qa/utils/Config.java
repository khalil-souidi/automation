package com.khalil.qa.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** Lit config.properties ; une variable système (-Dkey=value) ou d'env surcharge la valeur. */
public final class Config {
    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            PROPS.load(in);
        } catch (IOException | NullPointerException e) {
            throw new IllegalStateException("config.properties introuvable", e);
        }
    }

    private Config() {}

    public static String get(String key) {
        String sys = System.getProperty(key);
        if (sys != null) return sys;
        String env = System.getenv(key.toUpperCase().replace('.', '_'));
        if (env != null) return env;
        return PROPS.getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
