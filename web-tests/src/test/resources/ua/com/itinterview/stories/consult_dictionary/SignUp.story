Meta:

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

Scenario: Registering user with empty data
Given the user is on the registration page
When the user submits empty form
Then they should see the error message 'Укажите непустой логин' beside login field

Scenario: Registering user with invalid email
Given the user is on the registration page
When the user submits form with 'testLogin', 'testName', 'invalidEmail', '', ''
Then they should see the error message 'Неправильный email' beside email field

Scenario: Registering user with invalid password confirmation
Given the user is on the registration page
When the user submits form with 'testLogin', 'testName', 'invalidEmail', 'password', 'password2'
Then they should see the error message 'Подтверждение пароля не совпадает с паролем' beside password confirmation field

Scenario: Registering user with correct data
Given the user is on the registration page
When the user submits form with 'testLogin', 'testName', 'user@example.com', 'password', 'password'
Then they should see the destination page

