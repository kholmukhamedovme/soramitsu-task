package me.kholmukhamedov.soramitsutest.di.component;

import javax.inject.Singleton;

import dagger.Component;
import me.kholmukhamedov.soramitsutest.di.module.AppModule;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    MainComponent.Builder mainComponentBuilder();

}
