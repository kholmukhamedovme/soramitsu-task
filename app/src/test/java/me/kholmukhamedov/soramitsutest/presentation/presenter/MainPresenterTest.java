package me.kholmukhamedov.soramitsutest.presentation.presenter;

import android.support.annotation.StringRes;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import me.kholmukhamedov.soramitsutest.R;
import me.kholmukhamedov.soramitsutest.domain.Interactor;
import me.kholmukhamedov.soramitsutest.models.converter.AbstractConverter;
import me.kholmukhamedov.soramitsutest.models.domain.Item;
import me.kholmukhamedov.soramitsutest.models.presentation.ItemModel;
import me.kholmukhamedov.soramitsutest.presentation.utils.StubRxSchedulerProviderImpl;
import me.kholmukhamedov.soramitsutest.presentation.view.MainView$$State;
import retrofit2.HttpException;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@PrepareForTest({
        Interactor.class,
        Item.class,
        ItemModel.class
})
@RunWith(PowerMockRunner.class)
public class MainPresenterTest {

    private static final String SOME_TAG = "dogs";

    private MainPresenter mPresenter;
    private StubRxSchedulerProviderImpl mRxSchedulerProvider;

    @Mock
    private Interactor mInteractor;
    @Mock
    private AbstractConverter<Item, ItemModel> mConverter;
    @Mock
    private MainView$$State mViewState;

    @Before
    public void setUp() {
        mRxSchedulerProvider = new StubRxSchedulerProviderImpl();

        mPresenter = new MainPresenter(mInteractor, mConverter, mRxSchedulerProvider);
        mPresenter.setViewState(mViewState);
    }

    @Test
    public void loadItems() {
        Item domain = mock(Item.class);
        List<Item> domainList = Collections.singletonList(domain);
        ItemModel presentation = mock(ItemModel.class);
        List<ItemModel> presentationList = Collections.singletonList(presentation);

        when(mInteractor.requestItems()).thenReturn(Single.just(domainList));
        when(mConverter.convertList(domainList)).thenReturn(presentationList);

        mPresenter.loadItems();

        mRxSchedulerProvider.getTestScheduler().triggerActions();

        verify(mInteractor).requestItems();
        verify(mConverter).convertList(domainList);
        verify(mViewState).onItemsLoaded(presentationList);
    }

    @Test
    public void loadItems_errorWithHttpException() {
        loadItems_error(HttpException.class, R.string.error_http_response_code);
    }

    @Test
    public void loadItems_errorWithSocketTimeoutException() {
        loadItems_error(SocketTimeoutException.class, R.string.error_timeout);
    }

    @Test
    public void loadItems_errorWithIOException() {
        loadItems_error(IOException.class, R.string.error_io_parse);
    }

    @Test
    public void loadItems_errorWithException() {
        loadItems_error(Exception.class, R.string.error_unknown);
    }

    @Test
    public void loadItemsByTag() {
        Item domain = mock(Item.class);
        List<Item> domainList = Collections.singletonList(domain);
        ItemModel presentation = mock(ItemModel.class);
        List<ItemModel> presentationList = Collections.singletonList(presentation);

        when(mInteractor.requestItems(SOME_TAG)).thenReturn(Single.just(domainList));
        when(mConverter.convertList(domainList)).thenReturn(presentationList);

        mPresenter.loadItems(SOME_TAG);

        mRxSchedulerProvider.getTestScheduler().triggerActions();

        verify(mInteractor).requestItems(SOME_TAG);
        verify(mConverter).convertList(domainList);
        verify(mViewState).onItemsLoaded(presentationList);
    }

    @Test
    public void loadItemsByTag_errorWithHttpException() {
        loadItemsByTag_error(HttpException.class, R.string.error_http_response_code, SOME_TAG);
    }

    @Test
    public void loadItemsByTag_errorWithSocketTimeoutException() {
        loadItemsByTag_error(SocketTimeoutException.class, R.string.error_timeout, SOME_TAG);
    }

    @Test
    public void loadItemsByTag_errorWithIOException() {
        loadItemsByTag_error(IOException.class, R.string.error_io_parse, SOME_TAG);
    }

    @Test
    public void loadItemsByTag_errorWithException() {
        loadItemsByTag_error(Exception.class, R.string.error_unknown, SOME_TAG);
    }

    @Test
    public void loadItem() {
        ItemModel item = mock(ItemModel.class);

        mPresenter.loadItem(item);

        verify(mViewState).onItemShow(item);
    }

    @Test
    public void hideItem() {
        mPresenter.hideItem();

        verify(mViewState).onItemHide();
    }

    private void loadItems_error(Class<? extends Throwable> throwableClass,
                                 @StringRes int stringResource) {
        loadItemsByTag_error(throwableClass, stringResource, null);
    }

    private void loadItemsByTag_error(Class<? extends Throwable> throwableClass,
                                      @StringRes int stringResource,
                                      String tag) {
        Throwable throwable = mock(throwableClass);

        when(mInteractor.requestItems()).thenReturn(Single.error(throwable));

        mPresenter.loadItems();

        mRxSchedulerProvider.getTestScheduler().triggerActions();

        verify(mInteractor).requestItems();
        verify(mConverter, never()).convertList(anyList());
        verify(mViewState).showError(stringResource);
    }

}
