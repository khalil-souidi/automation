package com.khalil.qa.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

/** Un WebDriver par thread (prêt pour l'exécution parallèle). Selenium Manager gère le driver. */
public final class DriverFactory {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {}

    public static WebDriver get() {
        if (DRIVER.get() == null) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--window-size=1920,1080", "--disable-search-engine-choice-screen",
                    "--incognito", "--disable-features=PasswordLeakDetection");
            // Coupe le gestionnaire de mots de passe : sa popup "mot de passe compromis" bloque la page
            options.setExperimentalOption("prefs", Map.of(
                    "credentials_enable_service", false,
                    "profile.password_manager_enabled", false,
                    "profile.password_manager_leak_detection", false));
            if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
                options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
            }
            DRIVER.set(new ChromeDriver(options));
        }
        return DRIVER.get();
    }

    public static void quit() {
        if (DRIVER.get() != null) {
            DRIVER.get().quit();
            DRIVER.remove();
        }
    }
}