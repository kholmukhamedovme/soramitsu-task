package me.kholmukhamedov.soramitsutest.presentation.item.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import me.kholmukhamedov.soramitsutest.R;
import me.kholmukhamedov.soramitsutest.di.App;
import me.kholmukhamedov.soramitsutest.models.presentation.ItemModel;

public final class ItemFragment extends MvpAppCompatFragment {

    /**
     * Tag for fragment manager
     */
    public static final String TAG = "ItemFragment";

    /**
     * Injections
     */
    @Inject
    Picasso mPicasso;

    private ItemModel mItem;

    /**
     * Creates new instance
     *
     * @return new instance of fragment
     */
    public static ItemFragment newInstance() {
        return new ItemFragment();
    }

    /**
     * Provides dependencies, enables instance retaining and options menu existing
     *
     * @param context {@inheritDoc}
     */
    @Override
    public void onAttach(Context context) {
        App.getMainComponent().inject(this);
        super.onAttach(context);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    /**
     * Disables search option
     *
     * @param menu {@inheritDoc}
     * @param inflater {@inheritDoc}
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }

    /**
     * Loads image
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
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        mPicasso.load(mItem.getFullscreenImageUrl())
                .placeholder(R.drawable.ic_photo_placeholder_24dp)
                .into((ImageView) view.findViewById(R.id.image_view_fullscreen));

        return view;
    }

    /**
     * Saves item to local variable
     *
     * @param item item
     */
    public void setItem(ItemModel item) {
        mItem = item;
    }

}
