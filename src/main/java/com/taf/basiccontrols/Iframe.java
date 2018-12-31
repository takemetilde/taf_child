package com.taf.basiccontrols;

import com.taf.auto.WebDriverUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

import static com.taf.auto.WebDriverUtil.runWithTempTimeout;

public class Iframe extends Displayable{

    protected WebElement element;

    public Iframe(WebElement webElement) {
        element = webElement;
    }

    public Iframe(By by) {
        try {
            element = runWithTempTimeout(1000, TimeUnit.MILLISECONDS, () -> WebDriverUtil.driver().findElement(by));
        } catch (Exception ex) {
            element = null;
        }
    }

    @Override
    public boolean isDisplayed() {
        try {
            return element.isDisplayed();
        } catch (Exception ex) {
            return false;
        }
    }
}
