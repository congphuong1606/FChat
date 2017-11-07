package vn.phuongcong.fchat.ui.chat


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import kotlinx.android.synthetic.main.item_chat_one_one_receiver.view.*
import kotlinx.android.synthetic.main.item_chat_one_one_send.view.*
import kotlinx.android.synthetic.main.item_image_receiver.view.*
import kotlinx.android.synthetic.main.item_image_send.view.*
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.data.model.Message
import vn.phuongcong.fchat.common.utils.DateTimeUltil

/**
 * Created by Ominext on 10/20/2017.
 */
class ChatApdapter(var mMessage: MutableList<Message>,var mContext:Context,var isend:Isend) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        var LEFT_TEXT = 1
        var RIGHT_TEXT = 0
        var LEFT_IMAGE = 2
        var RIGHT_IMAGE = 3
    }
    interface Isend{
        fun sendDataImage(linkMessage:MutableList<String>,rcList:GridView)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        if (viewType == RIGHT_TEXT)
            viewHolder = ChatItemViewHolderSend(LayoutInflater.from(parent!!.context).inflate(R.layout.item_chat_one_one_send, parent, false))
        if (viewType == LEFT_TEXT)
            viewHolder = ChatItemViewHolderReceiver(LayoutInflater.from(parent!!.context).inflate(R.layout.item_chat_one_one_receiver, parent, false))
        if (viewType == RIGHT_IMAGE)
            viewHolder = ChatItemViewHolderSendImage(LayoutInflater.from(parent!!.context).inflate(R.layout.item_image_send, parent, false))
        if (viewType == LEFT_IMAGE)
            viewHolder = ChatItemViewHolderReceiverImage(LayoutInflater.from(parent!!.context).inflate(R.layout.item_image_receiver, parent, false))

        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return mMessage.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ChatItemViewHolderSend) {
            if (mMessage != null && mMessage.size > 0) {
                mMessage.sortBy { message: Message -> message.timeCreate.toLong() }
                var message: Message = mMessage.get(position)
                holder.bind(message,mContext,isend)
            }
        }

        if (holder is ChatItemViewHolderReceiver) {
            if (mMessage != null && mMessage.size > 0) {
                mMessage.sortBy { message: Message -> message.timeCreate.toLong() }
                var messageReceiver = mMessage.get(position)
                holder.bindReceiver(messageReceiver,mContext,isend)

            }
        }
        if (holder is ChatItemViewHolderSendImage) {
            if (mMessage != null && mMessage.size > 0) {
                mMessage.sortBy { message: Message -> message.timeCreate.toLong() }
                var messageReceiver = mMessage.get(position)
                holder.bindSendImage(messageReceiver,mContext,isend)

            }
        }
        if (holder is ChatItemViewHolderReceiverImage) {
            if (mMessage != null && mMessage.size > 0) {
                mMessage.sortBy { message: Message -> message.timeCreate.toLong() }
                var messageReceiver = mMessage.get(position)
                holder.bindReceiverImage(messageReceiver,mContext,isend)

            }
        }
    }

    class ChatItemViewHolderSend(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: Message, mContext: Context, isend: Isend) {
            itemView.txt_message_send.text = message.content
            itemView.txt_time_send.text = DateTimeUltil.fotmatTime(message.timeCreate.toLong(), DateTimeUltil.mTimeFormat)
        }
    }

    class ChatItemViewHolderReceiver(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindReceiver(message: Message, mContext: Context, isend: Isend) {
            itemView.txt_message_receiver.text = message.content
            itemView.txt_time_receicver.text = DateTimeUltil.fotmatTime(message.timeCreate.toLong(), DateTimeUltil.mTimeFormat)
        }
    }

    //----

    class ChatItemViewHolderSendImage(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bindSendImage(message: Message, mContext: Context, isend: Isend) {
            itemView.txt_time_send_image.text = DateTimeUltil.fotmatTime(message.timeCreate.toLong(), DateTimeUltil.mTimeFormat)
            if(message.msgImage!!.size>0)
                isend.sendDataImage(message.msgImage!!,itemView.gr_list_image_send)
        }
    }

    class ChatItemViewHolderReceiverImage(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindReceiverImage(message: Message, mContext: Context, isend: Isend) {
            itemView.txt_time_receicver_image.text = DateTimeUltil.fotmatTime(message.timeCreate.toLong(), DateTimeUltil.mTimeFormat)
            if(message.msgImage!!.size>0)
                isend.sendDataImage(message.msgImage!!,itemView.gr_list_image_send)
        }
    }

    //----



    override fun getItemViewType(position: Int): Int {
        if (mMessage.get(position).mType == RIGHT_TEXT)
            return RIGHT_TEXT
        else if(mMessage.get(position).mType == LEFT_TEXT)
            return LEFT_TEXT
        else if(mMessage.get(position).mType == LEFT_IMAGE)
            return LEFT_IMAGE
        else
            return RIGHT_IMAGE
    }
}