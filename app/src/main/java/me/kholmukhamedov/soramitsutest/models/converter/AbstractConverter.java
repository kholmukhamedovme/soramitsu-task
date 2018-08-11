package me.kholmukhamedov.soramitsutest.models.converter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static dagger.internal.Preconditions.checkNotNull;

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
    @NonNull
    public abstract To convert(@NonNull From from);

    /**
     * Convert list
     *
     * @param fromList list of from entities
     * @return list of to entities
     */
    @NonNull
    public List<To> convertList(@NonNull List<From> fromList) {
        checkNotNull(fromList, "List must be non-null");

        if (fromList.isEmpty()) {
            return Collections.emptyList();
        }

        List<To> toList = new ArrayList<>();
        for (From from : fromList) {
            toList.add(convert(from));
        }
        return toList;
    }

}
