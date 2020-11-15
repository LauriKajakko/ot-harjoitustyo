package domain;

import java.util.ArrayList;

public class Employee {

    private String firstName;
    private String lastName;
    private ArrayList<String> roles;

    /**
     *
     * @param firstName employee's first name
     * @param lastName employee's last name
     * @param roles employee's roles at work
     */
    public Employee(String firstName, String lastName, ArrayList<String> roles){
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
    }

}
