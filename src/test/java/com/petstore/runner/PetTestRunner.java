package com.petstore.runner;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;



@CucumberOptions(
        features = "src/test/resources",
        glue = "com.petstore"
 
)
public class PetTestRunner extends AbstractTestNGCucumberTests {

}
