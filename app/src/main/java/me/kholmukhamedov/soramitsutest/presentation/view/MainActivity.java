package me.kholmukhamedov.soramitsutest.presentation.view;

import android.os.Bundle;

import me.kholmukhamedov.soramitsutest.R;
import me.kholmukhamedov.soramitsutest.models.presentation.ItemModel;
import me.kholmukhamedov.soramitsutest.presentation.utils.BaseActivity;
import me.kholmukhamedov.soramitsutest.presentation.view.list.ListFragment;

/**
 * Main activity of the application
 */
public class MainActivity extends BaseActivity implements ListFragment.ListFragmentListener {

    /**
     * Sets the layout and starts fragment
     *
     * @param savedInstanceState bundle of data that has been saved while changing configuration
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, ListFragment.newInstance(), ListFragment.TAG)
                .commit();
    }

    @Override
    public void onItemClick(ItemModel item) {
        /*
            TODO
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame_layout, ItemFragment.newInstance(), ItemFragment.TAG)
                    .commit();
        */
    }

}
