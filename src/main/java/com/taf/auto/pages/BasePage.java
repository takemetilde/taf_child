package com.taf.auto.pages;

import com.taf.auto.FrameUtil;
import com.taf.auto.PageWaitUtil;
import com.taf.auto.page.AbstractPage;
import com.taf.utils.EmptyWebElement;
import com.taf.utils.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.taf.auto.PageUtil.handledSleep;
import static com.taf.auto.PageWaitUtil.setImplicitWait;
import static com.taf.auto.WebDriverUtil.driver;
import static com.taf.utils.ReflectionUtils.*;
import static com.taf.utils.WebDriverUtils.getDriverInstance;

public abstract class BasePage extends AbstractPage {

    //~ Instance Fields/Page Objects -----------------------------------------------------------------------------------

    private static final Logger LOG = LoggerFactory.getLogger(BasePage.class);
    private static final int WAIT_IN_SECONDS = 10;
    public static final int TWENTY_MILLISECONDS = 20;
    private static final String AJAX_LOAD = "ajax-load";

    //~ Constructors ---------------------------------------------------------------------------------------------------

    public BasePage() {
        PageFactory.initElements(getDriverInstance(), this);
        setImplicitWait(TWENTY_MILLISECONDS);
        /**
         * The BasePage constructor is primarily used to check if AngularJS is
         * on the page and if it is, wait until it has finished it's last
         * request up to 5 seconds(or as defined in the code below).
         */
        JavascriptExecutor executor = (JavascriptExecutor) getDriverInstance();
        getDriverInstance().manage().timeouts().setScriptTimeout(WAIT_IN_SECONDS, TimeUnit.SECONDS);
        String angularResult = "";

        try {
            angularResult = executor.executeScript("return angular.version;").toString();
        } catch (Exception e) {
            // AngularJS is not defined on the page.
        }
        if (!angularResult.contains("angular is not defined")) {
            long start = System.currentTimeMillis();
            try {
                executor.executeScript("var callback = arguments[arguments.length - 1];"
                        + "angular.element(document.body).injector().get('$browser').notifyWhenNoOutstandingRequests(callback);");
            } catch (Exception e) {
                // Reporter.log("AngularJS timed out.<br>");
            }
        }
        //Commented out DOM, JSUtil is accessible from base repo.
        //TODO: Try JSUtil.waitForStableDOM(5000); to replace above
//        this.dom = new DOM();
        handledSleep(500);
    }

    //~ Methods/Modules ------------------------------------------------------------------------------------------------

    public String getFullUrl() {
        return getDriverInstance().getCurrentUrl();
    }

    /**
     * Note: this will refresh the page if you are already on the page.
     *
     * @return - Current page.
     */
    public BasePage navigateToPage() {
        getDriverInstance().navigate().to(getFullUrl());

        return this;
    }

    /**
     * This method uses WebDriver's getCurrentUrl method to return the URL
     * currently in focus by the WebDriver.
     *
     * @return a string of the current URL.
     */
    public String getUrl() {
        return getCurrentUrl();
    }

    public static String getCurrentUrl() {
        return getDriverInstance().getCurrentUrl();
    }

    /**
     * This method returns the title of the current browser.
     *
     * @return the title string.
     */
    public static String getTitle() {
        return getDriverInstance().getTitle();
    }

    /**
     * This will verify if an element is displayed and if the element is not displayed instead of returning an exception
     * about the element not being found it will return false. This should allow for testers to reuse the same method for
     * positive and negative scenarios when checking for the existence of a page element.
     * @param element
     * @return
     */
    public boolean isElementPresent(WebElement element) {
        try {
            return element.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Same as the isElementPresent(WebElement element) method except instead we are allowing a by to be passed in instead.
     *
     * "This will verify if an element is displayed and if the element is not displayed instead of returning an exception
     * about the element not being found it will return false. This should allow for testers to reuse the same method for
     * positive and negative scenarios when checking for the existence of a page element."
     *
     * @param by
     * @return
     */
    public boolean isElementPresent(By by){
        try{
            driver().findElement(by);
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * This method will return the page source as you would see it in a web
     * browser.
     * <p>
     * This can be used to assert specific text exists in the page if the
     * location is not important.
     *
     * @return the page source
     */
    public String getPageSource() {
        return getDriverInstance().getPageSource();
    }

    public WebElement waitForHiddenElement(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriverInstance(), WAIT_IN_SECONDS);
            wait.ignoring(NoSuchElementException.class);
            wait.ignoring(StaleElementReferenceException.class);
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            WebElement element = getDriverInstance().findElement(by);
            return element;
        } catch (TimeoutException e) {
            return new EmptyWebElement(by);
        }
    }

    /**
     *
     * @param parent The WebElement that will be checked for sub element
     * @param childBy the "By" that we use to find sub elements
     * @return the child element or EmptyWebElement if none are found
     */
    protected WebElement getSubElement(WebElement parent, By childBy) {
        try {
            return parent.findElement(childBy);
        } catch(NoSuchElementException e){
            return new EmptyWebElement();
        }
    }

    /**
     *
     * @param parent The WebElement that will be checked for sub element
     * @param childBy the "By" that we use to find sub elements
     * @return the child element or EmptyWebElement if none are found
     */
    protected boolean hasSubElement(WebElement parent, By childBy) {
        return !(getSubElement(parent, childBy) instanceof EmptyWebElement);
    }

    public boolean compareLists(List<?> l1, List<?> l2) {
        // make a copy of the list so the original list is not changed, and remove() is supported
        ArrayList<?> cp = new ArrayList<>(l1);
        for (Object o : l2) {
            if (!cp.remove(o)) {
                return false;
            }
        }
        return cp.isEmpty();
    }

    /**
     * @return true if page refresh detected
     */
    public boolean expectPageRefresh(int timeOutInSeconds) {
        By by = defineUniqueElement();
        WebElement element = PageWaitUtil.waitForElementVisible(by);
        try {
            PageWaitUtil.waitForElementGone(by, timeOutInSeconds);
        } catch (TimeoutException e) {} // do nothing
        WebElement newElement = PageWaitUtil.waitForElementVisible(defineUniqueElement());
        return element.equals(newElement);
    }

    public void verifyIfWebElementIsDisplayed(AbstractPage page, String strIsOption, String strWebElement) throws Exception {
        Boolean b = isWrapperElementDisplayedOnPage(page, strWebElement);

        if (strIsOption.equals("is")) {
            Assert.assertTrue(b, "WebElement is not displayed: " + strWebElement);
        }
        else if (strIsOption.equals("is not")) {
            Assert.assertFalse(b, "WebElement is displayed: " + strWebElement);
        }
        LOG.info("The webelement {} displayed: {}. Test PASS.", strIsOption, strWebElement);
    }

    public void verifyEnteredTextInWebElement(AbstractPage page, String strText, String strWebElement) throws Exception {
        Boolean b = enterTextInWrapperElementOnPage(page, strText, strWebElement);
        Assert.assertTrue(b, "Text was entered incorrectly in WebElement: " + strText + ", " + strWebElement);
        LOG.info("The text \"{}\" was entered into the webelement: {}. Test PASS.", strText, strWebElement);
    }

    public void verifyOptionIsSelectedInWebElement(AbstractPage page, String strOption, String strWebElement) throws Exception {
        Boolean b = chooseOptionInWrapperElementOnPage(page, strOption, strWebElement);
        Assert.assertTrue(b, "Option was not selected in WebELement: " + strWebElement);
        LOG.info("The option {} was selected in the webelement: {}. Test PASS.", strOption, strWebElement);
    }

    public void verifyWebElementIsClicked(AbstractPage page, String strWebElement) throws Exception {
        Boolean b = clickOnWrapperElementOnPage(page, strWebElement);
        Assert.assertTrue(b, "WebElement was not clicked: " + strWebElement);
        LOG.info("The webelement {} was clicked. Test PASS", strWebElement);
    }

    public void switchToIframeOnPage(String fieldName) {
        String fieldNameFinal = "we" + StringUtils.convertSpaceDelimitedToPascalCase(fieldName);
        FrameUtil.switchTo(() -> {
            try {
                return getByFromFindBy(fieldNameFinal);
            } catch (NoSuchFieldException e) {
                return null;
            }
        });
        LOG.info("Switching to iframe webelement {}. Test PASS", fieldName);
    }
}