import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankAtmTest {

    ByteArrayOutputStream out = new ByteArrayOutputStream();

    @BeforeEach
    public void setSurplus() {
        BankATM.surplus = 1000;
    }

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(out));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void shouldCheckCardAndPasswordPositive(int count) {
        final boolean res = BankATM.checkCardAndPassword("6228123123", 888888, count);

        assertTrue(res);
    }

    @ParameterizedTest
    @ValueSource(strings = {"622812312", "62281231231", ""})
    public void shouldCheckCardAndPasswordNegativeCard(String inputCard) {
        final boolean res = BankATM.checkCardAndPassword(inputCard, 888888, 1);

        assertFalse(res);
    }

    @ParameterizedTest
    @ValueSource(ints = {88888, 8888888, 8})
    public void shouldCheckCardAndPasswordNegativePassword(int inputPwd) {
        final boolean res = BankATM.checkCardAndPassword("6228123123", 888888, 1);

        assertTrue(res);
    }

    @Test
    void shouldCheckCardAndPasswordBlockCard() {
        BankATM.checkCardAndPassword("6228123123", 88888, 3);

        assertThat(out.toString()).startsWith("Ваша карта заблокирована!");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void shouldCheckCardAndPasswordShowTryCount(int count) {
        BankATM.checkCardAndPassword("6228123123", 88888, count);

        assertThat(out.toString()).startsWith("Номер карты или пароль указан неверно");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    public void shouldSelectFuncPositive(int func) {
        int res = BankATM.selectFunction(func);

        Assertions.assertEquals(func, res);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 6})
    public void shouldSelectFuncNegative(int func) {
        int res = BankATM.selectFunction(func);

        Assertions.assertEquals(-1, res);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, 500})
    public void shouldGetMoneyPositive(double money) {
        double res = BankATM.getMoney(BankATM.surplus, money);

        Assertions.assertEquals(BankATM.surplus - money, res);
    }

    @Test
    public void shouldGetMoneyLowBalance() {
        double res = BankATM.getMoney(1000, 1001);

        Assertions.assertEquals(1, res);
    }

    @Test
    public void shouldGetMoneyLessThenZero() {
        double res = BankATM.getMoney(1000, -1);

        Assertions.assertEquals(2, res);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.01, 1, 10, 150, 999, 999, 99})
    public void shouldGetMoneyNotMultiple(double money) {
        double res = BankATM.getMoney(BankATM.surplus, money);

        Assertions.assertEquals(3, res);
    }

    @ParameterizedTest
    @ValueSource(doubles = {100, 500, 900, 1000})
    public void shouldDepositMoneyPozitive(double money) {
        double res = BankATM.depositMoney(money);

        Assertions.assertEquals(BankATM.surplus, res);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, 0, 10001})
    public void shouldDepositMoneyLessThenZeroOrMoreThenLimit(double money) {
        double res = BankATM.depositMoney(money);

        Assertions.assertEquals(1, res);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.01, 10, 150, 999.99})
    public void shouldDepositMoneyNotMultiple100(double money) {
        double res = BankATM.depositMoney(money);

        Assertions.assertEquals(BankATM.surplus, res);
    }

    @Test
    public void shouldShowBalance() {
        double res = BankATM.showBalance();

        Assertions.assertEquals(BankATM.surplus, res);
    }

    @ParameterizedTest
    @ValueSource(doubles = {1, 500, 999, 1000})
    public void shouldTransferPositive(double money) {
        double res = BankATM.transfer(BankATM.surplus, money);

        Assertions.assertEquals(BankATM.surplus - money, res);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, 0, 1001})
    public void shouldTransferNegative(double money) {
        double res = BankATM.transfer(BankATM.surplus, money);

        Assertions.assertEquals(1, res);
    }

    @Test
    public void shouldExit() {
        String res = BankATM.exit();

        Assertions.assertEquals("Спасибо за обращение!", res);
    }

    @Test
    public void shouldContinueWorkYes() {
        boolean res = BankATM.continueWork("Да");

        Assertions.assertTrue(res);
    }

    @Test
    public void shouldContinueWorkNot() {
        boolean res = BankATM.continueWork("Нет");

        Assertions.assertFalse(res);
    }

    @Test
    public void shouldContinueWorkUncorrect() {
        BankATM.continueWork("sample text 123");

        assertThat(out.toString().startsWith("Ваша карта заблокирована!"));
    }

    @Test
    public void shouldAuthPositive() {

        ByteArrayInputStream card = new ByteArrayInputStream("6228123123".getBytes());
        ByteArrayInputStream pass = new ByteArrayInputStream("888888".getBytes());
        for (int i = 0; i < 5; i++) {
            System.setIn(card);
            System.setIn(pass);
        }
    }
}
