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

    //MODIFIES: this
    //EFFECTS: process user input
    private void runApp() {
        boolean running = true;
        String command;

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
                displayMainMenu();

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
        } else if (command.equals("c")) {
            doChangeRating();
        } else {
            System.out.println("Invalid Command");
        }
    }

    private void displayMainMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Add a new country");
        System.out.println("\tr -> Remove exiting country");
        System.out.println("\td -> Display Countries");
        System.out.println("\tc -> Change rating of a country");
        System.out.println("\tq -> Quit");
    }

    //MODIFIES: this
    //EFFECTS: add country to my World
    private void doAddCountry() {
        String name;
        String continent;
        int rating;
        String desc;

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


    //MODIFIES: this
    //EFFECTS: remove country from my world
    private void doRemoveCountry() {
        String remove;
        System.out.println("Enter name of the country to be removed:");
        remove = input.next();
        if (myWorld.getCountriesNames().contains(remove)) {
            myWorld.removeCountry(remove);
            System.out.println(remove + " was removed successfully");
        } else {
            System.out.println("Cannot remove a country that is not in the list");
        }
    }

    private void doChangeRating() {
        String name;
        int newRating;

        System.out.println("Enter name of country to change its rating:");
        name = input.next();
        System.out.println("Enter you new rating for the country[0-10]:");
        newRating = input.nextInt();

        Country result = myWorld.getCountryFromName(name);

        if (checkRating(newRating)) {
            if (!(result == null)) {
                result.setRating(newRating);
                System.out.println(name + " rating was changed to " + newRating);
            } else {
                System.out.println("Change rating was unsuccessful, check spelling");
            }
        }
    }

    //EFFECTS: print names of countries in my world
    private void doDisplayCountryNames() {
        if (myWorld.getCountriesVisited().isEmpty()) {
            System.out.println("You have no countries");
        } else {
            List<String> countries = myWorld.getCountriesNames();
            System.out.println(countries);
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
}
