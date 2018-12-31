package com.taf.utils;

import com.taf.auto.PageWaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * Created by AF26263 on 8/1/2017.
 * 
 * @author AF26263
 */
public class WaitForElementUtils {
	public static Optional<WebElement> waitForElementVisible(WebElement element) {
		return waitForElementVisible(element, PageWaitUtil.WAIT_IN_SECONDS);
	}

	public static Optional<WebElement> waitForElementVisible(WebElement element, int timeoutInSeconds) {
		turnOffImplicitWait();
		try {
			return of(PageWaitUtil.waitForElementVisible(element, timeoutInSeconds));
		} catch (TimeoutException e) {
			return empty();
		} finally {
			setImplicitWaitToDefault();
		}
	}

	private static void turnOffImplicitWait() {
		PageWaitUtil.setImplicitWait(0);
	}

	private static void setImplicitWaitToDefault() {
		PageWaitUtil.setImplicitWait(PageWaitUtil.WAIT_IN_SECONDS);
	}

	public static Optional<WebElement> waitForElementVisible(By by) {
		return waitForElementVisible(by, PageWaitUtil.WAIT_IN_SECONDS);
	}

	public static Optional<WebElement> waitForElementVisible(By by, int timeoutInSeconds) {
		turnOffImplicitWait();
		try {
			return of(PageWaitUtil.waitForElementVisible(by, timeoutInSeconds));
		} catch (TimeoutException e) {
			return empty();
		} finally {
			setImplicitWaitToDefault();
		}
	}

}
