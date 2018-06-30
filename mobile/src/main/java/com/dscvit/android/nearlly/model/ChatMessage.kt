package com.dscvit.android.nearlly.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
data class ChatMessage(
        var sender: String,
        var message: String
) {
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0

        var color: String = ""
        var date: Date = Date()
}