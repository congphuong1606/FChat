package vn.phuongcong.fchat.ui.chat

import android.content.SharedPreferences
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_fchat.*
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.common.utils.ChatUtils
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.model.FChat
import vn.phuongcong.fchat.model.User
import vn.phuongcong.fchat.ui.adapter.FchatAdapter
import vn.phuongcong.fchat.ui.adapter.FriendsAdapter
import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import javax.inject.Inject

class FChatActivity : BaseActivity(), FChatView {
    private lateinit var mAdapter: FchatAdapter
    lateinit var chats: MutableList<FChat>
    private lateinit var friendChat: User
    private var imagechat: String = ""
    private var stickChat: String = ""
    private var voiceChat: String = ""
    private var contentChat: String = ""
    private var uId: String = ""
    private var idRoomChat: String? = null
    @Inject
    lateinit var sPref: SharedPreferences
    @Inject
    lateinit var mPresenter: FChatPresenter


    override val contentLayoutID: Int
        get() = R.layout.activity_fchat

    override fun injectDependence() {
        (application as App).component
                .plus(ViewModule(this))
                .injectTo(this)

    }

    override fun onClick() {
        btnSendchat.setOnClickListener {
            contentChat = edtInputMsg.text.toString().trim()
            if (!contentChat.isNullOrEmpty() || !imagechat.isNullOrEmpty()
                    || !stickChat.isNullOrEmpty() || !voiceChat.isNullOrEmpty()) {
                val fchat = FChat(System.currentTimeMillis(), uId, friendChat.id, contentChat, imagechat, voiceChat, stickChat, System.currentTimeMillis())
                mPresenter.sendchat(idRoomChat, fchat)
            }
        }
    }


    override fun initData() {
        uId = sPref.getString(Contans.PRE_USER_ID, "")
        idRoomChat = intent.getStringExtra("idRoomChat")
        if (idRoomChat.isNullOrEmpty()) {
            friendChat = intent.getBundleExtra("bFrienfragment").getSerializable("friend") as User
            idRoomChat = ChatUtils.getIdRoomChat(uId!!, friendChat.id)
        }
        var rcvChat = findViewById<RecyclerView>(R.id.rcvChat)
        rcvChat.layoutManager = LinearLayoutManager(this)
        rcvChat.setHasFixedSize(true)
        chats = mutableListOf()
        mAdapter = FchatAdapter(uId, chats)
        rcvChat.adapter = mAdapter
        loadDataRoomchat(idRoomChat)

    }

    private fun loadDataRoomchat(idRoomChat: String?) {

        if (!idRoomChat.isNullOrEmpty()) {
            mPresenter.loadDataRoomchat(idRoomChat, chats)
        }
    }

    override fun onLoadChatsSuccess(chats: MutableList<FChat>){
        if(chats.isNotEmpty()){
            mAdapter.notifyDataSetChanged()
            rcvChat.smoothScrollToPosition(chats.size - 1)
        }

    }

    override fun onSendChatSuccessful(fchat: FChat) {
        contentChat = ""
        imagechat = ""
        stickChat = ""
        voiceChat = ""
        edtInputMsg.setText("")
        mAdapter.notifyItemChanged(chats.size)
        rcvChat.smoothScrollToPosition(chats.size-1)

    }

    override fun onRequestFailure(string: String) {

    }

    override fun showToast(msg: String) {
    }


}
