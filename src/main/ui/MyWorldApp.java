package ui;

import model.Country;
import model.ListOfCountry;

import java.util.Scanner;
import java.util.List;

public class MyWorldApp {
    private Scanner input;
    private ListOfCountry myWorld;


    //EFFECTS: run my world application
    public MyWorldApp() {
        runApp();
    }

    private void runApp() {
        boolean running = true;
        String command = null;

        init();

        welcome();
        while (running) {
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                running = false;
                System.out.println("Thanks for using My World Application");
            } else {
                processCommand(command);
                displayMainMenue();

            }
        }
    }

    //MODIFIES: this
    //EFFECTS: initialize ListOfCountries and scanner
    private void init() {
        myWorld = new ListOfCountry();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    //EFFECTS: print welcome message
    private void welcome() {
        System.out.println("Welcome to My World app");
        System.out.println("\nWhat would you like to do?");
        System.out.println("\t a -> Add a country");
        System.out.println("\t q -> Quit");

    }

    //MODIFIES: this
    //EFFECTS: process given command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddCountry();
        } else if (command.equals("r")) {
            doRemoveCountry();
        } else if (command.equals("d")) {
            doDisplayCountryNames();
        } else {
            System.out.println("Invalid Command");
        }
    }

    private void displayMainMenue() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Add a new country");
        System.out.println("\tr -> Remove exiting country");
        System.out.println("\td -> Display Countries");
        System.out.println("\tq -> Quit");
    }

    //MODIFIES: this
    //EFFECTS: add country to my World
    private void doAddCountry() {
        String name = null;
        String continent = null;
        int rating = 0;
        String desc = null;

        System.out.println("\nEnter country name:");
        name = input.next();

        System.out.println("\nEnter continent name");
        continent = input.next();

        System.out.println("\nEnter description");
        desc = input.next();

        System.out.println("\nEnter your rating [0-10]:");
        rating = input.nextInt();

        if (checkRating(rating)) {
            myWorld.addCountry(new Country(name, continent, rating, desc));
            System.out.println(name + " Added successfully");
        } else {
            System.out.println(name + " couldn't be added invalid rating entered");
        }
    }

    //EFFECTS: check that rating is in [0-10]
    //         if not start entering country again
    private Boolean checkRating(int rating) {
        if (rating > 10 | rating < 0) {
            System.out.println("Invalid rating, try again");
            return false;
        } else {
            return true;
        }
    }

    //MODIFIES: this
    //EFFECTS: remove country from my world
    private void doRemoveCountry() {
        String remove = null;
        System.out.println("Enter name of the country to be removed:");
        remove = input.next();
        if (myWorld.getCountriesNames().contains(remove)) {
            myWorld.removeCountry(remove);
            System.out.println(remove + " was removed successfully");
        } else {
            System.out.println("Cannot remove a country that is not in the list");
        }
    }

    //EFFECTS: print names of countries in my world
    private void doDisplayCountryNames() {
        if (myWorld.countriesVisited.isEmpty()) {
            System.out.println("You have no countries");
        } else {
            List<String> countries = myWorld.getCountriesNames();
            System.out.println(countries);
        }
    }
}
