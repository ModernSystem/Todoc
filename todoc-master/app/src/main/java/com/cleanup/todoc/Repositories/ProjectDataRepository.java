package com.cleanup.todoc.Repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.Database.DAO.ProjectDao;
import com.cleanup.todoc.model.Project;

public class ProjectDataRepository {

    private final ProjectDao mProjectDao;

    public ProjectDataRepository(ProjectDao projectDao){mProjectDao=projectDao;}

    //Getter

    public LiveData<Project> getProject(long projectID){return mProjectDao.getProject(projectID);}
}
