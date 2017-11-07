package vn.phuongcong.fchat.ui.chat.imagesrc

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_grid_image.*
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.event.IitemClick
import vn.phuongcong.fchat.data.model.Chat
import vn.phuongcong.fchat.data.model.Message
import vn.phuongcong.fchat.ui.chat.ChatActivity
import vn.phuongcong.fchat.ui.chat.ChatPresenter
import vn.phuongcong.fchat.ui.chat.ChatView
import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import javax.inject.Inject

class GridImageActivity : BaseActivity(), ChatView, IitemClick, View.OnClickListener {


    lateinit var mChatPresenter: ChatPresenter
    private var mListImage: MutableList<String> = mutableListOf()
    private lateinit var mImageAdapter: GridImageAdapter
    private var mListPathCurrent: MutableList<String> = mutableListOf()
    private var count = 0
    private lateinit var mChatItem: Chat
    override val contentLayoutID: Int
        get() = R.layout.activity_grid_image

    override fun injectDependence() {
//        App().get(this).plus(ViewModule(this)).injectTo(this)
    }

    override fun initData() {
        if (intent.getSerializableExtra(Contans.CHAT_ITEM) != null) {
            mChatItem = intent.getSerializableExtra(Contans.CHAT_ITEM) as Chat
        }
        mImageAdapter = GridImageAdapter(mListImage, this, false)
        gr_image.apply {
            adapter = mImageAdapter
        }
//        mChatPresenter.getListImage(this)
        addEvent()
    }

    private fun addEvent() {
        btn_cancel1.setOnClickListener(this)
        btn_send1.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_cancel1 -> cancelChooseImage()
            R.id.btn_send1 -> chooseAndSendImage(mListPathCurrent)
        }
    }

    private fun chooseAndSendImage(mListPathCurrent: MutableList<String>) {
        mChatPresenter.sendImageFromStorage(mListPathCurrent)
        mImageAdapter.checkVisibleImageCheck = true
        mImageAdapter.notifyDataSetChanged()
        txt_count_send1.text = ""
        count = 0
    }


    private fun cancelChooseImage() {
        mImageAdapter.checkVisibleImageCheck = true
        mImageAdapter.notifyDataSetChanged()
        txt_count_send1.text = ""
        count = 0
        mListPathCurrent.clear()
        var intent = Intent(this, ChatActivity::class.java)
        intent.putExtra(Contans.CHAT_ITEM, mChatItem)
        startActivity(intent)
        finish()

    }

    override fun getListImageSuccess(listImage: MutableList<String>) {
        mImageAdapter.mListImage = listImage
        mImageAdapter.notifyDataSetChanged()
    }

    override fun iClick(strPath: Any, img_check: ImageView?) {

        if (mListPathCurrent.size > 0 && mListPathCurrent.contains(strPath)) {
            mListPathCurrent.remove(strPath)
            count--

            Log.d("count-----", count.toString())
            if (count > 0)
                txt_count_send1.text = "(" + count.toString() + ")"
            else
                txt_count_send1.text = ""
            img_check?.visibility = View.INVISIBLE
            if (count > 4) {
                Toast.makeText(this, "Bạn chỉ được chọn tối đa 4 ảnh", Toast.LENGTH_SHORT).show()
                return
            }
        } else {
            mListPathCurrent.add(strPath as String)
            count++
            Log.d("count-----", count.toString())
            txt_count_send1.text = "(" + count.toString() + ")"
            img_check?.visibility = View.VISIBLE
            if (count > 4) {
                Toast.makeText(this, "Bạn chỉ được chọn tối đa 4 ảnh", Toast.LENGTH_SHORT).show()
                img_check?.visibility = View.GONE
                if (mListPathCurrent.get(4) != null) {
                    mListPathCurrent.removeAt(4)
                }
                count--
                txt_count_send1.text = "(" + count.toString() + ")"
                return
            }
        }
    }

    override fun iClick(o: Any) {

    }

    override fun onRequestFailure(string: String) {

    }

    override fun showToast(msg: String) {

    }

    override fun getListMessageSuccess(messages: MutableList<Message>) {

    }


    override fun onClick() {

    }

    override fun sendImageCamereSuccess(downloadUrl: String?) {

    }

    override fun sendImageSuccess(linkImage: MutableList<String>) {
        txt_count_send1.text = null
        mChatPresenter.sendMessageImage(linkImage, mChatItem)
        mListPathCurrent.clear()
        var intent = Intent(this, ChatActivity::class.java)
        intent.putExtra(Contans.CHAT_ITEM, mChatItem)
        startActivity(intent)
        finish()
    }

}
