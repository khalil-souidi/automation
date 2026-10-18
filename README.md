# QA Automation Lab

![CI](https://github.com/<TON_USER>/qa-automation-lab/actions/workflows/ci.yml/badge.svg)

Projet d'automatisation de tests : **UI** (Selenium + Cucumber/Gherkin), **API** (RestAssured + Postman/Newman) et **CI/CD** (GitHub Actions).

| Couche | Outils | Cible |
|---|---|---|
| UI | Selenium 4, Cucumber 7, JUnit 5, Page Object Model | https://www.saucedemo.com |
| API (code) | RestAssured | https://restful-booker.herokuapp.com |
| API (collection) | Postman, Newman | https://restful-booker.herokuapp.com |
| CI/CD | GitHub Actions (push, PR, nightly, lancement manuel) | — |

## Prérequis

- Java 17, Maven 3.9+
- Google Chrome (le driver est téléchargé automatiquement par Selenium Manager)
- Node.js 20+ pour Newman : `npm install -g newman newman-reporter-htmlextra`

## Lancer les tests

```bash
mvn test                                    # tout (UI + API RestAssured)
mvn test -Papi                              # API RestAssured uniquement
mvn test -Pui                               # UI avec navigateur visible
mvn test -Pui -Dheadless=true               # UI sans fenêtre
mvn test -Pui -Dcucumber.filter.tags="@smoke"
mvn test -Pui -Dcucumber.filter.tags="@regression and not @checkout"

./scripts/run-tests.sh                      # tout, y compris Postman
./scripts/run-tests.sh ui @smoke
./scripts/run-tests.sh postman
```

## Rapports

| Rapport | Emplacement |
|---|---|
| Cucumber HTML (avec screenshots en cas d'échec) | `target/cucumber-report.html` |
| RestAssured / JUnit | `target/surefire-reports/` |
| Newman HTML | `target/newman-report.html` |

En CI, ces rapports sont téléchargeables dans l'onglet **Actions → run → Artifacts**.

## Structure

```
src/test/java/com/khalil/qa/
├── api/          Tests RestAssured
├── pages/        Page Objects (BasePage, LoginPage, InventoryPage, CheckoutPage)
├── runners/      Runner Cucumber
├── steps/        Step definitions + Hooks (screenshot si échec)
└── utils/        Config (properties, -D, variables d'env) et DriverFactory
src/test/resources/
├── features/     Scénarios Gherkin
└── config.properties
postman/          Collection et environnement Postman
scripts/          Script d'exécution local
.github/workflows CI GitHub Actions
```

## Configuration

Toute valeur de `config.properties` peut être surchargée :
- `-Dui.baseUrl=https://...` en ligne de commande
- variable d'environnement `UI_BASEURL=https://...` (pratique en CI avec les GitHub Secrets)

## Conventions

- Aucun `findElement` dans les steps : tout passe par les Page Objects.
- Attentes explicites uniquement, jamais de `Thread.sleep`.
- Tags : `@smoke` (critique, rapide), `@regression` (complet), `@ui`, `@api`.
- Chaque scénario est indépendant et crée ses propres données.
