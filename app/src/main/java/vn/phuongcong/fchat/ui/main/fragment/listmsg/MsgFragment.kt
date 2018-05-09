package vn.phuongcong.fchat.ui.main.fragment.listmsg

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_listmsg.*
import org.greenrobot.eventbus.EventBus
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.event.IStatusListener
import vn.phuongcong.fchat.model.Chat
import vn.phuongcong.fchat.model.Messagelast
import vn.phuongcong.fchat.model.User
import vn.phuongcong.fchat.ui.base.BaseFragment
import vn.phuongcong.fchat.ui.chat.ChatActivity
import javax.inject.Inject


@SuppressLint("ValidFragment")
open class MsgFragment @SuppressLint("ValidFragment") constructor
(private var mIstatus:IStatusListener) : BaseFragment(), ListMsgView, ListMessageAdapter.IChat, SwipeRefreshLayout.OnRefreshListener {
    override fun onDeleteChatSuccess() {

        mPresenter.loadListChat()
    }

    override fun onDeleteChatFail(message: String?) {

    }

    override fun deleteChat(chat: Chat) {
        AlertDialog.Builder(context)
                .setTitle(Contans.TITLE_DELE_FRIEND)
                .setMessage(Contans.REQUEST_DELETE_CHAT + " ${chat.mFriend} ?")
                .setPositiveButton(android.R.string.ok) { dialogInterface, i ->
                    dialogInterface.dismiss()
                    mPresenter.deleteChat(chat)

                }
                .setNegativeButton(android.R.string.cancel) { dialogInterface, i -> dialogInterface.dismiss() }.show()

    }

    @Inject
    lateinit var mPresenter: ListMsgPresenter
    private lateinit var listMessageAdapter: ListMessageAdapter
    private var mChats: MutableList<Chat> = mutableListOf()
    private var mMessagelasts:MutableList<Messagelast> = mutableListOf()


    override val LayoutId: Int
        get() = R.layout.fragment_listmsg

    override fun injectDependence() {
        App().get(context)
                .plus(ViewModule(this))
                .injectTo(this)
    }

    override fun initData(v: View) {
        val rcListMessage = v.findViewById<RecyclerView>(R.id.rc_list_msg)
         val srLoad =v.findViewById<SwipeRefreshLayout>(R.id.sr_load_chat)
        listMessageAdapter = ListMessageAdapter(mChats, this,mMessagelasts,activity)
        rcListMessage.apply {
            adapter = listMessageAdapter
            layoutManager = LinearLayoutManager(context)
        }

        mPresenter.loadListChat()
        srLoad.setOnRefreshListener(this)
    }

    override fun onDestroyComposi() {

    }
    override fun onRefresh() {

        mPresenter.loadListChat()

        sr_load_chat.isRefreshing=false
    }
    override fun chat(chat: Chat) {
        var intent = Intent(activity, ChatActivity::class.java)
        intent.putExtra(Contans.CHAT_ITEM, chat)
        startActivity(intent)
    }

    override fun onLoadListMessagelast(listMessagelast: MutableList<Messagelast>) {
        listMessageAdapter.mListMessageLast=listMessagelast
       listMessageAdapter.notifyDataSetChanged()
    }

    override fun OnLoadListChatSuccess(listChat: MutableList<User>) {
        val listMessage = mutableListOf<Chat>()
        listChat.mapTo(listMessage) { Chat(it.id, it.name, it.avatar,"",null) }
        mChats=listMessage

        listMessageAdapter.mListMessage = listMessage
        listMessageAdapter.notifyDataSetChanged()
    }

    override fun onRequestFailure(string: String) {
//        Toast.makeText(activity, "Fail", Toast.LENGTH_SHORT).show()
    }

    override fun showToast(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResume() {
        super.onResume()
        mPresenter.loadListChat()
    }

    override fun onStart() {
        super.onStart()
    mIstatus.sendStatus("aaa")
    }


}
