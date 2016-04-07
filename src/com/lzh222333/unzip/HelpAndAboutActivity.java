package com.lzh222333.unzip;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.webkit.*;
import android.content.*;
import android.view.View.*;
import android.content.pm.*;
import java.util.*;

public class HelpAndAboutActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
	WebView web;
	TextView titleTV;
	Intent intent;
	ImageButton backButton;
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setStatusBar();
        setContentView(R.layout.help_about_view);
		AdaptLayout();
		web = (WebView)super.findViewById(R.id.helpaboutviewwebview);
		titleTV = (TextView)findViewById(R.id.helpaboutviewTextViewTitleTV);
		backButton = (ImageButton)findViewById(R.id.helpaboutviewImageButtonBackButton);
		intent = this.getIntent();


		backButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					finish();
				}
			});

		web.getSettings().setLoadsImagesAutomatically(true);
		web.getSettings().setDomStorageEnabled(true);
		web.getSettings().setDatabaseEnabled(true);
		String databasePath = this.getApplicationContext()
			.getDir("database", Context.MODE_PRIVATE).getPath();
		web.getSettings().setDatabasePath(databasePath);
		web.getSettings().setAppCacheEnabled(true);
		String appCaceDir =this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
		web.getSettings().setAppCachePath(appCaceDir);
		web.getSettings().setJavaScriptEnabled(true);
		web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		web.getSettings().getJavaScriptEnabled();
		web.getSettings().getJavaScriptCanOpenWindowsAutomatically();
		/*

		 web.setWebViewClient(new WebViewClient(){
		 public void onPageFinished(WebView view,String url){

		 }

		 });
		 */
		web.setWebChromeClient(new WebChromeClient(){
				public boolean onJsAlert(WebView view, String url, String message, final JsResult result)
				{
//构建一个Builder来显示网页中的对话框
					AlertDialog.Builder builder = new AlertDialog.Builder(HelpAndAboutActivity.this);
					builder.setTitle("Alert");
					builder.setMessage(message);
					builder.setPositiveButton(android.R.string.ok,
						new AlertDialog.OnClickListener() {
							public void onClick(DialogInterface dialog, int which)
							{
								result.confirm();
							}
						});
					builder.setCancelable(false);
					builder.create();
					builder.show();
					return true;
				};
//处理javascript中的confirm
				public boolean onJsConfirm(WebView view, String url, String message, final JsResult result)
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(HelpAndAboutActivity.this);
					builder.setTitle("confirm");
					builder.setMessage(message);
					builder.setPositiveButton(android.R.string.ok,
						new AlertDialog.OnClickListener() {
							public void onClick(DialogInterface dialog, int which)
							{
								result.confirm();
							}
						});
					builder.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which)
							{
								result.cancel();
							}
						});
					builder.setCancelable(false);
					builder.create();
					builder.show();
					return true;
				};


			});
		//web.setWebViewClient(new WebViewClient(){});


		if (intent.getStringExtra("Type").equals("关于"))
		{
			web.loadUrl("file:///android_asset/" + SilenTool.getLanguageCode(HelpAndAboutActivity.this) + "/about.html");
			titleTV.setText(R.string.About);
		}
		else if (intent.getStringExtra("Type").equals("帮助"))
		{
			web.loadUrl("file:///android_asset/" + SilenTool.getLanguageCode(HelpAndAboutActivity.this) + "/help.html");
			titleTV.setText(R.string.Help);
		}
		else if (intent.getStringExtra("Type").equals("下载应用"))
		{
			web.loadUrl("file:///android_asset/" + SilenTool.getLanguageCode(HelpAndAboutActivity.this) + "/donate_download_app.html");
			titleTV.setText(R.string.donate_ad_text);
		}

    }

	private void AdaptLayout()
	{
		LinearLayout mainView=(LinearLayout)findViewById(R.id.helpaboutviewLinearLayoutAdapter);
		mainView.setPadding(0, SilenTool.getStatusHeight(HelpAndAboutActivity.this, false), 0, 0);

	}


	private void setStatusBar()
	{
		if (Build.VERSION.SDK_INT >= 21)
		{
			getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			getWindow().setStatusBarColor(0);
			return;
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
		{
            setTranslucentStatus(true);
        }

		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarAlpha(0);
		tintManager.setNavigationBarAlpha(0);
		tintManager.setTintAlpha(0);

	}

	private void setTranslucentStatus(boolean on)
	{
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on)
		{
			winParams.flags |= bits;
		}
		else
		{
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
}
