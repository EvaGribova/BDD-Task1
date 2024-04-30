package data;

import lombok.Value;
import pages.DashboardPage;

import java.util.Random;

public class DataHelper {
    private DataHelper() {}

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String verificationCode;
    }

    public static VerificationCode getVerificationCode() {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardInfo{
        private int index;
        private String cardNumber;
    }

    public static CardInfo getFirstCardInfo() {
        return new CardInfo(0,"5559 0000 0000 0001");
    }

    public static CardInfo getSecondCardInfo() {
        return new CardInfo(1, "5559 0000 0000 0002");
    }
    public static int generateValidAmount(int balance) {
        return new Random().nextInt(Math.abs(balance) - 1);
    }
    public static int generateInvalidAmount(int balance) {
        return Math.abs(balance) + new Random().nextInt(5000);
    }
}
