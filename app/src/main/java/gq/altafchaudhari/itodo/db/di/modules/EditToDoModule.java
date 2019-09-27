package gq.altafchaudhari.itodo.db.di.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import gq.altafchaudhari.itodo.ui.EditToDoActivity;


@Module
public abstract class EditToDoModule {
    @ContributesAndroidInjector()
    abstract EditToDoActivity contributeEditTodoActivity();
}