package com.mygps.related_to_device.chiyao;

import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mygps.MyApplication;
import com.mygps.R;
import com.mygps.related_to_device.chiyao.Adapter.Spiner_adapter;
import com.mygps.related_to_device.chiyao.Service.chiyao_service;
import com.mygps.related_to_device.map.model.Equipment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by zy on 2016/4/1.
 */
public class chiyao_addremind_additem extends AppCompatActivity implements View.OnClickListener{
    private TextView time;
    private ImageView time_image;
    private EditText medision;
    private TextView submite;
    private Spinner spinner;
    MyApplication app;
    private List<Equipment> spinner_data;
    private Spiner_adapter spiner_adapter;
    int hourOfDay;
    int minute;
    String time_text;
    String medision_text;
    String bundle_id=null;
    String bundle_time=null;
    String bundle_medision=null;
    String bundle_equip_name=null;
    String bundle_equip_id=null;
    String equip_name=null;
    String equip_id=null;
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
        spinner= (Spinner) findViewById(R.id.chiyaoaddremindadditem_spinner);
        app= (MyApplication) getApplication();
        spinner_data=new ArrayList<>();
        spinner_data=app.getEquips();
        spiner_adapter=new Spiner_adapter(chiyao_addremind_additem.this,spinner_data);

        Calendar calendar = Calendar.getInstance();
        hourOfDay= calendar.get(Calendar.HOUR_OF_DAY);
        minute= calendar.get(Calendar.MINUTE);
        Bundle bundle=getIntent().getExtras();
        bundle_id=bundle.getString("id");
        bundle_time=bundle.getString("time");
        bundle_medision=bundle.getString("medision");
        bundle_equip_name=bundle.getString("equip_name");
        bundle_equip_id=bundle.getString("equip_id");

        time.setText(bundle_time);
        medision.setText(bundle_medision);
        spinner.setAdapter(spiner_adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                equip_name= spinner_data.get(i).getName();
                equip_id= spinner_data.get(i).geteId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
        Log.d("shen",medision_text);
        Toast.makeText(chiyao_addremind_additem.this,medision_text,Toast.LENGTH_SHORT).show();
        if(time_text==""||medision_text==""){
            Toast.makeText(chiyao_addremind_additem.this,"内容不为空！",Toast.LENGTH_SHORT).show();
        }
        else if(bundle_id!=null){
            //spinner.setSelection(Integer.valueOf(bundle_id),true);
            new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... params) {
                    chiyao_service mService=new chiyao_service();
                    return chiyao_service.change_UserInfo(bundle_id,time_text, medision_text,bundle_equip_name,bundle_equip_id);
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
                    return chiyao_service.uploadUserInfo(medision_text,time_text,equip_name,equip_id);

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
