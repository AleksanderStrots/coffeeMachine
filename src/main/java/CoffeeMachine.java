import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

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

    private static final Logger logger = Logger.getLogger(CoffeeMachine.class.getName());

    ArrayList<Profile> profiles = new ArrayList<>();

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
        if (water == WATER_LIMIT) {
            System.out.println("В кофемашине максимальное количество воды");
        } else {
            Scanner w = new Scanner(System.in);
            System.out.println("Добавьте воду: ");
            int water = w.nextInt();
            this.water = Math.min(this.getWater() + water, WATER_LIMIT);
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
            Scanner m = new Scanner(System.in);
            System.out.println("Добавьте молоко: ");
            int milk = m.nextInt();
            this.milk = Math.min(this.getMilk() + milk, MILK_LIMIT);
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
            Scanner c = new Scanner(System.in);
            System.out.println("Добавьте кофе: ");
            int coffee = c.nextInt();
            this.coffee = Math.min(this.getCoffee() + coffee, COFFEE_LIMIT);
            logger.info("coffee has been added to the coffee machine");
        }
    }

    public int getCoffee() {
        return this.coffee;
    }

    void checkIngredients() {
        if (this.water < MIN_WATER_LIMIT) {
            System.out.println("Вода: " + getWater() + " - Недостаточно воды!");
        } else {
            System.out.println("Вода: " + getWater());
        }
        if (this.milk < MIN_MILK_LIMIT) {
            System.out.println("Молоко: " + getMilk() + " - Недостаточно молока!");
        } else System.out.println("Молоко: " + getMilk());

        if (this.coffee < MIN_COFFEE_LIMIT) {
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
        logger.info("espresso made in the amount of " + count + " pieces" );
    }

    void chooseCountOfEspresso() {
        System.out.print("Выберите количество:");
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        switch (count) {
            case (1) -> makeEspresso(1);
            case (2) -> makeEspresso(2);
            case (3) -> makeEspresso(3);
            default -> {
                System.out.println("Введите число от 1 до 3");
                chooseCountOfEspresso();
            }
        }
    }

    void espresso() {
        if (power && this.water >= MIN_WATER_LIMIT && this.coffee >= MIN_COFFEE_LIMIT) {
            if (COUNT_OF_CUPS >= CUPS_LIMIT_FOR_CLEAN) {
                System.out.println("Требуется очистка.\nПроизвести очистку?\n1 - Да\n2 - Нет");
                Scanner cl = new Scanner(System.in);
                int choice = cl.nextInt();
                if (choice == 1) {
                    clean();
                } else if (choice == 2) {
                    chooseCountOfEspresso();
                }
            } else chooseCountOfEspresso();
        } else this.checkIngredients();
    }

    void makeCappuccino(int count) {
        COUNT_OF_CUPS += count;
        this.water -= 80 * count;
        this.milk -= 40 * count;
        this.coffee -= 10 * count;
        System.out.println("Cappuccino готов!" + "(" + count + " шт.)");
        logger.info("cappuccino made in the amount of " + count + " pieces" );
    }

    void chooseCountOfCappuccino() {
        System.out.print("Выберите количество:");
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        switch (count) {
            case (1) -> makeCappuccino(1);
            case (2) -> makeCappuccino(2);
            case (3) -> makeCappuccino(3);
            default -> {
                System.out.println("Введите число от 1 до 3");
                chooseCountOfCappuccino();
            }
        }
    }

    void cappuccino() {
        if (power && this.water >= MIN_WATER_LIMIT && this.coffee >= MIN_COFFEE_LIMIT && this.milk >= MIN_MILK_LIMIT) {
            if (COUNT_OF_CUPS >= CUPS_LIMIT_FOR_CLEAN) {
                System.out.println("Требуется очистка.\nПроизвести очистку?\n1 - Да\n2 - Нет");
                Scanner cl = new Scanner(System.in);
                int choice = cl.nextInt();
                if (choice == 1) {
                    clean();
                } else if (choice == 2) {
                    chooseCountOfCappuccino();
                }
            } else chooseCountOfCappuccino();
        } else checkIngredients();
    }

    void chooseOptions() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите действие: \n1 - Espresso\n2 - Cappuccino\n3 - Проверить ингредиенты\n4 - Добавить ингредиенты\n5 - Проверить, нужна ли очистка\n6 - Показать рецепт Espresso\n7 - Показать рецепт Cappuccino\n8 - Быстрый набор\n9 - Создать профиль\n10 - Выключить");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> this.espresso();
            case 2 -> this.cappuccino();
            case 3 -> this.checkIngredients();
            case 4 -> this.addIngredients();
            case 5 -> this.checkClean();
            case 6 -> this.getIngredients(0);
            case 7 -> this.getIngredients(1);
            case 8 -> this.showProfiles();
            case 9 -> {
                if (profiles.size() < PROFILES_LIMIT) {
                    create();
                } else System.out.println("Настроено максимальное количество профилей");
            }
            case 10 -> setPower(false);
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
            Scanner cl = new Scanner(System.in);
            int choice = cl.nextInt();
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

    void showProfiles() throws Exception {
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите Ваш профиль");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                if (profiles.size() > 0) {
                    COUNT_OF_CUPS++;
                    this.water -= profiles.get(0).water;
                    this.milk -= profiles.get(0).milk;
                    this.coffee -= profiles.get(0).coffee;
                    System.out.println("Ваш кофе готов");
                    logger.info("profile (1) coffee made");
                }
            }
            case 2 -> {
                if (profiles.size() > 1) {
                    COUNT_OF_CUPS++;
                    this.water -= profiles.get(1).water;
                    this.milk -= profiles.get(1).milk;
                    this.coffee -= profiles.get(1).coffee;
                    System.out.println("Ваш кофе готов");
                    logger.info("profile (2) coffee made");
                }
            }
            case 3 -> {
                if (profiles.size() > 2) {
                    COUNT_OF_CUPS++;
                    this.water -= profiles.get(2).water;
                    this.milk -= profiles.get(2).milk;
                    this.coffee -= profiles.get(2).coffee;
                    System.out.println("Ваш кофе готов");
                    logger.info("profile (2) coffee made");
                }
            }
            case 4 -> {
                if (profiles.size() > 3) {
                    COUNT_OF_CUPS++;
                    this.water -= profiles.get(3).water;
                    this.milk -= profiles.get(3).milk;
                    this.coffee -= profiles.get(3).coffee;
                    System.out.println("Ваш кофе готов");
                    logger.info("profile (4) coffee made");
                }
            }
            case 5 -> {
                if (profiles.size() > 4) {
                    COUNT_OF_CUPS++;
                    this.water -= profiles.get(4).water;
                    this.milk -= profiles.get(4).milk;
                    this.coffee -= profiles.get(4).coffee;
                    System.out.println("Ваш кофе готов");
                    logger.info("profile (5) coffee made");
                }
            }
            default -> System.out.println("Профиль не найден");
        }
    }

    public void create() {
        Profile profile = new Profile();
        profiles.add(profile);
        System.out.println("Профиль '" + profile.name + "' создан");
        logger.info("profile '" + profile.name + "' created");
    }

    static void onOff() throws InputMismatchException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Включить кофемашину?\n1 - Да\n2 - Нет");
        int power = scanner.nextInt();
        if (power == 1) {
            CoffeeMachine.setPower(true);
            logger.info("coffee machine is ON");
        } else if (power == 2) {
            CoffeeMachine.setPower(false);
            logger.info("coffee machine is OFF");
        } else System.out.println("Введите число от 1 до 2");
    }

    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine(false, 720, 240, 60);
        try {
            CoffeeMachine.onOff();
        } catch (InputMismatchException e) {
            System.out.println("Введите число от 1 до 2");
        }

        while (CoffeeMachine.getPower()) {
            try {
                coffeeMachine.chooseOptions();
            } catch (Exception e) {
                System.out.println("Введите число от 1 до 10");
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
        System.out.println("Введите название: ");
        this.name = scanner.nextLine();
        System.out.println("Количество воды в мл: ");
        this.water = scanner.nextInt();
        System.out.println("Количество молока в мл: ");
        this.milk = scanner.nextInt();
        System.out.println("Количество кофе в г: ");
        this.coffee = scanner.nextInt();
    }
}
