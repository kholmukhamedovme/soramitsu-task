package me.kholmukhamedov.soramitsutest.presentation.main.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.kholmukhamedov.soramitsutest.domain.Interactor;
import me.kholmukhamedov.soramitsutest.models.converter.AbstractConverter;
import me.kholmukhamedov.soramitsutest.models.converter.DomainToPresentationConverter;
import me.kholmukhamedov.soramitsutest.models.domain.Item;
import me.kholmukhamedov.soramitsutest.models.presentation.ItemModel;
import me.kholmukhamedov.soramitsutest.presentation.main.view.MainView;
import me.kholmukhamedov.soramitsutest.presentation.utils.BasePresenter;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Presenter for {@link me.kholmukhamedov.soramitsutest.presentation.main.view.MainActivity}
 */
@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {

    private static final String TAG = "MainPresenter";

    private Interactor mInteractor;
    private AbstractConverter<Item, ItemModel> mConverter;

    /**
     * Inject dependencies and initializes converter
     *
     * @param interactor interactor for fetching content from Flickr
     */
    public MainPresenter(@NonNull Interactor interactor) {
        mInteractor = checkNotNull(interactor, "Interactor is required");
        mConverter = new DomainToPresentationConverter();
    }

    /**
     * Starts request to load feed items
     */
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadItems();
    }

    /**
     * Request to load feed items
     */
    public void loadItems() {
        getCompositeDisposable().add(
                mInteractor.requestItems()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(mConverter::convertList)
                        .subscribe(
                                items -> getViewState().onItemsLoaded(items),
                                throwable -> Log.e(TAG, throwable.getLocalizedMessage(), throwable)
                        )
        );
    }

    /**
     * Commands to view to show the item
     *
     * @param item item to show
     */
    public void loadItem(ItemModel item) {
        getViewState().onItemShow(item);
    }

    /**
     * Commands to view to hide the item
     */
    public void hideItem() {
        getViewState().onItemHide();
    }

}
