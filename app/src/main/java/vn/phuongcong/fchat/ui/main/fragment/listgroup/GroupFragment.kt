package vn.phuongcong.fchat.ui.main.fragment.listgroup

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.github.clans.fab.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_group.view.*
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.data.Group
import vn.phuongcong.fchat.ui.base.BaseFragment
import vn.phuongcong.fchat.utils.CalendarUtils
import vn.phuongcong.fchat.utils.DatabaseRef
import java.util.*
import kotlin.collections.ArrayList


class GroupFragment : BaseFragment {
    var arrGroup: ArrayList<Group> = ArrayList()
    lateinit var groupAdapter: GroupAdapter
    lateinit var recylerView: RecyclerView
    lateinit var fab: FloatingActionButton

    override val LayoutId: Int
        get() = R.layout.fragment_group

    constructor() : super() {
        groupAdapter = GroupAdapter(arrGroup)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v = inflater!!.inflate(LayoutId, container, false)
        initViews(v)
        recylerView.adapter = groupAdapter
        recylerView.layoutManager = GridLayoutManager(container!!.context, 2)
        var group = Group("", "", "Group NAme", "CurrentTime")
        groupAdapter.addItem(group)
        fab.setOnClickListener({
            showNameDialog()
        })
        initData()
        return v
    }

    fun initViews(v: View) {
        recylerView = v.rcvListGroup
        fab = v.fabCreateGroup
    }

    fun receiveData() {

    }

    override fun injectDependence() {

    }

    override fun initData() {
        DatabaseRef.groupInfoRef().addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, key: String?) {
                var group: Group = dataSnapshot.getValue(Group::class.java)!!
                groupAdapter.addItem(group);
            }

            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildRemoved(p0: DataSnapshot?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    override fun onDestroyComposi() {
    }

    fun showNameDialog() {
        var edtGroupName: EditText = EditText(context)

        var builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("Group name")
        builder.setView(edtGroupName)
        builder.setPositiveButton("OK", { dialogInterface: DialogInterface, i: Int ->
            if (!TextUtils.isEmpty(edtGroupName.text)) {
                var group: Group = Group("", FirebaseAuth.getInstance().currentUser!!.uid,
                        edtGroupName.text.toString(),
                        CalendarUtils.currentTime() + " " + CalendarUtils.currentDate())
                DatabaseRef.groupInfoRef().push().setValue(group)
            }
        })
        builder.setNegativeButton("CANCEL", null)
        var dialog: AlertDialog = builder.create()
        dialog.show()

    }
}
