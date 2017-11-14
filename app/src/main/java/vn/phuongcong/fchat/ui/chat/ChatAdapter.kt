package vn.phuongcong.fchat.ui.chat


import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_audio_receiver.view.*
import kotlinx.android.synthetic.main.item_audio_send.view.*
import kotlinx.android.synthetic.main.item_text_receiver.view.*
import kotlinx.android.synthetic.main.item_text_send.view.*
import kotlinx.android.synthetic.main.item_image_receiver.view.*
import kotlinx.android.synthetic.main.item_image_send.view.*
import vc908.stickerfactory.StickersManager
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.common.utils.DateTimeUltil
import vn.phuongcong.fchat.model.Chat
import vn.phuongcong.fchat.model.Message

/**
 * Created by Ominext on 10/20/2017.
 */
class ChatAdapter(var mMessage: MutableList<Message>, var mContext: Context, var isend: Isend, var chatItem: Chat, var mImgUserSend: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val LEFT_TEXT = 1
        val RIGHT_TEXT = 0
        val LEFT_IMAGE = 2
        val RIGHT_IMAGE = 3
        val LEFT_AUDIO = 5
        val RIGHT_AUDIO = 4
        val LEFT_STICKER = 7
        val RIGHT_STICKER = 6
    }

    interface Isend {
        fun sendDataImage(linkMessage: MutableList<String>, rcList: GridView)
        fun sendDataAudio(linkAudio: String, imgPlayPause: ImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        if (viewType == RIGHT_TEXT)
            viewHolder = ChatItemViewHolderSend(LayoutInflater.from(parent!!.context).inflate(R.layout.item_text_send, parent, false))
        if (viewType == LEFT_TEXT)
            viewHolder = ChatItemViewHolderReceiver(LayoutInflater.from(parent!!.context).inflate(R.layout.item_text_receiver, parent, false))
        if (viewType == RIGHT_IMAGE)
            viewHolder = ChatItemViewHolderSendImage(LayoutInflater.from(parent!!.context).inflate(R.layout.item_image_send, parent, false))
        if (viewType == LEFT_IMAGE)
            viewHolder = ChatItemViewHolderReceiverImage(LayoutInflater.from(parent!!.context).inflate(R.layout.item_image_receiver, parent, false))
        if (viewType == RIGHT_AUDIO)
            viewHolder = ChatItemViewHolderSendAudio(LayoutInflater.from(parent!!.context).inflate(R.layout.item_audio_send, parent, false))
        if (viewType == LEFT_AUDIO)
            viewHolder = ChatItemViewHolderReceiverAudio(LayoutInflater.from(parent!!.context).inflate(R.layout.item_audio_receiver, parent, false))

        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return mMessage.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ChatItemViewHolderSend) {
            if (mMessage != null && mMessage.size > 0) {
                mMessage.sortBy { message: Message -> message.timeCreate.toLong() }
                var message: Message = mMessage.get(position)
                holder.bind(message, mContext, isend, mImgUserSend)
            }
        }

        if (holder is ChatItemViewHolderReceiver) {
            if (mMessage != null && mMessage.size > 0) {
                mMessage.sortBy { message: Message -> message.timeCreate.toLong() }
                var messageReceiver = mMessage.get(position)
                holder.bindReceiver(messageReceiver, mContext, isend, chatItem)

            }
        }
        if (holder is ChatItemViewHolderSendImage) {
            if (mMessage != null && mMessage.size > 0) {
                mMessage.sortBy { message: Message -> message.timeCreate.toLong() }
                var messageReceiver = mMessage.get(position)
                holder.bindSendImage(messageReceiver, mContext, isend, mImgUserSend)

            }
        }
        if (holder is ChatItemViewHolderReceiverImage) {
            if (mMessage != null && mMessage.size > 0) {
                mMessage.sortBy { message: Message -> message.timeCreate.toLong() }
                var messageReceiver = mMessage.get(position)
                holder.bindReceiverImage(messageReceiver, mContext, isend, chatItem, mImgUserSend)

            }
        }

        if (holder is ChatItemViewHolderSendAudio) {
            if (mMessage != null && mMessage.size > 0) {
                mMessage.sortBy { message: Message -> message.timeCreate.toLong() }
                var messageReceiver = mMessage.get(position)
                holder.bindSendAudio(messageReceiver, mContext, isend, mImgUserSend)

            }
        }
        if (holder is ChatItemViewHolderReceiverAudio) {
            if (mMessage != null && mMessage.size > 0) {
                mMessage.sortBy { message: Message -> message.timeCreate.toLong() }
                var messageReceiver = mMessage.get(position)
                holder.bindReceiverAudio(messageReceiver, mContext, isend, chatItem, mImgUserSend)

            }
        }
    }

    // MessageText
    class ChatItemViewHolderSend(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: Message, mContext: Context, isend: Isend, mImgUserSend: String) {
            itemView.txt_message_send.text = message.content
            itemView.txt_time_send.text = DateTimeUltil.fotmatTime(message.timeCreate.toLong(), DateTimeUltil.mTimeFormat)
            Glide.with(mContext).load(mImgUserSend).into(itemView.img_send)
        }
    }

    class ChatItemViewHolderReceiver(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindReceiver(message: Message, mContext: Context, isend: Isend, chatItem: Chat) {
            itemView.txt_message_receiver.text = message.content
            itemView.txt_time_receicver.text = DateTimeUltil.fotmatTime(message.timeCreate.toLong(), DateTimeUltil.mTimeFormat)
            Glide.with(mContext).load(chatItem.mImageFriend).into(itemView.img_receiver)
        }
    }

    //----

    //Message Image

    class ChatItemViewHolderSendImage(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bindSendImage(message: Message, mContext: Context, isend: Isend, mImgUserSend: String) {
            itemView.txt_time_send_image.text = DateTimeUltil.fotmatTime(message.timeCreate.toLong(), DateTimeUltil.mTimeFormat)
            if (message.msgImage!!.size > 0)
                isend.sendDataImage(message.msgImage!!, itemView.gr_list_image_send)
            Glide.with(mContext).load(mImgUserSend).into(itemView.img_avatar_send)
        }
    }

    class ChatItemViewHolderReceiverImage(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindReceiverImage(message: Message, mContext: Context, isend: Isend, chatItem: Chat, mImgUserSend: String) {
            itemView.txt_time_receicver_image.text = DateTimeUltil.fotmatTime(message.timeCreate.toLong(), DateTimeUltil.mTimeFormat)
            if (message.msgImage!!.size > 0)
                isend.sendDataImage(message.msgImage!!, itemView.gr_list_image_receive)
            Glide.with(mContext).load(chatItem.mImageFriend).into(itemView.img_avatar_receive)
        }
    }

    //----

    //Message Audio

    class ChatItemViewHolderSendAudio(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bindSendAudio(message: Message, mContext: Context, isend: Isend, mImgUserSend: String) {
            try {
                val player = MediaPlayer.create(mContext, Uri.parse(message.audio))
                itemView.txt_time_duration_send.text = DateTimeUltil.fotmatTime(player.duration.toLong(), DateTimeUltil.mTimeSFormat)
                player.release()
            } catch (ex: Exception) {

            }
            itemView.txt_time_send_audio.text = DateTimeUltil.fotmatTime(message.timeCreate.toLong(), DateTimeUltil.mTimeFormat)
            itemView.setOnClickListener {
                isend.sendDataAudio(message.audio!!, itemView.img_play_pause)
            }
        }
    }

    class ChatItemViewHolderReceiverAudio(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindReceiverAudio(message: Message, mContext: Context, isend: Isend, chatItem: Chat, mImgUserSend: String) {
            itemView.txt_time_receiver_audio.text = DateTimeUltil.fotmatTime(message.timeCreate.toLong(), DateTimeUltil.mTimeFormat)
            try {
                val player = MediaPlayer.create(mContext, Uri.parse(message.audio))
                itemView.txt_time_duration_receiver.text = DateTimeUltil.fotmatTime(player.duration.toLong(), DateTimeUltil.mTimeSFormat)
                player.release()
            } catch (ex: Exception) {

            }
            itemView.setOnClickListener {
                isend.sendDataAudio(message.audio!!, itemView.img_play_pause_receiver)
            }
            Glide.with(mContext).load(chatItem.mImageFriend).into(itemView.img_avatar_receive_audio)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (mMessage.get(position).mType == RIGHT_TEXT)
            return RIGHT_TEXT
        else if (mMessage.get(position).mType == LEFT_TEXT)
            return LEFT_TEXT
        else if (mMessage.get(position).mType == LEFT_IMAGE)
            return LEFT_IMAGE
        else if (mMessage.get(position).mType == RIGHT_IMAGE)
            return RIGHT_IMAGE
        else if (mMessage.get(position).mType == LEFT_AUDIO)
            return LEFT_AUDIO
        else if (mMessage.get(position).mType == LEFT_STICKER)
            return LEFT_STICKER
        else
            return RIGHT_STICKER

    }

    private fun loadSticker(convertView: ImageView, message: String) {
        StickersManager.with(mContext)
                .loadSticker(message)
                .into(convertView)
    }
}