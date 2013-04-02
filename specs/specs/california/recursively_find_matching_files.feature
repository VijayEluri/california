
Scenario: finds matching files

    Given a directory 'testdata'
    Then it should contain 3 files with suffix 'txt'
    Then it should contain 1 file with suffix 'properties'