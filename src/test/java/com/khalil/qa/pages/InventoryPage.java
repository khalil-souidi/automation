package com.khalil.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InventoryPage extends BasePage {
    private static final By TITLE = By.cssSelector("[data-test='title']");
    private static final By CART_BADGE = By.cssSelector("[data-test='shopping-cart-badge']");
    private static final By CART_LINK = By.cssSelector("[data-test='shopping-cart-link']");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return wait.until(ExpectedConditions.urlContains("inventory.html"))
                && visible(TITLE).getText().equals("Products");
    }

    /** "Sauce Labs Backpack" -> add-to-cart-sauce-labs-backpack */
    public void addToCart(String productName) {
        String id = "add-to-cart-" + productName.toLowerCase().replace(" ", "-");
        click(By.id(id));
    }

    public int cartCount() {
        return Integer.parseInt(visible(CART_BADGE).getText());
    }

    public void openCart() {
        click(CART_LINK);
    }
}
