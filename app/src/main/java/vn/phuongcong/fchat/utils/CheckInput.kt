package vn.phuongcong.fchat.utils

import android.app.Activity
import android.content.Context
import android.widget.EditText
import vn.phuongcong.fchat.R
import java.util.regex.Pattern

/**
 * Created by Ominext on 10/13/2017.
 */
object CheckInput{
    fun isEmpty(etText: EditText?): Boolean {
        if (etText != null && etText.text.toString().trim().length > 0) {
            return true
        } else {
            etText!!.requestFocus()
            etText.error = "Vui lòng điền thông tin!"
            return false
        }
    }

    fun isEmailValid(email: String): Boolean {
        var isValid = false
        val expression = "[a-zA-Z0-9._-]+@[a-z]+(\\.+[a-z]+)+"

        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        if (matcher.matches()) {
            isValid = true
        }
        return isValid
    }

    fun checkInPutLogin(edtEmail: EditText, edtAccPass: EditText, activity: Activity): Boolean {
        if (isEmpty(edtEmail) && isEmpty(edtAccPass)) {
            val email = edtEmail.text.toString().trim { it <= ' ' }
            val pass = edtAccPass.text.toString().trim { it <= ' ' }
            if (!isEmailValid(email)) {
                edtEmail.requestFocus()
                edtEmail.error = activity.resources.getString(R.string.email_erorr)
                return false
            } else {
                if (pass.length < 6) {
                    edtAccPass.requestFocus()
                    edtAccPass.error = activity.resources.getString(R.string.pass_error)
                    return false
                }
            }
            return true
        } else {
            return false
        }
    }

    fun checkInPutRegis(edtEmail: EditText, edtPass: EditText,
                        edtConfirmPass: EditText, activity: Activity): Boolean {
        if (isEmpty(edtEmail) && isEmpty(edtPass) && isEmpty(edtConfirmPass)) {
            val email = edtEmail.text.toString().trim { it <= ' ' }
            val pass = edtPass.text.toString().trim { it <= ' ' }
            val confirmPass = edtConfirmPass.text.toString().trim { it <= ' ' }
            if (!isEmailValid(email)) {
                edtEmail.requestFocus()
                edtEmail.error = activity.resources.getString(R.string.email_erorr)
                return false
            } else {
                if (pass.length < 6) {
                    edtPass.requestFocus()
                    edtPass.error = activity.resources.getString(R.string.pass_error)
                    return false
                } else {
                    if (confirmPass != pass) {
                        edtConfirmPass.requestFocus()
                        edtConfirmPass.error = activity.resources.getString(R.string.confim_pass_erorr)
                        return false
                    }
                }

            }
            return true
        } else {
            return false
        }
    }
}