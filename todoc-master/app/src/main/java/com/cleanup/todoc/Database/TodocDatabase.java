package com.cleanup.todoc.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.cleanup.todoc.Database.DAO.ProjectDao;
import com.cleanup.todoc.Database.DAO.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;


@Database(entities = {Project.class, Task.class},version = 1,exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

    public static volatile TodocDatabase INSTANCE;

    /**
     * DAO
     */

    public abstract ProjectDao projectDao();
    public abstract TaskDao taskDao();

    /**
     * Build or get database
     */

    public static TodocDatabase getInstance(Context context){
        if (INSTANCE==null) {
            synchronized (TodocDatabase.class) {
                if (INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                            TodocDatabase.class,"TodoDatabase.db")
                            .addCallback(prePopulateDatabase())
                            .build();
                }

            }
        }
        return INSTANCE;
    }

    private static Callback prePopulateDatabase(){
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                for (Project project : Project.getAllProjects()){
                    ContentValues contentValues=new ContentValues();

                    contentValues.put("id",project.getId());
                    contentValues.put("name",project.getName());
                    contentValues.put("color",project.getColor());

                    db.insert("Project",OnConflictStrategy.IGNORE,contentValues);

                }

            }
        };
    }
}

