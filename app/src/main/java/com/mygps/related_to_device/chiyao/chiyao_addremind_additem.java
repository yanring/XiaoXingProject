package com.mygps.related_to_device.chiyao;

import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mygps.R;
import com.mygps.related_to_device.chiyao.Service.chiyao_service;

import java.util.Calendar;

/**
 * Created by zy on 2016/4/1.
 */
public class chiyao_addremind_additem extends AppCompatActivity implements View.OnClickListener{
    private TextView time;
    private ImageView time_image;
    private EditText medision;
    private TextView submite;
    int hourOfDay;
    int minute;
    String time_text;
    String medision_text;
    String bundle_id=null;
    String bundle_time=null;
    String bundle_medision=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chiyao_addremind_additem);
        innit();
        time_image.setOnClickListener(this);
        submite.setOnClickListener(this);
    }

    private void innit() {
        time= (TextView) findViewById(R.id.time);
        medision= (EditText) findViewById(R.id.medision);
        time_image= (ImageView) findViewById(R.id.time_image);
        submite= (TextView) findViewById(R.id.submite);
        Calendar calendar = Calendar.getInstance();
        hourOfDay= calendar.get(Calendar.HOUR_OF_DAY);
        minute= calendar.get(Calendar.MINUTE);
        Bundle bundle=getIntent().getExtras();
        bundle_id=bundle.getString("id");
        bundle_time=bundle.getString("time");
        bundle_medision=bundle.getString("medision");
        time.setText(bundle_time);
        medision.setText(bundle_medision);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch(id){
            case R.id.time_image:{
                showTimeDialog();
                break;
            }
            case R.id.time:{
                break;
            }
            case R.id.submite:{
                upLoadInformation();
                break;
            }
        }
    }

    private void upLoadInformation() {
        time_text=time.getText().toString();
        medision_text=medision.getText().toString();
        Toast.makeText(chiyao_addremind_additem.this,medision_text,Toast.LENGTH_SHORT).show();
        if(time_text==""||medision_text==""){
            Toast.makeText(chiyao_addremind_additem.this,"内容不为空！",Toast.LENGTH_SHORT).show();
        }
        else if(bundle_id!=null){
            new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... params) {
                    chiyao_service mService=new chiyao_service();
                    return chiyao_service.change_UserInfo(time_text, medision_text,bundle_id);
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    if(result==true){
                        Toast.makeText(chiyao_addremind_additem.this,"修改成功！",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(chiyao_addremind_additem.this,"修改失败！",Toast.LENGTH_SHORT).show();
                    }
                    super.onPostExecute(result);
                }
            }.execute();
        }
        else {
            new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... params) {
                    chiyao_service mService=new chiyao_service();
                    return chiyao_service.uploadUserInfo(time_text,medision_text);

                }

                @Override
                protected void onPostExecute(Boolean result) {
                    if(result==true){
                        Toast.makeText(chiyao_addremind_additem.this,"添加成功！",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(chiyao_addremind_additem.this,"添加失败！",Toast.LENGTH_SHORT).show();
                    }
                    super.onPostExecute(result);
                }
            }.execute();


        }
    }

    private void showTimeDialog() {
        TimePickerDialog timePickerDialog=new TimePickerDialog(chiyao_addremind_additem.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time.setText(hourOfDay+":"+minute);
            }
        },hourOfDay, minute, true);
        timePickerDialog.show();
    }
}
