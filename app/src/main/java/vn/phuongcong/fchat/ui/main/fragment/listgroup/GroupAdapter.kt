package vn.phuongcong.fchat.ui.main.fragment.listgroup

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.pawegio.kandroid.v
import com.stfalcon.multiimageview.MultiImageView
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.item_group.view.*
import org.w3c.dom.Text
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.data.Group

/**
 * Created by vietcoscc on 10/20/2017.
 */
class GroupAdapter constructor(var arrGorup: ArrayList<Group>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        var groupViewHolder: GroupViewHolder = holder as GroupViewHolder;
        groupViewHolder.bind(arrGorup!![position]);
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_group, parent, false);
        return GroupViewHolder(view);
    }

    override fun getItemCount(): Int {
        return arrGorup!!.size;
    }

    class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(group: Group) {
            itemView.tvGroupName.setText(group.groupName);
            itemView.tvTimeStamp.setText(group.timeStamp);
        }
    }

    fun addItem(group: Group) {
        arrGorup!!.add(group);
        notifyItemInserted(arrGorup!!.size - 1);
    }

    fun removeItem(position: Int) {
        arrGorup!!.removeAt(position);
        notifyItemRemoved(position);
    }
}