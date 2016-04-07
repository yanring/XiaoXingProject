package com.lzh222333.unzip;
import android.app.*;
import android.widget.*;
import android.os.*;
import android.view.View.*;
import android.view.*;
import android.util.*;
import android.widget.RelativeLayout.*;
import android.content.*;


public class donate extends Activity
{
	ImageButton backButton;
	ImageButton donate_ad,donate_alipay,donate_person_support;
	Button sendButton;
	EditText messageET,AttachWayET;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.donate);

		setStatusBar();
		findView();
		AdaptLayout();

		setButtonListener();
	}

	private void findView()
	{
		backButton = (ImageButton)findViewById(R.id.donateImageButtonBackButton);
		donate_ad = (ImageButton)findViewById(R.id.donateImageButtonAD);
		donate_alipay = (ImageButton)findViewById(R.id.donateImageButtoAliPay);
		donate_person_support = (ImageButton)findViewById(R.id.donateImageButtonPersonD);

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
		donate_person_support.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					new personSupport().show(getFragmentManager(),null);
				}
			});
		donate_ad.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					startActivity(new Intent(donate.this,HelpAndAboutActivity.class).putExtra("Type","下载应用"));
					finish();
				}
			});
		donate_alipay.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					ClipboardManager cmb = (ClipboardManager)getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
					cmb.setText(getResources().getString(R.string.donate_alipay_count));
					new AliPaySupport().show(getFragmentManager(),null);
				}
			});

	}

	class personSupport extends DialogFragment
	{

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			super.onCreateDialog(savedInstanceState);
			AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
			builder.setTitle(R.string.donate_personD_text);
			builder.setMessage(R.string.donate_personD_DialogMasseage);
			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface p1, int p2)
					{

					}
				});
			return builder.create();
		}

	}

	class AliPaySupport extends DialogFragment
	{

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			super.onCreateDialog(savedInstanceState);
			AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
			builder.setTitle(R.string.donate_alipay_text);
			builder.setMessage(R.string.donate_alipay_dialogTV);
			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface p1, int p2)
					{

					}
				});
			return builder.create();
		}

	}
	
	
	private void AdaptLayout()
	{
		LinearLayout mainView=(LinearLayout)findViewById(R.id.donateLinearLayoutAdapter);
		mainView.setPadding(0, SilenTool.getStatusHeight(donate.this, false), 0, 0);
		DisplayMetrics dm = new DisplayMetrics();
        //获取窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //窗口宽度
        int Width = (int)((dm.widthPixels - 20) / 3);
		Width = Width - 20;
		donate_ad.setLayoutParams(new LinearLayout.LayoutParams(Width, Width));
		donate_alipay.setLayoutParams(new LinearLayout.LayoutParams(Width, Width));
		donate_person_support.setLayoutParams(new LinearLayout.LayoutParams(Width, Width));

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
	private void ToastText(int textID)
	{
		Toast.makeText(donate.this, getResources().getString(textID), Toast.LENGTH_SHORT).show();
	}

}
