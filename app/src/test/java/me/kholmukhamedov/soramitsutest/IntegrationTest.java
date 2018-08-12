package me.kholmukhamedov.soramitsutest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.kotlin.KotlinModule;

import org.junit.Test;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import me.kholmukhamedov.soramitsutest.data.remote.ApiService;
import me.kholmukhamedov.soramitsutest.data.repository.RepositoryImpl;
import me.kholmukhamedov.soramitsutest.domain.Interactor;
import me.kholmukhamedov.soramitsutest.domain.Repository;
import me.kholmukhamedov.soramitsutest.models.converter.AbstractConverter;
import me.kholmukhamedov.soramitsutest.models.converter.DataToDomainConverter;
import me.kholmukhamedov.soramitsutest.models.converter.DomainToPresentationConverter;
import me.kholmukhamedov.soramitsutest.models.data.FeedBean;
import me.kholmukhamedov.soramitsutest.models.domain.Item;
import me.kholmukhamedov.soramitsutest.models.presentation.ItemModel;
import me.kholmukhamedov.soramitsutest.presentation.presenter.MainPresenter;
import me.kholmukhamedov.soramitsutest.utils.MainViewState;
import me.kholmukhamedov.soramitsutest.utils.MockResponseInterceptor;
import me.kholmukhamedov.soramitsutest.utils.StubRxSchedulerProviderImpl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class IntegrationTest {

    private MainPresenter mPresenter;
    private StubRxSchedulerProviderImpl mRxSchedulerProvider;
    private MainViewState mViewState;

    @Test
    public void success() {
        prepare("feed.json");

        mPresenter.loadItems();

        mRxSchedulerProvider.getTestScheduler().triggerActions();

        assertEquals(mViewState.getItemList(), getItemList());
    }

    @Test
    public void error_httpCodeNotOk() {
        prepare("feed.json", 403);

        mPresenter.loadItems();

        mRxSchedulerProvider.getTestScheduler().triggerActions();

        assertEquals(mViewState.getMessage(), R.string.error_http_response_code);
    }

    @Test
    public void error_networkError() {
        prepare(true);

        mPresenter.loadItems();

        mRxSchedulerProvider.getTestScheduler().triggerActions();

        assertEquals(mViewState.getMessage(), R.string.error_io_parse);
    }

    @Test
    public void showItem() {
        prepare(false);

        mPresenter.loadItem(getItem());

        assertEquals(mViewState.getItem(), getItem());
    }

    @Test
    public void hideItem() {
        prepare(false);

        mPresenter.hideItem();

        assertTrue(mViewState.isItemHide());
    }

    private void prepare(boolean throwException) {
        prepare(null, 0, throwException);
    }

    private void prepare(String filename) {
        prepare(filename, HttpURLConnection.HTTP_OK);
    }

    private void prepare(String filename, int httpCode) {
        prepare(filename, httpCode, false);
    }

    private void prepare(String filename, int httpCode, boolean throwException) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new MockResponseInterceptor(filename, httpCode, throwException))
                .build();

        //region Data layer
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient) // Mock
                .baseUrl(ApiService.HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(
                        new ObjectMapper().registerModule(new KotlinModule())))
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        AbstractConverter<FeedBean.Item, Item> dataToDomainConverter = new DataToDomainConverter();

        Repository repository = new RepositoryImpl(apiService, dataToDomainConverter);
        //endregion

        //region Domain layer
        Interactor interactor = new Interactor(repository);
        //endregion

        //region Presentation layer
        AbstractConverter<Item, ItemModel> domainToPresentationConverter = new DomainToPresentationConverter();

        mRxSchedulerProvider = new StubRxSchedulerProviderImpl();

        mPresenter = new MainPresenter(interactor, domainToPresentationConverter, mRxSchedulerProvider);
        //endregion

        mViewState = new MainViewState();
        mPresenter.setViewState(mViewState);
    }

    private List<ItemModel> getItemList() {
        List<ItemModel> result = new ArrayList<>();
        result.add(new ItemModel(
                "IMG_20180803_193703570",
                "https://farm1.staticflickr.com/939/29031075737_33d2ab4b90_m.jpg"
        ));
        result.add(new ItemModel(
                "Techniker Beachtour Zinnowitz",
                "https://farm2.staticflickr.com/1815/29031077737_4f3ae85cbd_m.jpg"
        ));
        return result;
    }

    private ItemModel getItem() {
        return new ItemModel(
                "IMG_20180803_193703570",
                "https://farm1.staticflickr.com/939/29031075737_33d2ab4b90_m.jpg"
        );
    }

}
