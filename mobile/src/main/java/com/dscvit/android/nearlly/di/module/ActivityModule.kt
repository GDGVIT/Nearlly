package com.dscvit.android.nearlly.di.module

import com.dscvit.android.nearlly.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contribueMainActivity(): MainActivity
}