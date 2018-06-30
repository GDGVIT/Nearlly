package com.dscvit.android.nearlly.ui.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import android.content.SharedPreferences
import com.dscvit.android.nearlly.model.ChatMessage
import com.dscvit.android.nearlly.repo.ChatRepo
import com.dscvit.android.nearlly.utils.Constants
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import javax.inject.Inject

class ChatViewModel @Inject constructor(val chatRepo: ChatRepo): ViewModel() {

    var userName: String? = null
    lateinit var chatMessages: LiveData<PagedList<ChatMessage>>

    init {
        userName = chatRepo.getUserName()
        initChatMessages()
    }

    private fun initChatMessages() {
        launch {
            chatMessages = chatRepo.getMessages()
        }
    }

    fun addMessage(chatMessage: ChatMessage) {
        launch {
            chatRepo.addMessage(chatMessage)
        }
    }

}