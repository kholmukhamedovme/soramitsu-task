package me.kholmukhamedov.soramitsutest.presentation.utils;

import io.reactivex.SingleTransformer;

/**
 * Interface of RxJava scheduler provider
 */
public interface RxSchedulerProvider {

    /**
     * Get RxJava {@link io.reactivex.Single} source that observed on IO scheduler and subscribed on
     * main thread of Android scheduler
     *
     * @param <T> type of source's emission
     * @return observed and subscribed source
     */
    <T> SingleTransformer<T, T> getSingleFromIOToMainThread();

}
