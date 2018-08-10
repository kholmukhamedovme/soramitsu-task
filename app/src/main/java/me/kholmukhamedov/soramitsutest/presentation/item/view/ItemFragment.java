package me.kholmukhamedov.soramitsutest.presentation.item.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import me.kholmukhamedov.soramitsutest.R;
import me.kholmukhamedov.soramitsutest.models.presentation.ItemModel;
import me.kholmukhamedov.soramitsutest.presentation.utils.BaseFragment;

public class ItemFragment extends BaseFragment {

    /**
     * Tag for fragment manager
     */
    public static final String TAG = "ItemFragment";

    /**
     * Injections
     */
    @Inject
    Picasso mPicasso;

    private ImageView mImageView;

    /**
     * Creates new instance
     *
     * @return new instance of fragment
     */
    public static ItemFragment newInstance() {
        return new ItemFragment();
    }

    /**
     * Init and setup views
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

        mImageView = view.findViewById(R.id.image_view_fullscreen);

        return view;
    }

    /**
     * Set item to view
     *
     * @param item item
     */
    public void setItem(ItemModel item) {
        mPicasso.load(item.getFullscreenImageUrl())
                .placeholder(R.drawable.ic_photo_placeholder_24dp)
                .into(mImageView);
    }

}
