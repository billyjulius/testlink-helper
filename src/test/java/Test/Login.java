package Test;

import net.serenitybdd.jbehave.SerenityStory;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import Steps.LoginSteps;

public class Login extends SerenityStory {

    @Steps
    LoginSteps loginSteps;

    @Given("I open Facebook page")
    public void GivenloginFacebook() {
        loginSteps.OpenFacebookPage();
    }

    @When("I Input email and password")
    public void WhenInputEmailPassword() {
        loginSteps.InputEmailAndPassword();
    }

    @When("I click button login")
    public void WhenClickButtonLogin() {
        loginSteps.ClickButtonLogin();
    }

    @Then("I got to my news feed")
    public void ThenLoginSuccess() {

    }

    @When("I Input invalid email and password")
    public void WhenInputInvalidEmailPassword() {
        loginSteps.InputEmailAndPassword();
    }

    @Then("I got error message invalid username or password")
    public void ThenLoginFailed() {

    }
}




