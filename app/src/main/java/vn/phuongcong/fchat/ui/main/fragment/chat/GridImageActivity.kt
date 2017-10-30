package vn.phuongcong.fchat.ui.main.fragment.chat

import kotlinx.android.synthetic.main.activity_grid_image.*
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.model.Message
import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import javax.inject.Inject

class GridImageActivity : BaseActivity(), ChatView {

    @Inject
    lateinit var mChatPresenter: ChatPresenter
    var mListImage: MutableList<String> = mutableListOf()
    lateinit var mImageAdapter: GridImageAdapter
    override val contentLayoutID: Int
        get() = R.layout.activity_grid_image


    override fun injectDependence() {
        App().get(this).plus(ViewModule(this)).injectTo(this)
    }

    override fun initData() {


        mImageAdapter = GridImageAdapter(mListImage)
        gr_image.apply {
            adapter = mImageAdapter

        }
        mChatPresenter.getListImage(this)
    }

    override fun onRequestFailure(string: String) {

    }

    override fun showToast(msg: String) {

    }

    override fun getListMessageSuccess(messages: MutableList<Message>) {

    }

    override fun getListImageSuccess(listImage: MutableList<String>) {
        mImageAdapter.mListImage = listImage
    }

    override fun onClick() {

    }


}
