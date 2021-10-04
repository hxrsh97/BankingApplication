# Banking Application

### Prerequisites

- JDK 11 or above
- Maven 3.6 or above

### How to Run

- Navigate to the root folder of the application
- Run `mvn clean package`
- Navigate to the target folder with `cd target`
- Run the command `java -jar BankingApplication-1.0.jar`
- The application will now be running on `http://localhost:8080`

### Initial Data

Under `com.banking.DataLoader` some initial data is loaded so that the application can be tested manually and with end to end tests

Two accounts under the IDs `19c0bb3a-221c-4254-a303-807ffd12750e` and `19fc08e9-a389-45a8-bb06-2cdfe7ad27e6` have been initialised and can be used to test the APIs listed in this README

Account 1, the former of the IDs above, has been given an initial starting balance of `10` 

### APIs

- `/api/v1/statement/generate` can be used with a GET HTTP request with an account ID as a request parameter. This will generate a statement with the latest balance for that account along with a list of all transactions for that account.

    e.g `http://localhost:8080/api/v1/statement/generate?accountId={accountId}`


- `/api/v1/transaction/send` can be used with a POST HTTP request to indicate a transaction between a creditor and a debtor. The body of this request should be in JSON format as follows (note amount is a number value):

  
  - "{"creditorId": "{creditorId}", "debtorId": "{debtorId}", "amount": {amount}}" 

    e.g `http://localhost:8080/api/v1/transaction/send`
 
### Running End-to-End Test

Under the `com.banking.integration` directory the test `EndToEnd` contains an automated test which uses the initial accounts to test balance transfers.

It first uses the statement API to get the balance of the creditor account, then it sends half of its balance to the debtor account using the transaction API.
Finally, it checks the debtor account to see that the balance has been updated and it has a new value of the value that the creditor sent to it.


It also checks the transaction list from the creditor to check that the transaction to the debtor has been posted.
