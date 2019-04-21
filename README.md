# Exam 1
1. The first part is a TextView displaying the current balance

2. The second part is for entering money adding/spending activities with
 A text box for the date
+ A text box for the amount
+ A text box for the spending purpose or the source of added money 
+ A button with caption "+" for adding money to the account
+ A button with caption "-" for spending money from the account.

3. The third part is a TextView displaying the historical activities. Each activity is written as a line, starting with Added (for adding money activities) or Spent (for spending activities), then the amount added/spent, the date of activity, and the spending purpose or the source of added money. The sketch displays a sample history.

Task 1. When the user entering information in the second part and click on one of the two buttons, the app needs to update the current balance and adds a new line describing the activity to the history.

Task 2. You need to save the current balance and the history using SharedPreferences and each time the app is loaded you will display such information from the last run.
