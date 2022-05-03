Feature: log in
  Background:
    Given navigate to the website

  Scenario Outline: Positive Login
    When enter "<username>" and "<password>" and click LOGIN button
    Then PRODUCTS page displays

    Examples:
      | username                | password     |
      | standard_user           | secret_sauce |
      | problem_user            | secret_sauce |
      | performance_glitch_user | secret_sauce |


  Scenario Outline: Negative Login
    When enter "<username>" and "<password>" and click LOGIN button
    Then Login failure with "<error>"

    Examples:
      | username        | password     | error                                                                     |
      | standard_user   |              | Epic sadface: Password is required                                        |
      |                 | secret_sauce | Epic sadface: Username is required                                        |
      | locked_out_user | a            | Epic sadface: Username and password do not match any user in this servic  |
      | locked_out_user | secret_sauce | Epic sadface: Sorry, this user has been locked out.                       |
      | b               | secret_sauce | Epic sadface: Username and password do not match any user in this service |
      |                 |              | Epic sadface: Username is required                                        |

  Scenario: Positive Login by reading from external xml file
    Then use login info from external xml file to login