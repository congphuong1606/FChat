package vn.phuongcong.fchat.ui.main.fragment.listfriend

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.model.User
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.event.OnFriendClick
import vn.phuongcong.fchat.ui.adapter.FriendsAdapter
import vn.phuongcong.fchat.ui.base.BaseFragment
import javax.inject.Inject
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.text.InputType
import com.yarolegovich.lovelydialog.LovelyTextInputDialog
import kotlinx.android.synthetic.main.fragment_friend.*
import vn.phuongcong.fchat.ui.main.fragment.chat.ChatActivity
import java.util.regex.Pattern


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
        var fab=v.findViewById<FloatingActionButton>(R.id.fabAddFriend)
        fab.setOnClickListener{showAddFrienđiaglog()}
    }

    private fun showAddFrienđiaglog() {
        LovelyTextInputDialog(context, R.style.EditTextTintTheme)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle("Thêm bạn")
                .setMessage("nhập email")
                .setIcon(R.drawable.ic_add)
                .setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                .setInputFilter("email không đúng", object : LovelyTextInputDialog.TextFilter {
                    override fun check(text: String): Boolean {
                        val VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
                        val matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(text)
                        return matcher.find()
                    }
                })
                .setConfirmButton(android.R.string.ok, object : LovelyTextInputDialog.OnTextInputConfirmListener{
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
    }

    override fun onDestroyComposi() {

    }

    override fun onItemClick(friend: User) {
        var intent: Intent = Intent(activity, ChatActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("friend", friend)
        intent.putExtra("b",bundle)
        activity.startActivity(intent)

    }

    override fun onRequestFailure(string: String) {

    }

    override fun showToast(msg: String) {
    }

    override fun onAddFriendSuccessful() {

    }

}

