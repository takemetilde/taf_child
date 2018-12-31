package com.taf.utils;

import com.taf.auto.PageWaitUtil;
import com.taf.auto.WebDriverUtil;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 *  This class was created to make negative tests,
 *
 *  The notDisplayed() method can only take a By and not WebElement so that we can try/catch the NoSuchElementException
 *  and can base the result of the test on the exception
 *
 *  Feel free to add other negative tests/helpers as needed
 */
public class NegativeTestUtils {

    public static Boolean notDisplayed(By by){

        try {

            WebElement element = WebDriverUtil.driver().findElement(by);

            try {
                PageWaitUtil.waitForElementVisible(element);
            }
            catch (Exception f){
                return true;
            }
        } catch (NoSuchElementException e){

            return true;
        }

        Assert.fail();

        return false;
    }
}
