package com.dscvit.android.nearlly.di.component

import android.app.Application
import com.dscvit.android.nearlly.ChatApp
import com.dscvit.android.nearlly.di.module.ActivityModule
import com.dscvit.android.nearlly.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    ActivityModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(chatApp: ChatApp)
}