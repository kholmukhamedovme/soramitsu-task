package me.kholmukhamedov.soramitsutest.presentation.view.grid;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import me.kholmukhamedov.soramitsutest.models.presentation.ItemModel;

/**
 * Interface for activity/fragment to implement in order to interact with presenter
 */
public interface GridView extends MvpView {

    /**
     * Reaction on loading list of items
     *
     * @param items list of items in presentation layer models
     */
    @StateStrategyType(AddToEndSingleStrategy.class)
    void onItemsLoaded(List<ItemModel> items);

}
