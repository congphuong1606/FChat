package vn.phuongcong.fchat.ui.main.fragment.listgroup.chat

import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pawegio.kandroid.runOnUiThread
import kotlinx.android.synthetic.main.item_audio_receiver.view.*
import kotlinx.android.synthetic.main.item_chat_group.view.*
import kotlinx.android.synthetic.main.item_image_chat_group.view.*
import kotlinx.android.synthetic.main.item_sticker_receiver.view.*
import vc908.stickerfactory.StickersManager
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.model.Message
import vn.phuongcong.fchat.model.User
import vn.phuongcong.fchat.common.utils.DatabaseRef
import java.util.*

/**
 * Created by vietcoscc on 01/11/2017.
 */
class ChatGroupAdapter(private var arrMessage: ArrayList<Message>, private var arrMessageKey: ArrayList<String>, private var adminKey: String, private var groupKey: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_TEXT_IMG = 0
    private val TYPE_AUDIO = 1
    private val TYPE_STICKER = 2
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TEXT_IMG -> {
                var v = LayoutInflater.from(parent!!.context).inflate(R.layout.item_chat_group, parent, false)
                ChatGroupViewHolder(v)
            }
            TYPE_AUDIO -> {
                var v = LayoutInflater.from(parent!!.context).inflate(R.layout.item_audio_receiver, parent, false)
                ChatGroupAudioViewHolder(v)
            }
            else -> {
                var v = LayoutInflater.from(parent!!.context).inflate(R.layout.item_sticker_receiver, parent, false)
                ChatGroupStickerViewHolder(v)
            }
        }
    }

    override fun getItemCount(): Int {
        return arrMessage.size
    }

    override fun getItemViewType(position: Int): Int {
        var type = arrMessage[position].mType
        return when (type) {
            1 -> TYPE_AUDIO
            0 -> TYPE_TEXT_IMG
            else -> TYPE_STICKER
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ChatGroupViewHolder) {

            holder.bindView(arrMessage[position], arrMessageKey[position], adminKey, groupKey)
        }
        if (holder is ChatGroupAudioViewHolder) {
            holder.bind(arrMessage[position])
        }
        if (holder is ChatGroupStickerViewHolder) {
            holder.bind(arrMessage[position])
        }
    }

    class ChatGroupViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var arrImage = arrayListOf<String>()
        lateinit var imageAdapter: ImageAdapter
        fun bindView(message: Message, messageKey: String, adminKey: String, groupKey: String) {

            if (message.msgImage!!.size > 0) {
                itemView.recyclerViewImage.visibility = View.VISIBLE
                if (message.msgImage!!.size == 1) {
                    itemView.recyclerViewImage.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                } else {
                    itemView.recyclerViewImage.layoutManager = GridLayoutManager(itemView.context, 2)
                }
                itemView.recyclerViewImage.adapter = ImageAdapter(arrImage)
            } else {
                itemView.recyclerViewImage.visibility = View.INVISIBLE
            }
            imageAdapter = ImageAdapter(message.msgImage!!)
            itemView.recyclerViewImage.adapter = imageAdapter
            itemView.tvContent.text = message.content
            itemView.tvTimeStamp.text = message.timeCreate
            DatabaseRef.userInfoRef(message.senderId)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {

                        }

                        override fun onDataChange(p0: DataSnapshot?) {
                            if (p0!!.exists()) {
                                var user: User = p0!!.getValue(User::class.java)!!
                                if (!TextUtils.isEmpty(user.avatar)) {
                                    Glide.with(itemView.context).load(user.avatar).into(itemView.ivAvatar)
                                } else {
                                    Glide.with(itemView.context).load(R.drawable.ic_no_image).into(itemView.ivAvatar)
                                }

                                if (!TextUtils.isEmpty(user.name)) {
                                    itemView.tvDisplayName.text = user.name
                                }
                            }
                        }
                    })
        }
    }

    class ChatGroupAudioViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        private var player = MediaPlayer()
        private var timer = Timer()


        fun bind(message: Message) {
            itemView.img_avatar_receive_audio
            itemView.txt_time_duration_receiver.text = ""
            itemView.txt_time_receiver_audio.text = ""

            itemView.setOnClickListener {
                if (player.isPlaying) {
                    player.pause()
                    itemView.img_play_pause_receiver.setImageResource(R.drawable.aar_ic_play)
                } else {
                    itemView.img_play_pause_receiver.setImageResource(R.drawable.aar_ic_pause)
                    player = MediaPlayer()
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC)
                    player.setDataSource(message.audio)
                    player.prepareAsync()
                    player.setOnPreparedListener {
                        player.start()
                        timer.cancel()
                        timer = Timer()
                        var timerTask = kotlin.concurrent.timerTask {
                            runOnUiThread {
                                itemView.txt_time_receiver_audio.text = "" + player.currentPosition
                            }

                        }
                        timer.schedule(timerTask, 0, 1000)
                        itemView.txt_time_duration_receiver.text = "" + player.duration
                        itemView.txt_time_receiver_audio.text = "" + player.currentPosition
                        Toast.makeText(itemView.context, "started", Toast.LENGTH_SHORT).show()
                    }
                    player.setOnCompletionListener {
                        itemView.img_play_pause_receiver.setImageResource(R.drawable.aar_ic_play)
                        timer.cancel()
                        timer = Timer()
                    }
                }

            }
        }
    }

    class ChatGroupStickerViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: Message) {
            StickersManager.with(itemView.context).loadSticker(message.content).into(itemView.img_sticker_receiver)
            DatabaseRef.userInfoRef(message.senderId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {

                }

                override fun onDataChange(p0: DataSnapshot?) {
                    if (p0!!.exists()) {
                        var user = p0.getValue(User::class.java)
                        if (!TextUtils.isEmpty(user!!.avatar)) {
                            Glide.with(itemView.context).load(user!!.avatar).into(itemView.avatar_receiver)
                        } else {
                            Glide.with(itemView.context).load(R.drawable.ic_no_image).into(itemView.avatar_receiver)
                        }
                    }
                }

            })
        }
    }

    fun addItem(message: Message, key: String) {
        arrMessage.add(message)
        arrMessageKey.add(key)
        notifyItemInserted(arrMessage.size - 1)
    }

    fun removeItem(position: Int) {
        arrMessage.removeAt(position)
        arrMessageKey.removeAt(position)
        notifyItemRemoved(position)
    }
}