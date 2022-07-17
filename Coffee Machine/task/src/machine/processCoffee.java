package machine;

import java.util.Scanner;
import java.lang.System;

public class processCoffee {
    private int startWater = 400;
    private int startMilk = 540;
    private int startCoffeeBeans = 120;
    private int startCups = 9;
    private int startMoney = 550;
    private final Scanner sc = new Scanner(System.in);

    public void play() {
        //I would have also put this method in the parent class and make the private class variables public
        while (true) {
            System.out.println("Write action (buy, fill, take,remaining,exit): ");
            MachineAction action = MachineAction.valueOf(sc.next().toUpperCase());
            switch (action) {
                case TAKE -> take();
                case FILL -> fill();
                case BUY -> {
                    System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                    String choice = sc.next();
                    if (choice.equals("back")) {
                        play();
                    } else {
                        buy(choice);
                    }
                }
                case REMAINING -> remaining();
                case EXIT -> exit();
            }
        }
    }

    public void buy(String choice) {
        int coffeeChoice = Integer.parseInt(choice);
        boolean makeCoffee = estimate(coffeeChoice);
        if (makeCoffee) {
            switch (coffeeChoice) {
                case 1 -> {
                    startWater -= 250;
                    startCoffeeBeans -= 16;
                    startMoney += 4;
                    startCups -= 1;
                }
                case 2 -> {
                    startWater -= 350;
                    startMilk -= 75;
                    startCoffeeBeans -= 20;
                    startMoney += 7;
                    startCups -= 1;
                }
                case 3 -> {
                    startWater -= 200;
                    startMilk -= 100;
                    startCoffeeBeans -= 12;
                    startMoney += 6;
                    startCups -= 1;
                }
            }
        }
    }

    public void fill() {
        System.out.println("Write how many ml of water you want to add:");
        startWater += sc.nextInt();
        System.out.println("Write how many ml of milk you want to add:");
        startMilk += sc.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add:");
        startCoffeeBeans += sc.nextInt();
        System.out.println("Write how many disposable cups of coffee you want to add:");
        startCups += sc.nextInt();
    }

    public void take() {
        System.out.printf("I gave you $%d\n\n", startMoney);
        startMoney = 0;
    }

    public static void process() {
        System.out.println("Starting to make a coffee\nGrinding coffee beans\nBoiling water");
        System.out.println("Mixing boiled water with crushed coffee beans\nPouring coffee into the cup");
        System.out.println("Pouring some milk into the cup\nCoffee is ready!");
    }

    public void remaining() {

        System.out.printf("""
                The coffee machine has:
                %d ml of water\s
                %d ml of milk\s
                %d g of coffee beans\s
                %d disposable cups\s
                $%d of money\s""", startWater, startMilk, startCoffeeBeans, startCups, startMoney);
        System.out.println();
        System.out.println();

    }

    public void exit() {
        System.exit(0);
    }

    public void quantity() {
        System.out.println("Write how many cups of coffee you will need: ");
//      1 cup of coffee =  200 ml of water, 50 ml of milk, and 15 g of coffee beans.
        int coffeeCups = sc.nextInt();
        int water = 200 * coffeeCups;
        int milk = 50 * coffeeCups;
        int beans = 15 * coffeeCups;
        String detail;
        detail = String.format("For %d cups of coffee you will need: %n %d ml of water %n %d ml of milk %n %d g of coffee beans", coffeeCups, water, milk, beans);
        System.out.println(detail);
    }

    public String getShortage() {
        String str = null;
        if (startCoffeeBeans < startWater && startCoffeeBeans < startMilk) {
            str = "coffee beans";
        } else if (startCoffeeBeans > startWater && startWater < startMilk) {
            str = "water";
        } else if (startMilk < startCoffeeBeans && startWater > startMilk) {
            str = "milk";
        }
        return str;
    }

    public boolean estimate(int coffeeChoice) {
        int water = startWater;
        int milk = startMilk;
        int beans = startCoffeeBeans;

        switch (coffeeChoice) {
            case 1:
                water = startWater - 250;
                beans = startCoffeeBeans - 16;
                break;
            case 2:
                water = startWater - 350;
                milk = startMilk - 75;
                beans = startCoffeeBeans - 20;
                break;
            case 3:
                water = startWater - 200;
                milk = startMilk - 100;
                beans = startCoffeeBeans - 12;
                break;
        }

        int smallest = Math.min(water, Math.min(milk, beans));
        String shortage = getShortage();
        boolean makeCoffee;


        if (smallest >= 1) {
            System.out.println("I have enough resources, making you a coffee!");
            System.out.println();
            makeCoffee = true;
        } else {
            System.out.printf("Sorry, not enough %s\n", shortage);
            System.out.println();
            makeCoffee = false;
        }
        return makeCoffee;
    }
}

enum MachineAction {
    BUY, FILL, TAKE, REMAINING, EXIT
}

