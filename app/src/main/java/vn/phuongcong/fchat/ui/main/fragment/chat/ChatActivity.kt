package vn.phuongcong.fchat.ui.main.fragment.chat

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.item_list_image.*
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.model.Chat
import vn.phuongcong.fchat.model.Message
import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import javax.inject.Inject
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.ImageView
import vn.phuongcong.fchat.event.IitemClick


class ChatActivity : BaseActivity(), ChatView, View.OnClickListener, IitemClick {



    var mMessages: MutableList<Message> = mutableListOf()
    var mListImage: MutableList<String> = mutableListOf()
    lateinit var mChatAdapter: ChatApdapter
    lateinit var mImageAdapter: ImageAdapter
    var count = 0
    var mListPathCurrent: MutableList<String> = mutableListOf()


    @Inject
    lateinit var mChatPresenter: ChatPresenter
    lateinit var mChatItem: Chat
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

        mImageAdapter = ImageAdapter(mListImage, this, this)
        rc_chat.setHasFixedSize(true)
        mChatAdapter = ChatApdapter(mMessage = mMessages)
        rc_chat.apply {
            adapter = mChatAdapter
            layoutManager = LinearLayoutManager(context)
        }

        mChatItem = intent.getSerializableExtra(Contans.CHAT_ITEM) as Chat
        mChatPresenter.getListChat(mChatItem)

        addEvent()

    }

    private fun addEvent() {
        btn_send_image.setOnClickListener(this)
        btn_send_message.setOnClickListener(this)
        edt_input_message.setOnClickListener(this)
        img_src_image.setOnClickListener(this)
        img_camera.setOnClickListener(this)
    }

    override fun onRequestFailure(string: String) {

    }

    override fun showToast(msg: String) {

    }


    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_send_message -> sendMessagetext(mChatItem)
            R.id.btn_send_image -> sendImage()
            R.id.edt_input_message -> focusMessage()
            R.id.img_src_image -> getListImage()
            R.id.img_camera -> getImageFromCamera()
        }
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
            val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, Contans.CAMERA_PIC_REQUEST)
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, Contans.CAMERA_PIC_REQUEST)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode === Contans.CAMERA_PIC_REQUEST) {
            if (data!!.getExtras() != null) {
                val image = data!!.getExtras().get("data") as Bitmap
            }

            //imageview.setImageBitmap(image)
        }
    }

    private fun getListImage() {
        startActivity(Intent(this, GridImageActivity::class.java))
    }

    private fun focusMessage() {
        list_image.visibility = View.GONE
    }

    private fun sendImage() {
        mChatPresenter.getListImage(this)
        list_image.visibility = View.VISIBLE
        rc_list_image.apply {
            adapter = mImageAdapter
            layoutManager = LinearLayoutManager(this@ChatActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun sendMessagetext(mChatItem: Chat) {
        var messagetext = edt_input_message.text.toString().trim()
        mChatPresenter.sendMessagetext(messagetext, mChatItem)
        edt_input_message.text = null
    }


    override fun getListMessageSuccess(messages: MutableList<Message>) {
        mChatAdapter.mMessage = messages
        mChatAdapter.notifyDataSetChanged()
        rc_chat.smoothScrollToPosition(messages.size)
    }

    override fun getListImageSuccess(list: MutableList<String>) {
        mImageAdapter.mListImage = list
        mImageAdapter.notifyDataSetChanged()
    }

    override fun iClick(strPath: Any, txt_count: ImageView?) {

        rl_send.visibility = View.VISIBLE
        if (mListPathCurrent.size > 0 && mListPathCurrent.contains(strPath)) {
            mListPathCurrent.remove(strPath)
            count--

            Log.d("count-----", count.toString())
            if (count > 0)
                txt_count_send.text = "(" + count.toString() + ")"
            else
                txt_count_send.text = ""
            txt_count?.visibility = View.INVISIBLE

        } else {
            mListPathCurrent.add(strPath as String)
            count++
            Log.d("count-----", count.toString())
            txt_count_send.text = "(" + count.toString() + ")"
            txt_count?.visibility = View.VISIBLE
        }



    }
}
