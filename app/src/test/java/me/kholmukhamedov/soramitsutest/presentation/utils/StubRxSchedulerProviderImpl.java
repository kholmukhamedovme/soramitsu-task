package me.kholmukhamedov.soramitsutest.presentation.utils;

import io.reactivex.SingleTransformer;
import io.reactivex.schedulers.TestScheduler;

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
