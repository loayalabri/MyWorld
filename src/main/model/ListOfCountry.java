package model;

import java.util.ArrayList;
import java.util.List;
import model.Country;

// Represents the list of countries visited
public class ListOfCountry {
    List<Country> countriesVisited;  //Countries visited

    //EFFECTS: create an empty list of countries visited
    public ListOfCountry() {
        countriesVisited = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: add country to countriesVisited
    public void addCountry(Country country) {
        countriesVisited.add(country);
    }

    //REQUIRES: name is a country name in countriesVisited
    //MODIFIES: this
    //EFFECTS: remove latest added country with given name
    public void removeCountry(String name) {
        for (Country next : countriesVisited) {
            if (next.getCountryName() == name) {
                countriesVisited.remove(next);
            }
        }
    }

    public int getLength() {
        int length = 0;
        for (Country next : countriesVisited) {
            length++;
        }
        return length;
    }

}
