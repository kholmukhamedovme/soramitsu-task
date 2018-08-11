package me.kholmukhamedov.soramitsutest.presentation.main.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;

import javax.inject.Inject;

import me.kholmukhamedov.soramitsutest.R;
import me.kholmukhamedov.soramitsutest.models.presentation.ItemModel;
import me.kholmukhamedov.soramitsutest.presentation.grid.view.GridFragment;
import me.kholmukhamedov.soramitsutest.presentation.item.view.ItemFragment;
import me.kholmukhamedov.soramitsutest.presentation.main.presenter.MainPresenter;
import me.kholmukhamedov.soramitsutest.presentation.utils.BaseActivity;

/**
 * Main activity of the application
 */
public final class MainActivity extends BaseActivity implements MainView, GridFragment.Listener {

    @Inject
    @InjectPresenter
    MainPresenter mPresenter;

    private GridFragment mGridFragment;
    private ItemFragment mItemFragment;
    private boolean mItemShowed;

    /**
     * Provide presenter presented by Dagger to Moxy
     *
     * @return presenter as {@link MainPresenter}
     */
    @ProvidePresenter
    MainPresenter providePresenter() {
        return mPresenter;
    }

    /**
     * Sets the layout and starts fragments
     *
     * @param savedInstanceState bundle of data that has been saved while changing configuration
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reloadOrInitGridFragment();
        initItemFragment();
    }

    /**
     * If fullscreen item showed then hides, otherwise closes activity
     */
    @Override
    public void onBackPressed() {
        if (mItemShowed) {
            mPresenter.hideItem();
        } else {
            super.onBackPressed();
        }
    }

    //region MainView

    /**
     * {@inheritDoc}
     */
    @Override
    public void onItemsLoaded(List<ItemModel> items) {
        mGridFragment.setItems(items);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onItemShow(ItemModel item) {
        mItemShowed = true;
        mItemFragment.setItem(item);
        switchFragmentsVisibility(mGridFragment, mItemFragment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onItemHide() {
        mItemShowed = false;
        switchFragmentsVisibility(mItemFragment, mGridFragment);
    }

    //endregion

    //region GridFragment.Listener

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPullToRefresh() {
        mPresenter.loadItems();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onItemClick(ItemModel item) {
        mPresenter.loadItem(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSearch(String query) {
        mPresenter.loadItems(query);
    }

    //endregion

    /**
     * Since {@link GridFragment} is retaining, first we try to reload it. If it is not available,
     * then initialize it.
     */
    private void reloadOrInitGridFragment() {
        mGridFragment = (GridFragment) getSupportFragmentManager()
                .findFragmentByTag(GridFragment.TAG);

        if (mGridFragment == null) {
            mGridFragment = GridFragment.newInstance();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, mGridFragment, GridFragment.TAG)
                .commit();
    }

    /**
     * Initializing {@link ItemFragment} and hiding it
     */
    private void initItemFragment() {
        mItemFragment = ItemFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, mItemFragment, ItemFragment.TAG)
                .hide(mItemFragment)
                .commit();
    }

    /**
     * Switch visibility of fragment to avoid imposition
     *
     * @param hide fragment to hide
     * @param show fragment to show
     */
    private void switchFragmentsVisibility(Fragment hide, Fragment show) {
        getSupportFragmentManager()
                .beginTransaction()
                .hide(hide)
                .show(show)
                .commit();
    }

}
