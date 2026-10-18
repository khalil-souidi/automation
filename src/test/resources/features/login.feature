# language: en
@ui @login
Feature: Authentification SauceDemo
  En tant que client
  Je veux me connecter
  Afin d'accéder au catalogue

  Background:
    Given je suis sur la page de login

  @smoke
  Scenario: Connexion valide
    When je me connecte avec "standard_user" et "secret_sauce"
    Then je vois la page des produits

  @regression
  Scenario Outline: Connexion refusée - <cas>
    When je me connecte avec "<user>" et "<password>"
    Then je vois le message d'erreur "<message>"

    Examples:
      | cas                 | user            | password     | message                                     |
      | utilisateur bloqué  | locked_out_user | secret_sauce | Sorry, this user has been locked out.       |
      | mauvais identifiants| wrong_user      | wrong_pass   | Username and password do not match          |
      | username vide       |                 | secret_sauce | Username is required                        |
      | mot de passe vide   | standard_user   |              | Password is required                        |
