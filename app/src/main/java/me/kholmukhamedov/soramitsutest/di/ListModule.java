package me.kholmukhamedov.soramitsutest.di;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import me.kholmukhamedov.soramitsutest.domain.Interactor;
import me.kholmukhamedov.soramitsutest.presentation.presenter.ListPresenter;

@Module
public class ListModule {

    @Provides
    ListPresenter provideListPresenter(@NonNull Interactor interactor) {
        return new ListPresenter(interactor);
    }

}
