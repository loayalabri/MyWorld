package model;

import model.exceptions.CountryNotFoundException;
import model.log.Event;
import model.log.EventLog;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;


// Represents the list of countries visited
public class ListOfCountry implements Writable {
    private final List<Country> countriesVisited;

    //EFFECTS: create an empty list of countries visited
    public ListOfCountry() {
        countriesVisited = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: add country to countriesVisited
    public void addCountry(Country country) {
        EventLog.getInstance().logEvent(new Event(country.getCountryName() + " was added"));
        countriesVisited.add(country);
    }


    //MODIFIES: this
    //EFFECTS: remove latest added country with given name,
    //         if name not found throw CountryNotFoundException
    public void removeCountry(String name) throws CountryNotFoundException {
        Country countryToRemove = null;
        for (Country next : countriesVisited) {
            if (next.getCountryName().equals(name)) {
                countryToRemove = next;
                EventLog.getInstance().logEvent(new Event(countryToRemove.getCountryName() + " was removed"));
                countriesVisited.remove(next);
                break;
            }
        }
        if (countryToRemove == null) {
            throw new CountryNotFoundException();
        }
    }

    public int getLength() {
        return countriesVisited.size();
    }

    public List<Country> getCountriesVisited() {
        return countriesVisited;
    }

    //EFFECTS: return the first country added that has a given name,
    //         if no country found with name throw CountryNotFoundException.
    public Country getCountryFromName(String name) throws CountryNotFoundException {
        Country country = null;
        for (Country next : countriesVisited) {
            if (next.getCountryName().equals(name)) {
                country = next;
                break;
            }
        }
        if (country == null) {
            throw new CountryNotFoundException();
        } else {
            return country;
        }
    }

    @Override
    public JSONObject toJason() {
        JSONObject json = new JSONObject();
        json.put("countries", countriesToJason());
        return json;
    }

    private JSONArray countriesToJason() {
        JSONArray jsonArray = new JSONArray();

        for (Country next : countriesVisited) {
            jsonArray.put(next.toJason());
        }
        return jsonArray;
    }
}
