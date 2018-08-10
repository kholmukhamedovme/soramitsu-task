package me.kholmukhamedov.soramitsutest.models.converter;

import me.kholmukhamedov.soramitsutest.models.domain.Item;
import me.kholmukhamedov.soramitsutest.models.presentation.ItemModel;

/**
 * Converter from data layer bean to domain layer entity
 */
public final class DomainToPresentationConverter extends AbstractConverter<Item, ItemModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemModel convert(Item item) {
        return new ItemModel(
                item.getTitle(),
                item.getImageUrl()
        );
    }

}
