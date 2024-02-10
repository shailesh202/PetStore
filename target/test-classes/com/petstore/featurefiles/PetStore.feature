Feature: Swagger Pet Store API Tests

  Scenario: Test 1 - Get a list of all available pets
    Given the API to get all available pets
    When I make a call to the GET API with query param "available"
    Then I validate the JSON schema

  Scenario: Test 2 - Update details of a random pet
    Given API to use PUT pet
    When I make a PUT call with a random Pet ID, name, status, and tag
    Then I validate the response from the PUT API

  Scenario: Test 3 - Get details of a pet by ID
    Given the API to get pet details by ID
    When I validate the put id JSON schema
    Then Validate the name, status and tag of the pet
