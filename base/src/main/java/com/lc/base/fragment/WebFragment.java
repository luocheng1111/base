package com.lc.base.fragment;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.lc.base.BaseHeadFragment;
import com.lc.base.R;


import butterknife.ButterKnife;

/**
 * User: Luocheng
 * Date: 2018/4/1
 * Time: 下午 4:30
 */

public class WebFragment extends BaseHeadFragment {

    ProgressBar progressBar;
    LinearLayout vError;
    WebView webView;

    private String url;
    private String title;

    protected boolean isError;

    private boolean isShowError;

    public static WebFragment newInstance(String url, String title) {
        WebFragment fragment = new WebFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.web_fragment, null, false);
        ButterKnife.bind(this, rootView);
        initData(getArguments());
        setView(rootView);
        return rootView;
    }


    public void initData(Bundle bundle) {
        if(bundle != null){
            url = bundle.getString("url");
            title = bundle.getString("title");
        }
    }


    public void setView(View view) {
        initHead(view, title, true);

        progressBar = view.findViewById(R.id.progressbar_);
        vError = view.findViewById(R.id.ll_error);
        webView = view.findViewById(R.id.webview_);

        progressBar.setMax(100);
        isShowError = addErrorView(vError);

        WebSettings ws = webView.getSettings();
        //是否允许脚本支持
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);

        ws.setJavaScriptCanOpenWindowsAutomatically(true);

//        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);

//        webView.setHorizontalScrollBarEnabled(false);

//        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                updateProgressBar(newProgress);
            }
        });
        webView.setWebViewClient(getWebViewClient());

        load(webView,url);
    }



    public WebViewClient getWebViewClient(){
        return new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                LogUtils.d("startUrl:" + url);
                isError = false;
                webView.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                LogUtils.d("url:" + url);

                return super.shouldOverrideUrlLoading(view,url);

            }


            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                updateProgressBar(100);
            }


            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
//                LogUtils.w("errorCode:" + errorCode + "|failingUrl:" + failingUrl);
                showReceiveError();
            }


            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.cancel();
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideReceiveError();
            }
        };
    }

    /**
     *
     * @param group
     * @return  true表示已添加ErrorView并显示ErrorView/false表示不处理
     */
    public boolean addErrorView(ViewGroup group){
        group.addView(LayoutInflater.from(getActivity()).inflate(R.layout.view_error,null));
        return true;
    }

    private void showReceiveError(){
        isError = true;
//        if(SystemUtils.isNetWorkActive(context)){
//            LogUtils.w("Page loading failed.");
//        }else{
//            LogUtils.w("Network unavailable.");
//        }

        if(isShowError){
            webView.setVisibility(View.GONE);
            vError.setVisibility(View.VISIBLE);

        }


    }

    private void hideReceiveError(){
        if(isError){
            showReceiveError();
        }else{
            webView.setVisibility(View.VISIBLE);
            vError.setVisibility(View.GONE);
        }

    }

    /**
     * 加载url
     * @param webView
     * @param url
     */
    private void load(WebView webView, String url){
//        LogUtils.d("url:" + url);
        if(!TextUtils.isEmpty(url)){
            webView.loadUrl(url);
        }

    }

    private boolean isGoBack(){
        return webView!=null && webView.canGoBack();
    }


    private void updateProgressBar(int progress){
        updateProgressBar(true,progress);
    }


    private void updateProgressBar(boolean isVisibility,int progress){
        progressBar.setVisibility((isVisibility && progress<100) ? View.VISIBLE : View.GONE);
        progressBar.setProgress(progress);
    }


}
