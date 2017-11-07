package vn.phuongcong.fchat.ui.chat

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.item_list_image.*
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.event.IitemClick
import vn.phuongcong.fchat.data.model.Chat
import vn.phuongcong.fchat.data.model.Message
import vn.phuongcong.fchat.ui.chat.imagesrc.GridImageActivity
import vn.phuongcong.fchat.ui.chat.showimage.ShowImageActivity
import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import javax.inject.Inject


class ChatActivity : BaseActivity(), ChatView, View.OnClickListener, IitemClick, ChatApdapter.Isend {


    @Inject
    lateinit var mChatPresenter: ChatPresenter
    private var mMessages: MutableList<Message> = mutableListOf()
    private var mListImage: MutableList<String> = mutableListOf()
    private var mListPathCurrent: MutableList<String> = mutableListOf()
    private lateinit var mChatAdapter: ChatApdapter
    private lateinit var mImageAdapter: ListImageAdapter
    private lateinit var mLinkImageAdapter: LinkImageAdapter
    private var count = 0
    private lateinit var mChatItem: Chat
    override val contentLayoutID: Int
        get() = R.layout.activity_chat

    override fun onClick() {

    }

    override fun injectDependence() {
        App().get(this).plus(ViewModule(this)).injectTo(this)
    }

    override fun initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {

                } else {

                }
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), Contans.EXTERNAL_PERMISSION_REQUEST)
            }
        }
        mImageAdapter = ListImageAdapter(mListImage, this, this, false)
        rc_chat.setHasFixedSize(true)
        mChatAdapter = ChatApdapter(mMessage = mMessages, mContext = this, isend = this)
        rc_chat.apply {
            adapter = mChatAdapter
            layoutManager = LinearLayoutManager(context)
        }
        if (intent.getSerializableExtra(Contans.CHAT_ITEM) != null) {
            mChatItem = intent.getSerializableExtra(Contans.CHAT_ITEM) as Chat
        }
        mChatPresenter.getListChat(mChatItem)
        addEvent()
    }

    private fun addEvent() {
        btn_send_image.setOnClickListener(this)
        btn_send_message.setOnClickListener(this)
        edt_input_message.setOnClickListener(this)
        img_src_image.setOnClickListener(this)
        img_camera.setOnClickListener(this)
        btn_send.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)
    }

    override fun onRequestFailure(string: String) {

    }

    override fun showToast(msg: String) {

    }


    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_send_message -> sendMessagetext(mChatItem)
            R.id.btn_send_image -> chooseImage()
            R.id.edt_input_message -> focusMessage()
            R.id.img_src_image -> getListImage()
            R.id.img_camera -> getImageFromCamera()
            R.id.btn_send -> sendImageFromStorage(mListPathCurrent)
            R.id.btn_cancel -> cancelChooseImage()
        }
    }

    private fun cancelChooseImage() {
        mImageAdapter.checkVisibleImageCheck = true
        mImageAdapter.notifyDataSetChanged()
        txt_count_send.text = ""
        count = 0
        mListPathCurrent.clear()
        rl_send.visibility = View.GONE

    }

    private fun sendImageFromStorage(mListPathCurrent: MutableList<String>) {
        mChatPresenter.sendImageFromStorage(mListPathCurrent)
        mImageAdapter.checkVisibleImageCheck = true
        mImageAdapter.notifyDataSetChanged()
        txt_count_send.text = ""
        count = 0
    }

    private fun getImageFromCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.CAMERA)) {

                } else {

                }
                requestPermissions(arrayOf(Manifest.permission.CAMERA), Contans.CAMERA_PERMISSION_REQUEST)

            }
        }
        val permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (permissionCamera == PackageManager.PERMISSION_GRANTED) {
            /*val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, Contans.CAMERA_PIC_REQUEST)*/
            val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, Contans.CAMERA_PIC_REQUEST)
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode === Contans.CAMERA_PIC_REQUEST) {
            if (data!!.getExtras() != null) {
                val image = data!!.getExtras().get("data") as Bitmap
                mChatPresenter.sendImageCamera(image)
            }

        }
    }

    private fun getListImage() {
        var intent = Intent(this, GridImageActivity::class.java)
        intent.putExtra(Contans.CHAT_ITEM, mChatItem)
        startActivity(intent)
    }

    private fun focusMessage() {
        list_image.visibility = View.GONE
        count = 0
        mListPathCurrent.clear()
    }

    private fun chooseImage() {
        mChatPresenter.getListImage(this)
        list_image.visibility = View.VISIBLE
        rc_list_image.apply {
            adapter = mImageAdapter
            layoutManager = LinearLayoutManager(this@ChatActivity, LinearLayoutManager.HORIZONTAL, false)
        }
        mListPathCurrent.clear()
    }

    private fun sendMessagetext(mChatItem: Chat) {
        var messagetext = edt_input_message.text.toString().trim()
        if (messagetext.isNullOrEmpty()) {
            return
        }
        mChatPresenter.sendMessagetext(messagetext, mChatItem)
        edt_input_message.text = null
    }


    override fun getListMessageSuccess(messages: MutableList<Message>) {
        mMessages=messages
        mChatAdapter.mMessage = messages
        mChatAdapter.notifyDataSetChanged()
        rc_chat.smoothScrollToPosition(messages.size)
    }

    override fun getListImageSuccess(list: MutableList<String>) {
        mImageAdapter.mListImage = list
        mImageAdapter.notifyDataSetChanged()
    }

    override fun iClick(strPath: Any, img_check: ImageView?) {
        rl_send.visibility = View.VISIBLE
        if (mListPathCurrent.size > 0 && mListPathCurrent.contains(strPath)) {
            mListPathCurrent.remove(strPath)
            count--

            Log.d("count-----", count.toString())
            if (count > 0)
                txt_count_send.text = "(" + count.toString() + ")"
            else
                txt_count_send.text = ""
            img_check?.visibility = View.INVISIBLE
            if (count > 4) {
                Toast.makeText(this, "Bạn chỉ được chọn tối đa 4 ảnh", Toast.LENGTH_SHORT).show()
                return
            }
        } else {
            mListPathCurrent.add(strPath as String)
            count++
            Log.d("count-----", count.toString())
            txt_count_send.text = "(" + count.toString() + ")"
            img_check?.visibility = View.VISIBLE
            if (count > 4) {
                Toast.makeText(this, "Bạn chỉ được chọn tối đa 4 ảnh", Toast.LENGTH_SHORT).show()
                img_check?.visibility = View.GONE
                if (mListPathCurrent.get(4) != null) {
                    mListPathCurrent.removeAt(4)
                }
                count--
                txt_count_send.text = "(" + count.toString() + ")"
                return
            }
        }
    }

    override fun sendImageSuccess(linkImage: MutableList<String>) {
        txt_count_send.text = null
        mChatPresenter.sendMessageImage(linkImage, mChatItem)
        mListPathCurrent.clear()
    }

    override fun sendDataImage(linkMessage: MutableList<String>, rcList: GridView) {
        mLinkImageAdapter = LinkImageAdapter(linkMessage, this)
        rcList.apply {
            adapter = mLinkImageAdapter
        }
    }

    override fun iClick(o: Any) {
        var intent = Intent(this, ShowImageActivity::class.java)
        intent.putExtra(Contans.CHAT_ITEM, mChatItem)
        intent.putExtra(Contans.SUM_MESSAGE,mMessages.size)
        intent.putExtra(Contans.LINK_IMAGE_CURRENT,o.toString())
        startActivity(intent)
    }

    override fun sendImageCamereSuccess(downloadUrl: String?) {
        mChatPresenter.sendMessageImageCamera(downloadUrl, mChatItem)
    }
}