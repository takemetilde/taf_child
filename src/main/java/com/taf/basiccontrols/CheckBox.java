package com.taf.basiccontrols;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CheckBox extends Button {

    public CheckBox(WebElement webElement) {
        super(webElement);
    }

    public CheckBox(By by) {
        super(by);
    }
}
