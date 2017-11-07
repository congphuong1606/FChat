package vn.phuongcong.fchat.ui.main.fragment.listgroup.chat

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import kotlinx.android.synthetic.main.activity_chat.*
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.data.model.Message
import vn.phuongcong.fchat.common.utils.DatabaseRef.Companion.ADMIN_KEY
import vn.phuongcong.fchat.common.utils.DatabaseRef.Companion.GROUP_KEY
import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import javax.inject.Inject

class ChatGroupActivity : BaseActivity(), ChatGroupView {


    private var arrMessage = ArrayList<Message>()
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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initViews()
        mPresenter.receiveChatData(adminKey,groupKey)
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
        rc_chat.smoothScrollToPosition(arrMessage.size-1)
    }

    override fun onRequestFailure(string: String) {

    }

    override fun showToast(msg: String) {

    }
}
