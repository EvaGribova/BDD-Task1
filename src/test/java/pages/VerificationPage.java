package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private SelenideElement verificationCodeSelector = $("[data-test-id='code'] input");
    private SelenideElement verificationCodeButton = $("[data-test-id='action-verify']");

    public VerificationPage(){
        verificationCodeSelector.shouldBe(visible);
    }

    public DashboardPage activeVerify(DataHelper.VerificationCode code) {
        verificationCodeSelector.setValue(code.getVerificationCode());
        verificationCodeButton.click();
        return new DashboardPage();
    }
}
