Feature: Add products to cart and checkout
  Scenario Outline: And 2 most expensive items from the catalog to the cart and finish a checkout
    Given navigate to the website
    When enter "<username>" and "<password>" and click LOGIN button
    Then PRODUCTS page displays
    Given "<filter>" the products and add <items> to cart to CHECKOUT
    And enter "<firstname>", "<lastname>" and "<zipcode>" and continue
    Then check the total amount of <items> and press FINISH
    Then thank you for your order

    Examples:
      | username      | password     | filter              | items | firstname | lastname | zipcode |
      | standard_user | secret_sauce | Price (high to low) | 2     | standard  | user     | 123456  |