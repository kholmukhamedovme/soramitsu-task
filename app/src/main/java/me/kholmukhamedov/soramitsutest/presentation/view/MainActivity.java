package me.kholmukhamedov.soramitsutest.presentation.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import me.kholmukhamedov.soramitsutest.R;
import me.kholmukhamedov.soramitsutest.di.App;
import me.kholmukhamedov.soramitsutest.models.presentation.ItemModel;
import me.kholmukhamedov.soramitsutest.presentation.presenter.MainPresenter;

/**
 * Main activity of the application
 */
public final class MainActivity extends MvpAppCompatActivity implements MainView {

    private static final int GRID_COLUMNS = 3;

    @Inject
    Picasso mPicasso;
    @Inject
    @InjectPresenter
    MainPresenter mPresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View mNoInternetConnectionView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private ItemFragment mItemFragment;
    private SearchView mSearchView;

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
     * Inject dependencies, init and sets views, adapter, listeners and item fragment
     *
     * @param savedInstanceState bundle of data that has been saved while changing configuration
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.getMainComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNoInternetConnectionView = findViewById(R.id.no_internet_connection_view);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = findViewById(R.id.recycler_view);

        mAdapter = new MainAdapter(mPicasso, this::onItemClick);

        mSwipeRefreshLayout.setOnRefreshListener(this::onPullToRefresh);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, GRID_COLUMNS));
        mRecyclerView.setAdapter(mAdapter);

        initItemFragment();

        if (isConnectedToInternet()) {
            mPresenter.loadItems();
        } else {
            shouldShowNoInternetConnection(true);
        }
    }

    /**
     * Sets search option and listeners on it
     *
     * @param menu {@inheritDoc}
     * @return always {@code true}
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        mSearchView = new SearchView(this);
        mSearchView.setOnCloseListener(this::onSearchClose);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.loadItems(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    mPresenter.loadItems();
                    return true;
                } else {
                    return false;
                }
            }
        });

        MenuItem menuItem = menu.findItem(R.id.search);
        menuItem.setActionView(mSearchView);

        return true;
    }

    /**
     * Commands to presenter to hide item fragment and manages back click
     */
    @Override
    public void onBackPressed() {
        mPresenter.hideItem();

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
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
        shouldShowNoInternetConnection(false);
        mSwipeRefreshLayout.setRefreshing(false);
        ((MainAdapter) mAdapter).updateItems(items);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onItemShow(ItemModel item) {
        mItemFragment.setItem(item);

        getSupportFragmentManager().popBackStackImmediate();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, mItemFragment, ItemFragment.TAG)
                .addToBackStack(null)
                .commit();

        mSwipeRefreshLayout.setVisibility(View.GONE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showError(@StringRes int message) {
        mSwipeRefreshLayout.setRefreshing(false);

        new AlertDialog.Builder(this)
                .setTitle(R.string.error_title)
                .setMessage(message)
                .create()
                .show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onItemHide() {
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    //endregion

    /**
     * Sets visibility of view for no internet connection according to {@code should} value
     *
     * @param should {@code true} if should show, otherwise {@code false}
     */
    private void shouldShowNoInternetConnection(boolean should) {
        mNoInternetConnectionView.setVisibility(should ? View.VISIBLE : View.GONE);
        mRecyclerView.setVisibility(should ? View.GONE : View.VISIBLE);
    }

    /**
     * Initializes item screen fragment
     * Since {@link ItemFragment} can retain, first we try to restore it. If it can't be restored,
     * then we create new instance
     */
    private void initItemFragment() {
        ItemFragment itemFragment = (ItemFragment) getSupportFragmentManager()
                .findFragmentByTag(ItemFragment.TAG);

        if (itemFragment == null) {
            mItemFragment = ItemFragment.newInstance();
        } else {
            mItemFragment = itemFragment;
        }
    }

    /**
     * Action for pull to refresh listener
     */
    private void onPullToRefresh() {
        mPresenter.loadItems();
    }

    /**
     * Action for item click listener
     *
     * @param v clicked view
     */
    private void onItemClick(View v) {
        int position = mRecyclerView.getChildLayoutPosition(v);
        ItemModel item = ((MainAdapter) mAdapter).getItem(position);
        mPresenter.loadItem(item);
    }

    /**
     * Action for search field close listener
     *
     * @return always {@code true}
     */
    private boolean onSearchClose() {
        mSearchView.onActionViewCollapsed();
        mPresenter.loadItems();
        return true;
    }

    /**
     * Checks whether connected to internet or not
     *
     * @return {@code true} if connected, otherwise {@code false}
     */
    private boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

}
