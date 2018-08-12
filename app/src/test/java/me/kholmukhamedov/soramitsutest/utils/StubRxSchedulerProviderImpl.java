package me.kholmukhamedov.soramitsutest.utils;

import io.reactivex.SingleTransformer;
import io.reactivex.schedulers.TestScheduler;
import me.kholmukhamedov.soramitsutest.presentation.utils.RxSchedulerProvider;

/**
 * Stub implementation of {@link RxSchedulerProvider}
 */
public final class StubRxSchedulerProviderImpl implements RxSchedulerProvider {

    private TestScheduler mTestScheduler;

    public StubRxSchedulerProviderImpl() {
        mTestScheduler = new TestScheduler();
    }

    @Override
    public <T> SingleTransformer<T, T> getSingleFromIOToMainThread() {
        return single -> single
                .subscribeOn(mTestScheduler)
                .observeOn(mTestScheduler);
    }

    public TestScheduler getTestScheduler() {
        return mTestScheduler;
    }

}
