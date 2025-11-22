Feature: Registration
  @registration
  Scenario Outline:
    Given the user is in registration page
    When the user enters valid registration details "<FirstName>" "<LastName>" "<Address>" "<City>" "<State>" "<ZipCode>" "<PhoneNumber>" "<SSN>" "<Username>" "<Password>" "<ConfirmPassword>"
    And the user submits the registration form
    Then the user should be directed to registration success page

    Examples:
      | FirstName | LastName | Address        | City      | State   | ZipCode | PhoneNumber | SSN        | Username   | Password   | ConfirmPassword |
      | Dwayne      | Bravo      | 123 Main St   | Springfield| IL      | 62701   | 555-1234    | 123-45-6789| dwaynebravo    | password123| password123     |
      | Jane      | Smith    | 456 Oak Ave   | Metropolis | NY      | 10001   | 555-5678    | 987-65-4321| janesmith  | mypass456  | mypass456       |