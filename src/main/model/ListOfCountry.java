package model;

import java.util.ArrayList;
import java.util.List;


// Represents the list of countries visited
public class ListOfCountry {
    private List<Country> countriesVisited;  //Countries visited

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
            if (next.getCountryName().equals(name)) {
                Country remove = next;
                countriesVisited.remove(remove);
                break;
            }
        }
    }

    public int getLength() {
        return countriesVisited.size();
    }

    public List<Country> getCountriesVisited() {
        return countriesVisited;
    }

    //MODIFIES: this
    //EFFECTS: return a list of names of visited countries
    public List<String> getCountriesNames() {
        List<String> countiesNames = new ArrayList<>();
        for (Country next : countriesVisited) {
            countiesNames.add(next.getCountryName());
        }
        return countiesNames;
    }
}
