package com.petstore.stepDefinitions;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

import java.io.File;

import org.testng.Assert;

import com.petstore.base.BaseTest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PetApiStepDefinitions extends BaseTest {
	
	private RequestSpecification request;
    private Response response;
    private long randomPetId;
    String JSONScheme;
    String updatedName;
    Response putRes, validateRes;

    @Given("the API to get all available pets")
    public void givenApiToGetAllAvailablePets() {
        request = RestAssured.given().baseUri(BASE_URL);
    }

    @When("I make a call to the GET API with query param {string}")
    public void whenMakeCallToGetApiWithQueryParam(String status)
        {
        response = request.queryParam("status", status)
                .get("/pet/findByStatus");
        
        response.statusCode();
        
        randomPetId = response.then().extract().jsonPath().getInt("[0].id");
        
        System.out.println("randomPetId : " + randomPetId);
        System.out.println("randomPetId : " + response.asPrettyString());
        String statusAvail = response.then().extract().jsonPath().getString("[0].status");
        
        Assert.assertEquals(statusAvail, "available", "Available status is not matched");
        
    
    }

    @Then("I validate the JSON schema")
    public void thenValidateJsonSchema() {
    	
    	 JSONScheme = response.asPrettyString();
    	
        response.then().assertThat().body(matchesJsonSchema(JSONScheme)).
        statusCode(200);
       
        Assert.assertEquals(200, response.then().assertThat().body(matchesJsonSchema(JSONScheme)), "Schema validation is not matched ");
        Assert.assertEquals(200, response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("findByStatus.json")));
        		
        	
    }
  //===============================================

    @Given("API to use PUT pet")
    public void givenAPIToUsePUTpet() 
    {
        putRes = RestAssured.given().
        		baseUri(BASE_URL).accept("application/json").
        		contentType(ContentType.JSON)
        		.queryParam("id", randomPetId)
        		.queryParam("name", "doggie")
        		
       .when()
       .body(new File("./pet.json"))
       .put("/pet");
       
       putRes.statusCode();
        
       updatedName = putRes.then().extract().jsonPath().getString("name"); 
      
      Assert.assertEquals("doggie", updatedName, "updated name is not matched ");
      Assert.assertEquals(200, putRes.statusCode() , "put response code is not matched ");
        
        
        
    }

    @When("I make a PUT call with a random Pet ID, name, status, and tag")
    public void whenMakePutCallWithRandomPetDetails() {
    	
    	Assert.assertEquals("available", response.then().extract().jsonPath().getString("[0].status"), "updated name is not matched ");  //tag name
    //	Assert.assertEquals("available", response.then().extract().jsonPath().getString("[0].id"), "updated name is not matched ");       // tag id
    	
        Assert.assertEquals("doggie", updatedName, "updated name is not matched "); // name
        Assert.assertEquals(200, putRes.statusCode() , "put response code is not matched ");  // response
    }

    @Then("I validate the response from the PUT API")
    public void thenValidatePutApiResponse() {
    	
    	Assert.assertEquals(200, putRes.then().assertThat().statusCode(200), "PUT method is failed");
    	putRes.then().assertThat().statusCode(200);

    }
//=============================
    @Given("the API to get pet details by ID")
    public void givenApiToGetPetDetailsById() {
        request = RestAssured.given().baseUri(BASE_URL);
        
        
        validateRes = RestAssured.given().
        		baseUri(BASE_URL).accept("application/json").
        		contentType(ContentType.JSON)
        		.queryParam("id", randomPetId)
        
        		
       .when()
       .get("/pet");
       
        validateRes.statusCode();
       
       
    }

    @When("I validate the put id JSON schema")
    public void IvalidatetheputidJSONschema() 
    {
    	Assert.assertEquals(200, response.then().assertThat().body(matchesJsonSchema(validateRes.asPrettyString())), "Schema validation is not matched ");
    	
        
    }

    @Then("Validate the name, status and tag of the pet")
    public void thenValidateJsonResponseAndPetDetails() {
        
    	Assert.assertEquals("doggie", updatedName, "updated name is not matched "); // name
    	
    	Assert.assertEquals(200, putRes.statusCode() , "put response code is not matched "); // response status 
    	
    	Assert.assertEquals("available", response.then().extract().jsonPath().getString("[0].status"), "updated name is not matched ");  //tag name available status.
     
        	
            
            
        
    }

}
