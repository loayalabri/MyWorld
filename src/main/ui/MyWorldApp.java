package ui;

import model.Country;
import model.ListOfCountry;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;

public class MyWorldApp {
    private static final String STORAGE_FILE = "./data/myWorld.json";
    private Scanner input;
    private ListOfCountry myWorld;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


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
        jsonReader = new JsonReader(STORAGE_FILE);
        jsonWriter = new JsonWriter(STORAGE_FILE);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    //EFFECTS: print welcome message
    private void welcome() {
        System.out.println("Welcome to My World app");
        System.out.println("\nWhat would you like to do?");
        System.out.println("\ta -> Add a country");
        System.out.println("\tl -> Load your world");
        System.out.println("\tq -> Quit");

    }

    //MODIFIES: this
    //EFFECTS: process given command
    private void processCommand(String command) {
        switch (command) {
            case "a":
                doAddCountry();
                break;
            case "r":
                doRemoveCountry();
                break;
            case "d":
                doDisplayCountryNames();
                break;
            case "c":
                doChangeRating();
                break;
            case "l":
                loadListOfCountry();
                break;
            case "s":
                saveListOfCountry();
                break;
            default:
                System.out.println("Invalid Command");
                break;
        }
    }


    // EFFECTS: prints out main menu
    private void displayMainMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Add a new country");
        System.out.println("\tr -> Remove exiting country");
        System.out.println("\td -> Display Countries");
        System.out.println("\tc -> Change rating of a country");
        System.out.println("\tl -> Load your world");
        System.out.println("\ts -> save your world");
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

    // MODIFIES: this
    // EFFECTS: change rating of a country chosen by user
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

    private void saveListOfCountry() {
        try {
            jsonWriter.open();
            jsonWriter.write(myWorld);
            jsonWriter.close();
            System.out.println("Your world is saved successfully");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save to" + STORAGE_FILE);
        }
    }

    private void loadListOfCountry() {
        try {
            myWorld = jsonReader.read();
            System.out.println("Your world is loaded successfully");
        } catch (IOException e) {
            System.out.println("Unable to load from file" + STORAGE_FILE);
        }
    }
}
