package vn.phuongcong.fchat.ui.chat.showimage

import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_show_image.*
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.model.Chat
import vn.phuongcong.fchat.model.Message
import vn.phuongcong.fchat.ui.chat.ChatPresenter
import vn.phuongcong.fchat.ui.chat.ChatView
import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import javax.inject.Inject

class ShowImageActivity : BaseActivity(), ChatView {
    override fun getAvatarUserSendSuccess(image: String) {

    }


    override fun sendAudioSuccess(downloadUrl: String) {

    }
@Inject
    lateinit var mChatPresenter: ChatPresenter
    private lateinit var mChatItem: Chat
    private lateinit var mShowImageAdapter: ShowImageAdapter
    private var listPath: MutableList<String> = mutableListOf()
    private var mSizeMessage = 0
    private var mLinkImageCurrent:String=""

    override fun injectDependence() {
        App().get(this).plus(ViewModule(this)).injectTo(this)
    }

    override fun initData() {
        mChatItem = intent.getSerializableExtra(Contans.CHAT_ITEM) as Chat
        mSizeMessage = intent.getIntExtra(Contans.SUM_MESSAGE, 0)
        mLinkImageCurrent=intent.getStringExtra(Contans.LINK_IMAGE_CURRENT)
        mShowImageAdapter = ShowImageAdapter(listPath)
        pager.adapter = mShowImageAdapter
        indicator.setViewPager(pager)

        //listPath=    mChatPresenter.getAllLinkImage(mChatItem)
        mChatPresenter.getListChat(mChatItem,0,5)

    }

    override fun onRequestFailure(string: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showToast(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getListMessageSuccess(messages: MutableList<Message>) {


        if (messages.size == mSizeMessage) {
            messages.sortBy { message: Message -> message.timeCreate.toLong() }
            messages.filter { message: Message -> message.msgImage!!.size > 0 }
            for (message in messages) {
                if (message.msgImage!!.size <= 0)
                    continue
                else {
                    for (i in 0..message.msgImage!!.size - 1)
                        listPath.add(message.msgImage!!.get(i))
                }
            }
            for(link in listPath){
                if(link==mLinkImageCurrent)
                    listPath.remove(link)
                break
            }
            listPath.add(0,mLinkImageCurrent)
            mShowImageAdapter.mLinkImage = listPath
            mShowImageAdapter.notifyDataSetChanged()
        }
    }

    override fun getListImageSuccess(absolutePathOfImage: MutableList<String>) {
    }

    override fun sendImageSuccess(linkImage: MutableList<String>) {

    }

    override fun sendImageCamereSuccess(downloadUrl: String?) {

    }

    override val contentLayoutID: Int
        get() = R.layout.activity_show_image

    override fun onClick() {

    }
    override fun isPlaying(imgPlayPause: ImageView) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isStop(imgPlayPause: ImageView) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
