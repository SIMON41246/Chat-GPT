package com.example.chatgptapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatgptapp.model.Message

class ChatGptAdapter(val messageList: List<Message>) :
    RecyclerView.Adapter<ChatGptAdapter.MyViewHolder>() {

    companion object {
        const val SENT_BY_ME = 0
        const val SENT_BY_ROBOT = 1
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val leftChatTextView: TextView? = itemView.findViewById(R.id.left_chat_text_view)
        private val leftChatTimestamp: TextView? = itemView.findViewById(R.id.left_chat_timestamp)
        private val rightChatTextView: TextView? = itemView.findViewById(R.id.right_chat_text_view)
        private val rightChatTimestamp: TextView? = itemView.findViewById(R.id.right_chat_timestamp)
        fun Bind(message: Message) {
            when (message.sentBy) {
                Message.SENT_BY_ME -> {
                    rightChatTextView?.text = message.message
                    rightChatTimestamp?.text = message.timestamp
                }
                else -> {
                    leftChatTextView?.text = message.message
                    leftChatTimestamp?.text = message.timestamp
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return when (viewType) {
            SENT_BY_ME -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.user_chat, parent, false)
                MyViewHolder(view)
            }
            else -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.bot_chat, parent, false)
                MyViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val message = messageList[position]
        holder.Bind(message)
    }

    override fun getItemCount(): Int = messageList.size

    override fun getItemViewType(position: Int): Int {
        val message = messageList[position]
        return when (message.sentBy) {
            Message.SENT_BY_ME -> SENT_BY_ME
            else -> SENT_BY_ROBOT
        }
    }
}