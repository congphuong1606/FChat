package vn.phuongcong.fchat.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_chat_one_one_receiver.view.*
import kotlinx.android.synthetic.main.item_chat_one_one_send.view.*
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.common.utils.DateTimeUltil
import vn.phuongcong.fchat.model.FChat

/**
 * Created by Ominext on 11/2/2017.
 */
class FchatAdapter(var uId:String,var chats: MutableList<FChat>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        var LEFT = 1
        var RIGHT = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        if (viewType == RIGHT)
            viewHolder = ChatItemViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_chat_one_one_send, parent, false))
        if (viewType == LEFT)
            viewHolder = ChatItemViewHolderReceiver(LayoutInflater.from(parent!!.context).inflate(R.layout.item_chat_one_one_receiver, parent, false))

        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ChatItemViewHolder) {
            if (chats != null && chats.size > 0) {

                var chat: FChat = chats.get(position)
                holder.bind(chat)
            }
        }

        if (holder is ChatItemViewHolderReceiver) {
            if (chats != null && chats.size > 0) {
                var messageReceiver = chats.get(position)
                holder.bindReceiver(messageReceiver)

            }
        }
    }

    class ChatItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: FChat) {
            itemView.txt_message_send.text = message.contentChat

        }
    }

    class ChatItemViewHolderReceiver(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindReceiver(message: FChat) {
            itemView.txt_message_receiver.text = message.contentChat

        }
    }

    override fun getItemViewType(position: Int): Int {
        if (chats.get(position).idSender.equals(uId))
            return RIGHT
        else
            return LEFT
    }
}