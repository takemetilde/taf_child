package com.taf.utils;

import org.openqa.selenium.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.taf.auto.WebDriverUtil.driver;

//Class derived from WebDriverBase.java from memberportal-testautomation

public class WebDriverUtils {

    private long waitMillis = 3000;

    private long implicitWaitMillis = 5000;

    public static WebDriver getDriverInstance() {
        return com.taf.auto.WebDriverUtil.driver();
    }

    public static void abort(){
        com.taf.auto.WebDriverUtil.dispose();
    }

    /**
     * This method is used to sleep the current thread for any duration of time.
     *
     * @param milliseconds
     *            This is the amount of milliseconds the thread will sleep.
     */
    public static void handledSleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will wait for the page load to complete or until the timeOut
     * time has elapsed. A negative timeout can cause indefinite page loads.
     *
     * @param timeOut
     *            The timeout value in milliseconds.
     */
    public void setPageTimeout(int timeOut) {
        if (timeOut > 0) {
            driver().manage().timeouts().pageLoadTimeout(timeOut, TimeUnit.SECONDS);
        }
    }

    /**
     * This method returns the text of an element
     *
     * @param elem
     *            the element that you wish to retrieve the text from.
     * @return the string of text from the element.
     */
    public String getTextFromElement(WebElement elem) {
        return elem.getText();
    }

    /**
     * This method is used to select a value in the radio group by its value
     * This method needs to be refactored to use web elements.
     *
     * @param radioGroup
     *            the radio group to be selected from.
     * @param valueToSelect
     *            the value in the radio group that will be selected.
     */
    public void selectRadioButtonByValue(By radioGroup, String valueToSelect) {
        // Find the radio group element
        List<WebElement> radioLabels = driver().findElements(radioGroup);
        String trim = valueToSelect.trim();
        for (WebElement radioLabel : radioLabels) {
            if (radioLabel.getText().trim().equalsIgnoreCase(trim)) {
                radioLabel.click();
                break;
            }
        }
    }

    /**
     * This method clicks (Selects) an option passed in the parameter
     * ('ItemToSelect'), in the dropdown ('dropDownList') This method needs to
     * be refactored to use web elements.
     *
     * @param dropDownList
     *            the dropdown list to be expanded.
     * @param itemToSelect
     *            the item in the dropdown list to select.
     */
    public void selectItemInDropDown(By dropDownList, String itemToSelect) {
        WebElement dropDownElement = driver().findElement(dropDownList);
        List<WebElement> options = dropDownElement.findElements(By.tagName("li"));
        String toUpperCase = itemToSelect.toUpperCase();
        for (WebElement option : options) {
            if (option.getText().toUpperCase().contains(toUpperCase)) {
                option.click();
                break;
            }
        }
    }

    /**
     * This method can take a string formatted as a CSS selector and return a
     * web element that the selector finds.
     *
     * @param cssSelector
     *            the css string that will be searched for on the page.
     * @return the web element that is found.
     */
    public WebElement findElementByCssSelector(String cssSelector) {
        return driver().findElement(By.cssSelector(cssSelector));
    }

    public boolean hasElement(By by) {

        return countElements(by) != 0;

    }

    public boolean hasElement(SearchContext searchContext, By by) {

        return countElements(searchContext, by) != 0;

    }

    /**
     *
     *
     *
     * {@inheritDoc}
     *
     *
     *
     */
    public int countElements(By by) {

        return countElements(getDriverInstance(), by);

    }

    public int countElements(SearchContext searchContext, By by) {

        int result = 0;

        long currentWaitMillis = implicitWaitMillis;

        try {

            if (currentWaitMillis > 0) {

                driver().manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);

            }

            result = searchContext.findElements(by).size();

        } finally {

            if (currentWaitMillis > 0) {

                driver().manage().timeouts().implicitlyWait(waitMillis, TimeUnit.MILLISECONDS);

            }

        }

        return result;

    }

    public void resizeViewport(String deviceType){
        WebDriver.Window window = driver().manage().window();
        Dimension dimension = window.getSize();
        switch (deviceType.toLowerCase()){
            case "iphone6":
                dimension = new Dimension(375, 559);
                break;
        }
        window.setSize(dimension);
    }
}
