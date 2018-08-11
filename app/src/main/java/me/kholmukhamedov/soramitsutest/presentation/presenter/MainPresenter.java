package me.kholmukhamedov.soramitsutest.presentation.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.arellomobile.mvp.InjectViewState;

import java.io.IOException;
import java.net.SocketTimeoutException;

import me.kholmukhamedov.soramitsutest.R;
import me.kholmukhamedov.soramitsutest.domain.Interactor;
import me.kholmukhamedov.soramitsutest.models.converter.AbstractConverter;
import me.kholmukhamedov.soramitsutest.models.domain.Item;
import me.kholmukhamedov.soramitsutest.models.presentation.ItemModel;
import me.kholmukhamedov.soramitsutest.presentation.utils.BasePresenter;
import me.kholmukhamedov.soramitsutest.presentation.utils.RxSchedulerProvider;
import me.kholmukhamedov.soramitsutest.presentation.view.MainView;
import retrofit2.HttpException;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Presenter for {@link me.kholmukhamedov.soramitsutest.presentation.view.MainActivity}
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
     * Request to load feed items
     */
    public void loadItems() {
        getCompositeDisposable().add(
                mInteractor.requestItems()
                        .compose(mRxSchedulerProvider.getSingleFromIOToMainThread())
                        .map(mConverter::convertList)
                        .subscribe(
                                items -> getViewState().onItemsLoaded(items),
                                this::handleError
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
                                this::handleError
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

    /**
     * Handles error according to type
     *
     * @param throwable error
     */
    private void handleError(Throwable throwable) {
        @StringRes
        int messageResource;

        if (throwable instanceof HttpException) {
            messageResource = R.string.error_http_response_code;
        } else if (throwable instanceof SocketTimeoutException) {
            messageResource = R.string.error_timeout;
        } else if (throwable instanceof IOException) {
            messageResource = R.string.error_io_parse;
        } else {
            messageResource = R.string.error_unknown;
        }

        getViewState().showError(messageResource);
    }

}
