package vn.phuongcong.fchat.ui.main.fragment.listgroup.chat

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.model.Message

/**
 * Created by vietcoscc on 01/11/2017.
 */
class ChatGroupAdapter(var arrMessage: ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var v = LayoutInflater.from(parent!!.context).inflate(R.layout.item_chat_group, parent, false)
        return ChatGroupViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrMessage.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

    }

    class ChatGroupViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bindView(message: Message) {

        }
    }

    fun addItem(message: Message) {
        arrMessage.add(message)
        notifyItemInserted(arrMessage.size - 1)
    }

    fun removeItem(position: Int) {
        arrMessage.removeAt(position)
        notifyItemRemoved(position)
    }
}