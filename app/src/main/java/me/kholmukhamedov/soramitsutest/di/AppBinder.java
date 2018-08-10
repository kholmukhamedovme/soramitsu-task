package me.kholmukhamedov.soramitsutest.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.kholmukhamedov.soramitsutest.presentation.view.MainActivity;
import me.kholmukhamedov.soramitsutest.presentation.view.list.ListFragment;

@Module
public abstract class AppBinder {

    @ContributesAndroidInjector(modules = AppModule.class)
    public abstract MainActivity contributeMainActivityInjector();

    @ContributesAndroidInjector(modules = {AppModule.class, ListModule.class})
    public abstract ListFragment contributeListFragmentInjector();

}
