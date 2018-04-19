package com.lc.base;


import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public abstract class BaseHeadFragment extends BaseFragment {

    ImageView ivLeft;
    TextView tvLeft;
    TextView tvTitle;
    ImageView ivTitle;
    ImageView ivRight;
    TextView tvRight;
    Toolbar toolbar;


    private void initHeadView(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        ivLeft = view.findViewById(R.id.iv_head_left);
        tvLeft = view.findViewById(R.id.tv_head_left);
        tvTitle = view.findViewById(R.id.tv_head_title);
        ivTitle = view.findViewById(R.id.iv_head_title);
        ivRight = view.findViewById(R.id.iv_head_right);
        tvRight = view.findViewById(R.id.tv_head_right);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.head_back_sel);

        //设置ToolBar的标题不显示
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        //返回按钮的监听
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }


    public void initHead(View view, String title, boolean isShowBack) {
        initHeadView(view);

        setTitle(title);
        setBackBtnShown(isShowBack);
    }


    public void initHead(View view, String title, int leftImgResourceId, int rightImgResourceId) {
        initHeadView(view);

        setTitle(title);
        setLeftImgId(leftImgResourceId);
        setRightImgId(rightImgResourceId);
    }

    public void initHead(View view, int titleResourceId, int leftImgResourceId, int rightImgResourceId) {
        initHeadView(view);

        setImgTitleResourceId(titleResourceId);
        setLeftImgId(leftImgResourceId);
        setRightImgId(rightImgResourceId);
    }


    public void initHead(View view, String title, int leftImgResourceId, String rightText) {
        initHeadView(view);

        setTitle(title);
        setLeftImgId(leftImgResourceId);
        setRightText(rightText);
    }

    public void initHead(View view, String title, boolean isShowBack, int rightImgResourceId) {
        initHeadView(view);

        setTitle(title);
        setBackBtnShown(isShowBack);
        setRightImgId(rightImgResourceId);
    }

    public void initHead(View view, String title, boolean isShowBack, String rightText) {
        initHeadView(view);

        setTitle(title);
        setBackBtnShown(isShowBack);
        setRightText(rightText);
    }

    public void setLeftImgId(int resourceId) {
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setImageResource(resourceId);
    }

    public void setLeftText(String text) {
        tvLeft.setVisibility(View.VISIBLE);
        tvLeft.setText(text);
    }

    public void setRightImgId(int resourceId) {
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(resourceId);
    }

    public void setRightText(String rightText) {
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(rightText);
    }


    public void setBackBtnShown(boolean isShow) {
        if (isShow) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }


    public void setBackBtnImg(int resourceId) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(resourceId);
    }

    public void setTitle(String title) {
        tvTitle.setVisibility(View.VISIBLE);
        ivTitle.setVisibility(View.GONE);
        tvTitle.setText(title);
    }

    public void setImgTitleResourceId(int resourceId) {
        tvTitle.setVisibility(View.GONE);
        ivTitle.setVisibility(View.VISIBLE);
        ivTitle.setImageResource(resourceId);
    }

    public void setHeadElevation(float elevation) {
        if (Build.VERSION.SDK_INT >= 21) {
            toolbar.setElevation(elevation);
        }
    }

    public ImageView getIvLeft() {
        return ivLeft;
    }

    public TextView getTvLeft() {
        return tvLeft;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public ImageView getIvTitle() {
        return ivTitle;
    }

    public ImageView getIvRight() {
        return ivRight;
    }

    public TextView getTvRight() {
        return tvRight;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
