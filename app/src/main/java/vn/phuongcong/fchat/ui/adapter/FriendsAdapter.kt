package vn.phuongcong.fchat.ui.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.common.utils.DateTimeUltil
import vn.phuongcong.fchat.model.User
import vn.phuongcong.fchat.event.OnFriendClick

/**
 * Created by Ominext on 10/19/2017.
 */
class FriendsAdapter(var friends: MutableList<User>, var listener: OnFriendClick)
    : RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>() {
//    lateinit var listener: OnFriendClick
//    fun onListener(onFriendClick: OnFriendClick) {
//        this.listener = onFriendClick
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_friend, parent, false)
        return FriendsAdapter.FriendsViewHolder(v)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        holder.bindItems(friends[position], listener)
    }

    override fun getItemCount(): Int {
        return friends.size

    }

    class FriendsViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bindItems(friend: User, listener: OnFriendClick) {
            val friendAvatar = itemView.findViewById<ImageView>(R.id.friendAvatar)
            val friendName = itemView.findViewById<TextView>(R.id.tvFriendName)
            val imvLight = itemView.findViewById<ImageView>(R.id.imvlight)
            val tvStatus = itemView.findViewById<TextView>(R.id.tvStatus)

            /*val timeStamp=friend.timeStamp
            if(timeStamp!=0L ){
                if(((System.currentTimeMillis() - timeStamp) > Contans.TIME_TO_OFFLINE)){
                    val timeOffLine=DateTimeUltil.convertLongToTime((System.currentTimeMillis() - timeStamp as Long))
                    imvLight.setImageDrawable(itemView.context.resources.getDrawable(R.drawable.offline))
                    tvStatus.setText(Contans.STATUS_OFFLINE+timeOffLine)
                }else{
                    imvLight.setImageDrawable(itemView.context.resources.getDrawable(R.drawable.online))
                    tvStatus.setText(Contans.STATUS_ONLINE)
                }

            }*/
            Glide.with(itemView.context).load(friend.avatar)
                    .error(R.drawable.ic_no_image)
                    .into(friendAvatar)
            friendName.text = friend.name
            tvStatus.text=friend.email

            itemView.setOnClickListener {
                listener.onItemClick(friend)
            }
            itemView.setOnLongClickListener {
                listener.onLongItemClick(friend,adapterPosition)
                return@setOnLongClickListener true
            }

        }
    }
}

