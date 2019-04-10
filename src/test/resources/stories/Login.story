Meta:

Narrative:
As a user
I want to perform login on facebook
So that I can see my news feed

Scenario: Scenario Login Success
Given I open Facebook page
When I Input email and password
When I click button login
Then I got to my news feed

Scenario: Scenario Login Failed
Meta: @failed
Given I open Facebook page
When I Input invalid email and password
When I click button login
Then I got error message invalid username or password






