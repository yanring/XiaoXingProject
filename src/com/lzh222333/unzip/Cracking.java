package com.lzh222333.unzip;

import android.app.*;
import android.content.*;
import android.graphics.drawable.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.lzh222333.unzip.Crack.*;

public class Cracking extends Activity
{
	ImageView ProgressCircus;
	Button PauseAndResume;
	static TextView ProgressTV;
	TextView FilePath,FileName,CrackStatus,CrackWay;
	static ScrollView progressLayout;
	public static Cracking cracking;
	AnimationDrawable animD;
	SharedPreferences sp;
	long hour=0,min=0,sec=0;//小时，分，秒，破解成功调用dialog之前需要赋值
	long startT,endT;//始末时间戳
	public String key="测试";//密码，破解成功调用dialog时需要赋值
	public boolean isRun=true;//控制线程暂停
	public boolean isFinished=false;//如果值为true，退出所有线程
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crack_view);
		setStatusBar();
		findView();
		AdaptLayout();
		initWidget();
		setButtonListener();

		cracking = this;
		//打开破解成功界面
		//openDialog(true);
	}

	public static void print(String msg)
	{
		if (ProgressTV.getText().length() > 500)
		{
			ProgressTV.setText("清屏\n");
		}
		ProgressTV.append(msg + "\n");
		progressLayout.fullScroll(ScrollView.FOCUS_DOWN);
	}

	public void openDialog(boolean IsSuccess)
	{
		isFinished=true;
		if (IsSuccess)
		{
			Succeed s=new Succeed();
			s.setCancelable(false);
			s.show(getFragmentManager(), null);

		}
		else
		{
			Failed s=new Failed();
			s.setCancelable(false);
			s.show(getFragmentManager(), null);

		}
	}

	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		super.onBackPressed();
		
		//应等待用户确认退出
		
		isFinished=true;
		finish();
	}

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		Intent intent=new Intent(Cracking.this, com.lzh222333.unzip.Crack.CrackService.class);
        getApplicationContext().bindService(intent, conn, Context.BIND_AUTO_CREATE);
		print("debug:绑定服务");
		final Intent it = new Intent();
	    it.setAction("android.intent.action.BOOST_DOWNLOADING");
		it.putExtra("package_name", "com.android.contacts");
		it.putExtra("enabled", true);
		sendBroadcast(it);
	}

	@Override
	protected void onDestroy()
	{
		// TODO: Implement this method
		getApplicationContext().unbindService(conn);
		print("debug:解绑服务");
		final Intent it = new Intent();
		it.setAction("android.intent.action.BOOST_DOWNLOADING");
		it.putExtra("package_name", "com.android.contacts");
		it.putExtra("enabled", false);
		sendBroadcast(it);
		super.onDestroy();
	}


	private void ToastText(int textID)
	{
		Toast.makeText(Cracking.this, getResources().getString(textID), Toast.LENGTH_SHORT).show();
	}

	private CrackService.MyBinder binder;
	ServiceConnection conn=new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name)
		{
            // TODO Auto-generated method stub

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
		{
            // TODO Auto-generated method stub
            binder = (CrackService.MyBinder)service;
			print("debug:服务连接");
			startT = System.currentTimeMillis(); 
			binder.startCrack();
        }
    };

	private void initWidget()
	{
		sp = getSharedPreferences("FileCrackSave", MODE_PRIVATE);
		FileName.setText(sp.getString("file_name", null));
		FilePath.setText(sp.getString("file_path", null));
		animD = (AnimationDrawable)getResources().getDrawable(R.anim.pojie_wait_anim);
		ProgressCircus.setBackgroundDrawable(animD);
		animD.start();
	}
	private void findView()
	{
		ProgressCircus = (ImageView)findViewById(R.id.crackviewImageViewProgressCircus);
		PauseAndResume = (Button)super.findViewById(R.id.crackviewButtonPauseAndResume);
		ProgressTV = (TextView)findViewById(R.id.crackviewTextViewProgressTv);
		progressLayout = (ScrollView)findViewById(R.id.crackviewScrollViewProgressLayout);
		FileName = (TextView)findViewById(R.id.crackviewTextViewFileName);
		FilePath = (TextView)findViewById(R.id.crackviewTextViewFilePath);
		CrackStatus = (TextView)findViewById(R.id.crackviewTextViewCrackStatus);
		CrackWay = (TextView)findViewById(R.id.crackviewTextViewCrackUseWay);
	}

	private void setButtonListener()
	{
		PauseAndResume.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					if (isRun)
					{
						PauseAndResume.setText(R.string.cracking_resume);
						print("debug:暂停破解，请等待当前任务完成");
						isRun = false;
					}
					else
					{
						PauseAndResume.setText(R.string.cracking_pause);
						print("debug:恢复破解");
						isRun = true;
					}
				}
			});
	}


	private void AdaptLayout()
	{

		LinearLayout mainView=(LinearLayout)findViewById(R.id.crackviewLinearLayoutAdapter);
		mainView.setPadding(0, SilenTool.getStatusHeight(Cracking.this, false), 0, 0);

	}

	class Succeed extends DialogFragment
	{

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			super.onCreateDialog(savedInstanceState);

			ClipboardManager cmb = (ClipboardManager)getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
			cmb.setText(key);

			endT = System.currentTimeMillis(); 
			long time=(endT - startT) / 1000;//精确到一秒
			if (time >= 3600)
			{

				hour = time / 3600;
				min = (time % 3600) / 60;
				sec = time % 60;
			}
			else if (time < 3600 && time >= 60)
			{
				min = time / 60;
				sec = time % 60;
			}
			else
			{
				sec = time;
			}

			AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
			builder.setTitle(R.string.crack_result_success_dialog_title);
			builder.setMessage(String.format(getResources().getString(R.string.crack_result_success_dialog_message), new String[]{"" + hour,"" + min,"" + sec,key}));
			builder.setPositiveButton(R.string.crack_result_button_text_donate, new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						startActivity(new Intent(Cracking.this, donate.class));
						finish();
					}
				});
			builder.setNegativeButton(R.string.crack_result_button_text_close, new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						finish();
					}
				});

			return builder.create();
		}

	}

	class Failed extends DialogFragment
	{

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			super.onCreateDialog(savedInstanceState);
			AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
			builder.setTitle(R.string.crack_result_fail_dialog_title);
			builder.setMessage(R.string.crack_result_fail_dialog_message);
			builder.setPositiveButton(R.string.crack_result_button_text_about, new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						startActivity(new Intent(Cracking.this, HelpAndAboutActivity.class).putExtra("Type", "关于"));
						finish();
					}
				});
			builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						finish();
					}
				});
			return builder.create();
		}
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
