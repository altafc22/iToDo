package gq.altafchaudhari.itodo.db.di.modules;



import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import gq.altafchaudhari.itodo.ui.AddToDoActivity;


@Module
public abstract class AddToDoModule {
    @ContributesAndroidInjector()
    abstract AddToDoActivity contributeAddToDoActivity();
}