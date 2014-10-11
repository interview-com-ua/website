Meta:

Narrative:
As a user
I want to list all my questions
So that I can review them and get prepared for future interviews

Scenario: Print out message when questions list is empty
Given the user is on MyQuestions page
When do nothing
Then they should empty question list message

Scenario: Print out questions list when it is not empty
Given the user is on MyQuestions page
When do nothing
Then they should see question list
