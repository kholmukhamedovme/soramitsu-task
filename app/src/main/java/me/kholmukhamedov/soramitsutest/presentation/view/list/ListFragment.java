package me.kholmukhamedov.soramitsutest.presentation.view.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import me.kholmukhamedov.soramitsutest.R;
import me.kholmukhamedov.soramitsutest.models.presentation.ItemModel;
import me.kholmukhamedov.soramitsutest.presentation.presenter.ListPresenter;
import me.kholmukhamedov.soramitsutest.presentation.utils.BaseFragment;

public class ListFragment extends BaseFragment implements ListView {

    /**
     * Tag for fragment manager
     */
    public static final String TAG = "ListFragment";

    private static final int GRID_COLUMNS = 3;

    /**
     * Injections
     */
    @Inject
    Picasso mPicasso;
    @Inject
    @InjectPresenter
    ListPresenter mPresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private ListFragmentListener mListener;

    /**
     * Creates new instance
     *
     * @return new instance of fragment
     */
    public static ListFragment newInstance() {
        return new ListFragment();
    }

    /**
     * Provide presenter presented by Dagger to Moxy
     *
     * @return presenter as {@link ListPresenter}
     */
    @ProvidePresenter
    ListPresenter providePresenter() {
        return mPresenter;
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
        mSwipeRefreshLayout.setOnRefreshListener(mPresenter::loadItems);

        mAdapter = new ListAdapter(mPicasso, v -> {
            int position = mRecyclerView.getChildLayoutPosition(v);
            ItemModel item = ((ListAdapter) mAdapter).getItem(position);
            mListener.onItemClick(item);
        });

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), GRID_COLUMNS));
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    /**
     * Saves link to activity for further interactions
     *
     * @param context context of activity
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof ListFragmentListener) {
            mListener = (ListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ListFragmentListener");
        }
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
     * {@inheritDoc}
     */
    @Override
    public void onItemsLoaded(List<ItemModel> items) {
        mSwipeRefreshLayout.setRefreshing(false);
        ((ListAdapter) mAdapter).updateItems(items);
    }

    /**
     * Interface for activity to implement in order to interact with fragment
     */
    public interface ListFragmentListener {

        /**
         * Reaction on item click
         *
         * @param item item in {@link ItemModel} model
         */
        void onItemClick(ItemModel item);

    }

}
