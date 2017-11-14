package vn.phuongcong.fchat.ui.main.fragment.listgroup.friend_adding

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.phuongcong.fchat.R

/**
 * Created by vietcoscc on 14/11/2017.
 */
class FriendMemberAdapter(private var arrMember: MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int = arrMember.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        var viewHolder = holder as FriendMemberViewHolder
        viewHolder.bind()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var view: View = LayoutInflater.from(parent!!.context).inflate(R.layout.item_member_friend, parent, false)
        return FriendMemberViewHolder(view)
    }

    class FriendMemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {

        }
    }
}