package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


public class FeePayment extends Fragment {
    ProgressBar progressBar;
    SwipeRefreshLayout mySwipeRefreshLayout;
    WebView webView;
    String url = "https://vignan.ac.in/tutionfee.php";
    public FeePayment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_fee_payment,container,false);

        SwipeRefreshLayout mySwipeRefreshLayout = v.findViewById(R.id.refreshLayout);
        webView = (WebView)v.findViewById(R.id.webview);
        progressBar = v.findViewById(R.id.progressbar);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //To open hyperlink in existing WebView
                view.loadUrl(request.getUrl().toString());
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(0);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view,url);

                view.evaluateJavascript("document.getElementsByClassName('col-lg-4 col-md-4 col-sm-6 col-xs-12 col-lg-push-0 col-md-push-0 col-sm-push-3 col-xs-push-0 sidebar blog-left')[0].style.display='none'; "
                                    +"document.getElementById('page-title').style.display='none';"+"document.getElementsByClassName('col-lg-3 col-md-4 col-lg-offset-0 col-md-offset-4 logo')[0].style.display='none'; "
                                    + "document.getElementsByClassName('col-lg-9 col-md-12 col-lg-pull-0 col-md-pull-1 mainmenu-container')[0].style.display='none'; "+
                                    "document.getElementById('sideslider').style.display='none';"
                                    + "document.getElementsByClassName('col-lg-12 col-md-12')[0].style.display='none'; "
                                    +"document.getElementsByClassName('col-lg-4 col-md-4 col-sm-8 col-xs-6 widget')[0].style.display='none'; "
                                    +"document.getElementsByClassName('popular-post')[0].style.display='none'; "
                                    +"document.getElementsByClassName('col-lg-4 col-md-4 col-sm-12 col-xs-12')[0].style.display='none'; "
                                    +"document.getElementsByClassName('engt-launcher-button engt-launcher-active')[0].style.display='none'; "
                                    + "document.getElementsByClassName('engt-popup-text engt-popup-text-right')[0].style.display='none'; "+
                                    "document.getElementById('gb-widget-2660').style.display='none';"+
                                    "document.getElementById('engt').style.display='none';"
                                    + "document.getElementsByClassName('engt-auto-popup-container engt-auto-popup-container-right engt-hide-element')[0].style.display='none'; "
                                    + "document.getElementsByClassName('q8c6tt-0 jmDuqF')[0].style.display='none'; "
                                    + "document.getElementsByClassName('credit pull-right')[0].style.display='none'; "

                        , new ValueCallback<String>() {
                    //"document.getElementsByClassName('wrapper row2')[0].style.display='none'; "+
                    //                                "document.getElementsByClassName('wrapper row3')[0].style.display='none'; "
                    //"document.getElementById('container').style.display='none';"+
                    @Override
                    public void onReceiveValue(String s) {
                        Log.e("MSG",s);
                    }
                });

                progressBar.setVisibility(View.GONE);
                mySwipeRefreshLayout.setRefreshing(false);
            }

        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView webView, int newProgress) {
                progressBar.setProgress(newProgress);
            }
        });
        //setting other settings
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setDomStorageEnabled(true);
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageFinished(WebView view, String url)
//            {
//                webView.loadUrl("javascript:(function() { " +
//                        "document.getElementByClass('container')[0].style.display='none'; " +
//                        "})()");
//            }
//        });

        webView.loadUrl(url);

        //setting swiperefreshlistener
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
            }
        });
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    WebView webView = (WebView) v;

                    switch(keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webView.canGoBack()) {
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }

                return false;
            }
        });

        return v;
    }


}