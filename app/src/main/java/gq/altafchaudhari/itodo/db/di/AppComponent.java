package gq.altafchaudhari.itodo.db.di;


import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import gq.altafchaudhari.itodo.ToDoApp;
import gq.altafchaudhari.itodo.db.di.modules.AddToDoModule;
import gq.altafchaudhari.itodo.db.di.modules.DetailActivityModule;
import gq.altafchaudhari.itodo.db.di.modules.EditToDoModule;
import gq.altafchaudhari.itodo.db.di.modules.MainActivityModule;

@Singleton

@Component(modules = {
        AndroidInjectionModule.class,
        MainActivityModule.class,
        DetailActivityModule.class,
        AddToDoModule.class,
        EditToDoModule.class

})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }
    void inject(ToDoApp todoApp);
}
