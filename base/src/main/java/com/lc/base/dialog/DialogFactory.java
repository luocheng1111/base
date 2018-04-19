package com.lc.base.dialog;
/**
 * name：luocheng
 * date：2018/01/16
 * QQ：1163011680
 */


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.lc.base.R;
import com.lc.base.util.DensityUtils;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;

import java.util.List;


public class DialogFactory {

    public static void showOneBtnDialog(Context mContext, String title,
                                        String message) {
        final RxAlertDialog rxDialogSure = new RxAlertDialog(mContext);//提示弹窗
        rxDialogSure.setTitle(title);
        rxDialogSure.setContent(message);
        rxDialogSure.show();

        rxDialogSure.getSureView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rxDialogSure.cancel();
            }
        });
    }


    public static void showTwoBtnDialog(Context mContext, String title,
                                        String message, final OnAlertViewClickListener positiveListener) {
        final RxAlertDialog rxDialogSure = new RxAlertDialog(mContext);//提示弹窗
        rxDialogSure.setStyle(2);
        rxDialogSure.setTitle(title);
        rxDialogSure.setContent(message);
        rxDialogSure.show();

        rxDialogSure.getSureView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != positiveListener) {
                    rxDialogSure.cancel();
                    positiveListener.confirm();
                }
            }
        });

        rxDialogSure.getCancelView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != positiveListener) {
                    rxDialogSure.cancel();
                    positiveListener.cancel();
                }
            }
        });
    }

    public static void showOneBtnDialog(Context mContext, String title,
                                        String message, final OnAlertViewClickListener positiveListener) {
        final RxAlertDialog rxDialogSure = new RxAlertDialog(mContext);//提示弹窗
//                rxDialogSure.getLogoView().setImageResource(R.drawable.logo);
        rxDialogSure.setTitle(title);
        rxDialogSure.setContent(message);
        rxDialogSure.show();

        rxDialogSure.getSureView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != positiveListener) {
                    rxDialogSure.cancel();
                    positiveListener.cancel();
                }
            }
        });
    }

    /*
    用法：
    Dialog loadingDialog = DialogFactory.createLoadingDialog(this);
    loadingDialog.show();
     */
    public static Dialog createLoadingDialog(Context context) {
        Dialog dialog = new Dialog(context, R.style.LoadingDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        view.getBackground().setAlpha(130);
        final int viewWidth = DensityUtils.dp2px(context, 150);
        view.setMinimumWidth(viewWidth);
        dialog.setContentView(view);
        //设置点击屏幕不自动消失
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    /*
    用法：
    1.DialogFactory.showOkOrNoDialog(this, "修改成功!", true);
    2.DialogFactory.showOkOrNoDialog(this, "修改失败!", false);
     */
    public static void showOkOrNoDialog(Context context, String msg, boolean isOk) {
        showOkOrNoDialog(context, msg, isOk, 599);
    }

    public static void showOkOrNoDialog(Context context, String msg, boolean isOk, long showDuration) {
        final Dialog dialog = new Dialog(context, R.style.LoadingDialog);
        dialog.getWindow().setDimAmount(0f);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        view.getBackground().setAlpha(130);
        final int viewWidth = DensityUtils.dp2px(context, 150);
        view.setMinimumWidth(viewWidth);
        view.findViewById(R.id.progressBar).setVisibility(View.GONE);
        ImageView iv = view.findViewById(R.id.iv_);
        iv.setVisibility(View.VISIBLE);
        if(isOk){
            iv.setImageResource(R.drawable.ok);
        }else {
            iv.setImageResource(R.drawable.no);
        }
        TextView tv = view.findViewById(R.id.tv_);
        tv.setText(msg);
        dialog.setContentView(view);

        dialog.show();

        //如果界面显示这个Dialog后就立即finish的话,建议finish延迟销毁, 且延迟销毁时间必须大于此时间
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(dialog!=null) {
                    dialog.dismiss();
                }
            }
        }, showDuration);
    }



    /*
    用法：
    final String items11111[] = {"男", "女"};
                DialogFactory.showSingleChoiceDialog(this, "标题", items11111, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                T.showShortToast(MainActivity.this,"选中的是:"+items11111[selectedIndex]);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                T.showShortToast(MainActivity.this,"取消");
                                break;
                            default:
                                selectedIndex = which;
                                break;
                        }

                    }
                });
     */
    public static void showSingleChoiceDialog(Context context, String title, String[] items, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setNegativeButton("取消", listener);
        builder.setPositiveButton("确定", listener);
        builder.setSingleChoiceItems(items, 0, listener);
        builder.show();
    }

    /*
    用法：
    final String items2222[] = {"篮球", "足球", "排球"};
                final boolean selected[] = {true, false, true};
                DialogFactory.showMultiChoiceDialog(this, "标题", items2222, selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                String result = "选中的有:";
                                for (int i=0; i<selected.length;i++){
                                    if(selected[i]==true){
                                        result = result+items2222[i]+" ";
                                        T.showShortToast(MainActivity.this, result);
                                    }
                                }
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                T.showShortToast(MainActivity.this,"取消");
                                break;
                            default:
                                selected[which]= !selected[which];
                                break;
                        }
                    }
                });
     */
    public static void showMultiChoiceDialog(Context context,  String title, String[] items, boolean[] selected, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setNegativeButton("取消", listener);
        builder.setPositiveButton("确定", listener);
        builder.setMultiChoiceItems(items, selected, new DialogInterface.OnMultiChoiceClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

            }
        });
        builder.show();
    }

    public static void showListDialog(Context context, String title, List<String> list, DialogInterface.OnClickListener listener) {
        showListDialog(context,title,listToArray(list),listener);
    }

    public static void showSingleChoiceDialog(Context context, String title, List<String> list, DialogInterface.OnClickListener listener) {
        showSingleChoiceDialog(context,title,listToArray(list),listener);
    }

    public static void showMultiChoiceDialog(Context context,  String title, List<String> list, boolean[] selected, DialogInterface.OnClickListener listener) {
        showMultiChoiceDialog(context,title,listToArray(list),selected,listener);
    }

    /*
    用法：
    final String items3333[]={"张三","李四","王五"};
                DialogFactory.showListDialog(this, "标题", items3333, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("123", "onClick: "+ which);
                        T.showShortToast(MainActivity.this, "选中的是:" + items3333[which]);
                    }
                });
     */
    public static void showListDialog(Context context, String title, String[] items, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
//        builder.setNegativeButton("取消", listener);
//        builder.setPositiveButton("确定", listener);
        builder.setItems(items, listener);
        builder.show();
    }

    public static void showEditDialog(Context context, String title, final OnEditDialogClickListener listener) {
        final RxEditDialog rxDialogEditSureCancel = new RxEditDialog(context);//提示弹窗
        rxDialogEditSureCancel.setTitle(title);

        rxDialogEditSureCancel.show();

        rxDialogEditSureCancel.getSureView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rxDialogEditSureCancel.cancel();
                listener.confirm(rxDialogEditSureCancel.getEditText().getText().toString());;
            }
        });

        rxDialogEditSureCancel.getCancelView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rxDialogEditSureCancel.cancel();
            }
        });
    }

    public static void showActionSheet(Context context, String[] others, com.bigkoo.alertview.OnItemClickListener onItemClickListener) {
        showActionSheet(context,null,null,"取消",null,others,onItemClickListener);
    }


    /*
    用法：
    DialogFactory.showActionSheet(this, new String[]{"高亮按钮1"}, new String[]{"其他按钮1", "其他按钮2", "其他按钮3"}, new OnItemClickListener(){
                    @Override
                    public void onItemClick(Object o, int position) {
                        Toast.makeText(MainActivity.this, "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
                    }
                });
     */
    public static void showActionSheet(Context context, String[] destructive, String[] others, OnItemClickListener onItemClickListener) {
        showActionSheet(context,null,null,"取消",destructive,others,onItemClickListener);
    }

    /*
    用法：
    DialogFactory.showActionSheet(this,"上传头像", new String[]{"拍照", "从相册中选择"}, new OnItemClickListener(){
                    @Override
                    public void onItemClick(Object o, int position) {
                        Toast.makeText(MainActivity.this, "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
                    }
                });
     */
    public static void showActionSheet(Context context, String title, String[] others, OnItemClickListener onItemClickListener) {
        showActionSheet(context,title,null,"取消",null,others,onItemClickListener);
    }

    public static void showActionSheet(Context context, List<String> others, OnItemClickListener onItemClickListener) {
        showActionSheet(context,null,null,"取消",null,listToArray(others),onItemClickListener);
    }

    public static void showActionSheet(Context context, List<String> destructive,  List<String> others, OnItemClickListener onItemClickListener) {
        showActionSheet(context,null,null,"取消",listToArray(destructive),listToArray(others),onItemClickListener);
    }

    public static void showActionSheet(Context context, String title, List<String> others, OnItemClickListener onItemClickListener) {
        showActionSheet(context,title,null,"取消",null,listToArray(others),onItemClickListener);
    }

    /*
    用法
    DialogFactory.showActionSheet(this,"标题", "内容", "取消",null,null, new OnItemClickListener(){
                    @Override
                    public void onItemClick(Object o, int position) {
                        Toast.makeText(MainActivity.this, "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
                    }
                });
     */
    public static void showActionSheet(Context context, String title, String msg, String cancel, String[] destructive, String[] others, OnItemClickListener onItemClickListener) {
        new AlertView(title, msg, cancel, destructive, others, context, AlertView.Style.ActionSheet, onItemClickListener).setCancelable(true).show();
    }




    /**
     * 回调接口
     *
     * @author Administrator
     */
    public interface OnAlertViewClickListener {
        public abstract void confirm();

        public abstract void cancel();
    }

    /**
     * 回调接口
     *
     * @author Administrator
     */
    public interface OnEditDialogClickListener {
        public abstract void confirm(String content);
    }

    public static String[] listToArray(List<String> list){
        String[] array=new String[list.size()];
        for(int i=0;i<list.size();i++){
            array[i]=(String)list.get(i);
        }
        return array;
    }

}
