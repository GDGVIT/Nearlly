package com.dscvit.android.nearlly.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
data class ChatMessage(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val sender: String = "",
        val message: String = "",
        val color: String = "",
        val date: Date = Date()
)