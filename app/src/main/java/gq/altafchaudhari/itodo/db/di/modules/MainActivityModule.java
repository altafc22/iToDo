package gq.altafchaudhari.itodo.db.di.modules;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import gq.altafchaudhari.itodo.ui.MainActivity;


@Module
public abstract class MainActivityModule {
    @ContributesAndroidInjector()
    abstract MainActivity contributeMainActivity();
}
