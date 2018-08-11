package me.kholmukhamedov.soramitsutest.presentation.view;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import me.kholmukhamedov.soramitsutest.models.presentation.ItemModel;
import me.kholmukhamedov.soramitsutest.presentation.utils.AddToEndSingleByTagStateStrategy;

/**
 * Interface for activity/fragment to implement in order to interact with presenter
 */
public interface MainView extends MvpView {

    String ITEM_FRAGMENT_TAG = "itemFragmentTag";

    /**
     * Reaction on loading list of items
     *
     * @param items list of items in presentation layer models
     */
    @StateStrategyType(AddToEndSingleStrategy.class)
    void onItemsLoaded(List<ItemModel> items);

    /**
     * Reaction to show item event
     *
     * @param item item in presentation layer model
     */
    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = ITEM_FRAGMENT_TAG)
    void onItemShow(ItemModel item);

    /**
     * Reaction to hide item event
     */
    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = ITEM_FRAGMENT_TAG)
    void onItemHide();

    /**
     * Reaction to error event
     *
     * @param message error message
     */
    @StateStrategyType(SkipStrategy.class)
    void showError(@StringRes int message);

}
