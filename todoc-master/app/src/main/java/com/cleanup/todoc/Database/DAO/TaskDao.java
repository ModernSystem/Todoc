package com.cleanup.todoc.Database.DAO;

import android.arch.lifecycle.LiveData;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT*FROM Task WHERE projectId= :projectId")
    LiveData<List<Task>> getTask(long projectId);

    @Insert
    long insertTask(Task task);

    @Update
    int updateTask(Task task);

    @Query("DELETE FROM Task where id=:taskId")
    int deleteTask(long taskId);

    @Query("SELECT*FROM Task")
    LiveData<List<Task>> getAllTask();
}
