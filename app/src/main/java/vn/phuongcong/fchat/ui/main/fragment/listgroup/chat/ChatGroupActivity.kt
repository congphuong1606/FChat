package vn.phuongcong.fchat.ui.main.fragment.listgroup.chat

import android.os.Bundle
import vn.phuongcong.fchat.R
import vn.phuongcong.fchattranslate.ui.base.BaseActivity

class ChatGroupActivity : BaseActivity(), ChatGroupView {
    override val contentLayoutID: Int
        get() = R.layout.activity_chat_group

    override fun onClick() {

    }

    override fun injectDependence() {

    }

    override fun initData() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_group)
    }
}
