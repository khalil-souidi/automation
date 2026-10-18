package com.khalil.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {
    private static final By CHECKOUT_BTN = By.id("checkout");
    private static final By FIRST_NAME = By.id("first-name");
    private static final By LAST_NAME = By.id("last-name");
    private static final By ZIP = By.id("postal-code");
    private static final By CONTINUE = By.id("continue");
    private static final By FINISH = By.id("finish");
    private static final By COMPLETE_HEADER = By.cssSelector("[data-test='complete-header']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void checkout(String firstName, String lastName, String zip) {
        click(CHECKOUT_BTN);
        type(FIRST_NAME, firstName);
        type(LAST_NAME, lastName);
        type(ZIP, zip);
        click(CONTINUE);
        click(FINISH);
    }

    public String confirmationMessage() {
        return visible(COMPLETE_HEADER).getText();
    }
}
