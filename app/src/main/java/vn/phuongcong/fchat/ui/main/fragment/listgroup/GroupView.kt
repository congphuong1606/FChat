package vn.phuongcong.fchat.ui.main.fragment.listgroup

import vn.phuongcong.fchat.model.Group
import vn.phuongcong.fchat.ui.base.BaseView

/**
 * Created by Ominext on 10/18/2017.
 */
interface GroupView : BaseView {
    fun showGroup(group: Group)

    fun removeGroup(groupKey: String)
}