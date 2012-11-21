
Feature: Google Tests

    Scenario: search google
        Given I am on 'http://www.google.com'
        And I search for 'node'
        Then I should see 10 results

    Scenario: search google again
        Given I am on 'http://www.google.com'
        And I search for 'chicken'
        Then I should see 10 results

