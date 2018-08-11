package me.kholmukhamedov.soramitsutest.presentation.main.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import me.kholmukhamedov.soramitsutest.domain.Interactor;
import me.kholmukhamedov.soramitsutest.models.converter.AbstractConverter;
import me.kholmukhamedov.soramitsutest.models.domain.Item;
import me.kholmukhamedov.soramitsutest.models.presentation.ItemModel;
import me.kholmukhamedov.soramitsutest.presentation.main.view.MainView;
import me.kholmukhamedov.soramitsutest.presentation.utils.BasePresenter;
import me.kholmukhamedov.soramitsutest.presentation.utils.RxSchedulerProvider;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Presenter for {@link me.kholmukhamedov.soramitsutest.presentation.main.view.MainActivity}
 */
@InjectViewState
public final class MainPresenter extends BasePresenter<MainView> {

    private static final String TAG = "MainPresenter";

    private final Interactor mInteractor;
    private final AbstractConverter<Item, ItemModel> mConverter;
    private final RxSchedulerProvider mRxSchedulerProvider;

    /**
     * Inject dependencies and initializes converter
     *
     * @param interactor          interactor for fetching content from Flickr
     * @param converter           converter from domain layer entity to presentation layer model
     * @param rxSchedulerProvider RxJava scheduler provider
     */
    public MainPresenter(@NonNull Interactor interactor,
                         @NonNull AbstractConverter<Item, ItemModel> converter,
                         @NonNull RxSchedulerProvider rxSchedulerProvider) {
        mInteractor = checkNotNull(interactor, "Interactor is required");
        mConverter = checkNotNull(converter, "AbstractConverter<Item, ItemModel> is required");
        mRxSchedulerProvider = checkNotNull(rxSchedulerProvider, "RxSchedulerProvider is required");
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
                        .compose(mRxSchedulerProvider.getSingleFromIOToMainThread())
                        .map(mConverter::convertList)
                        .subscribe(
                                items -> getViewState().onItemsLoaded(items),
                                throwable -> Log.e(TAG, throwable.getLocalizedMessage(), throwable) // TODO handle gently
                        )
        );
    }

    /**
     * Request to load feed items by tag
     *
     * @param tag search tag
     */
    public void loadItems(String tag) {
        getCompositeDisposable().add(
                mInteractor.requestItems(tag)
                        .compose(mRxSchedulerProvider.getSingleFromIOToMainThread())
                        .map(mConverter::convertList)
                        .subscribe(
                                items -> getViewState().onItemsLoaded(items),
                                throwable -> Log.e(TAG, throwable.getLocalizedMessage(), throwable) // TODO handle gently
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
