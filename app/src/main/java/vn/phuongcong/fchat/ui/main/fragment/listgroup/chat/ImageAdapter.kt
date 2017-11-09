package vn.phuongcong.fchat.ui.main.fragment.listgroup.chat

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image_chat_group.view.*
import vn.phuongcong.fchat.R

/**
 * Created by viet on 11/9/2017.
 */
class ImageAdapter(var arrImage: MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_image_chat_group, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as ImageViewHolder).bind(arrImage[position])
    }

    override fun getItemCount(): Int {
        return arrImage.size
    }

    class ImageViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(image: String) {
            if (!TextUtils.isEmpty(image)) {
                Glide.with(itemView.context).load(image)
                        .placeholder(android.R.color.darker_gray).error(android.R.color.black).crossFade(200).into(itemView.ivImage)
            }
        }
    }

    fun addItem(image: String) {
        arrImage.add(image)
        notifyItemInserted(arrImage.size - 1)
    }

    fun removeItem(position: Int) {
        arrImage.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clearItem() {
        arrImage.clear()
        notifyDataSetChanged()
    }
}