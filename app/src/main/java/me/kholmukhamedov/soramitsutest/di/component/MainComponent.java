package me.kholmukhamedov.soramitsutest.di.component;

import dagger.Subcomponent;
import me.kholmukhamedov.soramitsutest.di.module.MainModule;
import me.kholmukhamedov.soramitsutest.di.scope.MainScope;
import me.kholmukhamedov.soramitsutest.presentation.view.ItemFragment;
import me.kholmukhamedov.soramitsutest.presentation.view.MainActivity;

@MainScope
@Subcomponent(modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity target);

    void inject(ItemFragment target);

    @Subcomponent.Builder
    interface Builder {

        Builder mainModule(MainModule module);

        MainComponent build();

    }

}
