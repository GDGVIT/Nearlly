package com.dscvit.android.nearlly.di.module

import android.app.Application
import android.arch.persistence.room.Room
import android.content.SharedPreferences
import com.dscvit.android.nearlly.db.ChatDao
import com.dscvit.android.nearlly.db.ChatDatabase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideGron(): Gson = Gson()

//    @Provides
//    @Singleton
//    fun providePreferences(application: Application): SharedPreferences { }

    @Provides
    @Singleton
    fun provideDatabase(application: Application): ChatDatabase = Room.databaseBuilder(
            application,
            ChatDatabase::class.java,
            "chatdatabase.db"
    ).build()

    @Provides
    @Singleton
    fun provideDao(database: ChatDatabase): ChatDao = database.messageDao()
}