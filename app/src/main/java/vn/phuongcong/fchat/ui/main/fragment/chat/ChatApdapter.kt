package vn.phuongcong.fchat.ui.main.fragment.chat


import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_chat_one_one_receiver.view.*
import kotlinx.android.synthetic.main.item_chat_one_one_send.view.*
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.model.Message

/**
 * Created by Ominext on 10/20/2017.
 */
class ChatApdapter(var mMessage: MutableList<Message>, var mMessageReceiver: MutableList<Message>, var type: Int?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        var LEFT = 1
        var RIGHT = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder?=null
        if (viewType == RIGHT)
             viewHolder = ChatItemViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_chat_one_one_send, parent, false))
        if(viewType== LEFT)
            viewHolder = ChatItemViewHolderReceiver(LayoutInflater.from(parent!!.context).inflate(R.layout.item_chat_one_one_receiver, parent, false))

        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return mMessage.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ChatItemViewHolder) {
            if (mMessage != null && mMessage.size > 0) {
                mMessage.sortBy { message: Message -> message.timeCreate }

                var message: Message = mMessage.get(position)
                holder.bind(message)
            }
        }

        if (holder is ChatItemViewHolderReceiver) {
            if (mMessage != null && mMessage.size > 0) {
                mMessage.sortBy { message: Message -> message.timeCreate }
                var messageReceiver = mMessage.get(position)
                holder.bindReceiver(messageReceiver)

            }
        }
    }

    class ChatItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: Message) {
            itemView.txt_message_send.text = message.content
            itemView.txt_time_send.text = message.timeCreate
        }
    }

    class ChatItemViewHolderReceiver(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindReceiver(message: Message) {
            itemView.txt_message_receiver.text = message.content
            itemView.txt_time_receicver.text = message.timeCreate
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (mMessage.get(position).mType == RIGHT)
            return RIGHT
        else
            return LEFT
    }
}