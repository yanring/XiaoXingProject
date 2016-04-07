package com.lzh222333.unzip.Bmob;

import android.content.*;
import android.widget.*;
import cn.bmob.v3.*;
import cn.bmob.v3.listener.*;
import android.content.res.*;
import com.lzh222333.unzip.*;

public class FeedBack extends BmobObject
{
	private String content="";//反馈内容
	//联系方式
	private String contacts="";

	@Override
	public void save(final Context context, String content, String contacts,final Button v)
	{
		// TODO: Implement this method
		setContent(content);
		setContacts(contacts);
		super.save(context, new SaveListener()
			{
				@Override
				public void onSuccess()
				{
					v.setText(R.string.feedback_send_success_button);
					Toast.makeText(context,context.getResources().getString(R.string.feedback_send_success_toast),Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onFailure(int p1, String p2)
				{
					v.setText(R.string.feedback_send_faith);
				}
			});
	}

	public void setContacts(String contacts)
	{
		this.contacts = contacts;
	}

	public String getContacts()
	{
		return contacts;
	}
	public void setContent(String content)
	{
		this.content = content;
	}

	public String getContent()
	{
		return content;
	}
	}
