package vn.phuongcong.fchat.ui.main.fragment.listgroup.chat

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.item_chat_group.view.*
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.model.Message
import vn.phuongcong.fchat.model.User
import vn.phuongcong.fchat.common.utils.DatabaseRef
import java.util.*

/**
 * Created by vietcoscc on 01/11/2017.
 */
class ChatGroupAdapter(var arrMessage: ArrayList<Message>, var arrMessageKey: ArrayList<String>, var adminKey: String, var groupKey: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var v = LayoutInflater.from(parent!!.context).inflate(R.layout.item_chat_group, parent, false)
        return ChatGroupViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrMessage.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        var chatGroupViewHolder: ChatGroupViewHolder = holder as ChatGroupViewHolder
        chatGroupViewHolder.bindView(arrMessage[position], arrMessageKey[position], adminKey, groupKey)
    }

    class ChatGroupViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var arrImage = arrayListOf<String>()
        lateinit var imageAdapter: ImageAdapter
        fun bindView(message: Message, messageKey: String, adminKey: String, groupKey: String) {

            if (message.msgImage!!.size > 0) {
                itemView.recyclerViewImage.visibility = View.VISIBLE
                if (message.msgImage!!.size == 1) {
                    itemView.recyclerViewImage.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                } else {
                    itemView.recyclerViewImage.layoutManager = GridLayoutManager(itemView.context, 2)
                }
                itemView.recyclerViewImage.adapter = ImageAdapter(arrImage)
            } else {
                itemView.recyclerViewImage.visibility = View.INVISIBLE
            }
            imageAdapter = ImageAdapter(arrImage)
            itemView.recyclerViewImage.adapter = imageAdapter
            imageAdapter.clearItem()
            var childEvent = object : ChildEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                    var image = p0!!.getValue(String::class.java)
                    imageAdapter.addItem(image!!)
                }

                override fun onChildRemoved(p0: DataSnapshot?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            }
            DatabaseRef.groupContentRef(adminKey).child(groupKey).child(messageKey).child("msgImage").removeEventListener(childEvent)
            DatabaseRef.groupContentRef(adminKey).child(groupKey).child(messageKey).child("msgImage").addChildEventListener(childEvent)
            itemView.tvContent.text = message.content
            itemView.tvTimeStamp.text = message.timeCreate
            DatabaseRef.userInfoRef(FirebaseAuth.getInstance().currentUser!!.uid)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {

                        }

                        override fun onDataChange(p0: DataSnapshot?) {
                            if (p0!!.exists()) {
                                var user: User = p0!!.getValue(User::class.java)!!
                                if (!TextUtils.isEmpty(user.avatar)) {
                                    Glide.with(itemView.context).load(user.avatar).into(itemView.ivAvatar)
                                }
                                if (!TextUtils.isEmpty(user.name)) {
                                    itemView.tvDisplayName.text = user.name
                                }
                            }
                        }
                    })
        }
    }

    fun addItem(message: Message, key: String) {
        arrMessage.add(message)
        arrMessageKey.add(key)
        notifyItemInserted(arrMessage.size - 1)
    }

    fun removeItem(position: Int) {
        arrMessage.removeAt(position)
        arrMessageKey.removeAt(position)
        notifyItemRemoved(position)
    }
}