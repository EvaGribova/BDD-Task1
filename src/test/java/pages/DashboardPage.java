package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {

    private SelenideElement titleSelector = $("[data-test-id='dashboard']");
    private ElementsCollection cards = $$(".list__item");
    private ElementsCollection buttonsToTransfer = $$("[data-test-id='action-deposit']");
    private SelenideElement reloadButton = $("[data-test-id='action-reload']");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage(){
        titleSelector.shouldBe(visible);
    }
    public int getCardBalance(int index) {
        var text = cards.get(index).getText();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
    public TransferPage toTransferPage(int index){
        buttonsToTransfer.get(index).click();
        return new TransferPage();
    }

    public void reloadDashboardPage() {
        reloadButton.click();
        titleSelector.shouldBe(visible);
    }
}
