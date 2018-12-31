package com.taf.basiccontrols;

import com.taf.auto.WebDriverUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static com.taf.auto.WebDriverUtil.runWithTempTimeout;

public class Tout extends Displayable{

    private static final Logger LOG = LoggerFactory.getLogger(Tout.class);

    protected WebElement element;

    public Tout(WebElement webElement) {
        element = webElement;
    }

    public Tout(By by) {
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
        }
        catch (Exception ex) {
            return false;
        }
    }
}
