Meta:

Narrative:
As a user
I want to add  my interview
So that I can review them and get prepared for future interviews

Scenario: The user fills in the fields on the page, and then click add interview
Given the user login site with a 'test@email.com' and 'password' and go to the page add the interview
When the user submits form with 'city1', 'company1', 'Junior Software Engineer', 'APL','feedback'
Then they should view field interview added


