package vn.phuongcong.fchat.ui.chat.sticker

import android.app.AlertDialog
import android.support.v4.content.ContextCompat

import vc908.stickerfactory.StickersManager
import vc908.stickerfactory.billing.PricePoint
import vn.phuongcong.fchat.R

class ShopActivity : vc908.stickerfactory.ui.activity.ShopWebViewActivity() {
    override fun onPurchase(packTitle: String?, packName: String?, pricePoint: PricePoint?) {
        val dialog = AlertDialog.Builder(this)
                .setTitle("Charge dialog")
                .setMessage("Purchase " + packTitle + " for " + pricePoint!!.label + "?")
                .setPositiveButton("Purchase") { dialog, which ->
                    StickersManager.onPackPurchased(packName)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                    onPurchaseFail()
                }
                .setCancelable(false)
                .create()
        dialog.setOnShowListener {
            val primaryColor = ContextCompat.getColor(this@ShopActivity, R.color.colorPrimary)
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(primaryColor)
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(primaryColor)
        }
        dialog.show()
    }
}