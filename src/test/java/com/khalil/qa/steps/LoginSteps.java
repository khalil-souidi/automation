package com.khalil.qa.steps;

import com.khalil.qa.pages.InventoryPage;
import com.khalil.qa.pages.LoginPage;
import com.khalil.qa.utils.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {

    @Given("je suis sur la page de login")
    public void openLogin() {
        new LoginPage(DriverFactory.get()).open();
    }

    @When("je me connecte avec {string} et {string}")
    public void login(String user, String password) {
        new LoginPage(DriverFactory.get()).login(user, password);
    }

    @Given("je suis connecté en tant que {string}")
    public void loggedInAs(String user) {
        openLogin();
        login(user, "secret_sauce");
    }

    @Then("je vois la page des produits")
    public void productsPageDisplayed() {
        assertTrue(new InventoryPage(DriverFactory.get()).isDisplayed(), "La page Products n'est pas affichée");
    }

    @Then("je vois le message d'erreur {string}")
    public void errorDisplayed(String expected) {
        String actual = new LoginPage(DriverFactory.get()).errorMessage();
        assertTrue(actual.contains(expected), "Attendu : '" + expected + "' / Obtenu : '" + actual + "'");
    }
}
