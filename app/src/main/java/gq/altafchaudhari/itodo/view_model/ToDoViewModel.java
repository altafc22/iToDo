package gq.altafchaudhari.itodo.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import gq.altafchaudhari.itodo.model.ToDo;
import gq.altafchaudhari.itodo.repository.ToDoRepository;

public class ToDoViewModel extends AndroidViewModel {
    private ToDoRepository repository;
    private LiveData<List<ToDo>> todoList;

    public ToDoViewModel(@NonNull Application application) {
        super(application);
        repository = new ToDoRepository(application);
        todoList = repository.getAllTodos();
    }
    public void insert(ToDo todo){
        repository.insert(todo);
    }
    public void update(ToDo todo){
        repository.update(todo);
    }
    public void delete(ToDo todo){
        repository.delete(todo);
    }
    public void deleteAll(){
        repository.deleteAllTodos();
    }

    public LiveData<List<ToDo>> getTodoList() {
        return todoList;
    }

}
