package me.kholmukhamedov.soramitsutest.models.converter;

import me.kholmukhamedov.soramitsutest.models.data.FeedBean;
import me.kholmukhamedov.soramitsutest.models.domain.Item;

/**
 * Converter from data layer bean to domain layer entity
 */
public final class DataToDomainConverter extends AbstractConverter<FeedBean.Item, Item> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Item convert(FeedBean.Item item) {
        return new Item(
                item.getTitle(),
                item.getMedia().getM()
        );
    }

}
