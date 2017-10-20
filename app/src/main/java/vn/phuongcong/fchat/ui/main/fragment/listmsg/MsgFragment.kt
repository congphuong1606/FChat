package vn.phuongcong.fchat.ui.main.fragment.listmsg

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.model.Chat
import vn.phuongcong.fchat.model.User
import vn.phuongcong.fchat.ui.base.BaseFragment
import vn.phuongcong.fchat.ui.main.fragment.chat.ChatActivity
import javax.inject.Inject


class MsgFragment : BaseFragment(), ListMsgView, ListMessageAdapter.IChat {


    private lateinit var listMessageAdapter: ListMessageAdapter
    override fun OnLoadListChatSuccess(listChat: MutableList<User>) {
        var listMessage = mutableListOf<Chat>()
        for (user in listChat) {
            listMessage.add(Chat(user.name, user.avatar, "", null))
        }
        listMessageAdapter.mListMessage = listMessage
        listMessageAdapter.notifyDataSetChanged()
    }

    override fun onRequestFailure(string: String) {
        Toast.makeText(activity, "Fail", Toast.LENGTH_SHORT).show()
    }

    override fun showToast(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var mChats: MutableList<Chat> = mutableListOf()
    @Inject
    lateinit var mPresenter: ListMsgPresenter
    override val LayoutId: Int
        get() = R.layout.fragment_listmsg

    override fun injectDependence() {
        App().get(context)
                .plus(ViewModule(this))
                .injectTo(this)
    }

    override fun initData(v: View) {


        var rcListMessage = v.findViewById<RecyclerView>(R.id.rc_list_msg)
        listMessageAdapter = ListMessageAdapter(mChats,this)

        rcListMessage.apply {
            adapter = listMessageAdapter
            layoutManager = LinearLayoutManager(context)
        }
        mPresenter.loadListChat()
    }

    override fun onDestroyComposi() {

    }
    override fun chat(chat: Chat) {
        var intent =Intent(activity,ChatActivity::class.java)
        startActivity(intent)
    }


}
