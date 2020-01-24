package com.cleanup.todoc.Injections;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.cleanup.todoc.Repositories.ProjectDataRepository;
import com.cleanup.todoc.Repositories.TaskDataRepository;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.ui.TaskViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {


    private final TaskDataRepository mTaskDataSource;
    private final ProjectDataRepository mProjectDataSource;
    private final Executor executor;

    public ViewModelFactory(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor) {
        this.mProjectDataSource=projectDataSource;
        this.mTaskDataSource=taskDataSource;
        this.executor = executor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(mProjectDataSource, mTaskDataSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
