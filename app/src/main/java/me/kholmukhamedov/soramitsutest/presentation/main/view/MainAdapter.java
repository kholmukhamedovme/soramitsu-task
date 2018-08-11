package me.kholmukhamedov.soramitsutest.presentation.main.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.kholmukhamedov.soramitsutest.R;
import me.kholmukhamedov.soramitsutest.models.presentation.ItemModel;

import static android.view.View.OnClickListener;
import static dagger.internal.Preconditions.checkNotNull;

public final class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private final Picasso mPicasso;
    private final List<ItemModel> mItems;
    private final OnClickListener mOnClickListener;

    /**
     * Injects dependencies and initializes list of items
     *
     * @param picasso         Picasso
     * @param onClickListener listener on item click event
     */
    MainAdapter(@NonNull Picasso picasso,
                @NonNull OnClickListener onClickListener) {
        mPicasso = checkNotNull(picasso, "Picasso is required");
        mOnClickListener = checkNotNull(onClickListener, "RecyclerView.OnClickListener is required");

        mItems = new ArrayList<>();
    }

    /**
     * Creates view holder and sets listener on click event
     *
     * @param parent   {@inheritDoc}
     * @param viewType {@inheritDoc}
     * @return {@inheritDoc}
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);

        v.setOnClickListener(mOnClickListener);

        return new ViewHolder(v);
    }

    /**
     * Puts image into view holder
     *
     * @param holder   {@inheritDoc}
     * @param position {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {
        String imageUrl = mItems.get(position).getImageUrl();

        mPicasso.load(imageUrl)
                .placeholder(R.drawable.ic_photo_placeholder_24dp)
                .into(holder.mImageView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    /**
     * Replaces current list of items with new one
     *
     * @param items new list of items
     */
    public void updateItems(List<ItemModel> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * Get item model by index
     *
     * @param position index
     * @return item model
     */
    public ItemModel getItem(int position) {
        return mItems.get(position);
    }

    /**
     * View holder of item
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mImageView;

        ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
        }

    }

}
