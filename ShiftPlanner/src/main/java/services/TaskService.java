package services;

import dao.TaskDao;
import domain.Shift;
import domain.Task;

import java.sql.SQLException;
import java.util.ArrayList;

public class TaskService {

    private TaskDao taskDao;

    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public ArrayList<Task> getTasksByShift(Shift shift) {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            tasks = taskDao.getTasksByShift(shift);
        } catch (SQLException throwables) {

        }
        return tasks;
    }

    public void addTask(Task task) {
        try {
            taskDao.addTask(task);
        } catch (SQLException throwables) {
            
        }
    }
}
