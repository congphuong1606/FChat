package vn.phuongcong.fchat.ui.main.fragment.chat


import android.support.v7.widget.RecyclerView
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
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder : RecyclerView.ViewHolder
        if (type == 0)
            viewHolder= ChatItemViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_chat_one_one_send, parent, false))
        else
            viewHolder= ChatItemViewHolderReceiver(LayoutInflater.from(parent!!.context).inflate(R.layout.item_chat_one_one_receiver, parent, false))

        return  viewHolder
    }

    override fun getItemCount(): Int {
        if (type == 0) return mMessage.size
        else return mMessageReceiver.size


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ChatItemViewHolder) {
            if (mMessage != null && mMessage.size > 0) {
                var message: Message = mMessage.get(position)
                holder.bind(message)
            }
        }

        if (holder is ChatItemViewHolderReceiver) {
            if (mMessageReceiver != null && mMessageReceiver.size > 0) {
                var messageReceiver = mMessageReceiver.get(position)
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

}