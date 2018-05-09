package vn.phuongcong.fchattranslate.ui.base

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity


/**
 * Created by Ominext on 10/9/2017.
 */

abstract class BaseActivity : AppCompatActivity() {
    protected abstract val contentLayoutID: Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentLayoutID)
        injectDependence()
        initData()
        onClick()
    }


    protected abstract fun onClick()


    protected abstract fun injectDependence()
    protected abstract fun initData()


    fun onStartActivity(b: Class<*>) {
        startActivity(Intent(this, b))
    }


}
