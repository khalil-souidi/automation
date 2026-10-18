@ui @checkout
Feature: Panier et commande

  Background:
    Given je suis connecté en tant que "standard_user"

  @smoke
  Scenario: Ajouter des produits au panier
    When j'ajoute les produits suivants au panier :
      | Sauce Labs Backpack   |
      | Sauce Labs Bike Light |
    Then le panier contient 2 articles

  @regression
  Scenario: Commande de bout en bout
    When j'ajoute les produits suivants au panier :
      | Sauce Labs Backpack |
    And je valide la commande avec :
      | prénom      | Khalil |
      | nom         | QA     |
      | code postal | 20000  |
    Then je vois le message de confirmation "Thank you for your order!"
