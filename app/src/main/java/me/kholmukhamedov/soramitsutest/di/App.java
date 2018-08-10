package me.kholmukhamedov.soramitsutest.di;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public final class App extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }

}
