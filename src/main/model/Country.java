package model;

// Represents a country that have name, rating (from 1 to 10), continent,
// description, and photos.
public class Country {
    private String countryName;                      // the country name
    private int rating;                       // country rating
    private String description;               // description of the country
    private String continent;                 // continent of the country

    //REQUIRES: name has non-zero length.
    //          continent has non-zero length.
    //          rating is in [1-10]
    //EFFECTS: create a country with given name, continent, and
    //         rating.
    public Country(String name, String continent, int rating) {
        this.countryName = name;
        this.continent = continent;
        this.rating = rating;
    }

    public String getCountryName() {
        return countryName;
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

    //REQUIRES: newRating in [1-10]
    //MODIFIES: this
    //EFFECTS: change rating to newRating
    public void changeRating(int newRating) {
        rating = newRating;
    }
}
