package com.lzh222333.unzip;

import android.app.*;
import android.os.*;
import android.view.*;
import android.graphics.*;
import android.content.*;
import java.util.*;
import android.widget.*;

public class StartActivity extends Activity 
{
	SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
        setContentView(R.layout.start_activity_view);
		sp=getSharedPreferences("save",MODE_PRIVATE);
	
		startActivity(new Intent(StartActivity.this, MainActivity.class));
		
    }
}
