package vn.phuongcong.fchat

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import vn.phuongcong.fchat.di.component.AppComponent
import vn.phuongcong.fchat.di.module.AppModule
import javax.inject.Inject

/**
 * Created by Ominext on 10/11/2017.
 */
class MyApp: Application(), HasActivityInjector {
    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        createComponent();
    }

    private fun createComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}