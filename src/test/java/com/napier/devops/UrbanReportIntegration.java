package com.napier.devops;

import com.napier.sem.App;
import com.napier.sem.City;
import com.napier.sem.UrbanReport;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class UrbanReportIntegration
{
    static App app;
    static UrbanReport report;

    @BeforeAll
    static void init() {
        app = new App();
        app.connect();  // Connect first
        report = new UrbanReport(app.con);
    }
    @AfterAll
    static void close()
    {
        app.disconnect();
    }
}
