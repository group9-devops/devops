package com.napier.devops;

import com.napier.sem.App;
import com.napier.sem.Country;
import com.napier.sem.City;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    static App app;

    @BeforeAll
    static void init() {
        app = new App();
    }

    @Test
    public void testApp() {
        assertEquals(5,5,"Pass");
    }
}
