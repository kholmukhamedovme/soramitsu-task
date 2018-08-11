package me.kholmukhamedov.soramitsutest.di;

import android.app.Application;

import me.kholmukhamedov.soramitsutest.di.component.AppComponent;
import me.kholmukhamedov.soramitsutest.di.component.DaggerAppComponent;
import me.kholmukhamedov.soramitsutest.di.component.MainComponent;
import me.kholmukhamedov.soramitsutest.di.module.AppModule;
import me.kholmukhamedov.soramitsutest.di.module.MainModule;

public final class App extends Application {

    private static AppComponent sAppComponent;
    private static MainComponent sMainComponent;

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    public static MainComponent getMainComponent() {
        return sMainComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this.getApplicationContext()))
                .build();

        sMainComponent = sAppComponent
                .mainComponentBuilder()
                .mainModule(new MainModule())
                .build();
    }

}
