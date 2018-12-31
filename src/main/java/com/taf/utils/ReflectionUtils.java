package com.taf.utils;

import com.taf.auto.page.AbstractPage;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by hher on 4/13/2018.
 */
public class ReflectionUtils {

    /**
     * Dumps all the methods from a given class into a Method array.  Declared methods are directly associated methods,
     * not those inherited.  To get all associated Methods use getMethods().
     *
     * @param c
     * @return
     */
    public static Method[] getAllMethodsIntoArray(Class<?> c) {
        return c.getDeclaredMethods();
    }

    /**
     * Dumps all the fields from a given class into a Field array.  Declared fields are directly associated methods,
     * not those inherited.  To get all associated Fields use getFields().
     *
     * @param c
     * @return
     */
    public static Field[] getAllFieldsIntoArray(Class<?> c) {
        return c.getDeclaredFields();
    }

    /**
     * Calls getAllMethodsIntoArray(c) from a given class and iterates through array for an exact match of method name.
     *
     * @param c
     * @param methodNameInCamelCase
     * @return null if there is no match.  Throws NullPointerException
     */
    public static Method findMethodMatchInArray(Class<?> c, String methodNameInCamelCase) {

        Method[] m = getAllMethodsIntoArray(c);
        for (int i = 0; i < m.length - 1; i++) {
            if (m[i].getName().matches(methodNameInCamelCase)) {
                return m[i];
            }
        }
        return null;
    }

    /**
     * Calls getAllFieldsIntoArray(c) from a given class and iterates through array for an exact match of field name.
     *
     * @param c
     * @param fieldNameInCamelCase
     * @return
     */
    public static Field findFieldMatchInArray(Class<?> c, String fieldNameInCamelCase) {

        Field[] f = getAllFieldsIntoArray(c);
        for (int i = 0; i < f.length; i++) {
            if (f[i].getName().toString().equals(fieldNameInCamelCase)) {
                return f[i];
            }
        }
        return null;
    }

    public static Class<?> getClassFromFieldElement(WebElement e) {
        Class<?> c = e.getClass();
        Field f = findFieldMatchInArray(c, e.toString());;
        return f.getDeclaringClass();
    }

    /**
     * Invokes method from object using the declaring class.
     *
     * @param m
     * @param obj
     */
    public static void invokeMethod(Method m, Object obj) {
        try {
            m = m.getDeclaringClass().getMethod(m.getName());
            m.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Invokes a method from an object that has been instantiated as a wrapper class.  Generally used for basiccontrols.
     * The return type should be casted accordingly. WARN: Is currently built to handle only one argument.  Expansion
     * to come if needed.
     * @param f
     * @param m
     * @param page
     * @return
     * @throws Exception
     */
    public static Object invokeMethodFromWrapperField(Field f, Method m, AbstractPage page, String... mArgs) throws Exception {
        if (mArgs == null)
            return m.invoke(f.get(page));
        else
            return m.invoke(f.get(page), (Object[]) mArgs);
    }

    /**
     * Calls general wrapper invocation invokeWrapperElementWithMethodInput, with the additional methodInput argument.
     * @param page
     * @param strElement
     * @return
     */
    public static Boolean isWrapperElementDisplayedOnPage(AbstractPage page, String strElement) throws Exception {
        return (Boolean)invokeWrapperElementWithMethodInput(page, strElement, "isDisplayed");
    }

    /**
     * This method converts the string from space delimited to camelcase, finds the fields that exactly match by name in
     * the given page, and returns the invocation of an object.  Requires cast to expected return type.  WARN: Is currently
     * built to handle only one argument (String.class).  Expansion to come if needed.
     * @param page
     * @param strElement
     * @param methodInput
     * @return
     */
    public static Object invokeWrapperElementWithMethodInput(AbstractPage page, String strElement, String methodInput, String... methodArgs) throws Exception{

        String s = StringUtils.convertSpaceDelimitedToCamelCase(strElement);
        Field f = findFieldMatchInArray(page.getClass(), s);
        Method m;

        if (f != null) {
            if (methodArgs.length == 0)
                m = f.getType().getMethod(methodInput);
            else
                m = f.getType().getMethod(methodInput, String.class);
            return invokeMethodFromWrapperField(f, m, page, methodArgs);
        } else {throw new NullPointerException("There was no match for the field: " + f);
        }
    }

    /**
     * Calls general wrapper invocation invokeWrapperElementWithMethodInput, with the additional methodInput argument.
     * @param page
     * @param strElement
     * @return
     */
    public static Boolean clickOnWrapperElementOnPage(AbstractPage page, String strElement) throws Exception {
        return (Boolean)invokeWrapperElementWithMethodInput(page, strElement, "click");
    }

    public static Boolean enterTextInWrapperElementOnPage(AbstractPage page, String strText, String strElement) throws Exception {
        return (Boolean)invokeWrapperElementWithMethodInput(page, strElement, "enterText", strText);
    }

    public static Boolean chooseOptionInWrapperElementOnPage(AbstractPage page, String strOption, String strElement) throws Exception {
        return (Boolean)invokeWrapperElementWithMethodInput(page, strElement, "select");
    }
}