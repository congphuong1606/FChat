package vn.phuongcong.fchat.ui.adapter

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
import vn.phuongcong.fchat.data.model.User
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
        fun bindItems(friend: User, listener: OnFriendClick) {
            var friendAvatar = itemView.findViewById<ImageView>(R.id.friendAvatar)
            var friendName = itemView.findViewById<TextView>(R.id.tvFriendName)
            var imvLight = itemView.findViewById<ImageView>(R.id.imvlight)
            var tvStatus = itemView.findViewById<TextView>(R.id.tvStatus)
            var timeStamp=friend.timeStamp
            if(timeStamp!=0L ){
                if(((System.currentTimeMillis() - timeStamp) > Contans.TIME_TO_OFFLINE)){
                    var timeOffLine=DateTimeUltil.convertLongToTime((System.currentTimeMillis() - timeStamp as Long))
//                    Glide.with(itemView.context).load(R.drawable.offline)
//                            .into(imvLight)
                    imvLight.setImageDrawable(itemView.context.resources.getDrawable(R.drawable.offline))
                    tvStatus.setText(Contans.STATUS_OFFLINE+timeOffLine)
                }else{
//                    Glide.with(itemView.context).load(R.drawable.online)
//                            .into(imvLight)
                    imvLight.setImageDrawable(itemView.context.resources.getDrawable(R.drawable.online))
                    tvStatus.setText(Contans.STATUS_ONLINE)
                }

            }
            Glide.with(itemView.context).load(friend.avatar)
                    .error(R.drawable.ic_no_image)
                    .into(friendAvatar)
            friendName.text = friend.name

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

