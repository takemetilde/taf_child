package com.taf.basiccontrols;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by AF41814 on 10/13/2017.
 */
public class TextBox extends Text {

    public TextBox(WebElement webElement) {
        super(webElement);
    }

    public TextBox(By by) {
        super(by);
    }

    @Override
    public boolean isDisplayed() {
        try {
            return element.isDisplayed();
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isEnabled() {
        try {
            return element.isEnabled();
        } catch (Exception ex) {
            return false;
        }
    }

    //TODO: Add text validation after sending text
    public boolean enterText(String text) {
        if (isDisplayed() && isEnabled()) {
            element.clear();
            element.sendKeys(text);
            return true;
        }
        else return false;
    }
}
