package com.dscvit.android.nearlly.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.dscvit.android.nearlly.model.ChatMessage

@Dao
interface ChatDao {
    @Insert
    fun insertMessage(message: ChatMessage)

    @Query("SELECT * FROM chatmessage")
    fun fetchMessages(): LiveData<List<ChatMessage>>
}