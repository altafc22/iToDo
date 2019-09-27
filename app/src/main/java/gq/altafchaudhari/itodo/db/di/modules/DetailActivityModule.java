package gq.altafchaudhari.itodo.db.di.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import gq.altafchaudhari.itodo.ui.DetailActivity;


@Module
public abstract class DetailActivityModule {
    @ContributesAndroidInjector()
    abstract DetailActivity contributeDetailActivity();
}