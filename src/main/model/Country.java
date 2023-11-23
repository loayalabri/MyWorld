package model;

import model.exceptions.EmptyStringException;
import model.exceptions.RatingOutOfBoundException;
import org.json.JSONObject;
import persistence.Writable;

// Represents a country that have name, rating (from 1 to 10), continent,
// description, and photos.
public class Country implements Writable {
    private String name;               // the country name
    private int rating;                       // country rating
    private String description;               // description of the country
    private String continent;                 // continent of the country
    private static final  int MAX_RATING = 10;
    private static final int MIN_RATING = 0;

    //MODIFIES: this
    //EFFECTS: create a country with given name, continent, and rating.
    //         if name or continent are empty strings throw EmptyStringException.
    //         if rating is not in [MIN_RATING, MAX_RATING], throw RatingOutOfBoundException.
    public Country(String name, String continent, int rating, String desc) throws RatingOutOfBoundException,
            EmptyStringException {
        if (name.length() == 0 || continent.length() == 0) {
            throw new EmptyStringException();
        } else if (rating > MAX_RATING || rating < MIN_RATING) {
            throw new RatingOutOfBoundException();
        } else {
            this.name = name;
            this.continent = continent;
            this.rating = rating;
            this.description = desc;
        }
    }

    public String getCountryName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public String getContinent() {
        return continent;
    }

    public String getDescription() {
        return description;
    }

    // MODIFIES: this
    // EFFECTS: set given text to description
    public void createDescription(String description) {
        this.description = description;
    }


    //MODIFIES: this
    //EFFECTS: change rating to newRating,
    //         if newRating is not in [MIN_RATING, MAX_RATING], throw RatingOutOfBoundException
    public void setRating(int newRating) throws RatingOutOfBoundException {
        if (newRating > MAX_RATING || newRating < MIN_RATING) {
            throw new RatingOutOfBoundException();
        } else {
            rating = newRating;
        }
    }

    @Override
    public JSONObject toJason() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("continent", continent);
        json.put("rating", rating);
        json.put("description", description);
        return json;
    }

    @Override
    public String toString() {
        return name;
    }
}
