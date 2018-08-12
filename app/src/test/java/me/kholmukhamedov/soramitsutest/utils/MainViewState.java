package me.kholmukhamedov.soramitsutest.utils;

import com.arellomobile.mvp.viewstate.MvpViewState;

import java.util.ArrayList;
import java.util.List;

import me.kholmukhamedov.soramitsutest.models.presentation.ItemModel;
import me.kholmukhamedov.soramitsutest.presentation.view.MainView;

public class MainViewState extends MvpViewState<MainView> implements MainView {

    private List<ItemModel> mItemList;
    private ItemModel mItem;
    private boolean mIsItemHide;
    private int mMessage;

    public MainViewState() {
        mItemList = new ArrayList<>();
    }

    @Override
    public void onItemsLoaded(List<ItemModel> items) {
        mItemList.clear();
        mItemList.addAll(items);
    }

    @Override
    public void onItemShow(ItemModel item) {
        mItem = item;
    }

    @Override
    public void onItemHide() {
        mIsItemHide = true;
    }

    @Override
    public void showError(int message) {
        mMessage = message;
    }

    public List<ItemModel> getItemList() {
        return mItemList;
    }

    public ItemModel getItem() {
        return mItem;
    }

    public boolean isItemHide() {
        return mIsItemHide;
    }

    public int getMessage() {
        return mMessage;
    }

}
