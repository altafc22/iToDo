package gq.altafchaudhari.itodo.db;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import gq.altafchaudhari.itodo.model.ToDo;

@Dao
public interface ToDoDao {
    @Insert
    void insert(ToDo todo);

    @Update
    void update(ToDo todo);

    @Delete
    void delete(ToDo todo);

    @Query("DELETE FROM todo_table")
    void deleteAllTodos();

    @Query("SELECT * FROM todo_table ORDER BY id DESC")
    LiveData<List<ToDo>> getAllTodos();

}
