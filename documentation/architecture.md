# Architecture

## Program Structure

* Package "domain" contains objects used in the app
* Package "dao" contains data access objects
  * All communication with database is here
  * Database.java makes the connection and passes it to daos
* Package "services" communicates with daos 
  * Service classes get data from daos and modify it for UI

### User interface 

Has two views
  * View and Manage
    * Add shifts
    * Add employees
    * See a list of shifts for every employee
  * Employee info
    * Info about the employee selected
    * Bar graph on time worked
    
These two are implemented as separate Scenes and "View and Manage" is the main view that pops up when App is started. UI is implemented using java fxml. The fxml files are in the resources-directory and controllers can be found in shiftplanner package. User Interface has been separated from logic by using service classes.

When a form is filled and user presses the add button all the information is passed to a method in a service class. 

<img src=https://github.com/LauriKajakko/ot-harjoitustyo/blob/main/documentation/images/sequence_week5.png />

### Application logic

Services do all the logic in the application. 

For example showing the data of one employee when yser selects an employee and presses "Employee info":
 * ui calls "getLastWorkDays(Employee employee, int days)" in EmployeeService class
  * method gets all shifts and selects the amount of days to show
  * method does all the conversion logic for calculating the time


Services are between UI and DAOs and convert the data for the user to be easily readable. The package Domain contains only the object that represent different of types of information in the application.

<img src=https://github.com/LauriKajakko/ot-harjoitustyo/blob/main/documentation/images/ClassArchitecture.png />


### Storing the information

Package dao:
 * EmployeeDao
   * Stores Employees and their information
 * ShiftDao
   * Stores Shifts and their information
 * TaskDao
   * Stores Tasks and their information
 * Database
   * Makes the connection to the database and checks if it exists
   * Creates database if needed
   * Returns the connection for DAOs
  
The dates are stored as dd-mm-yyyy because LocalDate in java's own libraries use that format. Times are in hh:mm. 

 

