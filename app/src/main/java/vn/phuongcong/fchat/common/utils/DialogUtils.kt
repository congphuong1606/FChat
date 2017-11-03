package vn.phuongcong.fchat.common.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.support.v7.app.AlertDialog
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.event.OnPhotoListener

/**
 * Created by Ominext on 10/13/2017.
 */
class DialogUtils(internal var dialog: ProgressDialog?, internal var activity: Activity) {


    fun showLoading() {
        if (dialog != null) {
            if (dialog!!.isShowing) dialog!!.dismiss()
            dialog!!.show()
            return
        }
        dialog = ProgressDialog
                .show(activity, "", "Loading. Please wait...", true)
    }


    fun hideLoading() {
        if (dialog != null && dialog!!.isShowing)
            dialog!!.dismiss()
    }

    companion object {
        fun showErorr(mContext: Context, msg: String) {
            val builder = AlertDialog.Builder(mContext)
            builder.setTitle("Thông báo")
            builder.setMessage(msg)
            builder.setIcon(R.drawable.logo_app)
            builder.setCancelable(true)
            val dialog = builder.create()
            builder.setNegativeButton("ok") { dialogInterface, i -> dialog.dismiss() }
            dialog.show()
        }

        fun showDialogGetPhotoMenu(context: Context, mListener: OnPhotoListener) {
            val items = arrayOf<CharSequence>("Chọn hình", "Chụp hình")
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.resources.getString(R.string.replayavatar))
            builder.setIcon(R.drawable.ic_no_image)
            builder.setItems(items) { dialogInterface, i ->
                if (items[i] == "Chọn hình") {
                    mListener.onChoosePhoto()
                } else if (items[i] == "Chụp hình") {
                    mListener.onTakePhoto()
                }
            }
            builder.setCancelable(true)
            val dialog = builder.create()
            builder.setNegativeButton("hủy") { dialogInterface, i -> dialog.dismiss() }
            dialog.show()
        }
    }




    //    public static void showDialogConfim(Context context, final UserManagerclickListener mListener, int typeUpdate) {
    //        new AlertDialog.Builder(context)
    //                .setIcon(R.drawable.ic_about)
    //                .setTitle("Xác nhận ")
    //                .setMessage("Thông tin cá nhân của bạn sẽ bị thay đổi ?")
    //                .setPositiveButton("đồng ý", new DialogInterface.OnClickListener() {
    //                    @Override
    //                    public void onClick(DialogInterface dialog, int which) {
    //                        mListener.onConfim(typeUpdate);
    //                    }
    //
    //                })
    //                .setNegativeButton("hủy", null)
    //                .show();
    //    }
    //
    //
    //    public static void showDialogOptionPostHistory(Context context, final PHfragmentClickListener mListener) {
    //        CharSequence[] items = {"xóa khỏi lịch sử", "xem chi tiết"};
    //        AlertDialog.Builder builder = new AlertDialog.Builder(context);
    //        builder.setItems(items, new DialogInterface.OnClickListener() {
    //            @Override
    //            public void onClick(DialogInterface dialogInterface, int i) {
    //                if (items[i].equals("xóa khỏi lịch sử")) {
    //                    mListener.onClickDelete();
    //                } else if (items[i].equals("xem chi tiết")) {
    //                    mListener.onClickView();
    //                }
    //            }
    //        });
    //        builder.setCancelable(true);
    //        final AlertDialog dialog = builder.create();
    //        builder.setNegativeButton("hủy", new DialogInterface.OnClickListener() {
    //            @Override
    //            public void onClick(DialogInterface dialogInterface, int i) {
    //                dialog.dismiss();
    //            }
    //        });
    //        dialog.show();
    //    }

}