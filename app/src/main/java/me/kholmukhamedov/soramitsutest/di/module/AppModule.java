package me.kholmukhamedov.soramitsutest.di.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.kholmukhamedov.soramitsutest.data.remote.ApiService;
import me.kholmukhamedov.soramitsutest.di.component.MainComponent;
import me.kholmukhamedov.soramitsutest.presentation.utils.RxSchedulerProvider;
import me.kholmukhamedov.soramitsutest.presentation.utils.RxSchedulerProviderImpl;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module(subcomponents = MainComponent.class)
public final class AppModule {

    private final Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @Singleton
    @Provides
    Context provideApplicationContext() {
        return mContext.getApplicationContext();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(ApiService.HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(
                        new ObjectMapper().registerModule(new KotlinModule())))
                .build();
    }

    @Singleton
    @Provides
    Picasso providePicasso(@NonNull Context context) {
        return Picasso.with(context);
    }

    @Singleton
    @Provides
    RxSchedulerProvider provideRxSchedulerProvider() {
        return new RxSchedulerProviderImpl();
    }

}
