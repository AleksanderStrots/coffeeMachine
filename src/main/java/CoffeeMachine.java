import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.*;

public class CoffeeMachine {
    private static boolean power;
    private int water;
    private int milk;
    private int coffee;

    static final int COFFEE_LIMIT = 60;
    static final int WATER_LIMIT = 720;
    static final int MILK_LIMIT = 240;
    static final int MIN_COFFEE_LIMIT = 10;
    static final int MIN_WATER_LIMIT = 120;
    static final int MIN_MILK_LIMIT = 40;
    static final int CUPS_LIMIT_FOR_CLEAN = 50;
    static int COUNT_OF_CUPS = 0;
    final int PROFILES_LIMIT = 5;
    int count;

    private static final Logger logger = Logger.getLogger(CoffeeMachine.class.getName());

    ArrayList<Profile> profiles = new ArrayList<>();

    Scanner scanner = new Scanner(System.in);

    public CoffeeMachine(boolean power, int water, int milk, int coffee) {
        CoffeeMachine.power = power;
        this.water = water;
        this.milk = milk;
        this.coffee = coffee;
    }

    public static void setPower(boolean power) {
        CoffeeMachine.power = power;
    }

    public static boolean getPower() {
        return power;
    }

    public void setWater() {
        if (getWater() == WATER_LIMIT) {
            System.out.println("В кофемашине максимальное количество воды");
        } else {
            System.out.println("Добавьте воду: ");
            int water = scanner.nextInt();
            this.water = Math.min(getWater() + water, WATER_LIMIT);
            logger.info("water has been added to the coffee machine");
        }
    }

    public int getWater() {
        return this.water;
    }

    public void setMilk() {
        if (milk == MILK_LIMIT) {
            System.out.println("В кофемашине максимальное количество молока");
        } else {
            System.out.println("Добавьте молоко: ");
            int milk = scanner.nextInt();
            this.milk = Math.min(getMilk() + milk, MILK_LIMIT);
            logger.info("milk has been added to the coffee machine");
        }
    }

    public int getMilk() {
        return this.milk;
    }

    public void setCoffee() {
        if (coffee == COFFEE_LIMIT) {
            System.out.println("В кофемашине максимальное количество кофе");
        } else {
            System.out.println("Добавьте кофе: ");
            int coffee = scanner.nextInt();
            this.coffee = Math.min(getCoffee() + coffee, COFFEE_LIMIT);
            logger.info("coffee has been added to the coffee machine");
        }
    }

    public int getCoffee() {
        return this.coffee;
    }

    void checkIngredients() {
        if (getWater() < MIN_WATER_LIMIT * count) {
            System.out.println("Вода: " + getWater() + " - Недостаточно воды!");
        } else {
            System.out.println("Вода: " + getWater());
        }
        if (getMilk() < MIN_MILK_LIMIT * count) {
            System.out.println("Молоко: " + getMilk() + " - Недостаточно молока!");
        } else System.out.println("Молоко: " + getMilk());
        if (getCoffee() < MIN_COFFEE_LIMIT * count) {
            System.out.println("Кофе: " + getCoffee() + " - Недостаточно кофе!");
        } else System.out.println("Кофе: " + getCoffee());
    }

    void addIngredients() {
        setWater();
        setMilk();
        setCoffee();
    }

    void makeEspresso(int count) {
        COUNT_OF_CUPS += count;
        this.water -= 120 * count;
        this.coffee -= 10 * count;
        System.out.println("Espresso готов!" + "(" + count + " шт.)");
        logger.info("espresso made in the amount of " + count + " pieces");
    }

    void chooseCountOfEspresso() {
        System.out.print("Выберите количество:");
        count = scanner.nextInt();
        if (getWater() >= MIN_WATER_LIMIT * count && getCoffee() >= MIN_COFFEE_LIMIT * count) {
            switch (count) {
                case (1) -> makeEspresso(1);
                case (2) -> makeEspresso(2);
                case (3) -> makeEspresso(3);
                default -> {
                    System.out.println("Введите число от 1 до 3");
                    chooseCountOfEspresso();
                }
            }
        } else checkIngredients();
    }

    void espresso() {
        if (getPower() && getWater() >= MIN_WATER_LIMIT && getCoffee() >= MIN_COFFEE_LIMIT) {
            if (COUNT_OF_CUPS >= CUPS_LIMIT_FOR_CLEAN) {
                System.out.println("Требуется очистка.\nПроизвести очистку?\n1 - Да\n2 - Нет");
                int choice = scanner.nextInt();
                if (choice == 1) {
                    clean();
                } else if (choice == 2) {
                    chooseCountOfEspresso();
                }
            } else chooseCountOfEspresso();
        } else checkIngredients();
    }

    void makeCappuccino(int count) {
        COUNT_OF_CUPS += count;
        this.water -= 80 * count;
        this.milk -= 40 * count;
        this.coffee -= 10 * count;
        System.out.println("Cappuccino готов!" + "(" + count + " шт.)");
        logger.info("cappuccino made in the amount of " + count + " pieces");
    }

    void chooseCountOfCappuccino() {
        System.out.print("Выберите количество:");
        count = scanner.nextInt();
        if (getWater() >= MIN_WATER_LIMIT * count && getCoffee() >= MIN_COFFEE_LIMIT * count && getMilk() >= MIN_MILK_LIMIT * count) {
            switch (count) {
                case (1) -> makeCappuccino(1);
                case (2) -> makeCappuccino(2);
                case (3) -> makeCappuccino(3);
                default -> {
                    System.out.println("Введите число от 1 до 3");
                    chooseCountOfCappuccino();
                }
            }
        } else checkIngredients();
    }

    void cappuccino() {
        if (getPower() && getWater() >= MIN_WATER_LIMIT && getCoffee() >= MIN_COFFEE_LIMIT && getMilk() >= MIN_MILK_LIMIT) {
            if (COUNT_OF_CUPS >= CUPS_LIMIT_FOR_CLEAN) {
                System.out.println("Требуется очистка.\nПроизвести очистку?\n1 - Да\n2 - Нет");
                int choice = scanner.nextInt();
                if (choice == 1) {
                    clean();
                } else if (choice == 2) {
                    chooseCountOfCappuccino();
                }
            } else chooseCountOfCappuccino();
        } else checkIngredients();
    }

    public void chooseOptions() throws InputMismatchException {
        System.out.println("""
                Выберите действие:\s
                1 - Espresso
                2 - Тройной espresso
                3 - Cappuccino
                4 - Тройной cappuccino
                5 - Проверить ингредиенты
                6 - Добавить ингредиенты
                7 - Проверить, нужна ли очистка
                8 - Показать рецепт Espresso
                9 - Показать рецепт Cappuccino
                10 - Быстрый набор
                11 - Создать профиль
                12 - Показать логи
                13 - Выключить""");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> espresso();
            case 2 -> tripleEspresso();
            case 3 -> cappuccino();
            case 4 -> tripleCappuccino();
            case 5 -> checkIngredients();
            case 6 -> addIngredients();
            case 7 -> checkClean();
            case 8 -> getIngredients(0);
            case 9 -> getIngredients(1);
            case 10 -> showProfiles();
            case 11 -> {
                if (profiles.size() < PROFILES_LIMIT) {
                    create();
                } else System.out.println("Настроено максимальное количество профилей");
            }
            case 12 -> showLogs();
            case 13 -> {
                setPower(false);
                logger.info("coffee machine is OFF");
            }
            default -> chooseOptions();
        }
    }

    void clean() {
        if (COUNT_OF_CUPS >= CUPS_LIMIT_FOR_CLEAN) {
            System.out.println("Очистка...");
            this.water = 0;
            this.milk = 0;
            this.coffee = 0;
            COUNT_OF_CUPS = 0;
            checkIngredients();
            logger.info("coffee machine cleared");
        }
    }

    public void checkClean() {
        if (COUNT_OF_CUPS < CUPS_LIMIT_FOR_CLEAN) {
            System.out.println("Очистка пока не нужна.");
        } else {
            System.out.println("Требуется очистка.\nПроизвести очистку?\n1 - Да\n2 - Нет");
            int choice = scanner.nextInt();
            if (choice == 1) {
                clean();
            } else if (choice == 2) {
                return;
            }
        }
    }

    enum TypeOfCoffee {
        ESPRESSO(0), CAPPUCCINO(1);
        int type;

        TypeOfCoffee(int option) {
            this.type = option;
        }

        int getOption() {
            return this.type;
        }
    }

    public void getIngredients(int option) {
        if (option == TypeOfCoffee.ESPRESSO.getOption()) {
            System.out.println("Вода-120 мл, молоко-0 мл, кофе-10 г");
        } else if (option == TypeOfCoffee.CAPPUCCINO.getOption()) {
            System.out.println("Вода-80 мл, молоко-40 мл, кофе-10 г");
        }
    }

    void showProfiles() {
        if (!profiles.isEmpty()) {
            for (Profile p : profiles) {
                System.out.println((profiles.indexOf(p) + 1) + " - " + p.name);
            }
            chooseProfile();
        } else {
            System.out.println("Профили еще не созданы");
            chooseOptions();
        }
    }

    void chooseProfile() {
        System.out.println("Выберите Ваш профиль");
        int choice = scanner.nextInt();
        for (int i = 0; i < 5; i++) {
            if (choice == i + 1) {
                if (profiles.size() > i) {
                    if (getWater() < profiles.get(i).water || getMilk() < profiles.get(i).milk || getCoffee() < profiles.get(i).coffee) {
                        checkIngredients();
                    } else {
                        COUNT_OF_CUPS++;
                        this.water -= profiles.get(i).water;
                        this.milk -= profiles.get(i).milk;
                        this.coffee -= profiles.get(i).coffee;
                        System.out.println("Ваш кофе готов");
                        logger.info("profile (" + (i + 1) + ") coffee made");
                    }
                } else {
                    System.out.println("Профиль не найден");
                    break;
                }
            }
        }
    }

    void tripleEspresso() {
        if (getWater() >= MIN_WATER_LIMIT * 3 && getCoffee() >= MIN_COFFEE_LIMIT * 3) {
            makeEspresso(3);
        } else checkIngredients();
    }

    void tripleCappuccino() {
        if (getWater() >= MIN_WATER_LIMIT * 3 && getCoffee() >= MIN_COFFEE_LIMIT * 3 && getMilk() >= MIN_MILK_LIMIT * 3) {
            makeCappuccino(3);
        } else checkIngredients();
    }

    public void create() {
        Profile profile = new Profile();
        profiles.add(profile);
        System.out.println("Профиль '" + profile.name + "' создан");
        logger.info("profile '" + profile.name + "' created");
    }

    static void onOff() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Включить кофемашину?\n1 - Да\n2 - Нет");
        try {
            int power = scanner.nextInt();
            if (power == 1) {
                CoffeeMachine.setPower(true);
                logger.info("coffee machine is ON");
            } else if (power == 2) {
                CoffeeMachine.setPower(false);
                logger.info("coffee machine is OFF");
            } else {
                System.out.println("Введите число от 1 до 2");
                onOff();
            }
        } catch (InputMismatchException e) {
            System.out.println("Введите число от 1 до 2");
            onOff();
        }
    }

    static void showLogs() {
        try {
            FileInputStream fileInputStream = new FileInputStream("logs.log");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                if (str.contains("made")) {
                    System.out.println(str);
                }
            }
            fileInputStream.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            FileHandler fh;
            fh = new FileHandler("logs.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CoffeeMachine coffeeMachine = new CoffeeMachine(false, 0, 0, 0);

        onOff();
        while (CoffeeMachine.getPower()) {
            try {
                coffeeMachine.chooseOptions();
            } catch (InputMismatchException e) {
                System.out.println("Введите число от 1 до 13");
                coffeeMachine.scanner.next();
            }
        }
    }
}

class Profile {
    Scanner scanner = new Scanner(System.in);
    int water;
    int milk;
    int coffee;
    String name;

    public Profile() {
        System.out.println("Введите название профиля: ");
        this.name = scanner.nextLine();
        System.out.println("Количество воды в мл: ");
        this.water = scanner.nextInt();
        System.out.println("Количество молока в мл: ");
        this.milk = scanner.nextInt();
        System.out.println("Количество кофе в г: ");
        this.coffee = scanner.nextInt();
    }
}
