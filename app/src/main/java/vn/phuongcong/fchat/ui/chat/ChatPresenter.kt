package vn.phuongcong.fchat.ui.chat

import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.common.utils.DateTimeUltil
import vn.phuongcong.fchat.model.Chat
import vn.phuongcong.fchat.model.Message
import vn.phuongcong.fchat.model.Messagelast
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import java.io.File
import java.io.FileInputStream
import android.media.AudioManager
import android.media.MediaPlayer
import vn.phuongcong.fchat.model.Notification


/**
 * Created by Ominext on 10/18/2017.
 */
class ChatPresenter @Inject constructor(var mAuth: FirebaseAuth,
                                        var databaseReference: DatabaseReference,
                                        var spf: SharedPreferences,
                                        var chatView: ChatView,
                                        var storageReference: StorageReference) {
    private var uid = mAuth.currentUser!!.uid
    private var messages: MutableList<Message> = mutableListOf()

    fun getListChat(mChatItem: Chat, start: String, end: String) {
        //Todo mType=0: textSend, mType=3: ImageSend, mType=1: textReceiver, mType=2: ImageReceiver
        if (uid != "" && mChatItem != null) {
            //.orderByChild("timeCreate").startAt(start.toDouble()).endAt(end.toDouble()).limitToFirst(end)
            //.orderByKey().startAt(start).endAt(end)
            databaseReference.child(Contans.CHAT).child(uid).child(mChatItem.uIdFriend).addChildEventListener(object : ChildEventListener {
                override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildAdded(dataSnapshot: DataSnapshot?, p1: String?) {

                         var message: Message = dataSnapshot!!.getValue(Message::class.java)!!
                    if (message.mType == 1) {
                        message.mType = 6
                        messages.add(message)
                        chatView.getListMessageSuccess(messages)
                    }
                    if (message.mType == 0) {
                        if (!message.content.isNullOrEmpty()) {
                            message.mType = 0
                            messages.add(message)
                            chatView.getListMessageSuccess(messages)
                        } else {
                            if (!message.msgImage!!.isEmpty()) {
                                message.mType = 3
                                messages.add(message)
                                chatView.getListMessageSuccess(messages)
                            } else {
                                message.mType = 4
                                messages.add(message)
                                chatView.getListMessageSuccess(messages)
                            }
                        }
                    }
                }

                override fun onChildRemoved(p0: DataSnapshot?) {

                }

                override fun onCancelled(dataError: DatabaseError?) {
                }
            })
            databaseReference.child(Contans.CHAT).child(mChatItem.uIdFriend).child(uid).limitToLast(10).addChildEventListener(object : ChildEventListener {
                override fun onChildMoved(p0: DataSnapshot?, p1: String?) {

                }

                override fun onChildChanged(p0: DataSnapshot?, p1: String?) {

                }

                override fun onChildRemoved(p0: DataSnapshot?) {

                }

                override fun onChildAdded(dataSnapshot: DataSnapshot?, p1: String?) {
                    dataSnapshot
                    var message: Message = dataSnapshot!!.getValue(Message::class.java)!!
                    if (message.mType == 1) {
                        message.mType = 7
                        messages.add(message)
                        chatView.getListMessageSuccess(messages)
                    }
                    if (message.mType == 0) {
                        if (!message.content.isNullOrEmpty())
                        {
                            message.mType = 1
                            messages.add(message)
                            chatView.getListMessageSuccess(messages)
                        } else {
                            if (!message.msgImage!!.isEmpty()) {
                                message.mType = 2
                                messages.add(message)
                                chatView.getListMessageSuccess(messages)
                            } else {
                                message.mType = 5
                                messages.add(message)
                                chatView.getListMessageSuccess(messages)
                            }
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError?) {
                }
            })
        }
    }

    fun sendMessagetext(messagetext: String, mChatItem: Chat) {

        var message = Message(uid, messagetext, mutableListOf(), DateTimeUltil.getTimeCurrent(), null)
        databaseReference.child(Contans.CHAT).child(uid).child(mChatItem.uIdFriend).push().setValue(message)

        var messageLast = Messagelast(DateTimeUltil.getTimeCurrent(), messagetext)
        var noti = Notification("ok",messagetext)
        databaseReference.child(Contans.NOTIFICATION).child("NotiID").setValue(noti)
        databaseReference.child(Contans.MESSAGE_LASTS).child(uid).child(mChatItem.uIdFriend).child(Contans.MESSAGE_LAST).setValue(messageLast)
        databaseReference.child(Contans.MESSAGE_LASTS).child(mChatItem.uIdFriend).child(uid).child(Contans.MESSAGE_LAST).setValue(messageLast)
    }

    fun getListImage(context: Context) {
        val listOfAllImages = mutableListOf<String>()
        val cursor: Cursor
        val column_index_data: Int
        var absolutePathOfImage: String? = null
        val uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        cursor = context.contentResolver.query(uri, projection, null, null, null)
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data)
            listOfAllImages.add(absolutePathOfImage)
        }
        chatView.getListImageSuccess(listOfAllImages)
    }

    fun sendImageCamera(image: Bitmap) {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()
        val mountainImagesRef = storageReference.child(Contans.IMAGE_MESSAGE).child(DateTimeUltil.getTimeCurrent() + ".PNG")
        val uploadTask = mountainImagesRef.putBytes(data)
        uploadTask.addOnFailureListener({
        }).addOnSuccessListener({ taskSnapshot ->
            val downloadUrl = taskSnapshot.downloadUrl.toString()
            chatView.sendImageCamereSuccess(downloadUrl)
        })
    }

    fun sendImageFromStorage(mListPathCurrent: MutableList<String>) {
        var linkImage: MutableList<String> = mutableListOf()
        for (path: String in mListPathCurrent) {
            val stream = FileInputStream(File(path))
            val mountainImagesRef = storageReference.child(Contans.IMAGE_MESSAGE).child(DateTimeUltil.getTimeCurrent() + ".PNG")
            val uploadTask = mountainImagesRef.putStream(stream)
            uploadTask.addOnFailureListener({
                // Handle unsuccessful uploads
            }).addOnSuccessListener({ taskSnapshot ->
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                val downloadUrl = taskSnapshot.downloadUrl
                linkImage.add(downloadUrl.toString())
                if (linkImage.size == mListPathCurrent.size) {
                    chatView.sendImageSuccess(linkImage)
                }

            })
        }

    }

    fun sendMessageImage(linkImage: MutableList<String>, mChatItem: Chat) {
        var message = Message(uid, null, linkImage, DateTimeUltil.getTimeCurrent(), null)
        databaseReference.child(Contans.CHAT).child(uid).child(mChatItem.uIdFriend).push().setValue(message)
        var messageLast = Messagelast(DateTimeUltil.getTimeCurrent(), Contans.HINH_ANH)
        databaseReference.child(Contans.MESSAGE_LASTS).child(uid).child(mChatItem.uIdFriend).child(Contans.MESSAGE_LAST).setValue(messageLast)
        databaseReference.child(Contans.MESSAGE_LASTS).child(mChatItem.uIdFriend).child(uid).child(Contans.MESSAGE_LAST).setValue(messageLast)
    }


    fun sendMessageImageCamera(downloadUrl: String?, mChatItem: Chat) {
        var linkImage: MutableList<String> = mutableListOf()
        linkImage.add(downloadUrl!!)
        var message = Message(uid, null, linkImage, DateTimeUltil.getTimeCurrent(), null)
        databaseReference.child(Contans.CHAT).child(uid).child(mChatItem.uIdFriend).push().setValue(message)
        var messageLast = Messagelast(DateTimeUltil.getTimeCurrent(), Contans.HINH_ANH)
        databaseReference.child(Contans.MESSAGE_LASTS).child(uid).child(mChatItem.uIdFriend).child(Contans.MESSAGE_LAST).setValue(messageLast)
        databaseReference.child(Contans.MESSAGE_LASTS).child(mChatItem.uIdFriend).child(uid).child(Contans.MESSAGE_LAST).setValue(messageLast)
    }

    fun getAllLinkImage(mChatItem: Chat): MutableList<Message> {
        var mMessage: MutableList<Message> = mutableListOf()
        databaseReference.child(Contans.CHAT).child(uid).child(mChatItem.uIdFriend).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                for (children in dataSnapshot!!.children) {
                    var message = children.getValue(Message::class.java)
                    mMessage.add(message!!)
                }
            }
        })
        return mMessage
    }

    fun sendsticker(code: String, mChatItem: Chat) {
        var message = Message(uid, code, mutableListOf(), DateTimeUltil.getTimeCurrent(), null, 1)
        databaseReference.child(Contans.CHAT).child(uid).child(mChatItem.uIdFriend).push().setValue(message)

        var messageLast = Messagelast(DateTimeUltil.getTimeCurrent(), Contans.STICKER)
        databaseReference.child(Contans.MESSAGE_LASTS).child(uid).child(mChatItem.uIdFriend).child(Contans.MESSAGE_LAST).setValue(messageLast)
        databaseReference.child(Contans.MESSAGE_LASTS).child(mChatItem.uIdFriend).child(uid).child(Contans.MESSAGE_LAST).setValue(messageLast)
    }

    fun senAudio(audioPath: String, mChatItem: Chat) {


        val stream = FileInputStream(File(audioPath))
        val mountainImagesRef = storageReference.child(Contans.IMAGE_MESSAGE).child(DateTimeUltil.getTimeCurrent() + ".wav")
        val uploadTask = mountainImagesRef.putStream(stream)
        uploadTask.addOnFailureListener({
            // Handle unsuccessful uploads
        }).addOnSuccessListener({ taskSnapshot ->
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
            val downloadUrl = taskSnapshot.downloadUrl
            chatView.sendAudioSuccess(downloadUrl.toString())


        })


    }

    fun saveAudioFirebase(audio: String?, mChatItem: Chat) {
        var message = Message(uid, null, null, DateTimeUltil.getTimeCurrent(), audio)
        databaseReference.child(Contans.CHAT).child(uid).child(mChatItem.uIdFriend).push().setValue(message)
        var messageLast = Messagelast(DateTimeUltil.getTimeCurrent(), Contans.AM_THANH as String?)
        databaseReference.child(Contans.MESSAGE_LASTS).child(uid).child(mChatItem.uIdFriend).child(Contans.MESSAGE_LAST).setValue(messageLast)
        databaseReference.child(Contans.MESSAGE_LASTS).child(mChatItem.uIdFriend).child(uid).child(Contans.MESSAGE_LAST).setValue(messageLast)
    }

    fun getAvatarUserSend() {
        var image: String = ""
        databaseReference.child(Contans.USERS_PATH).child(uid).child(Contans.AVATAR).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot?) {
                image = data!!.getValue().toString()
                chatView.getAvatarUserSendSuccess(image)
            }

            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }

    fun playAudio(linkAudio: String, imgPlayPause: ImageView) {

        try {
            val player = MediaPlayer()
            player.setAudioStreamType(AudioManager.STREAM_MUSIC)
            player.setDataSource(linkAudio)
            player.prepare()
            player.start()

            if (player.isPlaying) {
                chatView.isStop(imgPlayPause)
            } else {
                chatView.isPlaying(imgPlayPause)
            }
        } catch (e: Exception) {
            // TODO: handle exception
        }
    }
}