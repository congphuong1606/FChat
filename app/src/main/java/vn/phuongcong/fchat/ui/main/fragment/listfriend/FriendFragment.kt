package vn.phuongcong.fchat.ui.main.fragment.listfriend

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.common.primitives.Ints
import kotlinx.android.synthetic.main.fragment_friend.*

import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.data.User
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.event.OnFriendClick
import vn.phuongcong.fchat.ui.adapter.FriendsAdapter
import vn.phuongcong.fchat.ui.base.BaseFragment
import javax.inject.Inject


class FriendFragment : BaseFragment(), FriendView, OnFriendClick {


    lateinit var friends: ArrayList<User>
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
    }

    private fun setAdapter(v: View) {
        var rcvListFriend = v.findViewById<RecyclerView>(R.id.rcvListFriend)
        rcvListFriend.layoutManager = LinearLayoutManager(context)
        rcvListFriend.setHasFixedSize(true)
        friends = ArrayList<User>()
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

    override fun onItemClick(user: User) {

    }

    override fun onRequestFailure(string: String) {

    }

    override fun showToast(msg: String) {

    }
}

