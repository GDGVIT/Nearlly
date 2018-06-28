package com.dscvit.android.nearlly.ui.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.dscvit.android.nearlly.model.ChatMessage

class ChatViewModel : ViewModel() {

    var userName: String? = null
    var chatMessages: MutableLiveData<List<ChatMessage>> = MutableLiveData()

}