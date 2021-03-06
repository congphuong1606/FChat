package vn.phuongcong.fchat.ui.main.fragment.listgroup.chat

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder
import cafe.adriel.androidaudiorecorder.model.AudioChannel
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate
import cafe.adriel.androidaudiorecorder.model.AudioSource
import com.pawegio.kandroid.visible
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.item_list_image.*
import vc908.stickerfactory.StickersKeyboardController
import vc908.stickerfactory.StickersManager
import vc908.stickerfactory.ui.OnStickerSelectedListener
import vc908.stickerfactory.ui.fragment.StickersFragment
import vc908.stickerfactory.ui.view.BadgedStickersButton
import vc908.stickerfactory.ui.view.StickersKeyboardLayout
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.model.Message
import vn.phuongcong.fchat.common.utils.DatabaseRef.Companion.ADMIN_KEY
import vn.phuongcong.fchat.common.utils.DatabaseRef.Companion.GROUP_KEY
import vn.phuongcong.fchat.common.utils.KeyboardUtils
import vn.phuongcong.fchat.common.utils.PermissionUtil
import vn.phuongcong.fchat.event.IitemClick
import vn.phuongcong.fchat.ui.chat.ChatActivity
import vn.phuongcong.fchat.ui.chat.LinkImageAdapter
import vn.phuongcong.fchat.ui.chat.ListImageAdapter
import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import java.text.CollationKey
import javax.inject.Inject

class ChatGroupActivity : BaseActivity(), ChatGroupView, IitemClick {
    override fun clearChatImage() {
        cancelChooseImage()
    }

    override fun onClick() {
    }

    override fun iClick(o: Any) {

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
            Toast.makeText(this, strPath as String, Toast.LENGTH_SHORT).show()
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

    override fun getListImageSuccess(list: MutableList<String>) {
        mImageAdapter.mListImage = list
        mImageAdapter.notifyDataSetChanged()
    }


    private var arrMessage = ArrayList<Message>()
    private var arrMessageKey = ArrayList<String>()
    private lateinit var adminKey: String
    private lateinit var groupKey: String
    private lateinit var chatGroupAdapter: ChatGroupAdapter
    @Inject
    lateinit var mPresenter: ChatGroupPresenter
    private lateinit var mImageAdapter: ListImageAdapter
    private var mListImage: MutableList<String> = mutableListOf()
    private var mListPathCurrent: MutableList<String> = mutableListOf()
    private var count = 0
    private var stickersFragment: StickersFragment? = null
    private var stickersKeyboardController: StickersKeyboardController? = null
    override val contentLayoutID: Int
        get() = R.layout.activity_chat

    override fun injectDependence() {
        App().get(this)
                .plus(ViewModule(this))
                .injectTo(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initData() {
        adminKey = intent.getStringExtra(ADMIN_KEY)
        groupKey = intent.getStringExtra(GROUP_KEY)
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), Contans.EXTERNAL_PERMISSION_REQUEST)
        }
        mImageAdapter = ListImageAdapter(mListImage, this, this, false)
        edt_input_message.isClickable=false
        initViews()
        mPresenter.receiveChatData(adminKey, groupKey)
        sticker()
    }

    private fun initViews() {
        rc_chat.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        chatGroupAdapter = ChatGroupAdapter(arrMessage, arrMessageKey, adminKey, groupKey)
        rc_chat.adapter = chatGroupAdapter
        edt_input_message.requestFocus()
        btn_send_message.setOnClickListener {
            var content = edt_input_message.text.toString()
            Log.e("content : ", content)
            if (!TextUtils.isEmpty(content) || (TextUtils.isEmpty(content) && mListPathCurrent.size > 0)) {
                mPresenter.onChat(edt_input_message.text.toString(), mListPathCurrent, adminKey, groupKey)
                edt_input_message.setText("")
            }

        }
        btn_send_image.setOnClickListener {
            chooseImage()
        }
        btn_cancel.setOnClickListener {
            cancelChooseImage()
        }
        btn_send_audio.setOnClickListener {
            chooseAudio()
        }
        edt_input_message.setOnClickListener {
            focusMessage()
        }
    }

    override fun showChatItem(message: Message, messageKey: String) {
        chatGroupAdapter.addItem(message, messageKey)
        rc_chat.smoothScrollToPosition(arrMessage.size - 1)
    }

    override fun onRequestFailure(string: String) {

    }

    override fun showToast(msg: String) {

    }


    private fun chooseImage() {
        list_image.visibility = View.VISIBLE
        sticker_frame.visibility = View.GONE
        KeyboardUtils.hideKeyboard(this, edt_input_message)
        mPresenter.getListImage(this)
        rc_list_image.apply {
            adapter = mImageAdapter
            layoutManager = LinearLayoutManager(this@ChatGroupActivity, LinearLayoutManager.HORIZONTAL, false)
        }
        mListPathCurrent.clear()
    }
//
//    private fun sendImageFromStorage(mListPathCurrent: MutableList<String>) {
//
//        mImageAdapter.checkVisibleImageCheck = true
//        mImageAdapter.notifyDataSetChanged()
//        txt_count_send.text = ""
//        count = 0
//    }

    private fun cancelChooseImage() {
        mImageAdapter.checkVisibleImageCheck = true
        mImageAdapter.notifyDataSetChanged()
        txt_count_send.text = ""
//        count = 0
        mListPathCurrent.clear()
        rl_send.visibility = View.GONE

    }

    private fun chooseAudio() {
        PermissionUtil.requestPermission(this, Manifest.permission.RECORD_AUDIO)
        PermissionUtil.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                AndroidAudioRecorder.with(this)
                        // Required
                        .setFilePath(ChatActivity.AUDIO_FILE_PATH)
                        .setColor(ContextCompat.getColor(this, R.color.red))
                        .setRequestCode(ChatActivity.REQUEST_RECORD_AUDIO)

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


    private fun sticker() {
//        list_image.visibility=View.GONE


        //supportFragmentManager.findFragmentById(R.id.sticker_frame) as StickersFragment
        if (stickersFragment == null) {
            stickersFragment = StickersFragment()
            supportFragmentManager.beginTransaction().replace(R.id.sticker_frame, stickersFragment).commit()
        }
        stickersFragment!!.setOnStickerSelectedListener(object : OnStickerSelectedListener {
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
                .setStickersFragment(stickersFragment!!)
                .setStickersFrame(sticker_frame)
                .setContentContainer(chat_content)
                .setStickersButton(btn_stickers)
               .setChatEdit(edt_input_message)
               // .setSuggestContainer(rc_chat)
                .build()

        stickersKeyboardController!!.setKeyboardVisibilityChangeListener(object :
                StickersKeyboardController.KeyboardVisibilityChangeListener {
            override fun onTextKeyboardVisibilityChanged(isVisible: Boolean) {
                if (arrMessage.isNotEmpty()) {
                    rc_chat.smoothScrollToPosition(arrMessage.size)
                }
            }

            override fun onStickersKeyboardVisibilityChanged(isVisible: Boolean) {

            }
        })
    }
    private fun focusMessage() {
        edt_input_message.isFocusable = true
        edt_input_message.isFocusableInTouchMode = true
        list_image.visibility = View.GONE
        count = 0
        mListPathCurrent.clear()
    }

    private fun addStickerMessage(code: String, b: Boolean, currentTimeMillis: Long) {
        if (code.isNullOrEmpty()) {
            return
        }
        if (StickersManager.isSticker(code)) {
            mPresenter.sendStickers(code,adminKey,groupKey)
            StickersManager.onUserMessageSent(true)
        } else {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == ChatActivity.REQUEST_RECORD_AUDIO) {
            if (resultCode == Activity.RESULT_OK) {
                mPresenter.onSendAudio(ChatActivity.AUDIO_FILE_PATH, adminKey, groupKey)
            } else if (requestCode == Activity.RESULT_CANCELED) {

            }
        }
    }
}
