package com.taf.basiccontrols;

import com.taf.auto.JSUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Link extends Text {

    public Link(WebElement webElement) {
        super(webElement);
    }

    public Link(By by) {
        super(by);
    }

    public Boolean click() {
        try {
            if (isDisplayed()) {
                element.click();
                JSUtil.waitForStableDOM(5000);
            }
            return true;
        } catch (Exception exp) {
            exp.printStackTrace();
            return false;
        }
    }
}
