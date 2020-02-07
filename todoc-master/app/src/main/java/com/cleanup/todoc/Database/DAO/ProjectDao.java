package com.cleanup.todoc.Database.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProject(Project project);

    @Query("SELECT*FROM Project where id=:projectId")
    LiveData<Project> getProject(long projectId);

    @Query("SELECT*FROM Project")
    LiveData<List<Project>> getAllProject();
}
