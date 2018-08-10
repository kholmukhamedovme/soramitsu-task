package me.kholmukhamedov.soramitsutest.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.kholmukhamedov.soramitsutest.presentation.grid.view.GridFragment;
import me.kholmukhamedov.soramitsutest.presentation.item.view.ItemFragment;
import me.kholmukhamedov.soramitsutest.presentation.main.view.MainActivity;

@Module
public abstract class AppBinder {

    @ContributesAndroidInjector
    public abstract MainActivity contributeMainActivityInjector();

    @ContributesAndroidInjector
    public abstract GridFragment contributeListFragmentInjector();

    @ContributesAndroidInjector
    public abstract ItemFragment contributeItemFragmentInjector();

}
