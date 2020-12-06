Feature: English to German

  Scenario: Get all words
    When I make a GET call to uri /words
    Then the response should be 200

  Scenario: Create and Get Words
    When I make a POST call to uri /words with body
    """
    {
      "english" : "Ten",
      "german" : "Zehn"
    }
    """
    Then the response should be 200
    When I make a GET call to uri /words
    Then the response should be 200
    And the response matches
      | path               | matcher | expected |
      | $.total            | isInt   | 1        |
      | $.items[0].english | is      | Ten      |
      | $.items[0].german  | is      | Zehn     |
      | $.items[0].id      | notNull |          |