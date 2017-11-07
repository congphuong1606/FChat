package vn.phuongcong.fchat.ui.main.fragment.listfriend

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R

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
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.data.model.Chat
import vn.phuongcong.fchat.data.model.User


import vn.phuongcong.fchat.ui.chat.ChatActivity


class FriendFragment : BaseFragment(), FriendView, OnFriendClick {


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

        v.fabAddFriend.setOnClickListener { showAddFrienđiaglog() }
        v.swipeRefreshLayout.setOnRefreshListener { onRefresh() }
    }

    private fun onRefresh() {
        friends.clear()
        mPresenter.onLoadFriendIds()
    }

    private fun showAddFrienđiaglog() {
        LovelyTextInputDialog(context, R.style.EditTextTintTheme)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle(Contans.TITLE_ADD_FRIEND)
                .setMessage(Contans.REQUEST_INPUT_EMAIL)
                .setIcon(R.drawable.ic_add)
                .setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                .setInputFilter(Contans.EMAIL_FAIL, object : LovelyTextInputDialog.TextFilter {
                    override fun check(text: String): Boolean {
                        val matcher = android.util.Patterns.EMAIL_ADDRESS.matcher(text)
                        return matcher.find()
                    }
                })
                .setConfirmButton(android.R.string.ok, object : LovelyTextInputDialog.OnTextInputConfirmListener {
                    override fun onTextInputConfirmed(text: String) {
                        mPresenter.findIDEmail(text)

                    }
                })
                .show()
    }


    private fun setAdapter(v: View) {
        var rcvListFriend = v.findViewById<RecyclerView>(R.id.rcvListFriend)
        rcvListFriend.layoutManager = LinearLayoutManager(context)
        rcvListFriend.setHasFixedSize(true)
        friends = mutableListOf()
        mAdapter = FriendsAdapter(friends, this)
        rcvListFriend.adapter = mAdapter
    }


    override fun onLoadFriendsSuccess(friend: User) {
        var isExist: Boolean = false
        for (i: Int in 0..friends.size - 1) {
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
        var chat= Chat(friend.id,friend.name,friend.avatar,null,null)
        var intent = Intent(activity, ChatActivity::class.java)
        intent.putExtra(Contans.CHAT_ITEM, chat)
        startActivity(intent)

    }

    override fun onRequestFailure(string: String) {

    }

    override fun showToast(msg: String) {
    }

    override fun onAddFriendSuccessful() {

    }

    override fun onDeleteFriendSuccess() {
        Toast.makeText(context, Contans.NOTI_DELETE_FRIEND_SUCCESS, Toast.LENGTH_LONG).show()
    }

    override fun onLongItemClick(friend: User, position: Int) {
        val friendName = friend.name
        AlertDialog.Builder(context)
                .setTitle(Contans.TITLE_DELE_FRIEND)
                .setMessage(Contans.REQUEST_DELE_FRIEND + " $friendName ?")
                .setPositiveButton(android.R.string.ok) { dialogInterface, i ->
                    dialogInterface.dismiss()
                    mPresenter.deleteFriend(friend.id)
                    friends.remove(friend)
                    mAdapter.notifyDataSetChanged()

                }
                .setNegativeButton(android.R.string.cancel) { dialogInterface, i -> dialogInterface.dismiss() }.show()

    }

}

