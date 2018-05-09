package vn.phuongcong.fchat.ui.profile

import android.app.Dialog
import android.app.ProgressDialog
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
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import kotlinx.android.synthetic.main.dialog_change_password.*


class ProfileActivity : BaseActivity(), ProfileView, OnPhotoListener {
    var dialog: ProgressDialog? = null
    private lateinit var dialogUtils: DialogUtils
    private var avatar: String? = null
    private var name: String? = null
    private var email: String? = null
    private var accId: String? = null
    var oldPass: String? = null
    var newPass: String? = null
    var newName: String? = null
    var newAvatar: ByteArray? = null
    @Inject
    lateinit var mPresenter: ProfilePresenter
    @Inject
    lateinit var sRef: SharedPreferences
    @Inject
    lateinit var editSRef: SharedPreferences.Editor

    override val contentLayoutID: Int
        get() = R.layout.activity_profile

    override fun initData() {
        dialogUtils = DialogUtils(dialog, this)
        avatar = sRef.getString(Contans.PRE_USER_AVATAR, "")
        name = sRef.getString(Contans.PRE_USER_NAME, "")
        email = sRef.getString(Contans.PRE_USER_EMAIL, "")
        accId = sRef.getString(Contans.PRE_USER_ID, "")
        Glide.with(this).load(avatar).error(resources.getDrawable(R.drawable.ic_no_image)).into(imvAvatar)
        tvAccName.text = name
        tvAccEmail.text = email

    }

    override fun injectDependence() {
        (application as App).component
                .plus(ViewModule(this))
                .injectTo(this)
    }

    override fun onSignOutSuccessful() {
        editSRef.clear().commit()
        val i = Intent(this@ProfileActivity, SplashActivity::class.java)
        startActivity(i)
        MainActivity().finish()
        finish()
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


    private fun showDialogChangName() {
        LovelyTextInputDialog(this, R.style.EditTextTintTheme)
                .setMessage(Contans.REQUEST_INPUT_NEW_ACCOUNT)
                .setInputFilter(Contans.OLD_NAME) { text ->
                    !text.equals(name)
                }
                .setInputFilter(Contans.NAME_NOT_NULL) { text ->
                    text.isNotEmpty()
                }
                .setConfirmButton(android.R.string.ok) { text ->
                    newName = text
                    showDialogConfirm(Contans.CF_CHANGE_NAME, Contans.REQUEST_CONFRIM_CHANGE_ACOUNT)
                }
                .show()
    }

    override fun onChoosePhoto() {
        if (isReadStorageAllowed()) {
            startActivityForResult(Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI), Contans.PIC_CHOOSE_CODE)
        } else {
            requestStoragePermission()
        }

    }


    private fun requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
        }
        ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                Contans.REQUEST_WRITE_STORAGE)
    }


    private fun isReadStorageAllowed(): Boolean {
        val result = ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == Contans.REQUEST_WRITE_STORAGE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI), Contans.PIC_CHOOSE_CODE)
            } else {
                dialogUtils.showInfor(this, Contans.REQUEST_PERMISION_WRITE_STORAGE)
            }
        }
    }

    override fun onTakePhoto() {
        startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), Contans.PIC_TAKE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        var bitmap: Bitmap? = null
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                Contans.PIC_TAKE_CODE -> bitmap = (data.extras)!!.get("data") as Bitmap
                Contans.PIC_CHOOSE_CODE -> bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, data.data)
            }
        }
        if (bitmap != null) {
            val imgB = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, imgB)
            newAvatar = imgB.toByteArray()
            showDialogConfirm(Contans.CF_CHANGE_AVATAR, Contans.REQUEST_CONFRIM_CHANGE_AVATAR)

        }
    }

    private fun showDialogConfirm(key: Int, msg: String) {
        AlertDialog.Builder(this).setMessage(msg)
                .setPositiveButton(android.R.string.ok) { a, i ->
                    dialogUtils.showLoading()
                    when (key) {
                        Contans.CF_CHANGE_PASS -> mPresenter.resetPassword(oldPass!!, newPass!!)
                        Contans.CF_CHANGE_NAME -> mPresenter.replaceAccName(newName!!)
                        Contans.CF_CHANGE_AVATAR -> mPresenter.changeAvatar(newAvatar!!)
                    }
                    a.dismiss()
                }
                .setNegativeButton(android.R.string.cancel) { a, i -> a.dismiss() }.show()
    }

    override fun onUpdateNameSuccessful(name: String) {
        editSRef.putString(Contans.PRE_USER_NAME, name).commit()
        dialogUtils.hideLoading()
        tvAccName.text = name
        dialogUtils.showInfor(this, Contans.CHANGE_NAME_SUCCESS)

    }

    override fun onUpdateAvatarSuccessful(avatarUrl: String) {
        editSRef.putString(Contans.PRE_USER_AVATAR, avatarUrl).commit()
        dialogUtils.hideLoading()
        Glide.with(this).load(avatarUrl).into(imvAvatar)
        dialogUtils.showInfor(this, Contans.CHANGE_AVATAR_SUCCESS)

    }

    override fun onUpdatePassWordSuccessfull() {
        dialogUtils.hideLoading()
        dialogUtils.showInfor(this, Contans.CHANGE_PASS_SUCCESS)
    }

    override fun onRequestFailure(error: String) {
        dialogUtils.hideLoading()
        dialogUtils.showInfor(this, error)
    }

    override fun showToast(msg: String) {

    }

    private fun showDialogChangPass() {
        val passDialog = Dialog(this)
        passDialog.setContentView(R.layout.dialog_change_password)
        passDialog.setTitle(Contans.TITLE_PASS)
        passDialog.btnOkChangPass.setOnClickListener {
            oldPass = passDialog.oldPass.text.toString().trim()
            newPass = passDialog.newPass.text.toString().trim()
            val repeatNewPass = passDialog.repeatNewPass.text.toString().trim()
            if (oldPass!!.isNotEmpty()) {
                if (newPass!!.isNotEmpty()) {
                    if (repeatNewPass.isNotEmpty()) {
                        if (!newPass.equals(oldPass)) {
                            if (newPass.equals(repeatNewPass)) {
                                showDialogConfirm(Contans.CF_CHANGE_PASS, Contans.REQUEST_CONFRIM_CHANGE_PASS)
                                passDialog.dismiss()
                            } else {
                                passDialog.repeatNewPass.requestFocus()
                                passDialog.repeatNewPass.error = Contans.REPEATPASS_NOT_FOUND
                            }
                        } else {
                            passDialog.newPass.requestFocus()
                            passDialog.newPass.error = Contans.REPEAT_OLD_PASS
                        }
                    } else {
                        passDialog.repeatNewPass.requestFocus()
                        passDialog.repeatNewPass.error = Contans.PLEASE_INPUT_PSS
                    }
                } else {
                    passDialog.newPass.requestFocus()
                    passDialog.newPass.error = Contans.PLEASE_INPUT_PSS
                }
            } else {
                passDialog.oldPass.requestFocus()
                passDialog.oldPass.error = Contans.PLEASE_INPUT_PSS
            }

        }
        passDialog.btnCancelChangPass.setOnClickListener { passDialog.dismiss() }
        passDialog.show()


    }
}
