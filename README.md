# Assignment
1. It will be serving some beverages.
2. Each beverage will be made using some ingredients.
3. Assume time to prepare a beverage is the same for all cases.
4. The quantity of ingredients used for each beverage can vary. Also, the same ingredient (ex:
water) can be used for multiple beverages.
5. There would be N ( N is an integer ) outlet from which beverages can be served.
6. Maximum N beverages can be served in parallel.
7. Any beverage can be served only if all the ingredients are available in terms of quantity.
8. There would be an indicator that would show which all ingredients are running low. We need
some methods to refill them.
9. Please provide functional integration test cases for maximum coverage.
JSON is present in src/test/resources/dunzo/coffeeMachine/{file.name} location

--------------------------------------------------------------------------------------------------------------------------------------------------------------------
Qus: How to run?
Ans: Download project -> Unzip -> import as Gradle project -> refresh gradle project -> run  App.java

Qus: how to run test cases ?
Ans: All the test cases are present in src/test/resources/dunzo/coffeeMachine/{file.name}. Change fileName (test1.json,test2.json...... test11.json) in App.java in        parseCoffeeMachine funtion. Now save the file ans run App.java

--------------------------------------------------------------------------------------------------------------------------------------------------------------------
Assumption : 
1. Outlet will consume remaining item data even if drink cannot be prepared. (ex. lets say hot_coffee needed 5KG milk but only 2 KG is present in inventory. In that case drink cannot be prepared but milk inventory will be 0 after running the test case as its an obvious behaviour of coffee machine).
2. machine have multiple CPUs
3. No of outlet will not cross max limit of Integer.
