package me.kholmukhamedov.soramitsutest.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public final class AppModule {

    @Provides
    Context provideApplicationContext(App application) {
        return application.getApplicationContext();
    }

}
