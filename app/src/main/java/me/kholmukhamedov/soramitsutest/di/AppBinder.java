package me.kholmukhamedov.soramitsutest.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.kholmukhamedov.soramitsutest.presentation.view.MainActivity;

@Module
public abstract class AppBinder {

    @ContributesAndroidInjector(modules = AppModule.class)
    public abstract MainActivity contributeMainActivityInjector();

}
