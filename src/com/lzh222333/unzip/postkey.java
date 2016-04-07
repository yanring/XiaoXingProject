package com.lzh222333.unzip;
import android.app.*;
import android.widget.*;
import android.os.*;
import android.view.View.*;
import android.view.*;
import com.lzh222333.unzip.Bmob.*;
import cn.bmob.v3.listener.*;


public class postkey extends Activity
{//改动过
	ImageButton backButton;
	Button sendButton;
	EditText messageET;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.postkey);

		setStatusBar();
		AdaptLayout();
		findView();
		setButtonListener();
	}

	private void findView()
	{
		backButton=(ImageButton)findViewById(R.id.postkeyImageButtonBackButton);
		sendButton=(Button)findViewById(R.id.postkeyButtonSend);
		messageET=(EditText)findViewById(R.id.postkeyEditTextMessage);
		
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

					if(messageET.getText().toString().equals("")){
						ToastText(R.string.postkey_send_error_notInput);
					}else{
						sendButton.setText(R.string.postkey_posting);
						Backups backups=new Backups(postkey.this);
						if(backups.queryAndSave(messageET.getText().toString()))
							sendButton.setText(R.string.feedback_send_success_button);
						else
							sendButton.setText(R.string.feedback_send_faith);
						new Dictionary(postkey.this).queryAndSave(postkey.this,messageET.getText().toString());
						backups.save(postkey.this, new SaveListener(){

								@Override
								public void onSuccess()
								{
									sendButton.setText(R.string.feedback_send_success_button);
									//Toast.makeText(context,context.getResources().getString(com.wocao.ysmmpjds.R.string.feedback_send_success_toast),Toast.LENGTH_SHORT).show();
									
								}

								@Override
								public void onFailure(int p1, String p2)
								{
									sendButton.setText(R.string.feedback_send_faith);
								}
							});
					}

				}
			});
	}
	private void AdaptLayout()
	{
		LinearLayout mainView=(LinearLayout)findViewById(R.id.postkeyLinearLayoutAdapter);
		mainView.setPadding(0, SilenTool.getStatusHeight(postkey.this,false), 0, 0);
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
		Toast.makeText(postkey.this,getResources().getString(textID),Toast.LENGTH_SHORT).show();
	}
	}
	


