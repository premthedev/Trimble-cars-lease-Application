Feature: Car API Functional Tests

  Background:
    * url 'http://localhost:8080/api/car/register'

  Scenario: Register a new car
    Given request { "ownerId": "c9ae6d1d-4452-4009-825d-d2c6db68ec35", "make": "Toyota", "model": "innova", "year": 2023 }
    When method POST
    Then status 200

