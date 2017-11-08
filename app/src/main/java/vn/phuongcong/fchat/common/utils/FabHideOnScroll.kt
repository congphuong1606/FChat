package vn.phuongcong.fchat.common.utils

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.View
import android.support.v4.view.ViewCompat



/**
 * Created by Congphuong on 10/23/2017.
 */
class FabHideOnScroll(var context: Context,attrs: AttributeSet): FloatingActionButton.Behavior() {
    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout,
                                child: FloatingActionButton,
                                target: View, dxConsumed:
                                Int, dyConsumed: Int,
                                dxUnconsumed: Int,
                                dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
        if (child.visibility == View.VISIBLE && dyConsumed > 0) {
            child.hide();
        } else if (child.visibility == View.GONE && dyConsumed < 0) {
            child.show();
        }
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout,
                                     child: FloatingActionButton,
                                     directTargetChild: View, target: View,
                                     nestedScrollAxes: Int): Boolean {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
    }
}