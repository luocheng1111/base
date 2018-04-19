package com.lc.base;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.lc.base.dialog.DialogFactory;
import com.lc.base.util.KeyboardUtils;
import com.lc.base.util.LogUtils;
import com.lc.base.util.ToastUtils;

import butterknife.ButterKnife;


/**
 * @author Luocheng
 *
   1.不建议在Base里封装流程，仅仅是省几行代码，会使得程序变复杂，将来在看代码或者给别人看时更难懂
   2.建议Base只是封装一些常用的或者通用的功能
 */
public abstract class BaseFragment extends Fragment {


	private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

	protected View rootView;
	Dialog dialog;


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		LogUtils.d("onCreate: ");
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			if (isSupportHidden) {
				ft.hide(this);
			} else {
				ft.show(this);
			}
			ft.commitAllowingStateLoss();
		}
	}


	public void showLoadingDialog(){
		if(dialog == null){
			dialog = DialogFactory.createLoadingDialog(getActivity());
		}

		if(!dialog.isShowing()){
			dialog.show();
		}
	}

	public void dimissLoadingDialog(){
		if(dialog == null){
			return;
		}
		if(dialog.isShowing()){
			dialog.dismiss();
		}
	}

	public void finishDelayed(long time){
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				getActivity().finish();
			}
		}, time);
	}



	protected void startActivity(Class<?> cls){
		startActivity(new Intent(getActivity(),cls));
	}

	protected void startActivityFinish(Class<?> cls){
		startActivity(cls);
		getActivity().finish();
	}


	//-----------------------------------


	public void replaceChildFragment(@IdRes int resId, Fragment fragment) {
		replaceFragment(getChildFragmentManager(),resId,fragment,false);
	}

	public void replaceFragment(@IdRes int resId, Fragment fragment){
		replaceFragment(resId,fragment,false);
	}

	public void replaceFragment(@IdRes int resId, Fragment fragment, boolean isBackStack) {
		replaceFragment(getFragmentManager(),resId,fragment,isBackStack);
	}

	public void replaceFragment(FragmentManager fragmentManager, @IdRes int resId, Fragment fragment, boolean isBackStack) {
		FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
		fragmentTransaction.replace(resId, fragment);
		if(isBackStack){
			fragmentTransaction.addToBackStack(null);
		}
		fragmentTransaction.commit();
	}

	//-----------------------------------


	protected void showToast(@StringRes  int resId){
		ToastUtils.showShort(resId);
	}

	protected void showToast(CharSequence text){
		ToastUtils.showShort(text);
	}

	protected void showLongToast(@StringRes  int resId){
		ToastUtils.showLong(resId);
	}

	protected void showLongToast(CharSequence text){
		ToastUtils.showLong(text);
	}



	public void startShake(View v, @StringRes int resId){
		startShake(v);
		showToast(resId);
	}

	public void startShake(View view){
		view.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.shake));
	}


	/**
	 * 隐藏软键盘
	 *
	 * @param v
	 */
	public void hideInputMethod(final EditText v) {
		KeyboardUtils.hideSoftInput(v);
	}

	/**
	 * 显示软键盘
	 *
	 * @param v
	 */
	public void showInputMethod(final EditText v) {
		KeyboardUtils.showSoftInput(v);;
	}

	//-----------------------------------

//	public Dialog getProgressDialog() {
//		return progressDialog;
//	}
//
//	public Dialog getDialog() {
//		return dialog;
//	}
//
//	protected void dismissProgressDialog(){
//		dismissDialog(progressDialog);
//	}
//
//	protected void dismissDialog(){
//		dismissDialog(dialog);
//	}
//
//	protected void dismissDialog(Dialog dialog){
//		if(dialog != null && dialog.isShowing()){
//			dialog.dismiss();
//		}
//	}
//
//	protected void dismissDialogFragment(DialogFragment dialogFragment){
//		if(dialogFragment != null){
//			dialogFragment.dismiss();
//		}
//	}
//
//	protected void showProgressDialog(){
//		showProgressDialog(new ProgressBar(context));
//	}
//
//	protected void showProgressDialog(@LayoutRes int resId){
//		showProgressDialog(LayoutInflater.from(context).inflate(resId,null));
//	}
//
//	protected void showProgressDialog(View v){
//		dismissProgressDialog();
//		progressDialog =  BaseProgressDialog.newInstance(context);
//		progressDialog.setContentView(v);
//		progressDialog.show();
//	}
//
//
//	public void showDialogFragment(DialogFragment dialogFragment){
//		String tag = dialogFragment.getTag() !=null ? dialogFragment.getTag() : dialogFragment.getClass().getSimpleName();
//		showDialogFragment(dialogFragment,tag);
//	}
//
//	public void showDialogFragment(DialogFragment dialogFragment, String tag) {
//		dialogFragment.show(getFragmentManager(),tag);
//	}
//
//	protected void showDialog(View contentView){
//		showDialog(context,contentView);
//	}
//
//	protected void showDialog(Context context, View contentView){
//		dismissDialog();
//		dialog = new Dialog(context,R.style.dialog);
//		dialog.setContentView(contentView);
//		dialog.setCanceledOnTouchOutside(false);
//		getDialogWindow(dialog);
//		dialog.show();
//
//	}
//
//	protected void getDialogWindow(Dialog dialog){
//		Window window = dialog.getWindow();
//		WindowManager.LayoutParams lp = window.getAttributes();
//		lp.width = (int)(getWidthPixels()*0.9f);
//		window.setAttributes(lp);
//	}

	//-----------------------------------


	//-----------------------------------

	protected DisplayMetrics getDisplayMetrics(){
		return getResources().getDisplayMetrics();
	}

	protected int getWidthPixels(){
		return getDisplayMetrics().widthPixels;
	}

	protected int getHeightPixels(){
		return getDisplayMetrics().heightPixels;
	}

	//-----------------------------------


}