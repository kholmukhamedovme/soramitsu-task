package me.kholmukhamedov.soramitsutest.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.kholmukhamedov.soramitsutest.di.component.MainComponent;

@Module(subcomponents = MainComponent.class)
public class AppModule {

    private Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @Singleton
    @Provides
    Context provideApplicationContext() {
        return mContext.getApplicationContext();
    }

}
