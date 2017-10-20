package vn.phuongcong.fchat.ui.main.fragment.listmsg

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_msg.view.*
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.model.Chat

/**
 * Created by Ominext on 10/20/2017.
 */
class ListMessageAdapter(var mListMessage: MutableList<Chat>, var mIChat:
IChat) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface IChat{
        fun chat(chat :Chat)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ChatHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_msg, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ChatHolder) {
            if (mListMessage != null && mListMessage.size > 0) {
                var chat: Chat = mListMessage.get(position)
                holder.bin(chat,mIChat)
            }

        }
    }

    override fun getItemCount(): Int {
        return mListMessage.size
    }

    class ChatHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bin(chat: Chat, mIChat: IChat) {
            itemView.txt_name_friend.text = chat.mFriend
            itemView.txt_message_last.text = chat.mLastMessage
            itemView.setOnClickListener {
                mIChat.chat(chat)
            }

        }
    }

}