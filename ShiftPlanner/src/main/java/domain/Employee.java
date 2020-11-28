package domain;

public class Employee {

    private String firstName;
    private String lastName;
    private String role;

    /**
     *
     * @param firstName employee's first name
     * @param lastName employee's last name
     * @param role employee's role at work
     */
    public Employee(String firstName, String lastName, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role=role;
    }
}
