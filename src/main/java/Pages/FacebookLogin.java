package Pages;


import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FacebookLogin extends PageObject {

    @FindBy(id="email")
    WebElement fieldEmail;

    public void InputEmail() {
        fieldEmail.sendKeys("test@gmail.com");
    }

    @FindBy(id="pass")
    WebElement fieldPassword;

    public void InputPassword() {
        fieldPassword.sendKeys("your_password");
    }

    @FindBy(id="loginbutton")
    WebElement buttonLogin;

    public void ClickButtonLogin() {
        buttonLogin.click();
    }
}
