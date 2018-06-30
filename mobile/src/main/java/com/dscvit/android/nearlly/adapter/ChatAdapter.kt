package com.dscvit.android.nearlly.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.dscvit.android.nearlly.R
import com.dscvit.android.nearlly.model.ChatMessage
import com.dscvit.android.nearlly.utils.inflate
import kotlinx.android.synthetic.main.item_chat_message.view.*

class ChatAdapter(
        private val listener: (ChatMessage) -> Unit
) : PagedListAdapter<ChatMessage, ChatAdapter.ChatViewHolder>(
        object : DiffUtil.ItemCallback<ChatMessage>() {
            override fun areItemsTheSame(oldItem: ChatMessage?, newItem: ChatMessage?) = oldItem?.id == newItem?.id
            override fun areContentsTheSame(oldItem: ChatMessage?, newItem: ChatMessage?) = oldItem == newItem
        }
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChatViewHolder(parent.inflate(R.layout.item_chat_message))

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) { getItem(position)?.let { holder.bind(it, listener) } }

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(chatMessage: ChatMessage, listener: (ChatMessage) -> Unit) {
            itemView.text_chat_username.text = chatMessage.sender
            itemView.text_chat_message.text = chatMessage.message
        }
    }
}