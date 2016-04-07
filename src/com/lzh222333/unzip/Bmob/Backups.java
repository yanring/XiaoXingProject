package com.lzh222333.unzip.Bmob;
import cn.bmob.v3.*;
import java.util.*;
import android.content.*;
import cn.bmob.v3.listener.*;
import org.json.*;
import com.lzh222333.unzip.Crack.*;

public class Backups extends BmobObject
{//该类存在问题，需要重写
	private String userid="";//设备唯一标识号，通过BmobInstallation.getInstallationId获取

	private ArrayList<String> pwd_backups;//用户备份的密码数组，优先破解

	private Context ctx;
	public Backups(Context context)
	{
		userid = BmobInstallation.getInstallationId(context);
		this.ctx = context;
	}

	int page=0;
	public ArrayList<String> pwdLists=null;
	public void query()
	{
		BmobQuery<Backups> query=new BmobQuery<Backups>();
		query.setLimit(CrackConfig.PAGELIMIT);
		query.setSkip(CrackConfig.PAGELIMIT * page);
		page++;
		query.addWhereEqualTo("userid", userid);
		query.findObjects(ctx, new FindListener<Backups>()
			{
				@Override
				public void onSuccess(List<Backups> p1)
				{
					// TODO: Implement this method
					if (p1 != null && p1.size() >= 1)
					{
						pwdLists=new ArrayList<String>();
						pwdLists = p1.get(0).getPwd_backups();
					}
					else
						pwdLists=new ArrayList<String>();
				}
				@Override
				public void onError(int p1, String p2)
				{
					// TODO: Implement this method
				}
			});
	}
	Boolean isSuccess=false;
	public boolean queryAndSave(final String key)
	{
		BmobQuery<Backups> query=new BmobQuery<Backups>();
		query.addWhereEqualTo("userid", userid);
		query.findObjects(ctx, new FindListener<Backups>()
			{
				@Override
				public void onSuccess(List<Backups> p1)
				{
					// TODO: Implement this method
					if (p1.size() == 0)
					{
						Backups b=p1.get(0);
						b.addUnique("pwd_backups", key);
						b.update(ctx, new UpdateListener()
							{
								@Override
								public void onSuccess()
								{
									// TODO: Implement this method
									isSuccess = true;
								}
								@Override
								public void onFailure(int p1, String p2)
								{
									// TODO: Implement this method
									isSuccess = false;
								}
							});
					}
					else
					{
						ArrayList<String> keys=new ArrayList<String>();
						keys.add(key);
						setPwd_backups(keys);
						save(ctx, new SaveListener()
							{
								@Override
								public void onSuccess()
								{
									// TODO: Implement this method
									isSuccess = true;
								}
								@Override
								public void onFailure(int p1, String p2)
								{
									// TODO: Implement this method
									isSuccess = false;
								}						
							});
					}
				}

				@Override
				public void onError(int p1, String p2)
				{
					// TODO: Implement this method
					isSuccess = false;
				}
			});
		return isSuccess;
	}

	public void setPwd_backups(ArrayList<String> pwd_backups)
	{
		this.pwd_backups = pwd_backups;
	}

	public ArrayList<String> getPwd_backups()
	{
		return pwd_backups;
	}

	public void setUserid(String userid)
	{
		this.userid = userid;
	}

	public String getUserid()
	{
		return userid;
	}
}
