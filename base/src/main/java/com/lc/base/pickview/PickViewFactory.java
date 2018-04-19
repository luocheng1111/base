package com.lc.base.pickview;


import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lc.base.pickview.bean.CityPickViewBean;
import com.lc.base.pickview.bean.SpecialTimePickViewBean;
import com.lc.base.util.TimeUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
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
public class PickViewFactory {

    OnOptionsStringSelectListener onOptionsStringSelectListener;
    public interface OnOptionsStringSelectListener {
        void onOptionsSelect(String options1, String options2, String options3);
    }

    OnSpecialTimeSelectListener onSpecialTimeSelectListener;
    public interface OnSpecialTimeSelectListener {
        void onOptionsSelect(String options1, String option1des, String options2, String options3);
    }


    public void showTimePickView(Context context, OnTimeSelectListener listener) {
        showDateTimePickView(context, new boolean[]{false, false, false, true, true, true}, true, listener);
    }

    public void showTimePickView(Context context, boolean isShowHour, boolean isShowMinute, boolean isShowSecond, OnTimeSelectListener listener) {
        showDateTimePickView(context, new boolean[]{false, false, false, isShowHour, isShowMinute, isShowSecond}, true, listener);
    }

    public void showDatePickView(Context context, OnTimeSelectListener listener) {
        showDateTimePickView(context, new boolean[]{true, true, true, false, false, false}, true, listener);
    }

    public void showDatePickView(Context context, boolean isShowYear, boolean isShowMonth, boolean isShowDay, OnTimeSelectListener listener) {
        showDateTimePickView(context, new boolean[]{isShowYear, isShowMonth, isShowDay, false, false, false}, true, listener);
    }

    public void showDateTimePickView(Context context, OnTimeSelectListener listener) {
        showDateTimePickView(context, new boolean[]{true, true, true, true, true, true}, true, listener);
    }

    public void showDateTimePickView(Context context, boolean isShowYear, boolean isShowMonth, boolean isShowDay, boolean isShowHour, boolean isShowMinute, boolean isShowSecond, OnTimeSelectListener listener) {
        showDateTimePickView(context, new boolean[]{isShowYear, isShowMonth, isShowDay, isShowHour, isShowMinute, isShowSecond}, true, listener);
    }

    public void showDateTimePickView(Context context, boolean[] type, boolean isShowBottom, OnTimeSelectListener listener) {
        TimePickerView pvTime = new TimePickerBuilder(context, listener)
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
//                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .isDialog(true)
                .setType(type)
                .build();

        if(isShowBottom) setShowInBotton(pvTime);

        pvTime.show();
    }

    //设置TimePickerView显示在底部
    public void setShowInBotton(TimePickerView timePickerView){
        Dialog mDialog = timePickerView.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            timePickerView.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
    }


    //农历日期选择器
    public void showLunarCalendarPickView(Context context, OnTimeSelectListener listener) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2014, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2069, 2, 28);
        //时间选择器 ，自定义布局
        TimePickerView pvCustomLunar = new TimePickerBuilder(context, listener)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();
        pvCustomLunar.setLunarCalendar(true);
        pvCustomLunar.show();
    }


    private ArrayList<CityPickViewBean> optionsCity1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> optionsCity2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> optionsCity3Items = new ArrayList<>();
    //城市选择器
    public void showCityPickView(Context context, OnOptionsStringSelectListener listener) {
        this.onOptionsStringSelectListener = listener;
        initCityData(context);
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context,new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String options1Str = optionsCity1Items.get(options1).getPickerViewText();
                String options2Str = optionsCity2Items.get(options1).get(options2);
                String options3Str = optionsCity3Items.get(options1).get(options2).get(options3);
                onOptionsStringSelectListener.onOptionsSelect(options1Str, options2Str , options3Str);
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(optionsCity1Items, optionsCity2Items, optionsCity3Items);//三级选择器
        pvOptions.show();
    }


    //特殊时间选择器
    public void showSpecialTimePickView(Context context, int addMinute, OnSpecialTimeSelectListener listener) {
        this.onSpecialTimeSelectListener = listener;
        initSpecialTimeDate(addMinute);

        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String options1Str = optionsTime1Items.get(options1).getPickerViewText();
                String options1Des = optionsTime1Items.get(options1).getDescription();
                String options2Str = optionsTime2Items.get(options1).get(options2);
                String options3Str = optionsTime3Items.get(options1).get(options2).get(options3);
                onSpecialTimeSelectListener.onOptionsSelect(options1Str, options1Des, options2Str , options3Str);
            }
        })

                .setLabels(" ", "时", "分")
//                .setBackgroundId(0x00000000) //设置外部遮罩颜色
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
//                        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                    }
                })
                .build();

        pvOptions.setPicker(optionsTime1Items, optionsTime2Items,optionsTime3Items);//三级选择器*/
        pvOptions.show();
    }

    //单项选择器
    public void showOneOptionsPickView(Context context, ArrayList<String> options1Items, OnOptionsStringSelectListener listener) {
        showOptionsPickView(context, options1Items, null, null, listener);
    }

    //二级联动选择器
    public void showTwoOptionsPickView(Context context, ArrayList<String> options1Items, ArrayList<ArrayList<String>> options2Items, OnOptionsStringSelectListener listener) {
        showOptionsPickView(context, options1Items, options2Items, null, listener);
    }

    //三级联动选择器
    public void showThreeOptionsPickView(Context context, ArrayList<String> options1Items, ArrayList<ArrayList<String>> options2Items, ArrayList<ArrayList<ArrayList<String>>> options3Items, OnOptionsStringSelectListener listener) {
        showOptionsPickView(context, options1Items, options2Items, options3Items, listener);
    }

    //二级无联动选择器
    public void showTwoOptionsNoLinkPickView(Context context, ArrayList<String> options1Items, ArrayList<String> options2Items, OnOptionsStringSelectListener listener) {
        showOptionsNoLinkPickView(context, options1Items, options2Items, null, listener);
    }

    //三级无联动选择器
    public void showThreeOptionsNoLinkPickView(Context context, ArrayList<String> options1Items, ArrayList<String> options2Items, ArrayList<String> options3Items, OnOptionsStringSelectListener listener) {
        showOptionsNoLinkPickView(context, options1Items, options2Items, options3Items, listener);
    }

    //联动选择器
    public void showOptionsPickView(Context context, final ArrayList<String> options1Items, final ArrayList<ArrayList<String>> options2Items, final ArrayList<ArrayList<ArrayList<String>>> options3Items, OnOptionsStringSelectListener listener) {
        this.onOptionsStringSelectListener = listener;
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String options1Str = options1Items.get(options1);
                String options2Str = options2Items!=null ? options2Items.get(options1).get(options2) : "";
                String options3Str = options3Items!=null ? options3Items.get(options1).get(options2).get(options3) : "";
                onOptionsStringSelectListener.onOptionsSelect(options1Str, options2Str , options3Str);
            }
        }).build();

        if(options2Items==null && options3Items==null){
            pvOptions.setPicker(options1Items);//一级选择器*/
        }else if(options3Items==null){
            pvOptions.setPicker(options1Items, options2Items);//二级选择器
        }else{
            pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器*/
        }
        pvOptions.show();
    }

    //无联动选择器
    public void showOptionsNoLinkPickView(Context context, final ArrayList<String> options1Items, final ArrayList<String> options2Items, final ArrayList<String> options3Items, OnOptionsStringSelectListener listener) {
        this.onOptionsStringSelectListener = listener;
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                Log.i(TAG, "onOptionsSelect: "+options1+"-"+options2+"-"+options3);
                String options1Str = options1Items.get(options1);
                String options2Str = options2Items!=null ? options2Items.get(options2) : "";
                String options3Str = options3Items!=null ? options3Items.get(options3) : "";
                onOptionsStringSelectListener.onOptionsSelect(options1Str, options2Str , options3Str);
            }
        }).build();

        pvOptions.setNPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
    }



    private void initCityData(Context context) {//初始化城市数据
        String JsonData = getFileContent(context, "province.json");//获取assets目录下的json文件数据
        ArrayList<CityPickViewBean> jsonBean = new Gson().fromJson(JsonData, new TypeToken<ArrayList<CityPickViewBean>>(){}.getType());;//用Gson 转成实体

        optionsCity1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            //添加城市数据
            optionsCity2Items.add(CityList);
            //添加地区数据
            optionsCity3Items.add(Province_AreaList);
        }
    }


    public String getFileContent(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    String TAG = "cc";
    private ArrayList<SpecialTimePickViewBean> optionsTime1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> optionsTime2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> optionsTime3Items = new ArrayList<>();
    public void initSpecialTimeDate(int addMinute) { //初始特殊时间选择器的值
        Date curDate = TimeUtils.millis2Date(TimeUtils.getCurrentTime());
//        Log.i(TAG, "当前时间: " + TimeUtils.getSystemCurrentTime("yyyy-MM-dd HH:mm:ss"));
        Date afterDate = TimeUtils.addTime(curDate, addMinute);
//        Log.i(TAG, "添加后的时间: " + TimeUtils.date2String(afterDate));
//        Log.i(TAG, "是否是今天: " + TimeUtils.isToday(afterDate));
        if (TimeUtils.isToday(afterDate)) {
            optionsTime1Items.add(new SpecialTimePickViewBean(0, "今天", "描述部分", "其他数据"));
        }else {
            int days = addMinute/1440+1;
//            Log.i(TAG, "天数: " + days);
            for (int i = 0; i < days; i++) {
                if(i==0){
                    optionsTime1Items.add(new SpecialTimePickViewBean(0, "今天", TimeUtils.date2String(TimeUtils.addTime(curDate, 1440*i), "yyyy-MM-dd"), "其他数据"));
                }else if(i==1){
                    optionsTime1Items.add(new SpecialTimePickViewBean(0, "明天", TimeUtils.date2String(TimeUtils.addTime(curDate, 1440*i), "yyyy-MM-dd"), "其他数据"));
                }else if(i==2){
                    optionsTime1Items.add(new SpecialTimePickViewBean(0, "后天", TimeUtils.date2String(TimeUtils.addTime(curDate, 1440*i), "yyyy-MM-dd"), "其他数据"));
                }else {
                    String tempDate = TimeUtils.date2String(TimeUtils.addTime(curDate, 1440*i), "MM-dd");
                    optionsTime1Items.add(new SpecialTimePickViewBean(0, tempDate, TimeUtils.date2String(TimeUtils.addTime(curDate, 1440*i), "yyyy-MM-dd"), "其他数据"));
                }
            }
        }
        String curHours = TimeUtils.date2String(curDate, "HH");
        String afterHours = TimeUtils.date2String(afterDate, "HH");
        int startHH = curHours.startsWith("0") ? Integer.parseInt(curHours.substring(1)) : Integer.parseInt(curHours);
        int endHH = afterHours.startsWith("0") ? Integer.parseInt(afterHours.substring(1)) : Integer.parseInt(afterHours);
//        Log.i(TAG, "小时: " + options1Items.size());
//        Log.i(TAG, "小时: " + startHH + "-" + endHH);
        for (int i = 0; i < optionsTime1Items.size(); i++) {
            int start = 0;
            int max = 23;
            if (i == 0) {
                start = startHH;
            } else if (i == optionsTime1Items.size() - 1) {
                max = endHH;
            }
//            Log.i(TAG, "小时1: " +start + "-" + max);
            ArrayList<String> options2Items_01 = new ArrayList<>();
            for (int j = start; j <= max; j++) {
                if(j<10){
                    options2Items_01.add(j + "");
                }else {
                    options2Items_01.add(j + "");
                }
//                Log.i(TAG, "小时2: " +j);

            }
            optionsTime2Items.add(options2Items_01);
        }

        String curMinute = TimeUtils.date2String(curDate, "mm");
        String afterMinute = TimeUtils.date2String(afterDate, "mm");
        int startMinute = curMinute.startsWith("0") ? Integer.parseInt(curMinute.substring(1)) : Integer.parseInt(curMinute);
        int endMinute = afterMinute.startsWith("0") ? Integer.parseInt(afterMinute.substring(1)) : Integer.parseInt(afterMinute);
        for (int i = 0; i < optionsTime2Items.size(); i++) {
            ArrayList<String> options2Items_01 = optionsTime2Items.get(i);
            ArrayList<ArrayList<String>> options22Items = new ArrayList<>();
            for (int k = 0; k < options2Items_01.size(); k++) {
                int start = 0;
                int max = 59;
                if (i == 0 && k == 0) {
                    start = startMinute;
                } else if (i == optionsTime2Items.size()-1 && k == options2Items_01.size() - 1) {
                    max = endMinute;
                }
                ArrayList<String> str = new ArrayList<>();
                for (int j = start; j <= max; j++) {
                    if(j<10){
                        str.add("0"+j);
                    }else {
                        str.add(j + "");
                    }

                }
                options22Items.add(str);
            }
            optionsTime3Items.add(options22Items);
        }
    }








}
