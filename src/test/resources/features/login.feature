Feature: User login


  @login
Scenario Outline: user login
  Given the user is in login page
  When the user enters valid login details "<username>" and "<password>"
  Then the user should be directed to user home page

    Examples:
      | username    | password  |
      | john   | demo   |
      | surya  | 123   |
      | john   | demo   |