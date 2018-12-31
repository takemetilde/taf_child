package com.taf.utils;

import org.openqa.selenium.*;

import java.util.List;

/**
 * Created by ncapasso on 9/12/2016.
 *
 * This is a custom class that is to be used almost exclusively for any waits in {@link com.taf.base.PageBase}
 * that fail due to a TimeoutException.
 *
 * <p>
 * When the TimeoutException is caught in one of the BasePage wait methods, they will return this
 * class, which is an empty WebElement that has all bools set to false and all other implementations throw the custom
 * EmptyWebElementException. This allows for a more robust series of wait methods in BasePage that can actually return
 * false for any isDisplayed, isClickable, isSelected, and isEnabled checks without having to circumvent the
 * TimeoutException that would otherwise be thrown.
 * </p>
 *
 */
public class EmptyWebElement implements WebElement {
	
	/**
	 * If this EmptyWebElement was created in place of searching for an element using a by, 
	 * record the intended target here for debugging purposes.
	 */
	private By intendedBy;
	
	public EmptyWebElement() {
		super();
	}

	public EmptyWebElement(By intendedBy) {
		this.intendedBy = intendedBy;
	}
	
    /**
     * This is a custom exception that operates similarly to the OperationNotSupported exception.
     *
     * <p>
     * Because an empty WebElement will be returned if the find fails via TimeoutException in BasePage,
     * this causes any operations that would work on a normal WebElement to fail, essentially the same way as if an
     * exception was thrown earlier.
     * </p>
     *
     *
     */
    public static class EmptyWebElementException extends UnsupportedOperationException{
        private EmptyWebElementException() {
            super("This operation cannot be done on an empty WebElement");
        }
        private EmptyWebElementException(String s) {
        	super(s);
        }
    }

    @Override
    public void click() {
    	if (intendedBy != null) {
    		throw new EmptyWebElementException(String.format("Could not click on unreachable element: \"%s\"", intendedBy));
    	} else {
    		throw new EmptyWebElementException();
    	}
    }

    @Override
    public void submit() {
        throw new EmptyWebElementException();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
    	if (intendedBy != null) {
    		throw new EmptyWebElementException(String.format("Could not send keys to unreachable element: \"%s\"", intendedBy));
    	} else {
    		throw new EmptyWebElementException();
    	}
    }

    @Override
    public void clear() {
        throw new EmptyWebElementException();
    }

    @Override
    public String getTagName() {
        throw new EmptyWebElementException();
    }

    @Override
    public String getAttribute(String name) {
    	if (intendedBy != null) {
    		throw new EmptyWebElementException(String.format("Could not get attribute \"%s\" on unreachable element: \"%s\"", name, intendedBy));
    	} else {
    		throw new EmptyWebElementException();
    	}
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getText() {
    	if (intendedBy != null) {
    		throw new EmptyWebElementException(String.format("Could not get text from unreachable element: \"%s\"", intendedBy));
    	} else {
    		throw new EmptyWebElementException();
    	}
    }

    @Override
    public List<WebElement> findElements(By by) {
    	if (by != null) {
    		throw new EmptyWebElementException(String.format("Could not find elements with by \"%s\" on unreachable element: \"%s\"", by, intendedBy));
    	} else {
    		throw new EmptyWebElementException();
    	}
    }

    @Override
    public WebElement findElement(By by) {
    	if (by != null) {
    		throw new EmptyWebElementException(String.format("Could not find element with by \"%s\" on unreachable element: \"%s\"", by, intendedBy));
    	} else {
    		throw new EmptyWebElementException();
    	}
    }

    @Override
    public boolean isDisplayed() {
        return false;
    }

    @Override
    public Point getLocation() {
    	if (intendedBy != null) {
    		throw new EmptyWebElementException(String.format("Could not get location of unreachable element: \"%s\"", intendedBy));
    	} else {
    		throw new EmptyWebElementException();
    	}
    }

    @Override
    public Dimension getSize() {
    	if (intendedBy != null) {
    		throw new EmptyWebElementException(String.format("Could not get size of unreachable element: \"%s\"", intendedBy));
    	} else {
    		throw new EmptyWebElementException();
    	}
    }

    @Override
    public Rectangle getRect() {
        throw new EmptyWebElementException();
    }

    @Override
    public String getCssValue(String propertyName) {
    	if (intendedBy != null) {
    		throw new EmptyWebElementException(String.format("Could not CSS value on unreachable element: \"%s\"", intendedBy));
    	} else {
    		throw new EmptyWebElementException();
    	}
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        throw new EmptyWebElementException();
    }
}
