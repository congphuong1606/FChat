package vn.phuongcong.fchat.ui.main.fragment.listgroup.chat

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.item_list_image.*
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.model.Message
import vn.phuongcong.fchat.common.utils.DatabaseRef.Companion.ADMIN_KEY
import vn.phuongcong.fchat.common.utils.DatabaseRef.Companion.GROUP_KEY
import vn.phuongcong.fchat.event.IitemClick
import vn.phuongcong.fchat.ui.chat.LinkImageAdapter
import vn.phuongcong.fchat.ui.chat.ListImageAdapter
import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import java.text.CollationKey
import javax.inject.Inject

class ChatGroupActivity : BaseActivity(), ChatGroupView, IitemClick {
    override fun onClick() {
    }

    override fun iClick(o: Any) {

    }

    override fun iClick(strPath: Any, img_check: ImageView?) {
        rl_send.visibility = View.VISIBLE
        if (mListPathCurrent.size > 0 && mListPathCurrent.contains(strPath)) {
            mListPathCurrent.remove(strPath)
            count--

            Log.d("count-----", count.toString())
            if (count > 0)
                txt_count_send.text = "(" + count.toString() + ")"
            else
                txt_count_send.text = ""
            img_check?.visibility = View.INVISIBLE
            if (count > 4) {
                Toast.makeText(this, "Bạn chỉ được chọn tối đa 4 ảnh", Toast.LENGTH_SHORT).show()
                return
            }
        } else {
            Toast.makeText(this, strPath as String, Toast.LENGTH_SHORT).show()
            mListPathCurrent.add(strPath as String)
            count++
            Log.d("count-----", count.toString())
            txt_count_send.text = "(" + count.toString() + ")"
            img_check?.visibility = View.VISIBLE
            if (count > 4) {
                Toast.makeText(this, "Bạn chỉ được chọn tối đa 4 ảnh", Toast.LENGTH_SHORT).show()
                img_check?.visibility = View.GONE
                if (mListPathCurrent.get(4) != null) {
                    mListPathCurrent.removeAt(4)
                }
                count--
                txt_count_send.text = "(" + count.toString() + ")"
                return
            }
        }
    }

    override fun getListImageSuccess(list: MutableList<String>) {
        mImageAdapter.mListImage = list
        mImageAdapter.notifyDataSetChanged()
    }


    private var arrMessage = ArrayList<Message>()
    private var arrMessageKey = ArrayList<String>()
    private lateinit var adminKey: String
    private lateinit var groupKey: String
    private lateinit var chatGroupAdapter: ChatGroupAdapter
    @Inject
    lateinit var mPresenter: ChatGroupPresenter
    private lateinit var mImageAdapter: ListImageAdapter
    private var mListImage: MutableList<String> = mutableListOf()
    private var mListPathCurrent: MutableList<String> = mutableListOf()
    private var count = 0
    override val contentLayoutID: Int
        get() = R.layout.activity_chat

    override fun injectDependence() {
        App().get(this)
                .plus(ViewModule(this))
                .injectTo(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initData() {
        adminKey = intent.getStringExtra(ADMIN_KEY)
        groupKey = intent.getStringExtra(GROUP_KEY)
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), Contans.EXTERNAL_PERMISSION_REQUEST)
        }
        mImageAdapter = ListImageAdapter(mListImage, this, this, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initViews()
        mPresenter.receiveChatData(adminKey, groupKey)
        Log.e("TAG", contentResolver.toString())
    }

    private fun initViews() {
        rc_chat.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        chatGroupAdapter = ChatGroupAdapter(arrMessage, arrMessageKey, adminKey, groupKey)
        rc_chat.adapter = chatGroupAdapter
        btn_send_message.setOnClickListener {
            var content = edt_input_message.text.toString()
            if (!TextUtils.isEmpty(content)) {
                mPresenter.onChat(edt_input_message.text.toString(), mListPathCurrent, adminKey, groupKey)
                edt_input_message.setText("")
            }
        }
        btn_send_image.setOnClickListener {
            chooseImage()
        }
        btn_cancel.setOnClickListener {
            cancelChooseImage()
        }
    }

    override fun showChatItem(message: Message, messageKey: String) {
        chatGroupAdapter.addItem(message, messageKey)
        rc_chat.smoothScrollToPosition(arrMessage.size - 1)
    }

    override fun onRequestFailure(string: String) {

    }

    override fun showToast(msg: String) {

    }

    private fun chooseImage() {
        mPresenter.getListImage(this)
        list_image.visibility = View.VISIBLE
        rc_list_image.apply {
            adapter = mImageAdapter
            layoutManager = LinearLayoutManager(this@ChatGroupActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun sendImageFromStorage(mListPathCurrent: MutableList<String>) {

        mImageAdapter.checkVisibleImageCheck = true
        mImageAdapter.notifyDataSetChanged()
        txt_count_send.text = ""
        count = 0
    }

    private fun cancelChooseImage() {
        mImageAdapter.checkVisibleImageCheck = true
        mImageAdapter.notifyDataSetChanged()
        txt_count_send.text = ""
        count = 0
        mListPathCurrent.clear()
        list_image.visibility = View.GONE
    }
}
