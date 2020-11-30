package domain;

import dao.ShiftDao;

import java.sql.SQLException;
import java.util.ArrayList;

public class ShiftService {

    private ShiftDao shiftDao;

    public ShiftService(ShiftDao shiftDao) {
        this.shiftDao = shiftDao;
    }

    public ArrayList<Shift> getShiftsByEmployee(Employee employee) {
        ArrayList<Shift> shiftsByEmployee = new ArrayList<>();
        try {
            shiftsByEmployee = shiftDao.getShiftsByEmployee(employee);
        } catch (SQLException throwables) {
            // :D
        }
        return shiftsByEmployee;
    }

    public void addShift(Shift shift) {
        try {
            shiftDao.addShift(shift);
        } catch (SQLException throwables) {
            
        }
    }

}
