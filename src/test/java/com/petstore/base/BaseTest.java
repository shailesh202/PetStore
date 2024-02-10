package com.petstore.base;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import io.restassured.RestAssured;

public class BaseTest {
	
	protected static final String BASE_URL = "https://petstore.swagger.io/v2";
	
	
	
	
	

    @BeforeSuite
    public void setUp() {
        // Set up REST-Assured base URI and other common configurations
        RestAssured.baseURI = BASE_URL;

        // Additional common setup code (e.g., setting up other configurations, logging, etc.)
    }

    @AfterSuite
    public void tearDown() {
        // Common teardown code (e.g., closing resources, logging, etc.)
    }

}
