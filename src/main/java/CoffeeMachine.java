import java.util.InputMismatchException;
import java.util.Scanner;

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
    int COUNT_OF_CUPS = 0;

    public CoffeeMachine(boolean power, int water, int milk, int coffee) {
        CoffeeMachine.power = power;
        this.water = water;
        this.milk = milk;
        this.coffee = coffee;
    }

    public void setPower(boolean power) {
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
        System.out.println();
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
        System.out.println(COUNT_OF_CUPS);
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

    void chooseOptions() throws InputMismatchException {
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
            case 9 -> this.createProfile();
            case 10 -> this.setPower(false);
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

    String[] profiles = new String[10];
    int profileIndex = 0;

    void showProfiles() {
        try {
            for (int i = 0; i < profiles.length; i++) {
                if (!profiles[i].equals(""))
                    System.out.println((i + 1) + ". " + profiles[i]);
            }
        } catch (NullPointerException ignored){}
    }

    void chooseProfile() {

    }

    void createProfile() {
        System.out.println("Введите название профиля: ");
        Scanner scanner = new Scanner(System.in);
        String profileName = scanner.nextLine();
        profiles[profileIndex] = profileName;
        System.out.println("Выберите напиток:\n1 - Espresso\n2 - Cappuccino");
        int choice = scanner.nextInt();

        System.out.println("Профиль " + profileName + " создан");
        profileIndex++;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMachine coffeeMachine = new CoffeeMachine(false, 720, 240, 60);
        System.out.println("Включить кофемашину?\n1 - Да\n2 - Нет");
        int power = scanner.nextInt();
        if (power == 1) {
            coffeeMachine.setPower(true);
        } else if (power == 2) {
            coffeeMachine.setPower(false);
        }
        while (getPower()) {
            try {
                coffeeMachine.chooseOptions();
            } catch (InputMismatchException e) {
                System.out.println("Введите число от 1 до 8");
            }
        }
    }
}


