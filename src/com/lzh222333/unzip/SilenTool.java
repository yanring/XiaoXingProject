package com.lzh222333.unzip;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.util.*;
import java.io.*;
import java.util.*;

public class SilenTool
{
	//修改
	//该方法用于文件排序(按名称)
	public static String[] compare(String filePath, String[] file)
	{
		//过滤文件
		//String[] filterate={"rar","zip"};

		String[] returnStr=new String[file.length];
		String[] returnStr2;
		String[] isfile=new String[file.length];
		String[] isDest=new String[file.length];

		int i=0,n=0;

		for (i = 0;i < file.length - 1;i++)
		{
			for (n = i + 1;n < file.length;n++)
			{
				if (file[i].compareToIgnoreCase(file[n]) > 0)
				{
					String min=file[n];
					file[n] = file[i];
					file[i] = min;
				}
			}
		}
		n = 0;i = 0;
		for (int l=0;l < file.length;l++)
		{
			if (file[l].indexOf(".") != 0)
			{
				if (new File(filePath + file[l]).isDirectory())
				{
					isDest[i] = file[l];
					i++;
				}
				else
				{
					isfile[n] = file[l];
					n++;
				}
			}
		}
		i = 0;n = 0;
		for (i = 0;i < isDest.length;i++)
		{
			if (isDest[i] == null)
			{
				break;
			}
			if (DirectoryPstfix(filePath + isDest[i]) && !(filePath + isDest[i]).equals("/storage/emulated") && !(filePath + isDest[i]).equals("/mnt/asec"))
			{
				returnStr[n] = isDest[i];
				n++;
			}
		}

		for (i = 0;i < isfile.length;i++)
		{
			if (isfile[i] == null)
			{
				break;
			}

			if (getFilePostfix(isfile[i]).equalsIgnoreCase("rar") || getFilePostfix(isfile[i]).equalsIgnoreCase("zip"))
			{
				returnStr[n] = isfile[i];
				n++;
			}
		}
		returnStr2 = new String[n];
		for (i = 0;i < n;i++)
		{
			returnStr2[i] = returnStr[i];
		}
		return returnStr2;
	}

	//该方法用于获取状态栏高度
	public static int getStatusHeight(Activity activity, Boolean Support)
	{

        int statusHeight = 0;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT || Support)
		{
			Rect localRect = new Rect();
			activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
			statusHeight = localRect.top;
			if (0 == statusHeight)
			{
				Class<?> localClass;
				try
				{
					localClass = Class.forName("com.android.internal.R$dimen");
					Object localObject = localClass.newInstance();
					int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
					statusHeight = activity.getResources().getDimensionPixelSize(i5);
				}
				catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				catch (InstantiationException e)
				{
					e.printStackTrace();
				}
				catch (NumberFormatException e)
				{
					e.printStackTrace();
				}
				catch (IllegalArgumentException e)
				{
					e.printStackTrace();
				}
				catch (SecurityException e)
				{
					e.printStackTrace();
				}
				catch (NoSuchFieldException e)
				{
					e.printStackTrace();
				}
			}}
        return statusHeight;
    }

	public static String getFilePostfix(String filename)
	{
		if (filename.lastIndexOf(".") >= 0)
		{
			return filename.substring(filename.lastIndexOf(".") + 1, filename.length()).toLowerCase();

			//filename.substring(filename.lastIndexOf(".") + 1);
		}
		else
		{
			return "未知类型文件";
		}
	}

	public static boolean DirectoryPstfix(String DirectoryPath)
	{
		boolean retureBoolean=false;
		//if (new File(DirectoryPath).list()!= null)
		//{
		Log.e("list", DirectoryPath);
		File f=new File(DirectoryPath);
		Log.e("list", DirectoryPath);
		String[] file={"s"};
		try
		{
			file = f.list();
		}
		catch (Exception e)
		{

		}
		Log.e("list", DirectoryPath);
		//int i=0,n=0;
		if (file != null)
		{
			for (int l=0;l < file.length;l++)
			{
				if (new File(DirectoryPath + "/" + file[l]).isDirectory())
				{
					retureBoolean = true;
					break;
				}
				else if (getFilePostfix(file[l]).equalsIgnoreCase("rar") || getFilePostfix(file[l]).equalsIgnoreCase("zip"))
				{
					retureBoolean = true;
					break;
				}
			}
		}
		else
		{
			retureBoolean = false;
		}
		return retureBoolean;
	}


	public static String getFileLength(String path)
	{
		long len=new File(path).length();
		float deltaDataS=(float)len;
		String returnStr=null;
		if (deltaDataS < 1024)
		{
			returnStr = (int)deltaDataS + " B";
		}
		else if ((deltaDataS = (deltaDataS / 1024)) < 1024)
		{

			if (deltaDataS >= 10)
			{
				returnStr = (int)deltaDataS + " K";
			}
			else
			{
				returnStr = (float)((int)(deltaDataS * 10)) / 10 + "K";

			}
		}
		else if ((deltaDataS = deltaDataS / 1024) < 1024)
		{

			if (deltaDataS >= 10)
			{
				returnStr = (int)deltaDataS + " M";
			}
			else
			{
				returnStr = (float)((int)(deltaDataS * 10)) / 10 + " M";
			}
		}
		else
		{
			deltaDataS = deltaDataS / 1024;
			if (deltaDataS >= 10)
			{
				returnStr = (int)deltaDataS + " G";
			}
			else
			{
				returnStr = (float)((int)(deltaDataS * 10)) / 10 + " G";
			}
		}
		return returnStr;
	}

	/**
	 * 根据文件后缀名获得对应的MIME类型。
	 * @param file
	 */
	public static String getMIMEType(File file)
	{
		String type="*/*";
		String fName=file.getName();
		//获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0)
		{
			return type;
		}
		/* 获取文件的后缀名 */
		String end=fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")return type;
		//在MIME和文件类型的匹配表中找到对应的MIME类型。
		for (int i=0;i < MIME_MapTable.length;i++)
		{
			if (end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}
	/**
	 * 打开文件
	 * @param file
	 */
	public static Intent openFile(File file)
	{
		//Uri uri = Uri.parse("file://"+file.getAbsolutePath());
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//设置intent的Action属性
		intent.setAction(Intent.ACTION_VIEW);
		//获取文件file的MIME类型
		String type = getMIMEType(file);
		//设置intent的data和Type属性。
		intent.setDataAndType(Uri.fromFile(file), type);
		//跳转
		return intent;
	}
	//建立一个MIME类型与文件后缀名的匹配表
	public static String[][] MIME_MapTable={
		//{后缀名，    MIME类型}
		{".3gp",    "video/3gpp"},
		{".apk",    "application/vnd.android.package-archive"},
		{".asf",    "video/x-ms-asf"},
		{".avi",    "video/x-msvideo"},
		{".bin",    "application/octet-stream"},
		{".bmp",      "image/bmp"},
		{".c",        "text/plain"},
		{".class",    "application/octet-stream"},
		{".conf",    "text/plain"},
		{".cpp",    "text/plain"},
		{".doc",    "application/msword"},
		{".exe",    "application/octet-stream"},
		{".gif",    "image/gif"},
		{".gtar",    "application/x-gtar"},
		{".gz",        "application/x-gzip"},
		{".h",        "text/plain"},
		{".htm",    "text/html"},
		{".html",    "text/html"},
		{".jar",    "application/java-archive"},
		{".java",    "text/plain"},
		{".jpeg",    "image/jpeg"},
		{".jpg",    "image/jpeg"},
		{".js",        "application/x-javascript"},
		{".log",    "text/plain"},
		{".m3u",    "audio/x-mpegurl"},
		{".m4a",    "audio/mp4a-latm"},
		{".m4b",    "audio/mp4a-latm"},
		{".m4p",    "audio/mp4a-latm"},
		{".m4u",    "video/vnd.mpegurl"},
		{".m4v",    "video/x-m4v"},    
		{".mov",    "video/quicktime"},
		{".mp2",    "audio/x-mpeg"},
		{".mp3",    "audio/x-mpeg"},
		{".mp4",    "video/mp4"},
		{".mpc",    "application/vnd.mpohun.certificate"},        
		{".mpe",    "video/mpeg"},    
		{".mpeg",    "video/mpeg"},    
		{".mpg",    "video/mpeg"},    
		{".mpg4",    "video/mp4"},    
		{".mpga",    "audio/mpeg"},
		{".msg",    "application/vnd.ms-outlook"},
		{".ogg",    "audio/ogg"},
		{".pdf",    "application/pdf"},
		{".png",    "image/png"},
		{".pps",    "application/vnd.ms-powerpoint"},
		{".ppt",    "application/vnd.ms-powerpoint"},
		{".prop",    "text/plain"},
		{".rar",    "application/x-rar-compressed"},
		{".rc",        "text/plain"},
		{".rmvb",    "audio/x-pn-realaudio"},
		{".rtf",    "application/rtf"},
		{".sh",        "text/plain"},
		{".tar",    "application/x-tar"},    
		{".tgz",    "application/x-compressed"}, 
		{".txt",    "text/plain"},
		{".wav",    "audio/x-wav"},
		{".wma",    "audio/x-ms-wma"},
		{".wmv",    "audio/x-ms-wmv"},
		{".wps",    "application/vnd.ms-works"},
		//{".xml",    "text/xml"},
		{".xml",    "text/plain"},
		{".z",        "application/x-compress"},
		{".zip",    "application/zip"},
		{"",        "*/*"}    
	};

	public static String getLanguageCode(Context context)
	{
		Locale locale = context.getResources().getConfiguration().locale;
		String language = locale.getLanguage();
		Boolean contain=false;

		AssetManager am=context.getAssets();
		try
		{
			for (String s:am.list(""))
			{
				if (s.equals(language))
				{
					contain = true;
					break;
				}
			}
		}
		catch (IOException e)
		{}
		System.out.println("测试结束");
		if (contain)
		{
			return language;
		}
		else
		{
			return "zh";
		}
	}
}
