package com.dscvit.android.nearlly.ui.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.dscvit.android.nearlly.model.ChatMessage
import com.dscvit.android.nearlly.repo.ChatRepo
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class ChatViewModel @Inject constructor(val chatRepo: ChatRepo): ViewModel() {

    var userName: MutableLiveData<String?> = MutableLiveData()
    var color: MutableLiveData<Int> = MutableLiveData()

    lateinit var chatMessages: LiveData<PagedList<ChatMessage>>

    init {
        fetchData()
    }

    fun fetchData() {
        userName.postValue(chatRepo.getUserName())
        color.postValue(chatRepo.getColor())

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

    fun saveUserName(userNameInput: String) {
        userName.postValue(userNameInput)
        chatRepo.saveUserName(userNameInput)
    }

    fun saveColor(colorInput: Int) {
        color.postValue(colorInput)
        chatRepo.saveColor(colorInput)
    }

}