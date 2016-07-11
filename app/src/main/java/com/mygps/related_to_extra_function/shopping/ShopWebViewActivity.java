package com.mygps.related_to_extra_function.shopping;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.mygps.R;

/**
 * Created by Yanring on 2016/4/9.
 */
public class ShopWebViewActivity extends AppCompatActivity {
    WebView web;

    String removeHeaderJS="javascript:var header=document.getElementById(\"header\");alert(\"cdsj\");header.parentNode().removeChild(header);";
    String t="javascript:alert(\"cdj\");";

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
            public void onPageFinished(WebView view, String url) {
                web.loadUrl(t);
                Log.i("webviewPageFinished","==============");
               // super.onPageFinished(view, url);

            }
        });

        web.setWebChromeClient(new WebChromeClient() {

            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                //构建一个Builder来显示网页中的对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(ShopWebViewActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(ShopWebViewActivity.this);
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

        web.setWebViewClient(new WebViewClient());
        //WebView加载web资源
        web.loadUrl("http://www.jianianle.com/");
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

