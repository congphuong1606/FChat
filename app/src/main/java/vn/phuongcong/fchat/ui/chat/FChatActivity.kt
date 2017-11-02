package vn.phuongcong.fchat.ui.chat

import android.content.SharedPreferences
import kotlinx.android.synthetic.main.activity_fchat.*
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.common.utils.ChatUtils
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.model.FChat
import vn.phuongcong.fchat.model.User
import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import javax.inject.Inject

class FChatActivity : BaseActivity(), FChatView {
    private lateinit var friendChat: User
    private var imagechat: String? = null
    private var stickChat: String? = null
    private var voiceChat: String? = null
    private var contentChat: String? = null
    private var uId: String? = null
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
            if (!contentChat.isNullOrEmpty() || !imagechat.isNullOrEmpty()
                    || !stickChat.isNullOrEmpty() || !voiceChat.isNullOrEmpty()) {
                var fchat= FChat(0,uId!!,friendChat.id,contentChat!!,imagechat!!,voiceChat!!,stickChat!!,0)
                mPresenter.sendchat(idRoomChat,fchat)
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
        loadDataRoomchat(idRoomChat)

    }

    private fun loadDataRoomchat(idRoomChat: String?) {
        if (!idRoomChat.isNullOrEmpty()) {
            mPresenter.loadDataRoomchat(idRoomChat)
        }
    }

    override fun onRequestFailure(string: String) {

    }

    override fun showToast(msg: String) {
    }


}
