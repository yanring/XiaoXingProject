package com.lzh222333.unzip.Crack;

import android.os.*;
import com.lzh222333.unzip.*;
import com.lzh222333.unzip.Bmob.*;
import com.lzh222333.unzip.Crack.*;
import java.util.*;

public class CrackHandler extends Handler
{
	@Override
	public void handleMessage(Message msg)
	{
		// TODO: Implement this method
		super.handleMessage(msg);
		switch (msg.what)
		{
			case CrackConfig.MSG_MD5_FINISHED:
				Cracking.print("debug:MD5计算完成:");
				List obj=(List)msg.obj;
				Cracking.print((String)obj.get(0));
				Thread Thread_FLASHCRACK=(Thread)obj.get(1);
				try
				{
					Thread.sleep(500);
				}
				catch (InterruptedException e)
				{}
				Thread_FLASHCRACK.start();
				break;
			case CrackConfig.MSG_FLASHCRACK_FINISHED:
				if (msg.arg1 == 1)
				{
					Cracking.print("debug:云秒破完成");
					Cracking.print("debug:获取到以下密码");
					Cracking.print(msg.obj.toString());
				}
				else
				{
					Cracking.print("debug:未获取到MD5对应密码，开始用户备份密码破解");
					Thread Thread_BACKUPS=(Thread)msg.obj;
					Thread_BACKUPS.start();
				}
				break;
			case CrackConfig.MSG_BACKUPS_FINISHED:
				Cracking.print("debug:用户备份密码破解完成");
				Thread Thread_DICTIONARY=(Thread)msg.obj;
				Thread_DICTIONARY.start();
			    break;
			case CrackConfig.MSG_DICTIONARY_FINISHED:
				Cracking.print("debug:密码云字典密码破解完成");
				//Thread Thread_DICTIONARY=(Thread)msg.obj;
				//Thread_DICTIONARY.start();
			    break;
			case CrackConfig.CRACK_RIGHT_PASSWORD:
				Cracking.print("debug:正确密码:");
				Cracking.print(msg.obj.toString());
				Cracking.cracking.key=msg.obj.toString();
				Cracking.cracking.openDialog(true);
				break;
			/*case CrackConfig.CRACK_WRONG_PASSWORD:
				Cracking.print("debug:错误密码:");
				Cracking.print(msg.obj.toString());
				break;*/
			case CrackConfig.CRACK_BROKEN_ARCHIVE:
				Cracking.print("debug:非正常RAR加密文件");
				break;
			case CrackConfig.MSG_OTHERS:
				Cracking.print(""+msg.obj);
				break;
		}
	}
}
