package com.napier.sem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PrintCityValues {

    public void getAllCities(Connection con) {
        ArrayList<City> cities = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT city.name AS CityName, " +
                            "country.name AS Country, " +
                            "city.population AS Population " +
                            "FROM city " +
                            "JOIN country ON city.countryCode = country.code " +
                            "ORDER BY city.population DESC";

            ResultSet rset = stmt.executeQuery(strSelect);

            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("CityName");
                city.country = rset.getString("Country");
                city.population = rset.getInt("Population");
                cities.add(city);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get the cities");
        }

        // Print table header
        System.out.printf("%-30s %-20s %-15s\n", "City", "Country", "Population");
        System.out.println("--------------------------------------------------------------");

        // Print rows
        for (City city : cities) {
            System.out.printf("%-30s %-20s %,15d\n",
                    city.name,
                    city.country,
                    city.population);
        }
    }
}

