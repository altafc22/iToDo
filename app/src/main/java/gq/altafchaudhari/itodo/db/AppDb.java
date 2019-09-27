package gq.altafchaudhari.itodo.db;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import gq.altafchaudhari.itodo.model.ToDo;

@Database(entities = {ToDo.class}, version = 1)
public abstract class AppDb extends RoomDatabase {
    private static AppDb appDatabase;

    public abstract ToDoDao todoDao();

    public static synchronized AppDb getAppDatabase(Context context){
        if(appDatabase == null){
            appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    AppDb.class,"i_todo_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return appDatabase;
    }

    private static Callback roomCallback = new Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDb(appDatabase).execute();
        }
    };
    private static class PopulateDb extends AsyncTask<Void, Void, Void>{
        private ToDoDao todoDao;

        private PopulateDb(AppDb database){
            todoDao = database.todoDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //todoDao.insert(new ToDo("title 1","Description 1"));
            return null;
        }
    }

}
