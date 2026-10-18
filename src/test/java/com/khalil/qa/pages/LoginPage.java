package com.khalil.qa.pages;

import com.khalil.qa.utils.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private static final By USERNAME = By.id("user-name");
    private static final By PASSWORD = By.id("password");
    private static final By LOGIN_BTN = By.id("login-button");
    private static final By ERROR = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(Config.get("ui.baseUrl"));
        return this;
    }

    public void login(String user, String password) {
        type(USERNAME, user);
        type(PASSWORD, password);
        click(LOGIN_BTN);
    }

    public String errorMessage() {
        return visible(ERROR).getText();
    }
}
