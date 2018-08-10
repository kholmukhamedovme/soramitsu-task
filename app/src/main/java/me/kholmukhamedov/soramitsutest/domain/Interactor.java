package me.kholmukhamedov.soramitsutest.domain;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import me.kholmukhamedov.soramitsutest.models.domain.Item;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Interactor for fetching content from Flickr
 */
public final class Interactor {

    private final Repository mRepository;

    /**
     * Inject dependencies
     *
     * @param repository repository for fetching content from Flickr
     */
    public Interactor(@NonNull Repository repository) {
        mRepository = checkNotNull(repository, "Repository is required");
    }

    /**
     * Request list of feed items in domain layer entities
     *
     * @return Single RxJava source with list of {@link Item} entity
     */
    public Single<List<Item>> requestItems() {
        return mRepository.getItems();
    }

}
