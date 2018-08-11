package me.kholmukhamedov.soramitsutest.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.kholmukhamedov.soramitsutest.data.remote.ApiService;
import me.kholmukhamedov.soramitsutest.data.repository.RepositoryImpl;
import me.kholmukhamedov.soramitsutest.domain.Interactor;
import me.kholmukhamedov.soramitsutest.domain.Repository;
import me.kholmukhamedov.soramitsutest.models.converter.AbstractConverter;
import me.kholmukhamedov.soramitsutest.models.converter.DataToDomainConverter;
import me.kholmukhamedov.soramitsutest.models.converter.DomainToPresentationConverter;
import me.kholmukhamedov.soramitsutest.models.data.FeedBean;
import me.kholmukhamedov.soramitsutest.models.domain.Item;
import me.kholmukhamedov.soramitsutest.models.presentation.ItemModel;
import me.kholmukhamedov.soramitsutest.presentation.main.presenter.MainPresenter;
import me.kholmukhamedov.soramitsutest.presentation.utils.RxSchedulerProvider;
import me.kholmukhamedov.soramitsutest.presentation.utils.RxSchedulerProviderImpl;
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
    Picasso providePicasso(@NonNull Context context) {
        return Picasso.with(context);
    }

    @Provides
    RxSchedulerProvider provideRxSchedulerProvider() {
        return new RxSchedulerProviderImpl();
    }

    //region Converters
    @Provides
    AbstractConverter<FeedBean.Item, Item> provideDataToDomainConverter() {
        return new DataToDomainConverter();
    }

    @Provides
    AbstractConverter<Item, ItemModel> provideDomainToDataConverter() {
        return new DomainToPresentationConverter();
    }
    //endregion

    @Provides
    ApiService provideApiService(@NonNull Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    Repository provideRepository(@NonNull ApiService apiService,
                                 @NonNull AbstractConverter<FeedBean.Item, Item> converter) {
        return new RepositoryImpl(apiService, converter);
    }

    @Provides
    Interactor provideInteractor(@NonNull Repository repository) {
        return new Interactor(repository);
    }

    @Provides
    @Singleton
    MainPresenter provideMainPresenter(@NonNull Interactor interactor,
                                       @NonNull AbstractConverter<Item, ItemModel> converter,
                                       @NonNull RxSchedulerProvider rxSchedulerProvider) {
        return new MainPresenter(interactor, converter, rxSchedulerProvider);
    }

}
