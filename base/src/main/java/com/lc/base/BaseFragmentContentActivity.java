package com.lc.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;


/**
 * @author Luocheng
 * @since 2017/2/20
 */

public abstract class BaseFragmentContentActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmentcontent_activity);

        //初始化Slidr 让其拖动左边有结束当前界面的效果
        initSlidr();

        switchFragment(getIntent());
    }

    //初始化Slidr 让其拖动左边有结束当前界面的效果
    private void initSlidr() {
        SlidrConfig config=new SlidrConfig.Builder()
                .position(SlidrPosition.LEFT)//滑动起始方向
                .edge(true)
                .edgeSize(0.18f)//距离左边界占屏幕大小的18%
                .build();
        Slidr.attach(this,config);
    }

    protected void replaceFragment(Fragment fragmnet){
        replaceFragment(R.id.fl_,fragmnet);
    }

    protected void replaceFragment(@IdRes int id, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(id, fragment).commit();
    }

    protected abstract void switchFragment(Intent intent);



    //Frgament中软键盘的处理,点击EditText外的地方，软键盘自动隐藏------------------------------------------------------------------------------------
    private OnHideKeyboardListener onHideKeyboardListener;
    public interface OnHideKeyboardListener{
        public boolean hideKeyboard();
    }

    public void setOnHideKeyboardListener(OnHideKeyboardListener onHideKeyboardListener){
        this.onHideKeyboardListener = onHideKeyboardListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                onHideKeyboardListener.hideKeyboard();
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
    //---------------------------------------------------------------------------------------------------------
}
