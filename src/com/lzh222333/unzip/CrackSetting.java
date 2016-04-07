package com.lzh222333.unzip;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.view.View.*;
import android.widget.CompoundButton.*;
import android.content.*;


/**
*开始破解代码在88行处
*
*/
public class CrackSetting extends Activity
{
	private ImageButton backButton;
	CheckBox useDict,useFlashyC,useViolence;
	CheckBox UperCaseCB,LowerCaseCB,NumberCB,SymbolCB;
	EditText MinLengthET,MaxLengthET,PrefixET,SuffixET;
	LinearLayout ViolenceWidgetGroup;
	Button startBT;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crack_setting);
		
		setStatusBar();
		findView();
		initwidget();
		AdaptLayout();
		setlistener();
	}

	private void initwidget()
	{
		useViolence.setChecked(false);
		useDict.setChecked(true);
		useFlashyC.setChecked(true);
		NumberCB.setChecked(true);
		SymbolCB.setChecked(true);
		UperCaseCB.setChecked(true);
		LowerCaseCB.setChecked(true);
		ViolenceWidgetGroup.setVisibility(View.GONE);
	}

	private void setlistener()
	{
		backButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					finish();
				}
			});
		useViolence.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton p1, boolean p2)
				{
					if (p2)
					{
						ViolenceWidgetGroup.setVisibility(View.VISIBLE);
					}
					else
					{
						ViolenceWidgetGroup.setVisibility(View.GONE);
					}
				}
			});
		startBT.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					if (!(useFlashyC.isChecked() || useDict.isChecked() || useViolence.isChecked()))
					{
						ToastText(R.string.cracksetting_startbutton_tips_wayNotChecked);
					}
					else if (useViolence.isChecked() && !(UperCaseCB.isChecked() || LowerCaseCB.isChecked() || NumberCB.isChecked() || SymbolCB.isChecked()))
					{
						ToastText(R.string.cracksetting_startbutton_tips_stringGroupNotChecked);
					}
					else
					{
						Toast.makeText(CrackSetting.this,"测试",Toast.LENGTH_SHORT).show();
						startActivity(new Intent(CrackSetting.this,Cracking.class));
						/*
						*是否使用秒破解:useFlashyC.isChecked();
						 *是否使用云字典:useDict.isChecked();
						 *是否使用暴力破解:useViolence.isChecked()
						 *密码位数:最小MinLengthET.getText().toString();最大为MaxLengthET;
						 *前后缀:PrefixET,SuffixET.都为EditText
						 *字符集组为CheckBox,数学numberCB,符号SymbolCB,大写字母UperCaseCB,LowerCaseCB;
						*/
						finish();
					}
				}
			});
	}

	private void findView()
	{
		backButton = (ImageButton)findViewById(R.id.cracksettingImageButtonBackButton);
		useDict = (CheckBox)findViewById(R.id.cracksettingCheckBoxUseDictoryCrack);
		useFlashyC = (CheckBox)findViewById(R.id.cracksettingCheckBoxUseFlashyCrack);
		useViolence = (CheckBox)findViewById(R.id.cracksettingCheckBoxUseViolenceCrack);
		UperCaseCB = (CheckBox)findViewById(R.id.cracksettingCheckBoxUperCase);
		LowerCaseCB = (CheckBox)findViewById(R.id.cracksettingCheckBoxLowerCase);
		NumberCB = (CheckBox)findViewById(R.id.cracksettingCheckBoxNumber);
		SymbolCB = (CheckBox)findViewById(R.id.cracksettingCheckBoxSymbol);
		SuffixET = (EditText)findViewById(R.id.cracksettingEditTextSuffix);
		PrefixET = (EditText)findViewById(R.id.cracksettingEditTextPrefix);
		MaxLengthET = (EditText)findViewById(R.id.cracksettingEditTextMaxLength);
		MinLengthET = (EditText)findViewById(R.id.cracksettingEditTextMinLength);
		ViolenceWidgetGroup = (LinearLayout)findViewById(R.id.cracksettingLinearLayoutViolenceGroup);
		startBT = (Button)findViewById(R.id.cracksetting_start);
	}
	private void AdaptLayout()
	{
		LinearLayout mainView=(LinearLayout)findViewById(R.id.cracksettingLinearLayoutAdapter);
		mainView.setPadding(0, SilenTool.getStatusHeight(CrackSetting.this, false), 0, 0);

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
		Toast.makeText(CrackSetting.this, getResources().getString(textID), Toast.LENGTH_SHORT).show();
	}

}
