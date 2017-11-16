package vn.phuongcong.fchat.ui.chat

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder
import cafe.adriel.androidaudiorecorder.model.AudioChannel
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate
import cafe.adriel.androidaudiorecorder.model.AudioSource

import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.fragment_friend.*
import kotlinx.android.synthetic.main.item_list_image.*
import vc908.stickerfactory.StickersKeyboardController
import vc908.stickerfactory.StickersManager
import vc908.stickerfactory.ui.OnStickerSelectedListener
import vc908.stickerfactory.ui.fragment.StickersFragment
import vc908.stickerfactory.ui.view.StickersKeyboardLayout
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.common.utils.PermissionUtil
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.event.IitemClick
import vn.phuongcong.fchat.model.Chat
import vn.phuongcong.fchat.model.Message
import vn.phuongcong.fchat.ui.chat.imagesrc.GridImageActivity
import vn.phuongcong.fchat.ui.chat.showimage.ShowImageActivity
import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import javax.inject.Inject


class ChatActivity : BaseActivity(), ChatView, View.OnClickListener, IitemClick, ChatAdapter.Isend, SwipeRefreshLayout.OnRefreshListener {


    @Inject
    lateinit var mChatPresenter: ChatPresenter
    private var stickersKeyboardController: StickersKeyboardController? = null
    private var mMessages: MutableList<Message> = mutableListOf()
    private var mListImage: MutableList<String> = mutableListOf()
    private var mListPathCurrent: MutableList<String> = mutableListOf()
    private lateinit var mChatAdapter: ChatAdapter
    private lateinit var mImageAdapter: ListImageAdapter
    private lateinit var mLinkImageAdapter: LinkImageAdapter
    private var count = 0
    private lateinit var mChatItem: Chat
    private  var mImgUserSend: String=""

    companion object {
        private val REQUEST_RECORD_AUDIO = 0
        private val AUDIO_FILE_PATH = Environment.getExternalStorageDirectory().path + "/recorded_audio.wav"

    }

    override val contentLayoutID: Int
        get() = R.layout.activity_chat

    override fun onClick() {

    }

    override fun injectDependence() {
        App().get(this).plus(ViewModule(this)).injectTo(this)
    }

    override fun initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.CAMERA)) {

                } else {

                }
                requestPermissions(arrayOf(Manifest.permission.CAMERA), Contans.CAMERA_PERMISSION_REQUEST)

            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {

                } else {

                }
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), Contans.EXTERNAL_PERMISSION_REQUEST)
            }
        }


        if (intent.getSerializableExtra(Contans.CHAT_ITEM) != null) {
            mChatItem = intent.getSerializableExtra(Contans.CHAT_ITEM) as Chat
        }
        mChatPresenter.getAvatarUserSend()
        mImageAdapter = ListImageAdapter(mListImage, this, this, false)
        mChatAdapter = ChatAdapter(mMessage = mMessages, mContext = this, isend = this, chatItem = mChatItem, mImgUserSend = mImgUserSend)

        rc_chat.apply {
            setHasFixedSize(true)
            adapter = mChatAdapter
            layoutManager = LinearLayoutManager(context)
        }


        mChatPresenter.getListChat(mChatItem, 0, 5)

        addEvent()
        sticker()

    }

    override fun onRefresh() {
        //  mMessages.clear()
        //  mChatPresenter.getListChat(mChatItem, 6,5)
        sr_load_more.isRefreshing = false
    }

    private fun sticker() {
        var stickersFragment: StickersFragment? = null

        //supportFragmentManager.findFragmentById(R.id.sticker_frame) as StickersFragment
        if (stickersFragment == null) {
            stickersFragment = StickersFragment()
            supportFragmentManager.beginTransaction().replace(R.id.sticker_frame, stickersFragment).commit()
        }
        stickersFragment.setOnStickerSelectedListener(object : OnStickerSelectedListener {
            override fun onStickerSelected(code: String) {
                addStickerMessage(code, false, System.currentTimeMillis())
            }

            override fun onEmojiSelected(emoji: String) {
                edt_input_message.append(emoji)
            }
        })
        val stickersLayout = findViewById<StickersKeyboardLayout>(R.id.sizeNotifierLayout)
        stickersKeyboardController = StickersKeyboardController.Builder(this)
                .setStickersKeyboardLayout(stickersLayout)
                .setStickersFragment(stickersFragment)
                .setStickersFrame(sticker_frame)
                .setContentContainer(chat_content)
                .setStickersButton(btn_stickers)
                .setChatEdit(edt_input_message)
                // .setSuggestContainer(rc_chat)
                .build()

        stickersKeyboardController!!.setKeyboardVisibilityChangeListener(object :
                StickersKeyboardController.KeyboardVisibilityChangeListener {
            override fun onTextKeyboardVisibilityChanged(isVisible: Boolean) {
                if (mMessages.isNotEmpty()) {
                    rc_chat.smoothScrollToPosition(mMessages.size)
                }
            }

            override fun onStickersKeyboardVisibilityChanged(isVisible: Boolean) {

            }
        })
    }


    private fun addStickerMessage(code: String, b: Boolean, currentTimeMillis: Long) {
        if (code.isNullOrEmpty()) {
            return
        }
        if (StickersManager.isSticker(code)) {
            mChatPresenter.sendsticker(code, mChatItem)
            StickersManager.onUserMessageSent(true)
        } else {

        }
    }

    private fun addEvent() {
        sr_load_more.setOnRefreshListener(this)
        btn_send_image.setOnClickListener(this)
        btn_send_message.setOnClickListener(this)
        edt_input_message.setOnClickListener(this)
        img_src_image.setOnClickListener(this)
        img_camera.setOnClickListener(this)
        btn_send.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)
        btn_send_audio.setOnClickListener(this)
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
            R.id.btn_send_audio -> chooseAudio()
        }
    }

    private fun chooseAudio() {
        PermissionUtil.requestPermission(this, Manifest.permission.RECORD_AUDIO)
        PermissionUtil.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                AndroidAudioRecorder.with(this)
                        // Required
                        .setFilePath(AUDIO_FILE_PATH)
                        .setColor(ContextCompat.getColor(this, R.color.red))
                        .setRequestCode(REQUEST_RECORD_AUDIO)

                        // Optional
                        .setSource(AudioSource.MIC)
                        .setChannel(AudioChannel.STEREO)
                        .setSampleRate(AudioSampleRate.HZ_48000)
                        .setAutoStart(false)
                        .setKeepDisplayOn(true)

                        // Start recording
                        .record()
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

        val permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        // if (permissionCamera == PackageManager.PERMISSION_GRANTED) {
        /*val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, Contans.CAMERA_PIC_REQUEST)*/
        val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, Contans.CAMERA_PIC_REQUEST)
        // }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode === Contans.CAMERA_PIC_REQUEST) {
            if (data!!.getExtras() != null) {
                val image = data!!.getExtras().get("data") as Bitmap
                mChatPresenter.sendImageCamera(image)
            } else {
                return
            }
        }
        if (requestCode == REQUEST_RECORD_AUDIO) {
            if (resultCode == Activity.RESULT_OK) {
                mChatPresenter.senAudio(AUDIO_FILE_PATH, mChatItem)
            } else if (requestCode == Activity.RESULT_CANCELED) {

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
        mMessages = messages
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
        intent.putExtra(Contans.SUM_MESSAGE, mMessages.size)
        intent.putExtra(Contans.LINK_IMAGE_CURRENT, o.toString())
        startActivity(intent)
    }

    override fun sendImageCamereSuccess(downloadUrl: String?) {
        mChatPresenter.sendMessageImageCamera(downloadUrl, mChatItem)
    }

    override fun sendAudioSuccess(downloadUrl: String) {
        mChatPresenter.saveAudioFirebase(downloadUrl, mChatItem)
    }

    override fun sendDataAudio(linkAudio: String, imgPlayPause: ImageView) {
        mChatPresenter.playAudio(linkAudio, imgPlayPause)
    }

    override fun isPlaying(imgPlayPause: ImageView) {
        imgPlayPause.setImageResource(R.drawable.ic_play)
    }

    override fun isStop(imgPlayPause: ImageView) {
        imgPlayPause.setImageResource(R.drawable.ic_stop)
    }

    override fun getAvatarUserSendSuccess(image: String) {
        mChatAdapter.mImgUserSend = image
        mChatAdapter.notifyDataSetChanged()
    }
}
