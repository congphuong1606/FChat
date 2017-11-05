package vn.phuongcong.fchat.ui.main.fragment.listgroup.chat

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.item_list_image.*
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.event.IitemClick
import vn.phuongcong.fchat.model.Message
import vn.phuongcong.fchat.ui.main.fragment.chat.ImageAdapter
import vn.phuongcong.fchat.utils.DatabaseRef.Companion.ADMIN_KEY
import vn.phuongcong.fchat.utils.DatabaseRef.Companion.GROUP_KEY
import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import javax.inject.Inject

class ChatGroupActivity : BaseActivity(), ChatGroupView, View.OnClickListener {
    override fun getListImageSuccess(absolutePathOfImage: MutableList<String>) {

    }

    lateinit var mImageAdapter: ImageAdapter
    private var arrMessage = ArrayList<Message>()
    var mListImage: MutableList<String> = mutableListOf()
    private lateinit var adminKey: String
    private lateinit var groupKey: String
    private lateinit var chatGroupAdapter: ChatGroupAdapter
    @Inject
    lateinit var mPresenter: ChatGroupPresenter
    override val contentLayoutID: Int
        get() = R.layout.activity_chat

    override fun onClick() {

    }

    override fun injectDependence() {
        App().get(this)
                .plus(ViewModule(this))
                .injectTo(this)
    }

    override fun initData() {
        adminKey = intent.getStringExtra(ADMIN_KEY)
        groupKey = intent.getStringExtra(GROUP_KEY)
        initViews()
        btn_send_image.setOnClickListener(this)
        mPresenter.receiveChatData(adminKey, groupKey)


        mImageAdapter = ImageAdapter(mListImage, this, object : IitemClick {
            override fun iClick(o: Any, txt_count: ImageView?) {

            }

        })

//        rc_chat.setHasFixedSize(true)
    }


    private fun initViews() {
        rc_chat.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        chatGroupAdapter = ChatGroupAdapter(arrMessage)
        rc_chat.adapter = chatGroupAdapter
        btn_send_message.setOnClickListener {
            var content = edt_input_message.text.toString()
            if (!TextUtils.isEmpty(content)) {
                mPresenter.onChat(edt_input_message.text.toString(), "", adminKey, groupKey)
                edt_input_message.setText("")
            }
        }
    }

    override fun showChatItem(message: Message) {
        chatGroupAdapter.addItem(message)
//        rc_chat.smoothScrollToPosition(arrMessage.size-1)
    }

    override fun onRequestFailure(string: String) {

    }

    override fun showToast(msg: String) {

    }

    override fun onClick(view: View?) {
        when (view!!.id) {

            R.id.btn_send_image -> sendImage()

        }
    }

    private fun sendImage() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), Contans.EXTERNAL_PERMISSION_REQUEST)
            }
        }
        val permissionExternal = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permissionExternal == PackageManager.PERMISSION_GRANTED) {
            mPresenter.getListImage(this)
            list_image.visibility = View.VISIBLE
            rc_list_image.apply {
                adapter = mImageAdapter
                layoutManager = LinearLayoutManager(this@ChatGroupActivity, LinearLayoutManager.HORIZONTAL, false)
        }

    }
}}
