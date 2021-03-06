package vn.phuongcong.fchat.ui.main.fragment.listgroup

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pawegio.kandroid.runOnUiThread
import com.squareup.picasso.Picasso
import com.stfalcon.multiimageview.MultiImageView
import kotlinx.android.synthetic.main.item_group.view.*
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.event.IitemClick
import vn.phuongcong.fchat.model.Group
import vn.phuongcong.fchat.model.User
import vn.phuongcong.fchat.common.utils.DatabaseRef
import vn.phuongcong.fchat.ui.main.fragment.listgroup.friend_adding.FriendAddingDialog

/**
 * Created by vietcoscc on 10/20/2017.
 */
class GroupAdapter constructor(private var arrGorup: ArrayList<Group>?, private var arrGroupKey: ArrayList<String>, private var onItemClick: IitemClick) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var context: Context


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        var groupViewHolder: GroupViewHolder = holder as GroupViewHolder
        groupViewHolder.bind(arrGorup!![position], onItemClick)

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        context = parent!!.context
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        return GroupViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrGorup!!.size

    }

    class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(group: Group, onItemClick: IitemClick) {
            itemView.setOnClickListener {
                onItemClick.iClick(position, null)
            }

            itemView.ivAddMember.setOnClickListener {
                var activity: AppCompatActivity = itemView.context as AppCompatActivity
                FriendAddingDialog(itemView.context, group.adminKey, group.groupKey).show(activity.fragmentManager, "")
            }

            itemView.tvGroupName.text = group.groupName
            itemView.tvTimeStamp.text = group.timeStamp
            itemView.ivAvatar.shape = MultiImageView.Shape.CIRCLE
//            DatabaseRef.userInfoRef(group.adminKey).addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onCancelled(p0: DatabaseError?) {
//
//                }
//
//                override fun onDataChange(p0: DataSnapshot?) {
//                    var user: User = p0!!.getValue(User::class.java)!!
//                    itemView.tvMember.text = "" + itemView.tvMember.text + user.email + ", "
//                    if (user.avatar == null) {
//                        Toast.makeText(itemView.context, "Null", Toast.LENGTH_SHORT).show()
//                        return
//                    }
//
//                }
//            })
            DatabaseRef.groupMemberRef(group.adminKey).child(group.groupKey).addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                    var uid: String = p0!!.getValue(String::class.java)!!
                    DatabaseRef.userInfoRef(uid).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {

                        }

                        override fun onDataChange(p0: DataSnapshot?) {
                            var user: User = p0!!.getValue(User::class.java)!!
                            itemView.tvMember.text = "" + itemView.tvMember.text + user.email + ", "
                            if (!TextUtils.isEmpty(user.avatar)) {
                                Thread {
                                    try {
                                        var bm = Glide.
                                                with(itemView.context).
                                                load(user.avatar).
                                                asBitmap().
                                                into(100, 100).get() // Width and height

                                        runOnUiThread {
                                            itemView.ivAvatar.addImage(bm)
                                        }
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }

                                }.start()
                            } else {

                            }
                        }
                    })
                }

                override fun onCancelled(p0: DatabaseError?) {

                }

                override fun onChildMoved(p0: DataSnapshot?, p1: String?) {

                }

                override fun onChildChanged(p0: DataSnapshot?, p1: String?) {


                }


                override fun onChildRemoved(p0: DataSnapshot?) {
                }

            })
        }
    }

    fun addItem(group: Group) {
        arrGorup!!.add(group)
        arrGroupKey.add(group.groupKey)
        notifyItemInserted(arrGorup!!.size - 1)
    }

    fun removeItem(position: Int) {
        if (position > -1) {
            arrGorup!!.removeAt(position)
            arrGroupKey.removeAt(position)
            notifyItemRemoved(position)
        }
    }

}