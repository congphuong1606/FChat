package vn.phuongcong.fchat.ui.main.fragment.chat

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_chat.*
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.model.Chat
import vn.phuongcong.fchat.model.Message
import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import java.util.*
import javax.inject.Inject


class ChatActivity : BaseActivity(), ChatView, View.OnClickListener {


    var mMessages: MutableList<Message> = mutableListOf()
    var mMessageReceiver:MutableList<Message> = mutableListOf()
    lateinit var mChatAdapter: ChatApdapter

    @Inject
    lateinit var mChatPresenter: ChatPresenter
    lateinit var mChatItem: Chat
    override val contentLayoutID: Int
        get() = R.layout.activity_chat

    override fun onClick() {

    }

    override fun injectDependence() {
        App().get(this).plus(ViewModule(this)).injectTo(this)
    }

    override fun initData() {

        rc_chat.setHasFixedSize(true)
        mChatAdapter = ChatApdapter(mMessage = mMessages, mMessageReceiver = mMessageReceiver, type = null)
        rc_chat.apply {
            adapter = mChatAdapter
            layoutManager = LinearLayoutManager(context)
        }

        mChatItem= intent.getSerializableExtra(Contans.CHAT_ITEM) as Chat
        mChatPresenter.getListChat(mChatItem)

        addEvent()

    }

    private fun addEvent() {
        btn_send_message.setOnClickListener(this)
        btn_send_image.setOnClickListener(this)
    }

    override fun onRequestFailure(string: String) {

    }

    override fun showToast(msg: String) {

    }


    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.btn_send_message->sendMessagetext(mChatItem)
            R.id.btn_send_image->sendImage()
        }
    }

    private fun sendImage() {

    }

    private fun sendMessagetext(mChatItem: Chat) {
        var messagetext=edt_input_message.text.toString().trim()
        mChatPresenter.sendMessagetext(messagetext,mChatItem)
        edt_input_message.text=null
    }


    override fun getListMessageSuccess(messages: MutableList<Message>) {
        mChatAdapter.mMessage=messages
        mChatAdapter.type=1
        mChatAdapter.notifyDataSetChanged()
        rc_chat.smoothScrollToPosition(messages.size)
    }

    override fun getListMessageReceiverSuccess(messages: MutableList<Message>) {
        mChatAdapter.mMessageReceiver=messages
        mChatAdapter.type=0
        mChatAdapter.notifyDataSetChanged()
        rc_chat.smoothScrollToPosition(messages.size)
    }

}
