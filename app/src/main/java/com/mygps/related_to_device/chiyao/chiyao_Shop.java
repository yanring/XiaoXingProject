package com.mygps.related_to_device.chiyao;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mygps.R;

/**
 * Created by Yanring on 2016/4/9.
 */
public class chiyao_Shop extends AppCompatActivity {
    WebView web;

    String removeHeaderJSAtFirst="javascript:window.onload=function(){var header=document.getElementById(\"header\");var parent=header.parentNode;parent.removeChild(header);parent.removeChild(document.getElementById(\"scrollimg\"))}";
    String removeHeaderJSAtBack="javascript:var header=document.getElementById(\"header\");var parent=header.parentNode;parent.removeChild(header);parent.removeChild(document.getElementById(\"scrollimg\"))";
    String removePageModeJSAtFirst="javascript:window.onload=function(){var linkss=document.getElementsByClassName(\"linkss\")[0];var parent=linkss.parentNode;parent.removeChild(linkss)}";
    String removePageModeJSAtBack="javascript:var linkss=document.getElementsByClassName(\"linkss\")[0];var parent=linkss.parentNode;parent.removeChild(linkss)";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_webview);
        init();

    }

    private void init() {
        web = (WebView) findViewById(R.id.webview);

        //webview初始化
        web.getSettings().setLoadsImagesAutomatically(true);
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setDatabaseEnabled(true);
        String databasePath = this.getApplicationContext()
                .getDir("database", Context.MODE_PRIVATE).getPath();
        web.getSettings().setDatabasePath(databasePath);
        web.getSettings().setAppCacheEnabled(true);
        String appCacheDir = this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        web.getSettings().setAppCachePath(appCacheDir);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        web.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d("webview","onPageStarted");

                web.loadUrl(removeHeaderJSAtFirst);
                web.loadUrl(removePageModeJSAtFirst);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("webview","onPageFinished");

                web.loadUrl(removeHeaderJSAtBack);
                web.loadUrl(removePageModeJSAtBack);
            }
        });

        web.setWebChromeClient(new WebChromeClient() {

            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                //构建一个Builder来显示网页中的对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(chiyao_Shop.this);
                builder.setTitle("Alert");
                builder.setMessage(message);
                builder.setPositiveButton(android.R.string.ok,
                        new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        });
                builder.setCancelable(false);
                builder.create();
                builder.show();
                return true;
            }

            //处理javascript中的confirm
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(chiyao_Shop.this);
                builder.setTitle("confirm");
                builder.setMessage(message);
                builder.setPositiveButton(android.R.string.ok,
                        new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        });
                builder.setCancelable(false);
                builder.create();
                builder.show();
                return true;
            }
        });

        web.loadUrl("http://m.jianke.com/?YD&TongYC&hmsrsogouhmmdydsshmkwzaixianyaofang");
  //      web.loadUrl(t);


    }

    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
        if (web.canGoBack()){
            web.goBack();
        }else{
            finish();
        }
    }
}

