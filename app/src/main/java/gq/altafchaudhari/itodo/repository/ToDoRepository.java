package gq.altafchaudhari.itodo.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import java.util.List;

import gq.altafchaudhari.itodo.db.AppDb;
import gq.altafchaudhari.itodo.db.ToDoDao;
import gq.altafchaudhari.itodo.model.ToDo;

public class ToDoRepository {
    private ToDoDao todoDao;
    private LiveData<List<ToDo>> allTodos;

    public ToDoRepository(Application application){
        AppDb database = AppDb.getAppDatabase(application);
        todoDao = database.todoDao();
        allTodos = todoDao.getAllTodos();
    }
    public void insert(ToDo todo){
        new InsertTodo(todoDao).execute(todo);
    }
    public void update(ToDo todo){
        new UpdateTodo(todoDao).execute(todo);
    }

    public void delete(ToDo todo){
        new DeleteTodo(todoDao).execute(todo);
    }
    public void deleteAllTodos(){
        new DeleteAllTodos(todoDao).execute();
    }

    public LiveData<List<ToDo>> getAllTodos() {
        return allTodos;
    }




    private static class InsertTodo extends AsyncTask<ToDo, Void, Void>{
        private ToDoDao todoDao;

        public InsertTodo(ToDoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(ToDo... todos) {
            todoDao.insert(todos[0]);
            return null;
        }
    }
    private static class UpdateTodo extends AsyncTask<ToDo, Void, Void>{
        private ToDoDao todoDao;

        public UpdateTodo(ToDoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(ToDo... todos) {
            todoDao.update(todos[0]);
            return null;
        }
    }

    private static class DeleteTodo extends AsyncTask<ToDo, Void, Void>{
        private ToDoDao todoDao;

        public DeleteTodo(ToDoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(ToDo... todos) {
            todoDao.delete(todos[0]);
            return null;
        }
    }
    private static class DeleteAllTodos extends AsyncTask<Void, Void, Void>{
        private ToDoDao todoDao;

        public DeleteAllTodos(ToDoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            todoDao.deleteAllTodos();
            return null;
        }
    }
}
