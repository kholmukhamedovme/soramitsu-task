package me.kholmukhamedov.soramitsutest.models.converter;

import android.support.annotation.NonNull;

import me.kholmukhamedov.soramitsutest.models.data.FeedBean;
import me.kholmukhamedov.soramitsutest.models.domain.Item;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Converter from data layer bean to domain layer entity
 */
public final class DataToDomainConverter extends AbstractConverter<FeedBean.Item, Item> {

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public Item convert(@NonNull FeedBean.Item item) {
        checkNotNull(item, "FeedBean.Item must be non-null");

        return new Item(
                item.getTitle(),
                item.getMedia().getM()
        );
    }

}
