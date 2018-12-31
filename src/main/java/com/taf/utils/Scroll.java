package com.taf.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import static com.taf.utils.WebDriverUtils.getDriverInstance;

/**
 * Created by AF41814 on 10/16/2017.
 */
public class Scroll {

    public static void toCenter(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriverInstance();
        String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";
        js.executeScript(scrollElementIntoMiddle, element);
    }

    public void scrollToTopOfPage() {
        JavascriptExecutor js = (JavascriptExecutor) getDriverInstance();
        js.executeScript("window.scrollTo(0,0)");
    }


    /**
     * @deprecated - Should be moved to shared library.
     */
    public void scrollToBottomOfPage() {
        JavascriptExecutor js = (JavascriptExecutor) getDriverInstance();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }
}
