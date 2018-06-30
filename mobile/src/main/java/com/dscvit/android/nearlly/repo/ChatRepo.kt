package com.dscvit.android.nearlly.repo

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.content.SharedPreferences
import com.dscvit.android.nearlly.db.ChatDao
import com.dscvit.android.nearlly.model.ChatMessage
import com.dscvit.android.nearlly.utils.Constants
import javax.inject.Inject

class ChatRepo @Inject constructor(val dao: ChatDao, val preferences: SharedPreferences) {

    fun getMessages(): LiveData<PagedList<ChatMessage>> {
        val pagedListConfig = PagedList.Config.Builder().setEnablePlaceholders(true)
                .setPrefetchDistance(20)
                .setPageSize(25)
                .build()
        return LivePagedListBuilder(dao.fetchMessages(), pagedListConfig)
                .build()
    }

    fun addMessage(message: ChatMessage) = dao.insertMessage(message)

    fun getUserName(): String? = preferences.getString(Constants.PREF_USERNAME_KEY, null)

}