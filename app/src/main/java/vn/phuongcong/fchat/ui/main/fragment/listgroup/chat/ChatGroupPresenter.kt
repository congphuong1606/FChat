package vn.phuongcong.fchat.ui.main.fragment.listgroup.chat

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.model.Message
import vn.phuongcong.fchat.common.utils.CalendarUtils
import vn.phuongcong.fchat.common.utils.DatabaseRef
import vn.phuongcong.fchat.common.utils.DateTimeUltil
import java.io.File
import java.io.FileInputStream
import javax.inject.Inject

/**
 * Created by vietcoscc on 01/11/2017.
 */
class ChatGroupPresenter @Inject constructor(var chatGroupView: ChatGroupView, var storageReference: StorageReference) {
    var mAuth = FirebaseAuth.getInstance().currentUser
    fun onChat(content: String, msgImage: MutableList<String>, adminKey: String, groupKey: String) {
        if (msgImage.size > 0) {
            sendImageFromStorage(msgImage, content, adminKey, groupKey)
        } else {
            var currentTime = CalendarUtils.currentTime() + " " + CalendarUtils.currentDate()
            var message = Message(mAuth!!.uid, content, mutableListOf(), currentTime, "", 0)
            DatabaseRef.groupContentRef(adminKey).child(groupKey).push().setValue(message)
        }
    }

    fun onSendAudio(audioPath: String, adminKey: String, groupKey: String) {
        val stream = FileInputStream(File(audioPath))
        val mountainImagesRef = storageReference.child(Contans.IMAGE_MESSAGE).child(DateTimeUltil.getTimeCurrent() + ".wav")
        val uploadTask = mountainImagesRef.putStream(stream)
        uploadTask.addOnFailureListener({
            // Handle unsuccessful uploads
        }).addOnSuccessListener({ taskSnapshot ->
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
            val downloadUrl = taskSnapshot.downloadUrl
            var currentTime = CalendarUtils.currentTime() + " " + CalendarUtils.currentDate()
            var message = Message(mAuth!!.uid, "", mutableListOf(), currentTime, downloadUrl.toString(), 1)
            DatabaseRef.groupContentRef(adminKey).child(groupKey).push().setValue(message)
        })

    }

    fun sendStickers(code: String, adminKey: String, groupKey: String) {
        var currentTime = CalendarUtils.currentTime() + " " + CalendarUtils.currentDate()
        var message = Message(mAuth!!.uid, code, mutableListOf(), currentTime, "", 2)
        DatabaseRef.groupContentRef(adminKey).child(groupKey).push().setValue(message)
    }

    fun receiveChatData(adminKey: String, groupKey: String) {
        DatabaseRef.groupContentRef(adminKey).child(groupKey).addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                var message: Message = p0!!.getValue(Message::class.java)!!

                chatGroupView.showChatItem(message, p0.key)
            }

            override fun onChildRemoved(p0: DataSnapshot?) {

            }

        })
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
        chatGroupView.getListImageSuccess(listOfAllImages)
    }

    fun sendImageFromStorage(mListPathCurrent: MutableList<String>, content: String, adminKey: String, groupKey: String) {
        var linkImage: MutableList<String> = mutableListOf()
        for (image: String in mListPathCurrent) {
            var name = "" + CalendarUtils.currentTime() + image.substring(image.lastIndexOf("/") + 1, image.length)
            var uploadTask = FirebaseStorage.getInstance().reference.child("Group/" + name).putStream(FileInputStream(File(image)))
            uploadTask.addOnSuccessListener({ taskSnapShot ->
                val downloadUrl = taskSnapShot.downloadUrl
                linkImage.add(downloadUrl.toString())
                if (linkImage.size == mListPathCurrent.size) {
                    var currentTime = CalendarUtils.currentTime() + " " + CalendarUtils.currentDate()
                    var message = Message(mAuth!!.uid, content, linkImage, currentTime)
                    DatabaseRef.groupContentRef(adminKey).child(groupKey).push().setValue(message)
                }
            })
        }
    }
}