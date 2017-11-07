package vn.phuongcong.fchat.ui.chat.showimage

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import vn.phuongcong.fchat.R

/**
 * Created by Ominext on 11/6/2017.
 */
class ShowImageAdapter(var mLinkImage: MutableList<String>) : PagerAdapter() {
    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view!!.equals(`object`)
    }

    override fun getCount(): Int {
        return mLinkImage.size
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        var view: View?
        view = LayoutInflater.from(container!!.context).inflate(R.layout.item_image_show, container, false)
        var imgShow = view.findViewById<ImageView>(R.id.img_show)

        Glide.with(container.context).load(mLinkImage.get(position)).into(imgShow)
        container.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container!!.removeView(`object` as View?)
    }
}