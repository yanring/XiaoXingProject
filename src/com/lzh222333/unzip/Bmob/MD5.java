package com.lzh222333.unzip.Bmob;
import android.content.*;
import android.util.*;
import android.widget.*;
import cn.bmob.v3.*;
import cn.bmob.v3.listener.*;
import java.util.*;

public class MD5 extends BmobObject
{
	private String MD5="";//MD5码
	private String password="";//MD5对应压缩密码
	@Override
	public void save(final Context context, String md5, String password)
	{
		// TODO: Implement this method
		setMD5(md5);
		setPassword(password);
		super.save(context, new SaveListener()
			{
				@Override
				public void onSuccess()
				{
					// TODO: Implement this method
					Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onFailure(int p1, String p2)
				{
					// TODO: Implement this method
					Toast.makeText(context, "上传失败", Toast.LENGTH_SHORT).show();
				}
			});
	}

	boolean isSuccess=false;
	public String query(final Context context, String md5)
	{
		BmobQuery<MD5> query = new BmobQuery<MD5>();
        //查询数据
		query.addWhereEqualTo("MD5", md5);
        //执行查询方法
		query.findObjects(context, new FindListener<MD5>() {
				@Override
				public void onSuccess(List<MD5> object)
				{
					// TODO Auto-generated method stub
			        //获得信息
					if (object.size() > 0)
						setPassword(object.get(0).getPassword());
					else
						setPassword(null);
					isSuccess=true;
				}
				@Override
				public void onError(int code, String msg)
				{
					// TODO Auto-generated method stub
					setPassword(null);
					isSuccess=true;
				}
			});
	    while(!isSuccess)
		{
			;//直到成功
		}
		isSuccess=false;
		return getPassword();
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getPassword()
	{
		return password;
	}
	public void setMD5(String md5)
	{
		this.MD5 = md5;
	}

	public String getMD5()
	{
		return MD5;
	}
}
