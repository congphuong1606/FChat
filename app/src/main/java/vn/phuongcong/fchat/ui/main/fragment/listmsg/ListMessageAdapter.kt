package vn.phuongcong.fchat.ui.main.fragment.listmsg

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_msg.view.*
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.model.Chat
import vn.phuongcong.fchat.model.Messagelast
import vn.phuongcong.fchat.common.utils.DateTimeUltil

/**
 * Created by Ominext on 10/20/2017.
 */
class ListMessageAdapter(var mListMessage: MutableList<Chat>, var mIChat:
IChat,var mListMessageLast :MutableList<Messagelast>,var mContext:Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface IChat{
        fun chat(chat :Chat)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ChatHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_msg, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ChatHolder) {

            if (mListMessage != null && mListMessage.isNotEmpty() && mListMessageLast.isNotEmpty()) {
                var chat: Chat = mListMessage.get(position)
                var messagelast =mListMessageLast.get(position)
               holder.bin(chat,mIChat,messagelast,mContext)

            }

        }
    }

    override fun getItemCount(): Int {
        return mListMessage.size
    }

    class ChatHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bin(chat: Chat, mIChat: IChat, messagelast: Messagelast, mContext: Context) {
            itemView.txt_name_friend.text = chat.mFriend
            if(messagelast.messageLast.isNullOrEmpty()){
                itemView.txt_message_last.text = Contans.HINH_ANH
            }else{
                itemView.txt_message_last.text = messagelast.messageLast
            }

            itemView.txt_time.text=DateTimeUltil.fotmatTime(messagelast.timeLastSend.toLong(),DateTimeUltil.mTimeFormat)
            Glide.with(mContext).load(chat.mImageFriend).into(itemView.img_image_friend)
            itemView.img_image_friend
            itemView.setOnClickListener {
                mIChat.chat(chat)
            }

        }
    }

}