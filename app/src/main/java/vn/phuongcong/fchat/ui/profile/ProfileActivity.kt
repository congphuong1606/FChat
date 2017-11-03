package vn.phuongcong.fchat.ui.profile

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.widget.*
import com.bumptech.glide.Glide
import com.yarolegovich.lovelydialog.LovelyTextInputDialog
import kotlinx.android.synthetic.main.activity_profile.*
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.event.OnPhotoListener
import vn.phuongcong.fchat.ui.main.MainActivity
import vn.phuongcong.fchat.ui.splash.SplashActivity
import vn.phuongcong.fchat.common.utils.DialogUtils
import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import java.io.ByteArrayOutputStream
import java.io.IOException
import javax.inject.Inject


class ProfileActivity : BaseActivity(), ProfileView, OnPhotoListener {



    @Inject
    lateinit var mPresenter: ProfilePresenter
    @Inject
    lateinit var  sRef: SharedPreferences


    override fun injectDependence() {
        (application as App).component
                .plus(ViewModule(this))
                .injectTo(this)
    }

    override fun onSignOutSuccessful() {
        val i = Intent(this@ProfileActivity, SplashActivity::class.java)
        startActivity(i)
        MainActivity().finish()
        finish()
    }


    override val contentLayoutID: Int
        get() = R.layout.activity_profile

    private var avatar: String?=null
    private var name: String?=null
    private var email: String?=null
    private var accId: String?=null

    override fun initData() {
        avatar = sRef.getString(Contans.PRE_USER_AVATAR, "")
        name = sRef.getString(Contans.PRE_USER_NAME, "")
        email = sRef.getString(Contans.PRE_USER_EMAIL, "")
        accId = sRef.getString(Contans.PRE_USER_ID, "")
        Glide.with(this).load(avatar).error(resources.getDrawable(R.drawable.ic_no_image)).into(imvAvatar)
        tvAccName.setText(name)
        tvAccEmail.setText(email)

    }

    override fun onClick() {
        btnSignOut.setOnClickListener {
            mPresenter.onSignOut()
        }
        imvAvatar.setOnClickListener {
            DialogUtils.showDialogGetPhotoMenu(this, this)
        }
        btnProfileBack.setOnClickListener { finish() }
        btnChangName.setOnClickListener { showDialogChangName() }
        btnChangePass.setOnClickListener { showDialogChangPass() }


    }

    private fun showDialogChangPass() {
        val passDialog = Dialog(this)
        passDialog.setContentView(R.layout.dialog_change_password)
        passDialog.setTitle(Contans.TITLE_PASS)
        val edtOldPass = passDialog.findViewById<EditText>(R.id.oldPass)
        val edtNewPass = passDialog.findViewById<EditText>(R.id.newPass)
        val edtRepeatNewPass = passDialog.findViewById<EditText>(R.id.repeatNewPass)
        val btnSave = passDialog.findViewById<Button>(R.id.btnOkChangPass)
        val btnCancel = passDialog.findViewById<Button>(R.id.btnCancelChangPass)
        btnSave.setOnClickListener{
            val oldPass=edtOldPass.text.toString().trim()
            val newPass=edtNewPass.text.toString().trim()
            val repeatNewPass=edtRepeatNewPass.text.toString().trim()
            if(oldPass.isNotEmpty()){
                if(newPass.isNotEmpty()){
                    if(repeatNewPass.isNotEmpty()){
                        if(!newPass.equals(oldPass)){
                            if(newPass.equals(repeatNewPass)){
                                showDialogConfirmChangePass(oldPass,newPass)

                                passDialog.dismiss()
                            }else{
                                edtRepeatNewPass.requestFocus()
                                edtRepeatNewPass.error = Contans.REPEATPASS_NOT_FOUND
                            }
                        }else{
                            edtNewPass.requestFocus()
                            edtNewPass.error = Contans.REPEAT_OLD_PASS
                        }
                    }else{
                        edtRepeatNewPass.requestFocus()
                        edtRepeatNewPass.error = Contans.PLEASE_INPUT_PSS
                    }
                }else{
                    edtNewPass.requestFocus()
                    edtNewPass.error = Contans.PLEASE_INPUT_PSS
                }
            }else{
                edtOldPass.requestFocus()
                edtOldPass.error = Contans.PLEASE_INPUT_PSS
            }

        }
        btnCancel.setOnClickListener { passDialog.dismiss() }
        passDialog.show()


    }

    private fun showDialogConfirmChangePass(oldPass: String, newPass: String) {
                AlertDialog.Builder(this)
                .setTitle(Contans.TITLE_PASS)
                .setMessage(Contans.CONFIRM_CHANGE_PASS)
                .setPositiveButton(android.R.string.ok) { dialogInterface, i ->
                    mPresenter.resetPassword(oldPass,newPass)
                    dialogInterface.dismiss()
                }
                .setNegativeButton(android.R.string.cancel) { dialogInterface, i -> dialogInterface.dismiss() }.show()
    }

    override fun onUpdateNameSuccessful(name: String) {
        tvAccName.setText(name)
    }
    private fun showDialogChangName() {
        LovelyTextInputDialog(this, R.style.EditTextTintTheme)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle(Contans.TITLE_RENAME)
                .setMessage(Contans.REQUEST_INPUT_NEW_ACCOUNT)
                .setIcon(R.drawable.ic_change)
                .setInputFilter(Contans.OLD_NAME, object : LovelyTextInputDialog.TextFilter {
                    override fun check(text: String): Boolean {
                       if(text.equals(name))
                        return false
                        else return true
                    }
                })
                .setInputFilter(Contans.NAME_NOT_NULL,object: LovelyTextInputDialog.TextFilter {
                    override fun check(text: String): Boolean {
                        if(text.isNotEmpty()){
                            return true
                        }else return false
                    }

                })
                .setConfirmButton(android.R.string.ok, object : LovelyTextInputDialog.OnTextInputConfirmListener {
                    override fun onTextInputConfirmed(text: String) {
                        mPresenter.replaceAccName(text)

                    }
                })
                .show()
    }
    override fun onUpdatePassWordSuccessfull() {
            Toast.makeText(this@ProfileActivity,Contans.CHANGE_PASS_SUCCESS,Toast.LENGTH_LONG).show()
    }
    override fun onChoosePhoto() {
        startActivityForResult(Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI), Contans.PIC_CHOOSE_CODE)
    }

    override fun onTakePhoto() {
        startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), Contans.PIC_TAKE_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        var bitmap: Bitmap? = null
        if (resultCode == RESULT_OK) {
            if (requestCode == Contans.PIC_TAKE_CODE) {
                val extras = data.getExtras()
                bitmap = extras!!.get("data") as Bitmap
            } else {
                if (data != null) {
                    val uri = data.data
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }


            }
        }
        if (bitmap != null) {
            val imageByte = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, imageByte)
            var picByte = imageByte.toByteArray()
            AlertDialog.Builder(this)
                    .setTitle("ảnh đại diện")
                    .setMessage("bạn chắc chắn muốn thay đổi ảnh đại diện")
                    .setPositiveButton(android.R.string.ok) { dialogInterface, i ->

                        mPresenter.changeAvatar(picByte)
                    }
                    .setNegativeButton(android.R.string.cancel)
                    { dialogInterface, i -> dialogInterface.dismiss() }.show()
        }
    }

    override fun onUpdateAvatarSuccessful(avatarUrl: String) {

        Glide.with(this).load(avatarUrl).into(imvAvatar)

    }


    override fun onRequestFailure(error: String) {
        DialogUtils.showErorr(this, error)
    }

    override fun showToast(msg: String) {

    }

}
