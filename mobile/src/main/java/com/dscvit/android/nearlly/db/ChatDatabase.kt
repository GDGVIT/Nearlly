package com.dscvit.android.nearlly.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import com.dscvit.android.nearlly.model.ChatMessage
import com.dscvit.android.nearlly.model.converter.DateConverter

@Database(entities = [ChatMessage::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun messageDao(): ChatDao
    @Volatile lateinit var INSTANCE: ChatDatabase

}