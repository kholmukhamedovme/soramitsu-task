package me.kholmukhamedov.soramitsutest.presentation.grid.view;

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

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    private final Picasso mPicasso;
    private final List<ItemModel> mItems;
    private final OnClickListener mOnClickListener;

    /**
     * Injects dependencies and initializes list of items
     *
     * @param picasso         Picasso
     * @param onClickListener on item click event listener
     */
    GridAdapter(@NonNull Picasso picasso,
                @NonNull OnClickListener onClickListener) {
        mPicasso = checkNotNull(picasso, "Picasso is required");
        mOnClickListener = checkNotNull(onClickListener, "RecyclerView.OnClickListener is required");

        mItems = new ArrayList<>();
    }

    /**
     * Sets on click event listener
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
     * Puts image to view holder
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
     * Update items
     * Clears current list of items and adds new
     *
     * @param items list of items
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
