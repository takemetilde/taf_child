package com.taf.auto.steps;

import com.taf.auto.pages.BasePage;
import com.taf.utils.StringUtils;
import cucumber.api.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PageSteps {

    //~ Instance Fields/Page Instantiation -----------------------------------------------------------------------------

    private static final Logger LOG = LoggerFactory.getLogger(PageSteps.class);
    BasePage page;
    private static final String PAGES_PACKAGE = "com.taf.auto.pages.";

    //~ Methods --------------------------------------------------------------------------------------------------------

    public void setPage(String strPage) {
        strPage = StringUtils.getPageNameFromString(strPage);
        String strPath = PAGES_PACKAGE + strPage;

        try{
            page = page.install(Class.forName(strPath).asSubclass(BasePage.class));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("The generated class='" + strPath + "' was not found for pageName='" + strPage + "'");
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate generate class='" + strPath + "' with pageName='" + strPage + "'", e);
        }
    }

    //~ Step Mapping ---------------------------------------------------------------------------------------------------

    @Then("^the user should verify if the \"([^\"]*)\" element (is|is not) displayed on the \"([^\"]*)\" Page$")
    public void theUserShouldVerifyIfTheElementIsDisplayedInThePage(String strElement, String strIsOption, String strPage) throws Throwable {
        setPage(strPage);
        page.verifyIfWebElementIsDisplayed(page, strIsOption, strElement);
    }

    @Then("^the user clicks on the \"([^\"]*)\" element on the \"([^\"]*)\" Page$")
    public void theUserClicksOnTheElementOnThePage(String strElement, String strPage) throws Throwable {
        setPage(strPage);
        page.verifyWebElementIsClicked(page, strElement);
    }

    @Then("^the user should verify if the list of elements is displayed on the \"([^\"]*)\" Page$")
    public void theUserShouldVerifyIfTheListOfElementsIsDisplayedOnThePage(String strPage, List<String> strList) throws Throwable {
        setPage(strPage);
        String strIsOption = "is";
        for (String strElement:strList) {
            page.verifyIfWebElementIsDisplayed(page, strIsOption, strElement);
        }
    }

    @Then("^the user should switch to the \"([^\"]*)\" iframe on the \"([^\"]*)\" Page$")
    public void theUserShouldVerifyIfTheIframeIsDisplayedOnThePage(String strIframe, String strPage) throws Throwable {
        setPage(strPage);
        page.switchToIframeOnPage(strIframe);
    }

    @Then("^the user should enter \"([^\"]*)\" in the \"([^\"]*)\" on the \"([^\"]*)\" Page$")
    public void theUserShouldEnterTextInTheWebElementOnThePage(String strText, String strWebElement, String strPage) throws Throwable {
        setPage(strPage);
        page.verifyEnteredTextInWebElement(page, strText, strWebElement);
    }

    @Then("^the user should select the \"([^\"]*)\" option in the \"([^\"]*)\" element on the \"([^\"]*)\" Page$")
    public void theUserShouldSelectTheOptionInTheElementOnThePage(String strOption, String strElement, String strPage) throws Throwable {
        setPage(strPage);
        page.verifyOptionIsSelectedInWebElement(page, strOption, strElement);
    }
    
   



   

   



}
