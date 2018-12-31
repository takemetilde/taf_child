package com.taf.basiccontrols;

import com.taf.auto.WebDriverUtil;
import com.taf.utils.Wait.Condition;
import com.taf.utils.Wait.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by AF41814 on 9/28/2017.
 */
public class ActivatableControl extends Displayable{
    final private WebElement element;

    public ActivatableControl(WebElement webElement) {
        element = webElement;
    }

    public ActivatableControl(By by) {
        element = by.findElement(WebDriverUtil.driver());
    }

    @Override
    public boolean isDisplayed() {
        try {
            return element.isDisplayed();
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isActive() {
        try {
            return element.getAttribute("class").contains("active");
        } catch (Exception ex) {
            return false;
        }
    }

    public void activate() {
        if (!isActive()) {
            click();
        }
        if (Wait.For(new Condition(o -> isActive()))) {
            throw new RuntimeException("Failed to activate control");
        }
    }

    public void click() {
        if (isDisplayed()) {
            element.click();
        } else {
            throw new RuntimeException("Control is not displayed");
        }
    }
}
