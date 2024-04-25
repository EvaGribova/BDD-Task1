package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement titleSelector = $(byText("Пополнение карты"));
    private String text = "Пополнение карты";
    private SelenideElement amountSelector = $("[data-test-id='amount'] input");
    private SelenideElement transferFromSelector = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private String firstCardNumber = "5559 0000 0000 0001";
    private String secondCardNumber = "5559 0000 0000 0002";
    private String sum1 = "500";
    private String sum2 = "30000";
    private SelenideElement errorSelector = $("[data-test-id='error-notification'] .notification__title");
    private String errorText = "Ошибка";

    public TransferPage() {
        titleSelector.shouldBe(visible);
    }

    public DashboardPage transferToCard(String amount, DataHelper.CardInfo cardInfo){
        amountSelector.setValue(amount);
        transferFromSelector.setValue(cardInfo.getCardNumber());
        transferButton.click();
        return new DashboardPage();
    }
    public void invalidTransferToCard(String amount, DataHelper.CardInfo cardInfo) {
        amountSelector.setValue(amount);
        transferFromSelector.setValue(cardInfo.getCardNumber());
        transferButton.click();
    }
    public void errorMsg(){
        errorSelector.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(Condition.text(errorText));
    }
}