package vn.phuongcong.fchat.ui.main.fragment.listgroup.friend_adding

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.pawegio.kandroid.v
import kotlinx.android.synthetic.main.dialog_add_member.view.*
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.di.module.ViewModule
import javax.inject.Inject

/**
 * Created by vietcoscc on 14/11/2017.
 */
class FriendAddingDialog() : DialogFragment(), FriendAddingView {
    override fun showMember(arrCurrentMember: MutableList<String>) {
        Log.e("showMember", "" + arrCurrentMember.size)
    }


    override fun showFriend(friendId: String) {
        adapter.addItem(friendId)
        arrCkecked.add(false)
    }

    private lateinit var dialog: AlertDialog
    lateinit var mContext: Context
    lateinit var groupKey: String
    private lateinit var adminKey: String
    lateinit var adapter: FriendMemberAdapter
    private var arrMember: MutableList<String> = arrayListOf()
    private var arrCkecked: MutableList<Boolean> = arrayListOf()
    @Inject
    lateinit var mPresenter: FriendAddingPresenter

    @SuppressLint("ValidFragment")
    constructor(context: Context, adminKey: String, groupKey: String) : this() {
        this.mContext = context
        this.groupKey = groupKey
        this.adminKey = adminKey
        adapter = FriendMemberAdapter(arrMember, arrCkecked, adminKey, groupKey)
        injectDependency()
        mPresenter.receiveFriendData(FirebaseAuth.getInstance().currentUser!!.uid)
//        mPresenter.receiveCurrentMemberData(adminKey, groupKey)
    }

    private fun injectDependency() {
        App().get(mContext)
                .plus(ViewModule(this))
                .injectTo(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = AlertDialog.Builder(mContext)
        builder.setPositiveButton("Ok") { _, _ ->
            Toast.makeText(mContext, "OK", Toast.LENGTH_SHORT).show()
            for ((index, value) in arrMember.withIndex()) {
                if (arrCkecked[index]) {
                    mPresenter.addMember(value, adminKey, groupKey)
                } else {
                    mPresenter.removeMember(value, adminKey, groupKey)
                }
            }
        }
        builder.setNegativeButton("CANCEL", null)
        dialog = builder.create()
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = LayoutInflater.from(mContext).inflate(R.layout.dialog_add_member, container, false)
        initViews(view)
        dialog.setView(view)
        return view
    }

    private fun initViews(view: View?) {
        view!!.recyclerViewFriend.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)

        view!!.recyclerViewFriend.adapter = adapter
    }
}