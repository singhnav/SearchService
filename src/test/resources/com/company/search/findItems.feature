Feature: As a user I want to be able to search for search items by search item name

  Background:
    Given the following search items exist:
      |APPLE                |
      |APPLE CAKE           |
      |BANANA               |
      |LEMON                |
      |LEMONADE             |
      |POTATO               |
      |PASTA                |
      |GRAPES               |
      |WINE                 |


  Scenario: Search for APPLE
    Given the service is up an running
    And I am on a RESTFull client screen
    When I enter the search item name 'APPLE'
    Then the response code should be 200
    And the search should return a JSON response as following:'{"items":[{"name":"APPLE"},{"name":"APPLE CAKE"}]}'

  Scenario: Search for LEMON
    Given the service is up an running
    And I am on a RESTFull client screen
    When I enter the search item name 'LEMON'
    Then the response code should be 200
    And the search should return a JSON response as following:'{"items":[{"name":"LEMON"},{"name":"LEMONADE"}]}'


  Scenario: Search for GINGER
    Given the service is up an running
    And I am on a RESTFull client screen
    When the input 'GINGER'
    Then the search should return no characters and no items

  Scenario: service is down
    Given the service not is up an running
    And I am on a RESTFull client screen
    When I call the searchAll API
    Then the response code should be 503

  Scenario: api doesnt's exist
    Given the service is up an running
    And I am on a RESTFull client screen
    When I call the searchAllThatDoesNotExist API
    Then the response code should be 404

  Scenario: check health of the service
    Given the service is up an running
    And I am on a RESTFull client screen
    When I call the health API
    Then the response code should be 200
    And the response body should contain a JSON message telling that the service is UP