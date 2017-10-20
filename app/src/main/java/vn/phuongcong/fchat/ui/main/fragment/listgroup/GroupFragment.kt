package vn.phuongcong.fchat.ui.main.fragment.listgroup

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_group.view.*
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.data.Group
import vn.phuongcong.fchat.ui.base.BaseFragment
import java.util.*
import kotlin.collections.ArrayList


class GroupFragment : BaseFragment {
    var arrGroup: ArrayList<Group> = ArrayList();
    lateinit var groupAdapter: GroupAdapter;

    override val LayoutId: Int
        get() = R.layout.fragment_group

    constructor() : super() {
        groupAdapter = GroupAdapter(arrGroup);
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v = inflater!!.inflate(LayoutId, container, false);
        var recylerView = v.rcvListGroup;
        recylerView.adapter = groupAdapter;
        recylerView.layoutManager = GridLayoutManager(container!!.context, 2);
        var group = Group(ArrayList(), "Group NAme", "CurrentTime");
        groupAdapter.addItem(group);
        return v;
    }


    override fun injectDependence() {

    }

    override fun initData() {

    }

    override fun onDestroyComposi() {
    }


}
