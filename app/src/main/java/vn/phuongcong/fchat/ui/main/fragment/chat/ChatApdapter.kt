package vn.phuongcong.fchat.ui.main.fragment.chat


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.model.Message

/**
 * Created by Ominext on 10/20/2017.
 */
class ChatApdapter(var mMessage: MutableList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ChatItemViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_msg, parent, false))
    }

    override fun getItemCount(): Int {
        return mMessage.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ChatItemViewHolder) {
            var message: Message = mMessage.get(position)
            holder.bind(message)
        }
    }

    class ChatItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: Message) {

        }
    }
}