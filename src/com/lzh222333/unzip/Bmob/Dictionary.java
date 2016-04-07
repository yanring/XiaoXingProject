package com.lzh222333.unzip.Bmob;

import android.content.*;
import android.util.*;
import android.widget.*;
import cn.bmob.v3.*;
import cn.bmob.v3.listener.*;
import com.lzh222333.unzip.Crack.*;
import java.util.*;

public class Dictionary extends BmobObject
{
	//此类暂未实现数据重复的删除功能
	private String password="";//压缩文件密码
	//密码计数，总共多少次破解成功
	private Integer total;
	private Context ctx;

	public boolean isSuccess=false;
	public Dictionary(Context ctx)
	{
		this.ctx=ctx;
	}
	
	public ArrayList<String> lists=null;
	int page=0;
	public void query()
	{
		BmobQuery<Dictionary> query=new BmobQuery<Dictionary>();
		query.setLimit(CrackConfig.PAGELIMIT);
		query.setSkip(CrackConfig.PAGELIMIT * page);
		page++;
		query.order("-total");
		//query.addWhereEqualTo("userid", userid);
		query.findObjects(ctx,new FindListener<Dictionary>()
			{
				@Override
				public void onSuccess(List<Dictionary> p1)
				{
					// TODO: Implement this method
					Log.e("p1",p1.size()+"");
					if (p1 != null && p1.size() >= 1)
				    {
						lists=new ArrayList<String>();
						for(int i=0;i<p1.size();i++)
						{
							Log.e("pwd",p1.get(i).getPassword());
							lists.add(p1.get(i).getPassword());
						}
					}
					else
						lists=new ArrayList<String>();
					isSuccess=true;
				}
				@Override
				public void onError(int p1, String p2)
				{
					// TODO: Implement this method
				}
			});
	}
	
	public void queryAndSave(final Context context, final String pwd)
	{
		BmobQuery<Dictionary> query=new BmobQuery<Dictionary>();
		query.addWhereEqualTo("password", pwd);
		query.findObjects(context, new FindListener<Dictionary>()
			{
				@Override
				public void onSuccess(List<Dictionary> p1)
				{
					// TODO: Implement this method
					Toast.makeText(context,"共"+p1.size()+"条数据",Toast.LENGTH_SHORT).show();
					if(p1.size()!=0)
					{
						Dictionary dictionary=p1.get(0);
						dictionary.setTotal(dictionary.getTotal()+1);
						dictionary.update(context);
					}
					else
					{
						setPassword(pwd);
						setTotal(1);
						save(context);
					}
				}

				@Override
				public void onError(int p1, String p2)
				{
					// TODO: Implement this method
				}
			});
	}

	public void setTotal(Integer total)
	{
		this.total = total;
	}

	public Integer getTotal()
	{
		return total;
	}


	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getPassword()
	{
		return password;
	}}
