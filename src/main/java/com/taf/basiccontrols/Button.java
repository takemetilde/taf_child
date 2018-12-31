package com.taf.basiccontrols;

import com.taf.auto.PageWaitUtil;
import com.taf.utils.Scroll;
import com.taf.utils.Wait.Wait;
import com.taf.utils.Wait.WebElementCondition;
import com.taf.utils.WebDriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Button extends Displayable{

    private WebElement element;

    public Button(WebElement webElement) {
        element = webElement;
    }

    public Button(By by) {
        element = WebDriverUtils.getDriverInstance().findElement(by);;
    }

    @Override
    public boolean isDisplayed() {
        try {
            PageWaitUtil.waitForElementVisible(element, 5);
            return element.isDisplayed();
        }
        catch (Exception ex) {
            return false;
        }
    }

    public boolean click() {
        if(isDisplayed()) {
            Scroll.toCenter(element);
            Wait.For(WebElementCondition.isEnabled(element));
            element.click();
            return true;
        }
        else {
            throw new RuntimeException("Button is not visible");
        }
    }
}
