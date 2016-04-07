package com.lzh222333.unzip;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.ViewGroup.*;
import android.widget.*;
import android.widget.AdapterView.*;
import cn.bmob.v3.*;
import cn.bmob.v3.listener.*;
import cn.bmob.v3.update.*;
import com.lzh222333.unzip.Crack.*;
import com.lzh222333.unzip.SnackBar.*;
import java.io.*;
import java.util.*;

import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;


public class MainActivity extends Activity
{
    @Override

    ResideMenu resideMenu;
	LinearLayout FilePathGroup;
	TextView titleTV;
	ImageButton menuButton,backButton;
	ImageButton FileClickDelete,FileClickOpen,FileClickPojie,FileClickRename,FileClickShare;
	ResideMenuItem AppFeedback,AppAbout,AppUpdata,AppExit,AppDonate,AppShare,AppHelp,AppPostKey;
	LinearLayout FileClickLayout;
	//设置加载的路径
	String SetFile="/storage/";

	String[] strFile;
	ListView FileListView;

	SimpleAdapter adapter;

	String name=null;

	boolean FileClickViewIsShow;
	List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
	List<String> fileList=new ArrayList<String>();
	
	String[] menuItem;
	
	boolean SnackBarIsShowing=false;
	
	SnackBar.Builder mSnackBarControl;
	
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//this.setTheme(android.R.style.Theme_Holo_Light_NoActionBar
		setStatusBar();
        setContentView(R.layout.main_screen_view);
		init();
		
        Bmob.initialize(this,"e0eab853a0c1241dcfe4230dbd0a28bb");
		BmobUpdateAgent.initAppVersion(this);
		BmobUpdateAgent.setUpdateOnlyWifi(true);
		BmobUpdateAgent.update(this);
		
		//Toast.makeText(MainActivity.this,String.format(getResources().getString(R.string.test),0),Toast.LENGTH_SHORT).show();
		
		CrackConfig.cachePath = Environment.getExternalStorageDirectory().toString() + "/zipcracker";
		if (!new File(CrackConfig.cachePath).exists())
		{
			new File(CrackConfig.cachePath).mkdirs();
		}
		
		initSnackBar();
	}


	private void init()
	{
		InitStoragePath();
		findView();
		setUpMenu();
		Adaptlayout();

		ButtonSetListener();

		DataOfListView();
		adapter = new SimpleAdapter(MainActivity.this, list, R.layout.file_listview_item, new String[]{"image","file","detail"}, new int[]{R.id.filelistviewitemImageView,R.id.filelistviewitemTextViewName,R.id.filelistviewitemTextViewDetail});
		FileListView.setAdapter(adapter);

		ListViewSetListener();
		
		
	}


	private void InitStoragePath()
	{
		if(new File(SetFile).list()==null){
			SetFile="/mnt/";
		}
	}

	private void setUpMenu()
	{

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_bg10);
		//resideMenu.setShadowVisible(false);
        resideMenu.attachToActivity(this);
		
		menuItem=getResources().getStringArray(R.array.menu_array);
		
		AppPostKey=new ResideMenuItem(MainActivity.this,R.drawable.post_key,menuItem[0]);
		resideMenu.addMenuItem(AppPostKey);
		AppDonate = new ResideMenuItem(MainActivity.this, R.drawable.donate, menuItem[1]);
		resideMenu.addMenuItem(AppDonate, ResideMenu.DIRECTION_LEFT);
		AppShare = new ResideMenuItem(MainActivity.this, R.drawable.share, menuItem[2]);
		resideMenu.addMenuItem(AppShare, ResideMenu.DIRECTION_LEFT);
		AppUpdata = new ResideMenuItem(MainActivity.this, R.drawable.updata, menuItem[3]);
		resideMenu.addMenuItem(AppUpdata, ResideMenu.DIRECTION_LEFT);
		AppFeedback = new ResideMenuItem(MainActivity.this, R.drawable.feedback, menuItem[4]);
		resideMenu.addMenuItem(AppFeedback, ResideMenu.DIRECTION_LEFT);
		AppHelp = new ResideMenuItem(MainActivity.this, R.drawable.help, menuItem[5]);
		resideMenu.addMenuItem(AppHelp, ResideMenu.DIRECTION_LEFT);
		AppAbout = new ResideMenuItem(MainActivity.this, R.drawable.about, menuItem[6]);
		resideMenu.addMenuItem(AppAbout, ResideMenu.DIRECTION_LEFT);
		
	}

	private void findView()
	{

		menuButton = (ImageButton)findViewById(R.id.mainscreenviewImageButtonMenuButton);
		backButton=(ImageButton)findViewById(R.id.mainscreenviewImageButtonBackButton);
		FileListView = (ListView)findViewById(R.id.mainscreenviewListViewFileListView);
		FilePathGroup = (LinearLayout)findViewById(R.id.mainscreenviewLinearLayoutFilePath);

		titleTV = (TextView)findViewById(R.id.mainscreenviewTextViewTitle);

		FileClickLayout = (LinearLayout)findViewById(R.id.mainscreenviewLinearLayoutFileClick);
		FileClickDelete = (ImageButton)findViewById(R.id.mainscreenviewImageButtonFileClickDelete);
		FileClickRename = (ImageButton)findViewById(R.id.mainscreenviewImageButtonFileClickRename);
		FileClickPojie = (ImageButton)findViewById(R.id.mainscreenviewImageButtonFileClickPojie);
		FileClickOpen = (ImageButton)findViewById(R.id.mainscreenviewImageButtonFileClickOpen);
		FileClickShare = (ImageButton)findViewById(R.id.mainscreenviewImageButtonFileClickShare);

	}

	private void Adaptlayout()
	{

		RelativeLayout mainView=(RelativeLayout)findViewById(R.id.mainscreenviewRelativeLayoutAllViewGroup);
		mainView.setPadding(0, SilenTool.getStatusHeight(MainActivity.this,true), 0, 0);
		
		DisplayMetrics dm = new DisplayMetrics();
        //获取窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //窗口宽度
        int Width = (int)(dm.widthPixels / 5);
        FileClickDelete.setLayoutParams(new RelativeLayout.LayoutParams(Width, LayoutParams.WRAP_CONTENT));
		FileClickOpen.setLayoutParams(new RelativeLayout.LayoutParams(Width, LayoutParams.WRAP_CONTENT));
		FileClickPojie.setLayoutParams(new RelativeLayout.LayoutParams(Width, LayoutParams.WRAP_CONTENT));
		FileClickShare.setLayoutParams(new RelativeLayout.LayoutParams(Width, LayoutParams.WRAP_CONTENT));
		FileClickRename.setLayoutParams(new RelativeLayout.LayoutParams(Width, LayoutParams.WRAP_CONTENT));
	}

	private void ButtonSetListener()
	{
		menuButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
				}
			});
		backButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					fileBack();
				}
			});
		FileClickShare.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					//Intent share = new Intent(Intent.ACTION_SEND);   
					//share.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(new File(SetFile+name)));
					//share.setType("*/*");

					startActivity(Intent.createChooser(new Intent(Intent.ACTION_SEND).putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(SetFile + name))).setType("*/*"), "Share"));
					
					FileClickLayout.setVisibility(View.GONE);
					backButton.setVisibility(View.VISIBLE);
					FileClickViewIsShow = false;
					titleTV.setText(R.string.app_name);
				}
			});
		FileClickDelete.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					new DeleteDialog().show(getFragmentManager(), null);

					FileClickLayout.setVisibility(View.GONE);
					backButton.setVisibility(View.VISIBLE);
					FileClickViewIsShow = false;
					titleTV.setText(R.string.app_name);

				}
			});
		FileClickPojie.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					
					SharedPreferences sp=getSharedPreferences("FileCrackSave",MODE_PRIVATE);
					sp.edit().putString("file_name",name).commit();
					sp.edit().putString("file_path",SetFile).commit();
					startActivity(new Intent(MainActivity.this, CrackSetting.class).putExtra("File", new String[]{SetFile,name}));
					FileClickLayout.setVisibility(View.GONE);
					backButton.setVisibility(View.VISIBLE);
					FileClickViewIsShow = false;
					titleTV.setText(R.string.app_name);

				}
			});
		FileClickRename.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{

				}
			});
		FileClickOpen.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					startActivity(SilenTool.openFile(new File(SetFile + name)));
					FileClickLayout.setVisibility(View.GONE);
					backButton.setVisibility(View.VISIBLE);
					FileClickViewIsShow = false;
					titleTV.setText(R.string.app_name);

				}
			});
		
		AppAbout.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					startActivity(new Intent(MainActivity.this, HelpAndAboutActivity.class).putExtra("Type", "关于"));
					resideMenu.closeMenu();
				}
			});
		AppHelp.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					startActivity(new Intent(MainActivity.this, HelpAndAboutActivity.class).putExtra("Type", "帮助"));
					resideMenu.closeMenu();
				}
			});
		AppFeedback.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					startActivity(new Intent(MainActivity.this, feedback.class).putExtra("Type", "帮助"));
					resideMenu.closeMenu();
				}
			});
			
		AppShare.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					PackageManager pm=getPackageManager();
					try
					{
						ApplicationInfo ai=pm.getApplicationInfo("com.wocao.unzip", 0);
						startActivity(Intent.createChooser(new Intent(Intent.ACTION_SEND).putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(ai.sourceDir))).setType("*/*"), "Share"));
						
					}
					catch (PackageManager.NameNotFoundException e)
					{
						
					}
					resideMenu.closeMenu();
				}
			});
		AppPostKey.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					startActivity(new Intent(MainActivity.this,postkey.class));
					resideMenu.closeMenu();
				}
			});
		AppDonate.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					startActivity(new Intent(MainActivity.this,donate.class));
					resideMenu.closeMenu();
				}
			});
		AppUpdata.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					BmobUpdateAgent.initAppVersion(MainActivity.this);
					/*
					BmobUpdateAgent.setUpdateOnlyWifi(true);
					BmobUpdateAgent.update(MainActivity.this);
					*/

					
					BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {

							@Override
							public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
								// TODO Auto-generated method stub
								if (updateStatus == UpdateStatus.Yes) {//版本有更新

								}else if(updateStatus == UpdateStatus.No){
									Toast.makeText(MainActivity.this, "版本无更新", Toast.LENGTH_SHORT).show();
								}else if(updateStatus==UpdateStatus.EmptyField){//此提示只是提醒开发者关注那些必填项，测试成功后，无需对用户提示
									Toast.makeText(MainActivity.this, "请检查你AppVersion表的必填项，1、target_size（文件大小）是否填写；2、path或者android_url两者必填其中一项。", Toast.LENGTH_SHORT).show();
								}else if(updateStatus==UpdateStatus.IGNORED){
									Toast.makeText(MainActivity.this, "该版本已被忽略更新", Toast.LENGTH_SHORT).show();
								}else if(updateStatus==UpdateStatus.ErrorSizeFormat){
									Toast.makeText(MainActivity.this, "请检查target_size填写的格式，请使用file.length()方法获取apk大小。", Toast.LENGTH_SHORT).show();
								}else if(updateStatus==UpdateStatus.TimeOut){
									Toast.makeText(MainActivity.this, "查询出错或查询超时", Toast.LENGTH_SHORT).show();
								}
							}
						});
						
					BmobUpdateAgent.forceUpdate(MainActivity.this);
				}
			});
			
			
	}

	private void ListViewSetListener()
	{
		FileListView.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
					try
					{
						if (!FileClickViewIsShow)
						{

							if (new File(SetFile + strFile[p3]).isDirectory())
							{
								SetFile = SetFile + strFile[p3] + "/";

								DataOfListView();
								adapter.notifyDataSetChanged();
							}
							else
							{
								name = strFile[p3];
								FileClickLayout.setVisibility(View.VISIBLE);
								backButton.setVisibility(View.GONE);
								FileClickViewIsShow = true;
								titleTV.setText(name);
							}
						}
						else
						{
							FileClickLayout.setVisibility(View.GONE);
							backButton.setVisibility(View.VISIBLE);
							FileClickViewIsShow = false;
							titleTV.setText(R.string.app_name);
						}
					}
					catch (Exception e)
					{
						Toast.makeText(MainActivity.this, e + "", Toast.LENGTH_LONG).show();
					}
				}
			});
	}
	

	private void initSnackBar()
	{

		mSnackBarControl = new SnackBar.Builder(this)

			.withMessage(getResources().getString(R.string.MainActivity_Exit_Message))

			. withActionMessage(getResources().getString(R.string.MainActivity_Exit_button_donate))
			. withActionMessage2(getResources().getString(R.string.MainActivity_Exit_button_exit))
			. withOnClickListener(new SnackBar.OnMessageClickListener(){

				@Override
				public void onMessageClick(Parcelable token)
				{
					startActivity(new Intent(MainActivity.this,donate.class));
				}
			})
			.withDuration(SnackBar.MED_SNACK)
			.withVisibilityChangeListener(new SnackBar.OnVisibilityChangeListener(){

				@Override
				public void onShow(int stackSize)
				{
					SnackBarIsShowing=true;
				}

				@Override
				public void onHide(int stackSize)
				{
					SnackBarIsShowing=false;
				}
			});
	}
	

	private void DataOfListView()
	{
		list.clear();
		if (new File(SetFile).list()!= null)
		{
			strFile = new File(SetFile).list();

			strFile = SilenTool.compare(SetFile, strFile);

			for (String str:strFile)
			{
				Map<String,Object> map=new HashMap<String,Object>();

				Date date=new Date(new File(SetFile + str).lastModified());
				if (new File(SetFile + str).isDirectory())
				{
					if ((SetFile + str).equals("/storage/sdcard0") || (SetFile + str).equals("/mnt/sdcard"))
					{
						map.put("file", "内置储存(sdcard0)");
					}
					else if ((SetFile + str).equals("/storage/sdcard1") || (SetFile + str).equals("/mnt/sdcard2"))
					{
						map.put("file", "外置储存(sdcard1)");
					}
					else if ((SetFile + str).equals("/storage/usbotg") || (SetFile + str).equals("/mnt/usbotg"))
					{
						map.put("file", "OTG挂载储存(usbotg)");
					}
					else
					{
						map.put("file", str);
					}

					map.put("image", R.drawable.file_folder);
					map.put("detail", date.getYear() + 1900 + "-" + (date.getMonth() + 1) + "-" + date.toString().substring(8, 19));
				}
				else if (SilenTool.getFilePostfix(str).equalsIgnoreCase("zip"))
				{
					map.put("file", str);
					map.put("image", R.drawable.file_zip);
					map.put("detail", SilenTool.getFileLength(SetFile + str) + "|" + (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.toString().substring(8, 19));

				}
				else
				{
					map.put("file", str);
					map.put("image", R.drawable.file_rar);
					map.put("detail", SilenTool.getFileLength(SetFile + str) + "|" + (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.toString().substring(8, 19));
				}

				list.add(map);
			}
		}
		FilePathViewOperate(SetFile);
		BackButtonOperate(SetFile);
	}

	

	//以下两个方法用于设置状态栏

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


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		switch (keyCode)
		{
			case KeyEvent.KEYCODE_MENU:
				OperateMenuOpenOrClose();
				break;
			case KeyEvent.KEYCODE_BACK:
				if (resideMenu.isOpened())
				{
					resideMenu.closeMenu();
				}
				else if (FileClickViewIsShow)
				{
					FileClickLayout.setVisibility(View.GONE);
					backButton.setVisibility(View.VISIBLE);
					FileClickViewIsShow = false;
					titleTV.setText(R.string.app_name);
				}
				else
				{
					if(SnackBarIsShowing==false){
					mSnackBarControl.show();
						SnackContainer.SnackHolder.button2.setOnClickListener(new OnClickListener(){

								@Override
								public void onClick(View p1)
								{
									finish();
								}
							});
						}else{
							mSnackBarControl.hide();
							//Toast.makeText(MainActivity.this,getResources().getString(R.string.MainActivity_BackClickExitToast),Toast.LENGTH_SHORT).show();
						}
						/*
					mSnackBar = new SnackBar.Builder(this)

						.withMessage(getResources().getString(R.string.MainActivity_Exit_Message))

						. withActionMessage(getResources().getString(R.string.MainActivity_Exit_button_donate))
						. withActionMessage2(getResources().getString(R.string.MainActivity_Exit_button_exit))
						. withOnClickListener(new SnackBar.OnMessageClickListener(){

							@Override
							public void onMessageClick(Parcelable token)
							{
								startActivity(new Intent(MainActivity.this,donate.class));
							}
						})
						.withDuration(SnackBar.LONG_SNACK)
						.withVisibilityChangeListener(new SnackBar.OnVisibilityChangeListener(){

							@Override
							public void onShow(int stackSize)
							{
								SnackBarIsShowing=true;
							}

							@Override
							public void onHide(int stackSize)
							{
								SnackBarIsShowing=false;
							}
						}).attemp();
						
					SnackContainer.SnackHolder.button2.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View p1)
							{
								finish();
							}
						});
						}else{
							SnackContainer sc;
							SnackBarIsShowing=false;
						}*/
					/*
					if(System.currentTimeMillis()-lastBackClick<=2000){
						finish();
					}else{
						lastBackClick=System.currentTimeMillis();
						Toast.makeText(MainActivity.this,getResources().getString(R.string.MainActivity_BackClickExitToast),Toast.LENGTH_SHORT).show();
					}
					*/
				}
				break;

		}
		return true;
	}
//用于回到文件的母文件夹
	private void fileBack()
	{
		if (!(SetFile.equals("/storage/")||SetFile.equals("/mnt/")))
		{
			SetFile = SetFile.substring(0, SetFile.lastIndexOf("/"));
			SetFile = SetFile.substring(0, SetFile.lastIndexOf("/")) + "/";
			DataOfListView();
			adapter.notifyDataSetChanged();
		}
		else
		{
			finish();
		}
	}

	//该方法用于控制菜单的开启和关闭
	private void OperateMenuOpenOrClose()
	{
		if (resideMenu.isOpened())
		{
			resideMenu.closeMenu();
		}
		else
		{
			resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
		}
	}

	private void FilePathViewOperate(String FilePath)
	{
		final String[] FilePathSpilt=FilePath.split("/");
		FilePathGroup.removeAllViews();
		for (int i=1;i < FilePathSpilt.length;i++)
		{
			final TextView name=new TextView(MainActivity.this);
			name.setText(FilePathSpilt[i]);
			name.setTextSize(14);
			if (i == FilePathSpilt.length - 1)
			{
				name.setTextColor(Color.rgb(22, 21, 22));
			}
			else
			{
				name.setTextColor(Color.rgb(117, 116, 117));
			}
			name.setPadding(10, 0, 10, 0);
			name.setId(i);
			name.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View p1)
					{
						SetFile = "/";
						for (int n=0;n < name.getId();n++)
						{
							SetFile += FilePathSpilt[n + 1] + "/";
						}

						DataOfListView();
						adapter.notifyDataSetChanged();

					}
				});

			LinearLayout linear=new LinearLayout(MainActivity.this);
			LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			linear.setOrientation(LinearLayout.HORIZONTAL);

			linear.addView(name);
			TextView tv=new TextView(MainActivity.this);
			tv.setText("＞");
			tv.setTextSize(14);
			tv.setTextColor(Color.rgb(163, 162, 163));
			linear.addView(tv);
			FilePathGroup.addView(linear);
		}
	}

	private void BackButtonOperate(String Path)
	{
		if(Path.equals("/storage/")||Path.equals("/mnt/")){
			backButton.setVisibility(View.GONE);
		}else{
			backButton.setVisibility(View.VISIBLE);
		}
	}

	class DeleteDialog extends android.app.DialogFragment
	{

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			// TODO: Implement this method
			super.onCreateDialog(savedInstanceState);
			AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
			builder.setTitle("删除—" + name);
			builder.setMessage("\n您确定删除该文件么？删除后可能无法恢复！\n");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						new File(SetFile + name).delete();
						DataOfListView();
						adapter.notifyDataSetChanged();
					}
				});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface p1, int p2)
					{

					}
				});
			return builder.create();
		}
	}
	
}
