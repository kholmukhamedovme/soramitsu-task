package me.kholmukhamedov.soramitsutest.models.converter;

import android.support.annotation.NonNull;

import me.kholmukhamedov.soramitsutest.models.domain.Item;
import me.kholmukhamedov.soramitsutest.models.presentation.ItemModel;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Converter from data layer bean to domain layer entity
 */
public final class DomainToPresentationConverter extends AbstractConverter<Item, ItemModel> {

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public ItemModel convert(@NonNull Item item) {
        checkNotNull(item, "Item must be non-null");

        return new ItemModel(
                item.getTitle(),
                item.getImageUrl()
        );
    }

}
