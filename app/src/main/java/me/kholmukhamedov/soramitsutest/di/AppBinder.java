package me.kholmukhamedov.soramitsutest.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.kholmukhamedov.soramitsutest.presentation.view.MainActivity;
import me.kholmukhamedov.soramitsutest.presentation.view.grid.GridFragment;

@Module
public abstract class AppBinder {

    @ContributesAndroidInjector
    public abstract MainActivity contributeMainActivityInjector();

    @ContributesAndroidInjector
    public abstract GridFragment contributeListFragmentInjector();

}
