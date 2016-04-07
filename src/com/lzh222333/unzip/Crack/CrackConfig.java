package com.lzh222333.unzip.Crack;

public class CrackConfig
{
	public static final int 
	MSG_MD5_FINISHED=1,//获取文件MD5完成
	MSG_FLASHCRACK_FINISHED=2,//秒破解完成
	MSG_BACKUPS_FINISHED=3,//用户备份字典破解完成
	MSG_DICTIONARY_FINISHED=4,//字典破解完成
	MSG_OTHERS=99;//其他信息输出
	
	public static final int
	CRACK_WRONG_PASSWORD=101,//密码错误，下一次尝试
	CRACK_RIGHT_PASSWORD=102,//密码正确，破解成功
	CRACK_BROKEN_ARCHIVE=103;//文件错误
	
	public final static int TYPE_NONE=0,TYPE_RAR=1,TYPE_ZIP=2,TYPE_7Z=3,TYPE_OTHERS=99;
	
	public static String cachePath="";
	
	public static String filePath="";
	
	public static final int PAGELIMIT=5;
}
