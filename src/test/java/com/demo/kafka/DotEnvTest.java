package com.demo.kafka;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class DotEnvTest {
    private Map<String, String> envVars;
    Dotenv dotenv;

    @BeforeTest
    public void setUp() {
        dotenv = Dotenv.configure().load();
    }


    @Test()
    public void loadFromDotEnv() {
        assertEquals(dotenv.get("DOTENV_TEST_MESSAGE"), "PING");
    }
}
