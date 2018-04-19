package com.lc.base.recycle.columnlistadapter;


import java.util.ArrayList;
import java.util.List;

//分栏Adapter对应的Bean
public class ColumnListBean {

    /**
     * style 对应值 显示的Item
     * 0 栏目名称
     * 1 左边标题 (默认)
     * 2 左边标题+左边详情
     * 3 左边标题+右边详情
     *
     * 4 左边标题+左边图标
     * 5 左边标题+左边图标+右边详情
     *
     * 6 左边详情 （左边详情与左边标题都区别在于 标题是纯黑色 详情则是浅色）
     * 7 左边详情+右边详情
     *
     * 11 左边标题               + 右边箭头
     * 21 左边标题+左边详情      + 右边箭头
     * 31 左边标题+右边详情      + 右边箭头
     *
     * 41 左边图标+左边标题      + 右边箭头
     * 51 左边图标+左边标题+右边详情   + 右边箭头
     */
    private int style = 1;

    private int leftIconId = -1;
    private String leftTitle;
    private String leftDes = "";



    public void setRightDes(String rightDes) {
        this.rightDes = rightDes;
    }

    private String rightDes = "";

    private boolean isFirst = false;

    public ColumnListBean(int style, String leftTitle) {
        this(style, -1, leftTitle, "","");
    }

    public ColumnListBean(int style, int leftIconId, String leftTitle) {
        this(style, leftIconId, leftTitle, "","");
    }

    public ColumnListBean(int style, int leftIconId, String leftTitle, String leftDes) {
        this(style, leftIconId, leftTitle, leftDes,"");
    }

    public ColumnListBean(int style, int leftIconId, String leftTitle, String leftDes, String rightDes) {
        this.style = style;
        this.leftIconId = leftIconId;
        this.leftTitle = leftTitle;
        this.leftDes = leftDes;
        this.rightDes = rightDes;
    }



    public static List<ColumnListBean> convertToOnewList(List<ColumnListBean>... args) {
        List<ColumnListBean> beanList = new ArrayList<>();
        for (List<ColumnListBean> list : args) {
            for (int i = 0; i < list.size(); i++) {
                ColumnListBean bean = list.get(i);
                if (i == 0) {
                    bean.setFirst(true);
                }
                beanList.add(bean);
            }
        }
        return beanList;
    }


    public int getLeftIconId() {
        return leftIconId;
    }

    public void setLeftIconId(int leftIconId) {
        this.leftIconId = leftIconId;
    }

    public String getLeftTitle() {
        return leftTitle;
    }

    public void setLeftTitle(String leftTitle) {
        this.leftTitle = leftTitle;
    }


    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }


    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public String getLeftDes() {
        return leftDes;
    }

    public void setLeftDes(String leftDes) {
        this.leftDes = leftDes;
    }

    public String getRightDes() {
        return rightDes;
    }

}