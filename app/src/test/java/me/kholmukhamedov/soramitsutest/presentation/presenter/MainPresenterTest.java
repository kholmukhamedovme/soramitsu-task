package me.kholmukhamedov.soramitsutest.presentation.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import me.kholmukhamedov.soramitsutest.domain.Interactor;
import me.kholmukhamedov.soramitsutest.models.converter.AbstractConverter;
import me.kholmukhamedov.soramitsutest.models.domain.Item;
import me.kholmukhamedov.soramitsutest.models.presentation.ItemModel;
import me.kholmukhamedov.soramitsutest.presentation.utils.StubRxSchedulerProviderImpl;
import me.kholmukhamedov.soramitsutest.presentation.view.MainView$$State;

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

}
