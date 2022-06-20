package com.inlocal.restaurantapp.app.di.module;



import dagger.Module;

@Module(includes = ViewModelModule.class)
public class ApplicationModule {

    private static final String BASE_URL = "https://reqres.in/api/";

//    @Singleton
//    @Provides
//    static Retrofit provideRetrofit() {
//        return new Retrofit.Builder().baseUrl(BASE_URL)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }
//
//    @Singleton
//    @Provides
//    static RepoService provideRetrofitService(Retrofit retrofit) {
//        return retrofit.create(RepoService.class);
//    }
}
