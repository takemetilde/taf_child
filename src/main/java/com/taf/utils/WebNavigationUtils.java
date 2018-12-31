package com.taf.utils;

import com.taf.auto.WebDriverUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.Iterator;
import java.util.Set;

/**
 * This class was created to be able to navigate between Google Chrome tabs when one clicks on
 * a link that opens a new tab. Feel free to add any other methods for other browsers as needed
 */
public class WebNavigationUtils {

    public static void navigateToFirstTab(){

        Set <String> handles = WebDriverUtil.driver().getWindowHandles();
        Iterator<String> it = handles.iterator();
        //iterate through your windows
        while (it.hasNext()){
            try {
                String parent = it.next();
                String newwin = it.next();
                WebDriverUtil.driver().switchTo().window(newwin);
                WebDriverUtil.driver().close();
                WebDriverUtil.driver().switchTo().window(parent);

            } catch (NoSuchElementException e){
                // do nothing, just let it continue
            }
        }
    }

    public static void navigateToSecondTab(){

        Set<String> handles = WebDriverUtil.driver().getWindowHandles();
        Iterator<String> it = handles.iterator();
        //iterate through your windows
        while (it.hasNext()){
            String parent = it.next();
            String newwin = it.next();
            WebDriverUtil.driver().switchTo().window(newwin);

        }
    }

    public static void iframeFocus(String iframe) {

        WebElement element = WebDriverUtil.driver().findElement(By.id(iframe));
        WebDriverUtil.driver().switchTo().frame(element);
    }

    public static void iframeUnfocus(){

        WebDriverUtil.driver().switchTo().defaultContent();
    }
}
