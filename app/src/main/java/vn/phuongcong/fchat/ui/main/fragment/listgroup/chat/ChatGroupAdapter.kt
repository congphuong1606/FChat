package vn.phuongcong.fchat.ui.main.fragment.listgroup.chat

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.item_chat_group.view.*
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.model.Message
import vn.phuongcong.fchat.model.User
import vn.phuongcong.fchat.common.utils.DatabaseRef

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
        var chatGroupViewHolder: ChatGroupViewHolder = holder as ChatGroupViewHolder
        chatGroupViewHolder.bindView(arrMessage[position])
    }

    class ChatGroupViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bindView(message: Message) {
           /* if (!TextUtils.isEmpty(message.msgImage)) {
                itemView.visibility = View.VISIBLE
                Glide.with(itemView.context).load(message.msgImage).into(itemView.ivImage)
            } else {
                itemView.visibility = View.INVISIBLE
            }*/
            itemView.tvContent.text = message.content
            itemView.tvTimeStamp.text = message.timeCreate
            DatabaseRef.userInfoRef(FirebaseAuth.getInstance().currentUser!!.uid)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {

                        }

                        override fun onDataChange(p0: DataSnapshot?) {
                            if (p0!!.exists()) {
                                var user: User = p0!!.getValue(User::class.java)!!
                                if (TextUtils.isEmpty(user.avatar)) {
                                    Glide.with(itemView.context).load(user.avatar).into(itemView.ivAvatar)
                                }
                                if (TextUtils.isEmpty(user.name)) {
                                    itemView.tvDisplayName.text = user.name
                                }
                            }
                        }
                    })
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