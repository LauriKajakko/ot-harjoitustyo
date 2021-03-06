package services;

import dao.ShiftDao;
import domain.Employee;
import domain.Shift;

import java.sql.SQLException;
import java.util.ArrayList;

public class ShiftService {

    private ShiftDao shiftDao;

    public ShiftService(ShiftDao shiftDao) {
        this.shiftDao = shiftDao;
    }

    /**
     * Gets all shifts by employee
     *
     * @param employee whose shifts are needed
     * @return arraylist of shifts
     */
    public ArrayList<Shift> getShiftsByEmployee(Employee employee) {
        ArrayList<Shift> shiftsByEmployee = new ArrayList<>();
        try {
            shiftsByEmployee = shiftDao.getShiftsByEmployee(employee);
        } catch (SQLException throwables) {

        }
        return shiftsByEmployee;
    }

    /**
     * Adds a shift to database using dao
     *
     * @param shift to add
     */
    public void addShift(Shift shift) {
        try {
            shiftDao.addShift(shift);
        } catch (SQLException throwables) {
            
        }
    }


    /**
     *  Service for editing a shifts time
     *
     * @param shift to edit
     * @param newFrom new beginning time
     * @param newTo new ending time
     */
    public void editShift(Shift shift, String newFrom, String newTo) {
        try {
            shiftDao.editShift(shift, newFrom, newTo);
        } catch (SQLException throwables) {

        }
    }

    /**
     * Service for deleting shift
     *
     * @param shift to delete
     */
    public void deleteShift(Shift shift) {
        try {
            shiftDao.deleteShift(shift);
        } catch (SQLException throwables) {

        }
    }

}
