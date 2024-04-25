package test;

import com.codeborne.selenide.Configuration;
import data.DataHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.DashboardPage;
import pages.LoginPage;
import pages.VerificationPage;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.open;
import static data.DataHelper.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferTest {

    DashboardPage dashboardPage;
    CardInfo firstCardInfo;
    CardInfo secondCardInfo;
    int firstCardBalance;
    int secondCardBalance;

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        Configuration.browserCapabilities = options;

        open("http://localhost:9999/");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verifyInfo = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.activeVerify(verifyInfo);
        firstCardInfo = getFirstCardInfo();
        secondCardInfo = getSecondCardInfo();
        firstCardBalance = dashboardPage.getCardBalance(0);
        secondCardBalance = dashboardPage.getCardBalance(1);
    }

    @Test
    void transferValidAmountToFirstCard() {
        var amount = generateValidAmount(secondCardBalance);
        var expectedBalanceFirstCard = firstCardBalance + amount;
        var expectedBalanceSecondCard = secondCardBalance - amount;
        var transferPage = dashboardPage.toTransferPage(0);
        dashboardPage = transferPage.transferToCard(String.valueOf(amount), secondCardInfo);
        dashboardPage.reloadDashboardPage();
        var actualBalanceFirstCard = dashboardPage.getCardBalance(0);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(1);
        assertAll(() -> assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard),
        ()-> assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard)
        );
    }

    @Test
    void transferValidAmountToSecondCard() {
        var amount = generateValidAmount(firstCardBalance);
        var expectedBalanceFirstCard = firstCardBalance - amount;
        var expectedBalanceSecondCard = secondCardBalance + amount;
        var transferPage = dashboardPage.toTransferPage(1);
        dashboardPage = transferPage.transferToCard(String.valueOf(amount), firstCardInfo);
        dashboardPage.reloadDashboardPage();
        var actualBalanceFirstCard = dashboardPage.getCardBalance(0);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(1);
        assertAll(() -> assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard),
                ()-> assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard)
        );
    }
    @Test
    void showErrorMsgIfAmountMoreBalanceTransferToFirstCard() {
        var amount = generateInvalidAmount(secondCardBalance);
        var transferPage = dashboardPage.toTransferPage(0);
        transferPage.invalidTransferToCard(String.valueOf(amount), secondCardInfo);
        transferPage.errorMsg();
        dashboardPage.reloadDashboardPage();
        var actualFirstCardBalance = dashboardPage.getCardBalance(0);
        var actualSecondCardBalance = dashboardPage.getCardBalance(1);
        assertAll(() -> assertEquals(firstCardBalance, actualFirstCardBalance),
        () -> assertEquals(secondCardBalance, actualSecondCardBalance)
        );
    }
    @Test
    void showErrorMsgIfAmountMoreBalanceTransferToSecondCard() {
        var amount = generateInvalidAmount(firstCardBalance);
        var transferPage = dashboardPage.toTransferPage(1);
        transferPage.invalidTransferToCard(String.valueOf(amount), firstCardInfo);
        transferPage.errorMsg();
        dashboardPage.reloadDashboardPage();
        var actualFirstCardBalance = dashboardPage.getCardBalance(0);
        var actualSecondCardBalance = dashboardPage.getCardBalance(1);
        assertAll(() -> assertEquals(firstCardBalance, actualFirstCardBalance),
                () -> assertEquals(secondCardBalance, actualSecondCardBalance)
        );
    }
}
