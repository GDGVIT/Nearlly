package com.dscvit.android.nearlly.di.module

import com.dscvit.android.nearlly.ui.fragment.ChatFragment
import com.dscvit.android.nearlly.ui.fragment.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeChatFragment(): ChatFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment
}