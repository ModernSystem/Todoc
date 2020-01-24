package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.Database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private TodocDatabase database;
    private static long PROJECT_ID=1L;
    private static Project PROJECT_DEMO=new Project(PROJECT_ID, "Projet Tartampion", 0xFFEADAD1);

    private static Task TASK_DEMO_1=new Task(PROJECT_ID,"Nettoyage",new Date().getTime() );
    private static Task TASK_DEMO_2=new Task(PROJECT_ID,"Balayage",new Date().getTime() );
    private static Task TASK_DEMO_3=new Task(PROJECT_ID,"Fenetres",new Date().getTime() );

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
        }

    @After
    public void closeDb() throws Exception{
        database.close();
    }

    @Test
    public void insertAndGetUser() throws InterruptedException {
        database.projectDao().insertProject(PROJECT_DEMO);
        Project project=TestUtils.getValue(database.projectDao().getProject(PROJECT_ID));
        assertTrue(  project.getId()==PROJECT_DEMO.getId()
        && project.getColor()==PROJECT_DEMO.getColor() && project.getName().equals(PROJECT_DEMO.getName()));
    }

    @Test
    public void insertAndGetTask() throws InterruptedException {
        //Init values
        database.projectDao().insertProject(PROJECT_DEMO);
        database.taskDao().insertTask(TASK_DEMO_1);
        database.taskDao().insertTask(TASK_DEMO_2);
        database.taskDao().insertTask(TASK_DEMO_3);

        List<Task> taskList=TestUtils.getValue(database.taskDao().getTask(PROJECT_ID));
        assertEquals(taskList.size(),3);

    }

    @Test
    public void insertAndUpdateTask() throws InterruptedException{
        database.projectDao().insertProject(PROJECT_DEMO);
        database.taskDao().insertTask(TASK_DEMO_1);
        Task taskToUpdate=TestUtils.getValue(database.taskDao().getTask(PROJECT_ID)).get(0);
        taskToUpdate.setName("Rangement");
        database.taskDao().updateTask(taskToUpdate);

        assertTrue(TestUtils.getValue(database.taskDao().getTask(PROJECT_ID)).size()==1
        && TestUtils.getValue(database.taskDao().getTask(PROJECT_ID)).get(0).getName().equals("Rangement"));
    }

    @Test
    public void insertAndDeleteTask()throws InterruptedException {

        database.projectDao().insertProject(PROJECT_DEMO);
        database.taskDao().insertTask(TASK_DEMO_1);
        database.taskDao().insertTask(TASK_DEMO_2);
        database.taskDao().insertTask(TASK_DEMO_3);
        database.taskDao().deleteItem(
                TestUtils.getValue(database.taskDao().getTask(PROJECT_ID))
                .get(2)
                .getId());

        List<Task> taskList=TestUtils.getValue(database.taskDao().getTask(PROJECT_ID));
        assertEquals(taskList.size(),2);
        assertFalse(TestUtils.getValue(database.taskDao().getTask(PROJECT_ID)).contains(TASK_DEMO_3));
    }
}

