Feature: New User Api Function

  Background:
    * url 'http://localhost:8080/api/user/register'

  Scenario: Register a new User
    Given request { "email": "prem@gmail.com", "role": "OWNER", "name": "prem" }
    When method POST
    Then status 200
    Then match response ==
      """
      {
        "id": "#uuid",
        "name": "prem",
        "email": "prem@gmail.com",
        "role": "OWNER"
      }
      """