package com.napier.sem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * The Language Report class retrieves and displays
 * information about the number of people who speak selected languages.
 */
public class LanguageReport {
    private Connection con;

    /**
     * Constructor to inject database connection.
     *
     * @param con the active database connection
     */
    public LanguageReport(Connection con) {
        this.con = con;
    }

    /**
     * Retrieves how many people speak the selected languages.
     *
     * @return A list of languages and speaker counts.
     */
    public ArrayList<CountryLanguage> retrieveLanguageSpeakers() {
        if (con == null) {
            return new ArrayList<>();
        }

        String sql = """
                SELECT c.Language, SUM(country.Population) AS "Number of Speakers"
                FROM CountryLanguage c
                JOIN country ON country.Code = c.CountryCode
                WHERE c.Language IN ("Chinese", "English", "Hindi","Spanish", "Arabic")
                GROUP BY c.Language
                ORDER BY SUM(country.Population) DESC;
                """;

        return executeLanguageQuery(sql);
    }

    /**
     * Executes SQL queries and maps results to CountryLanguage objects.
     *
     * @param sql    SQL query
     * @param params Optional query parameters
     * @return A list of CountryLanguage objects
     */
    private ArrayList<CountryLanguage> executeLanguageQuery(String sql, String... params) {
        ArrayList<CountryLanguage> languages = new ArrayList<>();

        if (con == null) {
            return languages;
        }

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            // Bind parameters if provided
            for (int i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]);
            }

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                CountryLanguage lang = new CountryLanguage();
                lang.Language = rset.getString("Language");
                lang.NumberOfSpeakers = rset.getLong("Number of Speakers");

                languages.add(lang);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to execute language query.");
            return null;
        }

        return languages;
    }

    /**
     * Prints the list of languages and speaker populations.
     */
    public void printLanguageReport(ArrayList<CountryLanguage> languages) {
        if (languages == null || languages.isEmpty()) {
            System.out.println("No language data available.");
            return;
        }

        System.out.printf("%-20s %20s%n", "Language", "Number of Speakers");
        System.out.println("-------------------------------------------------------");

        for (CountryLanguage lang : languages) {
            if (lang == null) continue;

            System.out.printf("%-20s %,20d%n",
                    lang.Language, lang.NumberOfSpeakers);
        }
    }
}
