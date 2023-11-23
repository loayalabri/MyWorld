package ui;

import model.Country;
import model.ListOfCountry;
import model.exceptions.CountryNotFoundException;
import model.exceptions.EmptyStringException;
import model.exceptions.RatingOutOfBoundException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;

/*
Represents console user interface for myWorld App.
 */
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

        try {
            myWorld.addCountry(new Country(name, continent, rating, desc));
            System.out.println(name + " was added successfully");
        } catch (EmptyStringException e) {
            System.out.println("Add was unsuccessful. Country name and continent cannot be empty names");
        } catch (RatingOutOfBoundException e) {
            System.out.println("Add was unsuccessful. Rating out of boundary");
        }
    }


    //MODIFIES: this
    //EFFECTS: remove country from my world
    private void doRemoveCountry() {
        String remove;
        System.out.println("Enter name of the country to be removed:");
        remove = input.next();
        try {
            myWorld.removeCountry(remove);
        } catch (CountryNotFoundException e) {
            System.out.println("Remove was unsuccessful. Cannot remove a country that is not in your world");
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
        Country result = null;
        try {
            result = myWorld.getCountryFromName(name);
        } catch (CountryNotFoundException e) {
            System.out.println("Country not found with name " + name);
        }
        try {
            result.setRating(newRating);
            System.out.println(name + " rating was changed to " + newRating);
        } catch (RatingOutOfBoundException e) {
            System.out.println("Rating is invalid.");
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
        } catch (EmptyStringException e) {
            System.out.println("Error in Country name or continent from file");
        } catch (RatingOutOfBoundException e) {
            System.out.println("Error in Country rating from file");
        }
    }
}
