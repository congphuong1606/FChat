package vn.phuongcong.fchat.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_image.view.*
import vn.phuongcong.fchat.R

/**
 * Created by Ominext on 10/30/2017.
 */
class GridImageAdapter(var mListImage: MutableList<String>) : BaseAdapter() {
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var view: View
        view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_image, parent, false)
        Glide.with(parent.context).load(mListImage.get(position)).into(view.img_image)
        return view
    }

    override fun getItem(position: Int): Any {
        return mListImage.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mListImage.size
    }
}