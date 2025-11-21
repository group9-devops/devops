package com.napier.sem;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

/**
 * Represents details of Country Language
 */
public class CountryLanguage {

    /**
     * Country's Language Code
     */
    public String CountryCode;
    /**
     * Country's Language
     */
    public String Language;
    /**
     * Country's continent
     */
    public boolean isOfficial;
    public long NumberOfSpeakers;
}
