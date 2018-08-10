package me.kholmukhamedov.soramitsutest.models.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract class for converting entities between layers
 *
 * @param <From> from entity class
 * @param <To>   to entity class
 */
public abstract class AbstractConverter<From, To> {

    /**
     * Convert
     *
     * @param from from entity
     * @return to entity
     */
    public abstract To convert(From from);

    /**
     * Convert list
     *
     * @param fromList list of from entities
     * @return list of to entities
     */
    public List<To> convertList(List<From> fromList) {
        if (fromList == null || fromList.isEmpty()) {
            return Collections.emptyList();
        }

        List<To> toList = new ArrayList<>();
        for (From from : fromList) {
            toList.add(convert(from));
        }
        return toList;
    }

}
