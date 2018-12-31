package com.taf.utils.Wait;

import org.openqa.selenium.*;

public class WebElementCondition {
    public static Condition isDisplayed(WebElement webElement) {
        Condition condition = new Condition(o -> {
            try {
                return webElement.isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        });
        condition.name = webElement.toString() + "- isDisplayed";
        return condition;
    }

    public static Condition isDisplayed(By by, SearchContext searchContext) {
        Condition condition = new Condition(o -> {
            try {
                return by.findElement(searchContext).isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        });
        condition.name = by.toString() + "- isDisplayed";
        return condition;
    }

    public static Condition isNotDisplayed(WebElement webElement) {
        Condition condition = new Condition(o -> {
            try {
                return !webElement.isDisplayed();
            } catch (Exception ex) {
                return true;
            }
        });
        condition.name = webElement.toString() + "- isNotDisplayed";
        return condition;
    }

    public static Condition exists(WebElement webElement) {
        Condition condition = new Condition(o -> {
            try {
                webElement.isDisplayed();
                return true;
            } catch (Exception ex) {
                return false;
            }
        });
        condition.name = webElement.toString() + "- exists";
        return condition;
    }

    public static Condition exists(By by, SearchContext searchContext) {
        Condition condition = new Condition(o -> {
            try {
                by.findElement(searchContext).isDisplayed();
                return true;
            } catch (Exception ex) {
                return false;
            }
        });
        condition.name = by.toString() + "- exists";
        return condition;
    }

    public static Condition nonexistent(WebElement webElement) {
        Condition condition = new Condition(o -> {
            try {
                webElement.isDisplayed();
                return false;
            } catch (Exception ex) {
                return true;
            }
        });
        condition.name = webElement.toString() + "- nonexistent";
        return condition;
    }

    public static Condition isStale(WebElement webElement) {
        Condition condition = new Condition(o -> {
            try {
                webElement.isEnabled();
                return false;
            } catch (StaleElementReferenceException ex) {
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        });
        condition.name = webElement.toString() + "- isStale";
        return condition;
    }

    public static Condition isNotStale(WebElement webElement) {
        Condition condition = new Condition(o -> {
            try {
                webElement.isEnabled();
                return true;
            } catch (StaleElementReferenceException ex) {
                return false;
            } catch (Exception ex) {
                ex.printStackTrace();
                return true;
            }
        });
        condition.name = webElement.toString() + "- isNotStale";
        return condition;
    }

    public static Condition isStable(WebElement webElement) {
        Condition condition = new Condition(o -> {
            try {
                double elapsed = 0;
                while (elapsed < Wait.SHORT_TIMEOUT) {
                    Thread.sleep(250);
                    elapsed += .25;
                    if (isStale(webElement).apply(null) || !exists(webElement).apply(null)) {
                        return false;
                    }
                }
                return true;
            } catch (Exception ex) {
                return false;
            }
        });
        condition.name = webElement.toString() + "- isStable";
        return condition;
    }

    public static Conditions isDisplayedAndIsNotStale(WebElement webElement) {
        Conditions conditions = new Conditions(Wait.ConditionChainType.And);
        conditions.add(isDisplayed(webElement));
        conditions.add(isNotStale(webElement));
        return conditions;
    }

    public static Conditions existsAndIsNotStale(WebElement webElement) {
        Conditions conditions = new Conditions(Wait.ConditionChainType.And);
        conditions.add(exists(webElement));
        conditions.add(isNotStale(webElement));
        return conditions;
    }

    public static Conditions isStaleOrNonexistent(WebElement webElement) {
        Conditions conditions = new Conditions(Wait.ConditionChainType.Or);
        conditions.add(nonexistent(webElement));
        conditions.add(isStale(webElement));
        return conditions;
    }

    public static Condition textContains(WebElement webElement, String text) {
        Condition condition = new Condition(o -> {
            try {
                return webElement.getText().equalsIgnoreCase(text);
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        });
        condition.name = webElement.toString() + "- textContains";
        return condition;
    }

    public static Condition isWithinViewPort(WebElement webElement, WebDriver webDriver) {
        Condition condition = new Condition(o -> {
            try {
                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                long windowHeight = (long) js.executeScript("return Math.max(document.documentElement.clientHeight, window.innerHeight || 0);");
                double elementHeight = (double) js.executeScript("return arguments[0].getBoundingClientRect().top;", webElement);
                return windowHeight > elementHeight && elementHeight > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        });
        condition.name = webElement.toString() + "- isWithinViewPort";
        return condition;
    }

    public static Condition isEnabled(WebElement webElement) {
        Condition condition = new Condition(o -> {
            try {
                return webElement.isEnabled();
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        });
        condition.name = webElement.toString() + "- isEnabled";
        return condition;
    }
}