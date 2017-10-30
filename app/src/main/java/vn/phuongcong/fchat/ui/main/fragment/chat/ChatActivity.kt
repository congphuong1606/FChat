package vn.phuongcong.fchat.ui.main.fragment.chat

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.item_list_image.*
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.model.Chat
import vn.phuongcong.fchat.model.Message
import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import javax.inject.Inject


class ChatActivity : BaseActivity(), ChatView, View.OnClickListener {


    var mMessages: MutableList<Message> = mutableListOf()
    var mListImage :MutableList<String> = mutableListOf()
    lateinit var mChatAdapter: ChatApdapter
    lateinit var mImageAdapter:ImageAdapter

    @Inject
    lateinit var mChatPresenter: ChatPresenter
    lateinit var mChatItem: Chat
    override val contentLayoutID: Int
        get() = R.layout.activity_chat

    override fun onClick() {

    }

    override fun injectDependence() {
        (application as App).component
                .plus(ViewModule(this))
                .injectTo(this)
    }

    override fun initData() {



        mImageAdapter= ImageAdapter(mListImage,this)
        rc_chat.setHasFixedSize(true)
        mChatAdapter = ChatApdapter(mMessage = mMessages)
        rc_chat.apply {
            adapter = mChatAdapter
            layoutManager = LinearLayoutManager(context)
        }

        mChatItem = intent.getSerializableExtra(Contans.CHAT_ITEM) as Chat
        mChatPresenter.getListChat(mChatItem)

        addEvent()

    }

    private fun addEvent() {
        btn_send_message.setOnClickListener(this)
        btn_send_image.setOnClickListener(this)
        edt_input_message.setOnClickListener(this)

    }

    override fun onRequestFailure(string: String) {

    }

    override fun showToast(msg: String) {

    }


    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_send_message -> sendMessagetext(mChatItem)
            R.id.btn_send_image -> sendImage()
            R.id.edt_input_message->focusMessage()
        }
    }

    private fun focusMessage() {
        list_image.visibility=View.GONE
    }

    private fun sendImage() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {

                }
                else {

                }
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)

            }
        }
        mChatPresenter.getListImage(this)
        list_image.visibility=View.VISIBLE

        rc_list_image.apply {
            adapter = mImageAdapter
            layoutManager = LinearLayoutManager(this@ChatActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun sendMessagetext(mChatItem: Chat) {
        var messagetext = edt_input_message.text.toString().trim()
        mChatPresenter.sendMessagetext(messagetext, mChatItem)
        edt_input_message.text = null
    }


    override fun getListMessageSuccess(messages: MutableList<Message>) {
        mChatAdapter.mMessage = messages
        mChatAdapter.notifyDataSetChanged()
        rc_chat.smoothScrollToPosition(messages.size)
    }

    override fun getListImageSuccess(list: MutableList<String>) {
        mImageAdapter.mListImage=list
        mImageAdapter.notifyDataSetChanged()
    }

}
