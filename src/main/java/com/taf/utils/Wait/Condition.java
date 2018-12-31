package com.taf.utils.Wait;

import com.taf.basiccontrols.Displayable;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class Condition implements Function<Object, Boolean> {
    private static final Logger LOG = LoggerFactory.getLogger(Condition.class);
    private Function<Object, Boolean> condition;
    protected String name;

    public Condition(Function<Object, Boolean> cond) {
        condition = cond;
    }

    public Boolean apply() {
        return condition.apply(null);
    }

    @Override
    public Boolean apply(Object o) {
        return condition.apply(o);
    }


    @Override
    public String toString() {
        return name != null ? name : condition.toString();
    }

    public static Condition pageLoad(WebDriver webDriver) {
        return new Condition(o -> {
            try {
                LOG.debug("Checking if page is loaded...");
                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                boolean jQueryActive = js.executeScript("return jQuery.active").toString().equals("0");
                LOG.debug("jQuery active:'" + jQueryActive + "'");
                boolean documentReady = js.executeScript("return document.readyState").toString().equals("complete");
                LOG.debug("document ready:'" + documentReady + "'");
                return jQueryActive && documentReady;
            } catch (Exception ex) {
                LOG.debug(ex.getMessage());
                return false;
            }
        });
    }

    public static Condition alertIsDisplayed(WebDriver webDriver) {
        Condition condition = new Condition(o -> {
            try {
                webDriver.switchTo().alert();
                return true;
            } catch (Exception ex) {
                return false;
            }
        });
        condition.name = "alertIsDisplayed";
        return condition;
    }

    public static Condition alertIsNotDisplayed(WebDriver webDriver) {
        Condition condition = new Condition(o -> {
            try {
                webDriver.switchTo().alert();
                return false;
            } catch (Exception ex) {
                return true;
            }
        });
        condition.name = "alertIsNotDisplayed";
        return condition;
    }

    public static Condition urlToChange(WebDriver webDriver, String currentUrl) {
        Condition condition = new Condition(o -> {
            try {
                return !webDriver.getCurrentUrl().equalsIgnoreCase(currentUrl);
            }
            catch (Exception ex){
                return false;
            }
        });
        condition.name = "urlToChange";
        return condition;
    }

    public static Condition isDisplayed(Displayable displayable) {
        Condition condition = new Condition(o -> {
            try {
                return displayable.isDisplayed();
            }
            catch (Exception ex) {
                return false;
            }
        });
        condition.name = "isDisplayed";
        return condition;
    }

    public static Condition isNotDisplayed(Displayable displayable) {
        Condition condition = new Condition(o -> {
            try {
                return !displayable.isDisplayed();
            }
            catch (Exception ex) {
                return true;
            }
        });
        condition.name = "isNotDisplayed";
        return condition;
    }
}