package com.taf.utils;

import com.taf.auto.WebDriverUtil;
import com.taf.auto.page.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;

/**
 * Created by AF41814 on 10/13/2017.
 */
public class GeneralUtils {

    //When given a simple page object name from the com.taf.pages package this will create an instance of that class and attempt to
    //see if that pages defineUniqueElement() is currently being displayed.
    public static boolean isPageDisplayed(String pagename) {
        try {
            String[] nonClassNameChars = {" ", "&"};
            for(String s : nonClassNameChars) {
                pagename = pagename.replace(s, "");
            }
            if(!isAbstractPageDisplayed(pagename)) {
                Object page = Class.forName("com.taf.pages." + pagename.concat("Page")).newInstance();
                Method method = page.getClass().getMethod("defineUniqueElement");
                WebElement uniquePageElement = ((By) method.invoke(page)).findElement(WebDriverUtil.driver());
                return uniquePageElement.isDisplayed();
            }
            else
                return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private static boolean isAbstractPageDisplayed(String pagename) {
        try {
            AbstractPage.install(Class.forName("com.taf.pages." + pagename.concat("Page")).asSubclass(AbstractPage.class)).waitForPageLoad();
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

}
