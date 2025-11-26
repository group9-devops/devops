package com.napier.sem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UrbanReport {
    public double population;
    public double urbanPopulation;
    public double percentage;

    /**
     * Retrieves the total population of the world.
     *
     * @param con the active database connection
     */
    public void getPopulationOfWorld(Connection con) {
        String sql = "SELECT SUM(population) FROM country";
        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rset = stmt.executeQuery()) {
            if (rset.next()) {
                population = rset.getDouble(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get world population");
            population = 0;
        }
    }

    /**
     * Retrieves the total urban population of the world.
     *
     * @param con the active database connection
     */
    public void getUrbanPopulation(Connection con) {
        String sql = "SELECT SUM(population) FROM city";
        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rset = stmt.executeQuery()) {
            if (rset.next()) {
                urbanPopulation = rset.getDouble(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get urban population");
            urbanPopulation = 0;
        }
    }

    /**
     * Retrieves the total population of a specific region.
     *
     * @param con    the active database connection
     * @param region the region to query
     */
    public void getPopulationOfRegion(Connection con, String region) {
        String sql = "SELECT SUM(population) FROM country WHERE Region = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, region);
            try (ResultSet rset = stmt.executeQuery()) {
                if (rset.next()) {
                    population = rset.getDouble(1);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population of region");
            population = 0;
        }
    }

    /**
     * Retrieves the total urban population of a specific region.
     *
     * @param con    the active database connection
     * @param region the region to query
     */
    public void getUrbanPopulationOfRegion(Connection con, String region) {
        String sql = "SELECT SUM(city.population) FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Region = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, region);
            try (ResultSet rset = stmt.executeQuery()) {
                if (rset.next()) {
                    urbanPopulation = rset.getDouble(1);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get urban population of region");
            urbanPopulation = 0;
        }
    }

    /**
     * Retrieves the total population of a specific continent.
     *
     * @param con       the active database connection
     * @param continent the continent to query
     */
    public void getPopulationOfContinent(Connection con, String continent) {
        String sql = "SELECT SUM(population) FROM country WHERE Continent = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, continent);
            try (ResultSet rset = stmt.executeQuery()) {
                if (rset.next()) {
                    population = rset.getDouble(1);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population of continent");
            population = 0;
        }
    }

    /**
     * Retrieves the total urban population of a specific continent.
     *
     * @param con       the active database connection
     * @param continent the continent to query
     */
    public void getUrbanPopulationOfContinent(Connection con, String continent) {
        String sql = "SELECT SUM(city.population) FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Continent = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, continent);
            try (ResultSet rset = stmt.executeQuery()) {
                if (rset.next()) {
                    urbanPopulation = rset.getDouble(1);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get urban population of continent");
            urbanPopulation = 0;
        }
    }

    /**
     * Retrieves the population of a specific country.
     *
     * @param con     the active database connection
     * @param country the country to query
     */
    public void getPopulationOfCountry(Connection con, String country) {
        String sql = "SELECT population FROM country WHERE Name = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, country);
            try (ResultSet rset = stmt.executeQuery()) {
                if (rset.next()) {
                    population = rset.getDouble(1);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population of country");
            population = 0;
        }
    }

    /**
     * Retrieves the urban population of a specific country.
     *
     * @param con     the active database connection
     * @param country the country to query
     */
    public void getUrbanPopulationOfCountry(Connection con, String country) {
        String sql = "SELECT SUM(city.population) FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Name = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, country);
            try (ResultSet rset = stmt.executeQuery()) {
                if (rset.next()) {
                    urbanPopulation = rset.getDouble(1);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get urban population of country");
            urbanPopulation = 0;
        }
    }

    /**
     * Retrieves the population of a specific city.
     *
     * @param con  the active database connection
     * @param city the city to query
     */
    public void getPopulationOfCity(Connection con, String city) {
        String sql = "SELECT population FROM city WHERE Name = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, city);
            try (ResultSet rset = stmt.executeQuery()) {
                if (rset.next()) {
                    population = rset.getDouble(1);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population of city");
            population = 0;
        }
    }

    /**
     * Generates lists of regions, countries, and cities, then generates continent report.
     *
     * @param con active database connection
     */
    public void generateReportLists(Connection con) {
        String[] continents = {
                "Africa", "Antarctica", "Asia", "Europe",
                "North America", "Oceania", "South America"
        };

        try {
            // 1. Regions
            List<String> regions = new ArrayList<>();
            try (Statement stmt = con.createStatement();
                 ResultSet rset = stmt.executeQuery(
                         "SELECT DISTINCT Region FROM country WHERE Region IS NOT NULL"
                 )) {
                while (rset.next()) {
                    regions.add(rset.getString("Region"));
                }
            }

            // 2. Countries
            List<String> countries = new ArrayList<>();
            try (Statement stmt = con.createStatement();
                 ResultSet rset = stmt.executeQuery(
                         "SELECT DISTINCT Name FROM country ORDER BY Name"
                 )) {
                while (rset.next()) {
                    countries.add(rset.getString("Name"));
                }
            }

            // 3. Cities
            List<String> cities = new ArrayList<>();
            try (Statement stmt = con.createStatement();
                 ResultSet rset = stmt.executeQuery(
                         "SELECT DISTINCT Name FROM city ORDER BY Name"
                 )) {
                while (rset.next()) {
                    cities.add(rset.getString("Name"));
                }
            }

        } catch (Exception e) {
            System.out.println("List creation error");
            System.out.println(e.getMessage());
        }

        generateContinentReport(con, continents, "ContinentReport");
    }

    /**
     * Outputs continent population data to a Markdown file.
     *
     * @param con        active database connection
     * @param continents list of continent names
     * @param filename   Markdown file name to create
     */
    public void generateContinentReport(Connection con, String[] continents, String filename) {
        StringBuilder sb = new StringBuilder();
        // Markdown table header
        sb.append("| Continent | Population | Urban Population | Urbanisation Percentage |\r\n");
        sb.append("| --- | --- | --- | --- |\r\n");

        for (String continent : continents) {
            getPopulationOfContinent(con, continent);
            getUrbanPopulationOfContinent(con, continent);
            double pct = (population != 0) ? (urbanPopulation / population) * 100 : 0.0;
            sb.append("| ").append(continent)
                    .append(" | ").append(population)
                    .append(" | ").append(urbanPopulation)
                    .append(" | ").append(String.format("%.2f", pct))
                    .append(" |\r\n");
        }

        try {
            java.io.File reportsDir = new java.io.File("./reports/");
            if (!reportsDir.exists()) reportsDir.mkdirs();

            try (java.io.BufferedWriter writer = new java.io.BufferedWriter(
                    new java.io.FileWriter("./reports/" + filename))) {
                writer.write(sb.toString());
            }

            System.out.println("Continental urbanisation report written to ./reports/" + filename);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.out.println("Failed to write continental urbanisation report.");
        }
    }
}