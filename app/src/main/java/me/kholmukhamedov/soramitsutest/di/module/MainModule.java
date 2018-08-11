package me.kholmukhamedov.soramitsutest.di.module;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import me.kholmukhamedov.soramitsutest.data.remote.ApiService;
import me.kholmukhamedov.soramitsutest.data.repository.RepositoryImpl;
import me.kholmukhamedov.soramitsutest.di.scope.MainScope;
import me.kholmukhamedov.soramitsutest.domain.Interactor;
import me.kholmukhamedov.soramitsutest.domain.Repository;
import me.kholmukhamedov.soramitsutest.models.converter.AbstractConverter;
import me.kholmukhamedov.soramitsutest.models.converter.DataToDomainConverter;
import me.kholmukhamedov.soramitsutest.models.converter.DomainToPresentationConverter;
import me.kholmukhamedov.soramitsutest.models.data.FeedBean;
import me.kholmukhamedov.soramitsutest.models.domain.Item;
import me.kholmukhamedov.soramitsutest.models.presentation.ItemModel;
import me.kholmukhamedov.soramitsutest.presentation.presenter.MainPresenter;
import me.kholmukhamedov.soramitsutest.presentation.utils.RxSchedulerProvider;
import retrofit2.Retrofit;

@Module
public final class MainModule {

    //region Converters
    @MainScope
    @Provides
    AbstractConverter<FeedBean.Item, Item> provideDataToDomainConverter() {
        return new DataToDomainConverter();
    }

    @MainScope
    @Provides
    AbstractConverter<Item, ItemModel> provideDomainToDataConverter() {
        return new DomainToPresentationConverter();
    }
    //endregion

    //region Data layer
    @MainScope
    @Provides
    ApiService provideApiService(@NonNull Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @MainScope
    @Provides
    Repository provideRepository(@NonNull ApiService apiService,
                                 @NonNull AbstractConverter<FeedBean.Item, Item> converter) {
        return new RepositoryImpl(apiService, converter);
    }
    //endregion

    //region Domain layer
    @MainScope
    @Provides
    Interactor provideInteractor(@NonNull Repository repository) {
        return new Interactor(repository);
    }
    //endregion

    //region Presentation layer
    @MainScope
    @Provides
    MainPresenter provideMainPresenter(@NonNull Interactor interactor,
                                       @NonNull AbstractConverter<Item, ItemModel> converter,
                                       @NonNull RxSchedulerProvider rxSchedulerProvider) {
        return new MainPresenter(interactor, converter, rxSchedulerProvider);
    }
    //endregion

}
