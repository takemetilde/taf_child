package com.taf.auto.steps;

import com.anthem.auto.page.AbstractPage;
import com.anthem.auto.pages.LoginPage;
import com.anthem.enums.Environment;
import com.anthem.maps.DataMaps;
import cucumber.api.java.en.Given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.anthem.utils.WebDriverUtils.getDriverInstance;

public class SetupSteps {

    //~ Instance Fields/Page Objects -----------------------------------------------------------------------------------

    private static final Logger LOG = LoggerFactory.getLogger(SetupSteps.class);

    //Initial run requires manual instantiation.
    LoginPage loginPage;

    //~ Methods/Modules ------------------------------------------------------------------------------------------------

    /**
     * Pulls username from Gherkin statement and logs in by referencing Environment.getEnv
     * @param username
     * @throws Throwable
     */
    @Given("^member logs in at the Consumer Portal as \"([^\"]*)\"$")
    public void memberLogsInAs(String username) throws Throwable {
        switch (Environment.getEnv()) {
            case LOCALHOST: directLogin(username); break;
            case SIT: directLogin(username); break;
            default:
                throw new RuntimeException("Unrecognized environment for login: " + Environment.getEnv().toString());
        }
    }

    /**
     * First, navigates to LoginPage, installs page, and logs in using DataMaps.  Note that this is the very first instance.
     * @param username
     */
    private void directLogin(String username) {
        getDriverInstance().get(Environment.getLoginUrl());
        loginPage = AbstractPage.install(LoginPage.class);
        loginPage.login(DataMaps.getDataValue(username), DataMaps.getSharedDataValue("Generic Password"));
        loginPage.waitForLoginSuccess();
    }
}
