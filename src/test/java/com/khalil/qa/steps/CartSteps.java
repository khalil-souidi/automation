package com.khalil.qa.steps;

import com.khalil.qa.pages.CheckoutPage;
import com.khalil.qa.pages.InventoryPage;
import com.khalil.qa.utils.DriverFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartSteps {

    @When("j'ajoute les produits suivants au panier :")
    public void addProducts(DataTable table) {
        InventoryPage inventory = new InventoryPage(DriverFactory.get());
        table.asList().forEach(inventory::addToCart);
    }

    @Then("le panier contient {int} article(s)")
    public void cartContains(int expected) {
        assertEquals(expected, new InventoryPage(DriverFactory.get()).cartCount());
    }

    @When("je valide la commande avec :")
    public void checkout(DataTable table) {
        Map<String, String> data = table.asMap();
        new InventoryPage(DriverFactory.get()).openCart();
        new CheckoutPage(DriverFactory.get()).checkout(data.get("prénom"), data.get("nom"), data.get("code postal"));
    }

    @Then("je vois le message de confirmation {string}")
    public void confirmation(String expected) {
        assertEquals(expected, new CheckoutPage(DriverFactory.get()).confirmationMessage());
    }
}
