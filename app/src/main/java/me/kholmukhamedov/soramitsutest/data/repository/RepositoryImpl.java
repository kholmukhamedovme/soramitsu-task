package me.kholmukhamedov.soramitsutest.data.repository;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import me.kholmukhamedov.soramitsutest.data.remote.ApiService;
import me.kholmukhamedov.soramitsutest.domain.Repository;
import me.kholmukhamedov.soramitsutest.models.converter.AbstractConverter;
import me.kholmukhamedov.soramitsutest.models.converter.DataToDomainConverter;
import me.kholmukhamedov.soramitsutest.models.data.FeedBean;
import me.kholmukhamedov.soramitsutest.models.domain.Item;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Repository for fetching content from Flickr
 */
public final class RepositoryImpl implements Repository {

    private final ApiService mApiService;
    private final AbstractConverter<FeedBean.Item, Item> mConverter;

    /**
     * Injects dependencies and initializes converter
     *
     * @param apiService HTTP API for Flickr service
     */
    public RepositoryImpl(@NonNull ApiService apiService) {
        mApiService = checkNotNull(apiService, "ApiService is required");
        mConverter = new DataToDomainConverter();
    }

    /**
     * Get list of feed items from data layer bean and convert them to domain layer entities
     *
     * @return Single RxJava source with list of {@link Item} entity
     */
    @Override
    public Single<List<Item>> getItems() {
        return mApiService.getFeed()
                .map(FeedBean::getItems)
                .map(mConverter::convertList);
    }

    /**
     * Get list of feed items from data layer bean and convert them to domain layer entities
     *
     * @param tag tag string
     * @return Single RxJava source with list of {@link Item} entity
     */
    @Override
    public Single<List<Item>> getItems(String tag) {
        return mApiService.getFeed(tag)
                .map(FeedBean::getItems)
                .map(mConverter::convertList);
    }

}
