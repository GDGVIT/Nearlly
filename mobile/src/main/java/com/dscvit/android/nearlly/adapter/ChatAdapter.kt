package com.dscvit.android.nearlly.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.dscvit.android.nearlly.R
import com.dscvit.android.nearlly.model.ChatMessage
import com.dscvit.android.nearlly.utils.inflate

class ChatAdapter(var messages: List<ChatMessage>, val listener: (ChatMessage) -> Unit)
    : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.item_chat_message))

    override fun getItemCount() = messages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(messages[position], listener)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(chatMessage: ChatMessage, listener: (ChatMessage) -> Unit) {
            // TODO: Use DataBinding
        }
    }

    fun updateList(messages: List<ChatMessage>) {
        this.messages = messages
        notifyDataSetChanged()
    }
}