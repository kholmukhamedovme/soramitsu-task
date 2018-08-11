package me.kholmukhamedov.soramitsutest.data.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import me.kholmukhamedov.soramitsutest.data.remote.ApiService;
import me.kholmukhamedov.soramitsutest.domain.Repository;
import me.kholmukhamedov.soramitsutest.models.converter.AbstractConverter;
import me.kholmukhamedov.soramitsutest.models.data.FeedBean;
import me.kholmukhamedov.soramitsutest.models.domain.Item;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@PrepareForTest({
        FeedBean.class,
        Item.class
})
@RunWith(PowerMockRunner.class)
public class RepositoryImplTest {

    private static final String SOME_TAG = "dogs";

    private Repository mRepository;

    @Mock
    private ApiService mApiService;
    @Mock
    private AbstractConverter<FeedBean.Item, Item> mConverter;

    @Before
    public void setUp() {
        mRepository = new RepositoryImpl(mApiService, mConverter);
    }

    @Test
    public void getItems() {
        FeedBean data = mock(FeedBean.class);
        FeedBean.Item dataInternal = mock(FeedBean.Item.class);
        List<FeedBean.Item> dataList = Collections.singletonList(dataInternal);
        Item domain = mock(Item.class);
        List<Item> domainList = Collections.singletonList(domain);

        when(mApiService.getFeed()).thenReturn(Single.just(data));
        when(data.getItems()).thenReturn(dataList);
        when(mConverter.convertList(dataList)).thenReturn(domainList);

        Single<List<Item>> actual = mRepository.getItems();

        actual
                .test()
                .assertNoErrors()
                .assertValue(domainList);

        verify(mApiService).getFeed();
        verify(mConverter).convertList(dataList);
    }

    @Test
    public void getItems_error() {
        Throwable throwable = new Throwable();

        when(mApiService.getFeed()).thenReturn(Single.error(throwable));

        Single<List<Item>> actual = mRepository.getItems();

        actual
                .test()
                .assertNoValues()
                .assertError(throwable);

        verify(mApiService).getFeed();
        verify(mConverter, never()).convertList(anyList());
    }

    @Test
    public void getItemsByTag() {
        FeedBean data = mock(FeedBean.class);
        FeedBean.Item dataInternal = mock(FeedBean.Item.class);
        List<FeedBean.Item> dataList = Collections.singletonList(dataInternal);
        Item domain = mock(Item.class);
        List<Item> domainList = Collections.singletonList(domain);

        when(mApiService.getFeed(SOME_TAG)).thenReturn(Single.just(data));
        when(data.getItems()).thenReturn(dataList);
        when(mConverter.convertList(dataList)).thenReturn(domainList);

        Single<List<Item>> actual = mRepository.getItems(SOME_TAG);

        actual
                .test()
                .assertNoErrors()
                .assertValue(domainList);

        verify(mApiService).getFeed(SOME_TAG);
        verify(mConverter).convertList(data.getItems());
    }

    @Test
    public void getItemsByTag_error() {
        Throwable throwable = new Throwable();

        when(mApiService.getFeed(SOME_TAG)).thenReturn(Single.error(throwable));

        Single<List<Item>> actual = mRepository.getItems(SOME_TAG);

        actual
                .test()
                .assertNoValues()
                .assertError(throwable);

        verify(mApiService).getFeed(SOME_TAG);
        verify(mConverter, never()).convertList(anyList());
    }
}
