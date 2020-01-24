package com.cleanup.todoc.Repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.Database.DAO.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDao mTaskDao;

    public TaskDataRepository(TaskDao taskDao){mTaskDao=taskDao;}

    //Get
    public LiveData<List<Task>> getTask(long projectiD){return mTaskDao.getTask(projectiD);}

    //Create
    public void createTask(Task task){mTaskDao.insertTask(task);}

    //Update
    public void updateTask(Task task){mTaskDao.updateTask(task);}

    //Delete
    public void deleteTask(long taskId){mTaskDao.deleteTask(taskId);}

}
