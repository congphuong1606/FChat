package vn.phuongcong.fchat.common.utils

import android.content.Context
import android.os.AsyncTask
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
/**
 * Created by Congphuong on 10/23/2017.
 */
object KeyboardUtils {

    fun hideKeyboard(context: Context, field: EditText) {
        val imm = context.getSystemService(
                Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(field.windowToken, WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }


    @JvmOverloads
    fun showDelayedKeyboard(context: Context, view: View, delay: Int? = 100) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void): Void? {
                try {
                    Thread.sleep(delay!!.toLong())
                } catch (e: InterruptedException) {
                }

                return null
            }

            override fun onPostExecute(result: Void) {
                val imm = context.getSystemService(
                        Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }

        }.execute()
    }
}