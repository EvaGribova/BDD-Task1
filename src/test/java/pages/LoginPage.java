package pages;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private SelenideElement loginSelector = $("[data-test-id='login'] input");
    private SelenideElement passwordSelector = $("[data-test-id='password'] input");
    private SelenideElement authButton = $("[data-test-id='action-login']");

    public VerificationPage validLogin (DataHelper.AuthInfo info){
        loginSelector.setValue(info.getLogin());
        passwordSelector.setValue(info.getPassword());
        authButton.click();
        return new VerificationPage();
    }
}
