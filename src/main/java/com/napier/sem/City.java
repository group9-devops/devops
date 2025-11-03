package com.napier.sem;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

/**
 * Represents details of a  City
 */
public class City {
    /**
     * City's name
     */
    public String Name;
    /**
     * City's country (Country Code)
     */
    public String CountryCode;
    /**
     * City's District
     */
    public String District;
    /**
     * City's Population
     */
    public int Population;
    /**
     * True or False if City is a capital
     */
    public boolean isCapital;
    /**
     * City's ID in DB
     */
    public int ID;

    public String Country;
}
