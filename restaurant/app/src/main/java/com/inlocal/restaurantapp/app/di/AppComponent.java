package com.inlocal.restaurantapp.app.di;

import com.inlocal.restaurantapp.app.di.module.ActivityBindingModule;
import com.inlocal.restaurantapp.app.di.module.ApplicationModule;
import com.inlocal.restaurantapp.app.di.module.ContextModule;
import com.inlocal.restaurantapp.app.instance.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {ContextModule.class, ApplicationModule.class,
        AndroidSupportInjectionModule.class, ActivityBindingModule.class})
public interface AppComponent extends AndroidInjector<DaggerApplication> {
    void inject(Application application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
