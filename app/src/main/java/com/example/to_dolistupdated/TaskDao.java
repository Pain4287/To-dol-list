package com.example.to_dolistupdated;

// TaskDao.java
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks")
    List<TaskEntity> getAllTasks();

    @Query("SELECT * FROM tasks WHERE categoryId = :categoryId")
    List<TaskEntity> getTasksByCategory(long categoryId);

    @Insert
    void insertTask(TaskEntity task);

    @Update
    void updateTask(TaskEntity task);

    @Delete
    void deleteTask(TaskEntity task);
}
