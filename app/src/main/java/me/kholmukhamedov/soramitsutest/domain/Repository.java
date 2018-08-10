package me.kholmukhamedov.soramitsutest.domain;

import java.util.List;

import io.reactivex.Single;
import me.kholmukhamedov.soramitsutest.models.domain.Item;

/**
 * Repository for fetching content from Flickr
 */
public interface Repository {

    /**
     * Get list of feed items in domain layer entities
     *
     * @return Single RxJava source with list of {@link Item} entity
     */
    Single<List<Item>> getItems();

    /**
     * Get list of feed items by tag in domain layer entities
     *
     * @param tag tag string
     * @return Single RxJava source with list of {@link Item} entity
     */
    Single<List<Item>> getItems(String tag);

}
