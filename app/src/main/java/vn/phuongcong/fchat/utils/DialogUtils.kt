package vn.phuongcong.fchat.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.support.v7.app.AlertDialog
import vn.phuongcong.fchat.R

/**
 * Created by Ominext on 10/13/2017.
 */
class DialogUtils(internal var dialog: ProgressDialog?, internal var activity: Activity) {

    //    public static void showDialogGetPhotoMenu(Context context, final UserManagerclickListener mListener) {
    //        CharSequence[] items = {"Chọn hình", "Chụp hình"};
    //        AlertDialog.Builder builder = new AlertDialog.Builder(context);
    //        builder.setTitle(context.getResources().getString(R.string.replayavatar));
    //        builder.setIcon(R.drawable.ic_noavatar);
    //        builder.setItems(items, new DialogInterface.OnClickListener() {
    //            @Override
    //            public void onClick(DialogInterface dialogInterface, int i) {
    //                if (items[i].equals("Chọn hình")) {
    //                    mListener.eventChoosePhoto();
    //                } else if (items[i].equals("Chụp hình")) {
    //                    mListener.eventTakePicture();
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
            if (msg != mContext.resources.getString(R.string.isempity)) {
                var error = msg
                if (msg == mContext.resources.getString(R.string.nointernet)) {
                    error = mContext.resources.getString(R.string.chedokhonginternet)
                } else if (msg.split("/".toRegex()).dropLastWhile({
                    it.isEmpty() }).toTypedArray()[0] == mContext.resources.getString(R.string.loiketnoi)) {
                    error = mContext.resources.getString(R.string.connectfail)
                }
                val builder = AlertDialog.Builder(mContext)
                builder.setTitle("lỗi")
                builder.setMessage(error)
                builder.setIcon(R.drawable.logo_app)
                builder.setCancelable(true)
                val dialog = builder.create()
                builder.setNegativeButton("ok") { dialogInterface, i -> dialog.dismiss() }
                dialog.show()
            }
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