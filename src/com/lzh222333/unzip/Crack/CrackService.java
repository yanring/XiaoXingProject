package com.lzh222333.unzip.Crack;

import android.app.*;
import android.content.*;
import android.os.*;
import com.lzh222333.unzip.*;
import com.lzh222333.unzip.Bmob.*;
import java.io.*;
import java.util.*;

public class CrackService extends Service
{
	private final IBinder binder = new MyBinder();
    CrackHandler handler=new CrackHandler();
	String filePath=null;
	String MD5=null;
	Message m=null;
    MyUnrar unrar=null;
	Thread Thread_MD5 = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				// TODO: Implement this method
				try
				{
					//String md5=MD5Tool.fileMD5(archiverFilePath);
					String md5=MD5Tool.getMd5ByFile(new File(filePath));
					MD5 = md5;
					m = Message.obtain();
					m.what = CrackConfig.MSG_MD5_FINISHED;
					List<Object> obj=new ArrayList<Object>();
					obj.add(MD5);
					obj.add(Thread_FLASHCRACK);
					m.obj = obj;
					m.setTarget(handler);
					m.sendToTarget();
				}
				catch (IOException e)
				{

				}
				// File archiverFile=new File(archiverFilePath);
			}
		});

	Thread Thread_FLASHCRACK = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				// TODO: Implement this method
				MD5 md5=new MD5();
				String pwd=md5.query(getApplicationContext(), MD5);
				m = Message.obtain();
				m.what = CrackConfig.MSG_FLASHCRACK_FINISHED;
				if (pwd == null)
				{
					//未获取到对应md5
					m.arg1 = 0;
					m.obj = Thread_BACKUPS;
					if(unrar.getType()==CrackConfig.TYPE_ZIP)
					{
						handler.sendMessage(m);
					}
					else
					{
						///其他类型的文件只获取了md5
						
						
						
					}
				}
				else
				{	
				    //md5获取成功
					m.arg1 = 1;
					m.obj=pwd;
				}
			}
		});

	Thread Thread_BACKUPS = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				// TODO: Implement this method
				Backups backups=new Backups(CrackService.this);
				ArrayList<String> lists=new ArrayList<String>();
				while (!Cracking.cracking.isFinished)
				{
					if (Cracking.cracking.isRun)
					{
						//正常运行状态
						if (lists.size() == 0)
						{
							backups.query();
							do
							{//只要backups.pwdLists为空，就等待其不为空
								try
								{
									Thread_BACKUPS.sleep(10);
								}
								catch (InterruptedException e)
								{}
							}while(backups.pwdLists == null);
							lists = backups.pwdLists;//将backups内值给pwdLists,之后操作pwdLists
							if(lists.size()==0)
							{
								m=Message.obtain();
								m.what=CrackConfig.MSG_BACKUPS_FINISHED;
								m.obj=Thread_DICTIONARY;
								handler.sendMessage(m);
								break;
							}
							m=Message.obtain();
							m.what=CrackConfig.MSG_OTHERS;
							m.obj="debug:新请求密码成功";
							handler.sendMessage(m);
							continue;
						}
						else
						{
							unrar.tryPassword(lists.get(0));
							lists.remove(0);
						}
					}
					//isRun==false情况说明暂停
					else
					{
						try
						{
							m = Message.obtain();
							m.what = CrackConfig.MSG_OTHERS;
							m.obj = "debug:正在暂停，请您尽快恢复破解";
							handler.sendMessage(m);
							Thread_BACKUPS.sleep(1000);
						}
						catch (InterruptedException e)
						{

						}
					}
				}
			}
		});

	Thread Thread_DICTIONARY = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				// TODO: Implement this method
				//crackhere
				com.lzh222333.unzip.Bmob.Dictionary dictionary=new com.lzh222333.unzip.Bmob.Dictionary(CrackService.this);
				ArrayList<String> lists=new ArrayList<String>();
				while (!Cracking.cracking.isFinished)
				{
					if (Cracking.cracking.isRun)
					{
						//正常运行状态
						if (lists.size() == 0)
						{
							dictionary.isSuccess=false;
							dictionary.query();
							/*do
							{//只要pwdLists为空，就等待其不为空
								try
								{
									Thread_BACKUPS.sleep(50);
								}
								catch (InterruptedException e)
								{}
							}while(dictionary.lists == null);*/
							while(!dictionary.isSuccess)
							{
								
							}
							dictionary.isSuccess=false;
							lists = dictionary.lists;//将dictionary内值给lists,之后操作lists
							if(lists.size()==0)
							{//破解完成
								m=Message.obtain();
								m.what=CrackConfig.MSG_DICTIONARY_FINISHED;
								//m.obj=Thread_DICTIONARY;
								handler.sendMessage(m);
								break;
							}
							m=Message.obtain();
							m.what=CrackConfig.MSG_OTHERS;
							m.obj="debug:新请求密码成功";
							handler.sendMessage(m);
							continue;
						}
						else
						{
							unrar.tryPassword(lists.get(0));
							lists.remove(0);//从lists中移除已破解的密码
						}
					}
					//isRun==false情况说明暂停
					else
					{
						try
						{
							m = Message.obtain();
							m.what = CrackConfig.MSG_OTHERS;
							m.obj = "debug:正在暂停，请您尽快恢复破解";
							handler.sendMessage(m);
							Thread_DICTIONARY.sleep(1000);
						}
						catch (InterruptedException e)
						{

						}
					}
				}
			}
		});

	Thread Thread_ = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				// TODO: Implement this method
			}
		});



	@Override
	public IBinder onBind(Intent intent)
	{
		return binder;
	}
	/**
	 * 该类是获得Service对象
	 * @author Administrator
	 *
	 */
	public class MyBinder extends Binder
	{
		public CrackService getService()
		{
			return CrackService.this;
		}
		public void startCrack()
		{
			SharedPreferences sp=getSharedPreferences("FileCrackSave", MODE_PRIVATE);
			String archiverFilePath=sp.getString("file_path", null) + sp.getString("file_name", null);
			filePath = archiverFilePath;
			unrar = new MyUnrar(handler);
			unrar.setPath(filePath);
			Cracking.print("debug:开始破解");
			Cracking.print("debug:正在计算MD5......");
			
			Thread_MD5.start();
		}
	}
	@Override
	public void onCreate()
	{
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId)
	{
		super.onStart(intent, startId);

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();

	}

}

