package vn.phuongcong.fchat.ui.main.fragment.listfriend

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R

import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.model.User
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.event.OnFriendClick
import vn.phuongcong.fchat.ui.adapter.FriendsAdapter
import vn.phuongcong.fchat.ui.base.BaseFragment
import javax.inject.Inject
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.widget.Toast
import com.yarolegovich.lovelydialog.LovelyTextInputDialog
import kotlinx.android.synthetic.main.fragment_friend.*
import kotlinx.android.synthetic.main.fragment_friend.view.*
import vn.phuongcong.fchat.event.OnFabClick
import vn.phuongcong.fchat.model.Chat


import vn.phuongcong.fchat.ui.chat.ChatActivity
import vn.phuongcong.fchat.ui.main.MainActivity


class FriendFragment : BaseFragment(), FriendView, OnFriendClick, OnFabClick {
    override fun onFindError(error: String) {
        Toast.makeText(activity, "Email chưa được đăng ký", Toast.LENGTH_SHORT).show()
    }

    lateinit var friends: MutableList<User>
    lateinit var mAdapter: FriendsAdapter
    @Inject
    lateinit var mPresenter: FriendPresenter

    override val LayoutId: Int
        get() = R.layout.fragment_friend

    override fun injectDependence() {
        App().get(context)
                .plus(ViewModule(this))
                .injectTo(this)
    }


    override fun initData(v: View) {
        setAdapter(v)
        mPresenter.onLoadFriendIds()
        v.swipeRefreshLayout.setOnRefreshListener { onRefresh() }
        MainActivity.setOnFabClickListenner(this)
    }

    private fun onRefresh() {
        friends.clear()
        mPresenter.onLoadFriendIds()
    }

    private fun showAddFrienđiaglog() {
        LovelyTextInputDialog(context, R.style.EditTextTintTheme)
                .setTitle(Contans.TITLE_ADD_FRIEND)
                .setMessage(Contans.REQUEST_INPUT_EMAIL)
                .setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                .setInputFilter(Contans.EMAIL_FAIL) { text ->
                    val matcher = android.util.Patterns.EMAIL_ADDRESS.matcher(text)
                    matcher.find()
                }
                .setConfirmButton(android.R.string.ok) { text -> mPresenter.findIDEmail(text) }
                .show()
    }


    private fun setAdapter(v: View) {
        val rcvListFriend = v.findViewById<RecyclerView>(R.id.rcvListFriend)
        rcvListFriend.layoutManager = LinearLayoutManager(context)
        rcvListFriend.setHasFixedSize(true)
        friends = mutableListOf()
        mAdapter = FriendsAdapter(friends, this)
        rcvListFriend.adapter = mAdapter
    }


    override fun onLoadFriendsSuccess(friend: User) {
        var isExist: Boolean = false
        for (i: Int in 0 until friends.size) {
            if (friends[i].id == friend.id) {
                friends[i] = friend
                isExist = true
            }
        }
        if (!isExist)
            friends.add(friend)
        mAdapter.notifyDataSetChanged()
        rcvListFriend.smoothScrollToPosition(0)
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onDestroyComposi() {

    }

    override fun onItemClick(friend: User) {
        var chat = Chat(friend.id, friend.name, friend.avatar, null, null)
        var intent = Intent(activity, ChatActivity::class.java)
        intent.putExtra(Contans.CHAT_ITEM, chat)
        startActivity(intent)

    }

    override fun onFabClickListener() {
        showAddFrienđiaglog()
    }

    override fun onRequestFailure(string: String) {

    }

    override fun showToast(msg: String) {
    }

    override fun onAddFriendSuccessful() {

    }

    override fun onDeleteFriendSuccess() {
        Toast.makeText(context, Contans.NOTI_DELETE_FRIEND_SUCCESS, Toast.LENGTH_LONG).show()
        friends.clear()
        mPresenter.onLoadFriendIds()
    }

    override fun onLongItemClick(friend: User, position: Int) {
        val friendName = friend.name
        AlertDialog.Builder(context)
                .setTitle(Contans.TITLE_DELE_FRIEND)
                .setMessage(Contans.REQUEST_DELE_FRIEND + " $friendName ?")
                .setPositiveButton(android.R.string.ok) { dialogInterface, i ->
                    dialogInterface.dismiss()
                    mPresenter.deleteFriend(friend.id)


                }
                .setNegativeButton(android.R.string.cancel) { dialogInterface, i -> dialogInterface.dismiss() }.show()

    }

}

