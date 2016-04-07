package com.lzh222333.unzip;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.view.View.*;
import com.lzh222333.unzip.Bmob.*;
import cn.bmob.v3.*;

public class feedback extends Activity
{
	ImageButton backButton;
	Button sendButton;
	EditText messageET,AttachWayET;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		
		setStatusBar();
		AdaptLayout();
		findView();
		setButtonListener();
		Bmob.initialize(this,"e0eab853a0c1241dcfe4230dbd0a28bb");
		BmobInstallation.getCurrentInstallation(this).save();
		
	}

	private void findView()
	{
		backButton=(ImageButton)findViewById(R.id.feedbackImageButtonBackButton);
		sendButton=(Button)findViewById(R.id.feedbackButtonSend);
		messageET=(EditText)findViewById(R.id.feedbackEditTextMessage);
		AttachWayET=(EditText)findViewById(R.id.feedbackEditTextAttachWay);
		
	}
	
	private void setButtonListener()
	{
		backButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					finish();
				}
			});
		sendButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					
					if(messageET.getText().toString().equals("")||AttachWayET.getText().toString().equals("")){
						ToastText(R.string.feedback_send_error_notInputAll);
					}else{
						sendButton.setText(R.string.feedback_sending);
						new FeedBack().save(feedback.this,messageET.getText().toString(),AttachWayET.getText().toString(),sendButton);
						
					}
					
				}
			});
	}
	private void AdaptLayout()
	{
		LinearLayout mainView=(LinearLayout)findViewById(R.id.feedbackLinearLayoutAdapter);
		mainView.setPadding(0, SilenTool.getStatusHeight(feedback.this,false), 0, 0);
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
	private void ToastText(int textID){
		Toast.makeText(feedback.this,getResources().getString(textID),Toast.LENGTH_SHORT).show();
	}
	public void finishSelf(){
		finish();
	}
}
