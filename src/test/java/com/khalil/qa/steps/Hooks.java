package com.khalil.qa.steps;

import com.khalil.qa.utils.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    /** Capture d'écran attachée au rapport Cucumber si le scénario échoue. */
    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                byte[] png = ((TakesScreenshot) DriverFactory.get()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(png, "image/png", scenario.getName());
            }
        } finally {
            DriverFactory.quit();
        }
    }
}
