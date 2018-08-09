package me.kholmukhamedov.soramitsutest.di;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public final class App extends DaggerApplication {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        mAppComponent = DaggerAppComponent
                .builder()
                .application(this)
                .build();

        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return mAppComponent;
    }

}
