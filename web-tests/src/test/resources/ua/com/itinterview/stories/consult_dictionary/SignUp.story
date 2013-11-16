Meta:

Narrative:
As a user
I want to perform an action
So that I can achieve a business goal

Scenario: Registering user with correct data
Given the user is on the registration page
When the user submits form with 'testName', 'user@example.com', 'password', 'password'
Then they should see the destination page


Scenario: Registering user with incorrect data
Given the user is on the registration page
When the user submits form with '<name>', '<email>', '<password>', '<confirmPassword>'
Then they should see the error messages '<nameError>', '<emailError>', '<passwordError>', '<confirmPasswordError>'

Examples:
| name          | email           | password | confirmPassword | nameError            | emailError         | passwordError      | confirmPasswordError |
|               |                 |          |                 | Укажите непустое имя | Укажите email      | Минимум 6 символов | Минимум 6 символов   |
| John Smith    | bbb             |          |                 |                      | Неправильный email | Минимум 6 символов | Минимум 6 символов   |
| John Smith    | bbb@example.com | 1111     | 2222            |                      |                    | Минимум 6 символов | Подтверждение пароля не совпадает с паролем |
