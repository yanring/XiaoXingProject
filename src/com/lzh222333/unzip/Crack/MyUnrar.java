package com.lzh222333.unzip.Crack;

import android.os.*;
import net.lingala.zip4j.core.*;
import net.lingala.zip4j.exception.*;
import net.lingala.zip4j.model.*;

public class MyUnrar
{
	private ZipFile zip;
	private String path;
	private int type=0;
	
	Message msg;
	Handler h;
	private String tempfile;//zip内最小文件
    public MyUnrar(Handler h)
	{
		this.h = h;
	} 
	public void setPath(String path)
	{
		this.path = path;
		Message msg=Message.obtain();
		msg.what = CrackConfig.MSG_OTHERS;
		if (path.endsWith(".rar"))
		{
			type = CrackConfig.TYPE_RAR;
			msg.obj = "debug:选取了RAR文件";
		}
		else if(path.endsWith(".7z"))
		{
			type= CrackConfig.TYPE_7Z;
			msg.obj = "debug:选取了7Z文件";
		}
		else if (path.endsWith(".zip"))
		{
			type = CrackConfig.TYPE_ZIP;
			msg.obj = "debug:选取了ZIP文件";
			try
			{
				zip = new ZipFile(path);
				if (!zip.isValidZipFile())
				{
					throw new ZipException();
				}
				if (!zip.isEncrypted())
				{
					throw new ZipException();
				}
				FileHeader minfh=null;
				long tempsize=0;
				for (int i=0;i < zip.getFileHeaders().size();i++)
				{
					FileHeader fh=(FileHeader)zip.getFileHeaders().get(i);
					if (fh.isDirectory())
					{
						continue;
					}
					else//不是目录
					{
						if (null == minfh)
						{
							minfh = fh;
							tempsize = fh.getCompressedSize();
						}
						else if (fh.getCompressedSize() < tempsize)
						{
							tempsize = fh.getCompressedSize();
							minfh = fh;
						}
					}
				}
				tempfile = minfh.getFileName();
				msg.obj = msg.obj + "\n" + tempfile + "是zip内最小文件";
			}
			catch (ZipException e)
			{
				//坏文件
			}
		}
		else
		{
			type = CrackConfig.TYPE_OTHERS;
			msg.obj = "debug:选取了其他类型的压缩文件";
		}
		h.sendMessage(msg);
	}
	public void tryPassword(String key)
	{
		msg = Message.obtain();
		msg.what = CrackConfig.MSG_OTHERS;
		msg.obj = "debug:正在破解:" + key;
		h.sendMessage(msg);
		/*if (type == RAR)
		{
			//rar
			处理rar
		}*/
		/*else */if (type == CrackConfig.TYPE_ZIP)
		{
			try
			{
				zip = new ZipFile(path);
				zip.setPassword(key);
				zip.extractFile(tempfile, CrackConfig.cachePath);
				msg = Message.obtain();
				msg.what = CrackConfig.CRACK_RIGHT_PASSWORD;
				msg.obj = key;
				h.sendMessage(msg);
			}
			catch (ZipException e)
			{
				//密码错误
				
			}
		}
	}
	public int getType()
	{
		return type;
	}
}
