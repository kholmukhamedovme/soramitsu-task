package me.kholmukhamedov.soramitsutest.presentation.view.list;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import me.kholmukhamedov.soramitsutest.models.presentation.ItemModel;

/**
 * Interface for activity/fragment to implement in order to interact with presenter
 */
public interface ListView extends MvpView {

    /**
     * Reaction on loading list of items
     *
     * @param items list of items in presentation layer models
     */
    void onItemsLoaded(List<ItemModel> items);

}
