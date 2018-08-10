package me.kholmukhamedov.soramitsutest.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import me.kholmukhamedov.soramitsutest.data.remote.ApiService;
import me.kholmukhamedov.soramitsutest.data.repository.RepositoryImpl;
import me.kholmukhamedov.soramitsutest.domain.Interactor;
import me.kholmukhamedov.soramitsutest.domain.Repository;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public final class AppModule {

    @Provides
    Context provideApplicationContext(App application) {
        return application.getApplicationContext();
    }

    @Provides
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(ApiService.HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(
                        new ObjectMapper().registerModule(new KotlinModule())))
                .build();
    }

    @Provides
    ApiService provideApiService(@NonNull Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    Repository provideRepository(@NonNull ApiService apiService) {
        return new RepositoryImpl(apiService);
    }

    @Provides
    Interactor provideInteractor(@NonNull Repository repository) {
        return new Interactor(repository);
    }

    @Provides
    Picasso providePicasso(@NonNull Context context) {
        return Picasso.with(context);
    }

}
