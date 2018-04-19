package com.lc.base.pickview;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.lc.base.util.TimeUtils;

import java.util.Date;

/**
 * name：luocheng
 * date：2018/01/16
 * E-mail：1163011680@qq.com
 * 说明：此类是在Android-PickerView(https://github.com/Bigkoo/Android-PickerView)的基础上进行了封装，使得一行代码调用就可以实现我们想要的效果
 *
 * 此类的调用方式和效果展示图见 https://github.com/luocc/pickviewfactory.git
 *
 */
public class PickViewFactoryHelper {

    public static void showSpecialTimePickView(Context context, final TextView showTextView, int addMinute) {
        new PickViewFactory().showSpecialTimePickView(context, addMinute, new PickViewFactory.OnSpecialTimeSelectListener() {
            @Override
            public void onOptionsSelect(String options1, String option1des, String options2, String options3) {
                String tx = options1 +"-" + option1des+"-"+options2 +"-" +options3;
                showTextView.setText(tx);
            }
        });
    }



    public static void showDateTimePicker(Context context, final TextView showTextView) {
//        if (timeFormat == DateTimePickerType.Date) { //2017.16.62
            new PickViewFactory().showTimePickView(context, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    showTextView.setText(TimeUtils.date2String(date));
                }
            });
//        } else if (timeFormat == DateTimePickerType.Time) {
//            new PickViewFactory().showDatePickView(context, new OnTimeSelectListener() {
//                @Override
//                public void onTimeSelect(Date date, View v) {
//                    showTextView.setText(TimeUtils.date2String(date));
//                }
//            });
//        } else if (timeFormat == DateTimePickerType.DateTime) {
//            new PickViewFactory().showDateTimePickView(context, new OnTimeSelectListener() {
//                @Override
//                public void onTimeSelect(Date date, View v) {
//                    showTextView.setText(TimeUtils.date2String(date));
//                }
//            });
//        }


    }





}
