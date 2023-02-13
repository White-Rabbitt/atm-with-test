import java.util.InputMismatchException;
import java.util.Scanner;

public class BankATM {

    static double surplus = 1000;

    public static void main(String[] args) {

        BankATM atm = new BankATM();

        auth();
        selectFunction(atm.scannerNum());
        getMoney(surplus, atm.scannerMoney());
        depositMoney(atm.scannerMoney());
        showBalance();
        transfer(surplus, atm.scannerMoney());
        continueWork(atm.scannerStr());
        exit();
    }

    public int scannerNum() {
        System.out.println("Выберите функцию:");
        int inputFunc = new Scanner(System.in).nextInt();
        return inputFunc;
    }

    public double scannerMoney() {
        System.out.println("Введите сумму:");
        double money = new Scanner(System.in).nextDouble();
        System.out.println("Введенная сумма: " + money);
        return money;
    }

    public String scannerStr() {
        System.out.println("Продолжить обслуживание: Да / Нет");
        String inputStr = new Scanner(System.in).nextLine();
        return inputStr;
    }

    public static boolean auth() {
        boolean flag = false;
        Scanner input = new Scanner(System.in);
        for (int i = 1; i <= 3; i++) {
            System.out.println("Введите номер карты:");
            String inputCard = input.next();
            try {
                Long.parseLong(inputCard);
                System.out.println("Номер карты введен");
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат ввода");
            }
            System.out.println("Введите пароль:");
            int inputPwd = input.nextInt();
            try {
                System.out.println("Пароль введен");
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат ввода");
            }
            flag = checkCardAndPassword(inputCard, inputPwd, i);
            if (flag) {
            }
        }
        return flag;
    }

    public static boolean checkCardAndPassword(String inputCard, int inputPwd, int count) {
        String cardNum = "6228123123";
        int pwd = 888888;
        if (inputCard.equals(cardNum) && inputPwd == pwd) {
            return true;
        } else {
            if (count <= 2) {
                System.out.println("Номер карты или пароль указан неверно. Осталось попыток: " + (3 - count));
            } else {
                System.out.println("Ваша карта заблокирована!");
            }
            return false;
        }
    }

    public static int selectFunction(int inputFunc) {
        try {
            switch (inputFunc) {
                case 1 -> {
                    System.out.println("Операция: Вывод средств");
                    return 1;
                }
                case 2 -> {
                    System.out.println("Операция: Пополнение счета");
                    return 2;
                }
                case 3 -> {
                    System.out.println("Операция: Проверка баланса");
                    System.out.println("На счету: " + surplus);
                    return 3;
                }
                case 4 -> {
                    System.out.println("Операция: Платежи и переводы");
                    System.out.println("Поместите банкноты в купюроприемник (сумма не более 10000)");
                    return 4;
                }
                case 5 -> {
                    System.out.println("Операция: Выход");
                    System.out.println("Спасибо за обращение!");
                    return 5;
                }
                default -> {
                    System.out.println("Неверное значение");
                    return -1;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Неверное значение");
            return -1;
        }
    }

    public static double getMoney(double surplus, double money) {
        try {
            if (surplus < money) {
                System.out.println("Недостаточно средств");
                return 1;
            } else if (money < 0) {
                System.out.println("Введена некорректная сумма");
                return 2;
            } else if (money % 100 != 0) {
                System.out.println("Невозможно выдать запрошенную сумму");
                return 3;
            } else {
                surplus -= money;
                System.out.println("Выдано: " + money);
                System.out.println("Остаток на карте: " + surplus);
                return surplus;
            }
        } catch (InputMismatchException e) {
            System.out.println("Неверное значение");
            return -1;
        }
    }

    public static double depositMoney(double money) {
        try {
            if (money <= 0 || money > 10000) {
                System.out.println("Вы пытаетесь внести недопустимую сумму");
                return 1;
            } else if (money % 100 == 0) {
                surplus += money;
                System.out.println("Вы внесли: " + money + ". Баланс: " + surplus);
                return surplus;
            } else if (money % 100 != 0 || money % 10 != 0) {
                double backMoney = money % 100;
                surplus = surplus + money - backMoney;
                System.out.println("Вы внесли: " + (money - backMoney) + ". Баланс: " + surplus + ". Сдача: " + backMoney);
                return surplus;
            }
        } catch (InputMismatchException e) {
            System.out.println("Купюра является поддельной и будет конфискована");
            return -1;
        }
        return surplus;
    }

    public static double showBalance() {
        System.out.println("Баланс карты: " + surplus);
        return surplus;
    }

    public static double transfer(double surplus, double money) {
        try {
            if (money <= 0 || money > surplus) {
                System.out.println("Невозможно перевести указанную сумму");
                return 1;
            } else {
                surplus -= money;
                System.out.println("Перевод выполнен успешно. Баланс: " + surplus);
                return surplus;
            }
        } catch (InputMismatchException e) {
            System.out.println("Неверное значение");
            return -1;
        }
    }

    public static boolean continueWork(String inputStr) {
        try {
            if (inputStr.equals("Да")) {
                System.out.println("Выход в главное меню");
                return true;
            } else if (inputStr.equals("Нет")) {
                System.out.println("Спасибо за обращение!");
                return false;
            }
        } catch (InputMismatchException e) {
            System.out.println("Неверное значение");
        }
        return false;
    }

    public static String exit() {
        String exit = "Спасибо за обращение!";
        System.out.println(exit);
        return exit;
    }
}
