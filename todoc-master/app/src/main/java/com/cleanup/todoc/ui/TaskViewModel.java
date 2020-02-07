package com.cleanup.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.cleanup.todoc.Repositories.ProjectDataRepository;
import com.cleanup.todoc.Repositories.TaskDataRepository;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    private final ProjectDataRepository mProjectDataRepository;
    private final TaskDataRepository mTaskDataRepository;
    private final Executor mExecutor;

    public TaskViewModel(ProjectDataRepository projectDataRepository, TaskDataRepository taskDataRepository, Executor executor){
        this.mProjectDataRepository=projectDataRepository;
        this.mTaskDataRepository=taskDataRepository;
        this.mExecutor=executor;
    }

    //Data
    @Nullable
    private LiveData<Project> mProject;

    public void init(long projectId){
        if (mProject==null){
            mProject=mProjectDataRepository.getProject(projectId);
        }
    }

    //Get all project
    public LiveData<List<Project>> getAllProject(){return mProjectDataRepository.getAllProjects();}

    // Get tasks

    public LiveData<List<Task>> getTask(long projectId){
        return mTaskDataRepository.getTask(projectId);
    }

    //Get all tasks
    public LiveData<List<Task>> getAllTasks(){
        return mTaskDataRepository.getAllTasks();
    }

    //updateTask
    public void updateTask(Task task){
        mExecutor.execute(()->mTaskDataRepository.updateTask(task));
    }

    //createTask
    public void createTask(Task task){
        mExecutor.execute(()->mTaskDataRepository.createTask(task));
    }

    //DeleteTask
    public void deleteTask(long taskId){
        mExecutor.execute(()->mTaskDataRepository.deleteTask(taskId));
    }
}
