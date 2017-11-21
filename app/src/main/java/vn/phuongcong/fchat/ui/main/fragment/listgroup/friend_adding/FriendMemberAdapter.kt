package vn.phuongcong.fchat.ui.main.fragment.listgroup.friend_adding

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioGroup
import com.bumptech.glide.Glide
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pawegio.kandroid.e
import kotlinx.android.synthetic.main.item_friend.view.*
import kotlinx.android.synthetic.main.item_member_friend.view.*
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.common.utils.DatabaseRef
import vn.phuongcong.fchat.model.User

/**
 * Created by vietcoscc on 14/11/2017.
 */
class FriendMemberAdapter(private var arrMember: MutableList<String>, private var arrCheck: MutableList<Boolean>,
                          private var adminKey: String, private var groupKey: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int = arrMember.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        var viewHolder = holder as FriendMemberViewHolder
        viewHolder.bind(arrMember[position], adminKey, groupKey, arrCheck, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var view: View = LayoutInflater.from(parent!!.context).inflate(R.layout.item_member_friend, parent, false)
        return FriendMemberViewHolder(view)
    }

    class FriendMemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(uid: String, adminKey: String, groupKey: String, arrCheck: MutableList<Boolean>, position: Int) {
            itemView.checkBox.setOnClickListener {
                arrCheck[position] = itemView.checkBox.isChecked
            }
            DatabaseRef.userInfoRef(uid).addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onCancelled(p0: DatabaseError?) {

                }

                override fun onDataChange(p0: DataSnapshot?) {
                    if (p0!!.exists()) {
                        var user: User = p0!!.getValue(User::class.java)!!
                        if (!TextUtils.isEmpty(user.avatar)) {
                            Glide.with(itemView.context).load(user.avatar).into(itemView.friendAvatar)
                        }
                        if (!TextUtils.isEmpty(user.name)) {
                            itemView.tvFriendName.text = user.name
                        } else {
                            itemView.tvFriendName.text = user.email
                        }

                    }
                }

            })

            DatabaseRef.groupMemberRef(adminKey).child(groupKey).child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {

                }

                override fun onDataChange(p0: DataSnapshot?) {
                    itemView.checkBox.isChecked = p0 != null && p0!!.exists()
                    arrCheck[position] = p0 != null && p0!!.exists()
                }
            })

        }

    }

    fun addItem(uid: String) {
        arrMember.add(uid)
        notifyItemInserted(arrMember.size - 1)
    }

    fun removeItem(position: Int) {
        arrMember.removeAt(position)
        notifyItemRemoved(position)
    }

}