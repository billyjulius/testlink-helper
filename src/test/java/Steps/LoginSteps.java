package Steps;

import Pages.FacebookLogin;
import net.thucydides.core.steps.ScenarioSteps;

public class LoginSteps extends ScenarioSteps {

    FacebookLogin facebookLogin;

    public void OpenFacebookPage() {
        facebookLogin.open();
    }

    public void InputEmailAndPassword() {
        facebookLogin.InputEmail();
        facebookLogin.InputPassword();
    }

    public void ClickButtonLogin() {
        facebookLogin.ClickButtonLogin();
    }
}
