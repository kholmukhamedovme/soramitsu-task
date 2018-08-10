package me.kholmukhamedov.soramitsutest.presentation.main.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import me.kholmukhamedov.soramitsutest.models.presentation.ItemModel;

/**
 * Interface for activity/fragment to implement in order to interact with presenter
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    /**
     * Reaction on loading list of items
     *
     * @param items list of items in presentation layer models
     */
    void onItemsLoaded(List<ItemModel> items);

    /**
     * Reaction to show item event
     *
     * @param item item in presentation layer model
     */
    void onItemShow(ItemModel item);

    /**
     * Reaction to hide item event
     */
    void onItemHide();

}
