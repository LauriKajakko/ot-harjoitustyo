# Architecture

## Program Structure

<img src=https://github.com/LauriKajakko/ot-harjoitustyo/blob/main/documentation/images/ClassArchitecture.png />



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

