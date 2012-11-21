
Feature: Google Tests

    Scenario: search google
        Given I am on the 'google homepage'
        And I type 'node'
        Then I should see 10 results

    Scenario: search google again
        Given I am on the 'google homepage'
        And I type 'chicken'
        Then I should see 10 results

