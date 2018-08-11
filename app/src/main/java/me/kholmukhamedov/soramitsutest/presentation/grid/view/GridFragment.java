package me.kholmukhamedov.soramitsutest.presentation.grid.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import me.kholmukhamedov.soramitsutest.R;
import me.kholmukhamedov.soramitsutest.di.App;
import me.kholmukhamedov.soramitsutest.models.presentation.ItemModel;

public final class GridFragment extends MvpAppCompatFragment {

    /**
     * Tag for fragment manager
     */
    public static final String TAG = "GridFragment";

    private static final int GRID_COLUMNS = 3;

    /**
     * Injections
     */
    @Inject
    Picasso mPicasso;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private Listener mListener;

    /**
     * Creates new instance
     *
     * @return new instance of fragment
     */
    public static GridFragment newInstance() {
        return new GridFragment();
    }

    /**
     * Provides dependencies, saves link to activity for further interactions
     *
     * @param context context of activity
     */
    @Override
    public void onAttach(Context context) {
        App.getMainComponent().inject(this);
        super.onAttach(context);

        if (context instanceof Listener) {
            mListener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Listener");
        }
    }

    /**
     * Enables menu and set fragment instance retaining
     * This method is only called once for this fragment
     *
     * @param savedInstanceState {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    /**
     * Inflates menu, adds listeners on search view
     *
     * @param menu     {@inheritDoc}
     * @param inflater {@inheritDoc}
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = new SearchView(this.getContext());

        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menuItem.setActionView(searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mListener.onSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnCloseListener(() -> {
            searchView.onActionViewCollapsed();
            mListener.onPullToRefresh();
            return true;
        });
    }

    /**
     * Init and setup views, set listeners and initialize adapter and layout manager
     *
     * @param inflater           {@inheritDoc}
     * @param container          {@inheritDoc}
     * @param savedInstanceState {@inheritDoc}
     * @return inflated view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(() -> mListener.onPullToRefresh());

        mAdapter = new GridAdapter(mPicasso, v -> {
            int position = mRecyclerView.getChildLayoutPosition(v);
            ItemModel item = ((GridAdapter) mAdapter).getItem(position);
            mListener.onItemClick(item);
        });

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), GRID_COLUMNS));
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    /**
     * Unlink this fragment from activity
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Set items to adapter
     *
     * @param items list of items
     */
    public void setItems(List<ItemModel> items) {
        mSwipeRefreshLayout.setRefreshing(false);
        ((GridAdapter) mAdapter).updateItems(items);
    }

    /**
     * Interface for activity to implement in order to interact with fragment
     */
    public interface Listener {

        /**
         * Reaction on pull to refresh event
         */
        void onPullToRefresh();

        /**
         * Reaction on item click event
         *
         * @param item item in {@link ItemModel} model
         */
        void onItemClick(ItemModel item);

        /**
         * Reaction on search event
         *
         * @param query query string
         */
        void onSearch(String query);

    }

}
