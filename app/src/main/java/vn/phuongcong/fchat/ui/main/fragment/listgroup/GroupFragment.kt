package vn.phuongcong.fchat.ui.main.fragment.listgroup

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.github.clans.fab.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_group.view.*
import kotlinx.android.synthetic.main.item_list_image.*
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.event.IitemClick
import vn.phuongcong.fchat.model.Group
import vn.phuongcong.fchat.ui.base.BaseFragment
import vn.phuongcong.fchat.ui.main.fragment.listgroup.chat.ChatGroupActivity
import vn.phuongcong.fchat.common.utils.CalendarUtils
import vn.phuongcong.fchat.common.utils.DatabaseRef.Companion.ADMIN_KEY
import vn.phuongcong.fchat.common.utils.DatabaseRef.Companion.GROUP_KEY
import javax.inject.Inject


class GroupFragment : BaseFragment, GroupView {
    override fun removeGroup(groupKey: String) {
        groupAdapter.removeItem(arrGroupKey.indexOf(groupKey))
    }

    override fun showGroup(group: Group) {
        groupAdapter.addItem(group)
    }

    override fun onRequestFailure(string: String) {

    }

    override fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    var arrGroup: ArrayList<Group> = ArrayList()
    var arrGroupKey: ArrayList<String> = ArrayList()

    private var groupAdapter: GroupAdapter
    private lateinit var recylerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    @Inject
    lateinit var mPresenter: GroupPresenter

    constructor() : super() {

        groupAdapter = GroupAdapter(arrGroup, arrGroupKey, object : IitemClick {
            override fun iClick(o: Any) {

            }

            override fun iClick(o: Any, txt_count: ImageView?) {
                var position: Int = o as Int
                var intent = Intent(context, ChatGroupActivity::class.java)
                intent.putExtra(ADMIN_KEY, arrGroup[position].adminKey)
                intent.putExtra(GROUP_KEY, arrGroup[position].groupKey)
                startActivity(intent)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v = inflater!!.inflate(LayoutId, container, false)
        injectDependence()
        initViews(v)
        initData(v)
        return v
    }

    fun initViews(v: View) {
        recylerView = v.rcvListGroup
   //    fab = v.fabCreateGroup
        recylerView.adapter = groupAdapter
        recylerView.layoutManager = GridLayoutManager(v!!.context, 2)
       /* fab.setOnClickListener({
            showNameDialog()
        })*/
    }

    override val LayoutId: Int
        get() = R.layout.fragment_group

    override fun injectDependence() {
        App().get(context)
                .plus(ViewModule(this))
                .injectTo(this)
    }

    override fun initData(v: View) {
        mPresenter.receiveGroupData()
        mPresenter.receiveOtherGroupData()
    }

    override fun onDestroyComposi() {

    }

    private fun showNameDialog() {
        var edtGroupName = EditText(context)

        var builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("Group name")
        builder.setView(edtGroupName)
        builder.setPositiveButton("OK", { dialogInterface: DialogInterface, i: Int ->
            if (!TextUtils.isEmpty(edtGroupName.text)) {
                var group: Group = Group("", FirebaseAuth.getInstance().currentUser!!.uid,
                        edtGroupName.text.toString(),
                        CalendarUtils.currentTime() + " " + CalendarUtils.currentDate())
                mPresenter.createGroup(group)
            }
        })
        builder.setNegativeButton("CANCEL", null)
        var dialog: AlertDialog = builder.create()
        dialog.show()

    }

}
