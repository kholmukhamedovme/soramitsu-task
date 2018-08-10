package me.kholmukhamedov.soramitsutest.presentation.presenter;

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
import me.kholmukhamedov.soramitsutest.presentation.utils.BasePresenter;
import me.kholmukhamedov.soramitsutest.presentation.view.list.ListFragment;
import me.kholmukhamedov.soramitsutest.presentation.view.list.ListView;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Presenter for {@link ListFragment}
 */
@InjectViewState
public class ListPresenter extends BasePresenter<ListView> {

    private static final String TAG = "ListPresenter";

    private Interactor mInteractor;
    private AbstractConverter<Item, ItemModel> mConverter;

    /**
     * Inject dependencies and initializes converter
     *
     * @param interactor interactor for fetching content from Flickr
     */
    public ListPresenter(@NonNull Interactor interactor) {
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
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(mConverter::convertList)
                        .subscribe(
                                items -> getViewState().onItemsLoaded(items),
                                throwable -> Log.e(TAG, throwable.getLocalizedMessage(), throwable)
                        )
        );
    }

}
