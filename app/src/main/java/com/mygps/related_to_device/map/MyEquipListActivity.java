package com.mygps.related_to_device.map;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mygps.MyApplication;
import com.mygps.R;
import com.mygps.related_to_device.map.HttpRequest.GpsRequestThread;
import com.mygps.related_to_device.map.adapter.MyEquipListAdapter;
import com.mygps.related_to_device.map.model.Equipment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by HowieWang on 2016/3/9.
 */
public class MyEquipListActivity extends AppCompatActivity {

    MyApplication app;

    ListView equipList;
    MyEquipListAdapter adp;

    ProgressDialog pro;
    List<Equipment> equips;

    FloatingActionButton FABAdd;
    private Context mContext;

    RequestQueue queue;

    private static final String ADDEQUIP_URL = "http://123.206.30.177/GPSServer/user/addEquip.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equiplist);

        mContext = this;
        initView();

        queue = Volley.newRequestQueue(this);
        getEquipmentDate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adp.notifyDataSetChanged();
    }


    private void getEquipmentDate() {


        //测试thread
        GpsRequestThread gpsRequestThread = new GpsRequestThread(this, "867521029822977");
        gpsRequestThread.start();
    }

    private void initView() {


        FABAdd = (FloatingActionButton) findViewById(R.id.activityEquiplistAdd);

        app = (MyApplication) getApplication();
        equips = app.getEquips();

        equipList = (ListView) findViewById(R.id.equiplist);
        equipList.setDividerHeight(0);

        adp = new MyEquipListAdapter(this, equips);
        equipList.setAdapter(adp);

        adp.setOnViewClickListener(new MyEquipListAdapter.OnViewClickListener() {
            @Override
            public void onItemClick(int position) {
                /**
                 * 在这里可以查看实时位置，也可以查看历史轨迹
                 */
                Intent intent;
                intent = new Intent(MyEquipListActivity.this, MyEquipDetailActivity.class);
                intent.putExtra("equipPos", position);
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(int position) {
                new deleteEquipDialog(position, MyEquipListActivity.this).show(getSupportFragmentManager(), null);
            }

            @Override
            public void onPenClick(int position) {
                startActivity(new Intent(MyEquipListActivity.this, MyEquipPenActivity.class).putExtra("equipPos", position));
            }
        });


        FABAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AddEquipDialog().show(getSupportFragmentManager(), null);
            }
        });
    }

    public void notifyDataSetChanged() {
        adp.notifyDataSetChanged();
    }

    public void showPro() {
        if (pro == null) {
            pro = ProgressDialog.show(this, null, "正在加载");
        } else {
            pro.show();
        }
    }

    public void disPro() {
        pro.dismiss();
    }

    @SuppressLint("ValidFragment")
    class AddEquipDialog extends DialogFragment {
        @NonNull
        @Override

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            super.onCreateDialog(savedInstanceState);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("添加设备");
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_equiplist_add_view, null);

            final EditText name = (EditText) view.findViewById(R.id.addequip_name);
            final EditText phone = (EditText) view.findViewById(R.id.addequip_phone);
            builder.setView(view);
            builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String nameStr = name.getText().toString();
                    String phoneStr = phone.getText().toString();

                    /**
                     * 这里做号码和名称的检测
                     */
                    final Equipment equip = new Equipment();
                    equip.setPhone(phoneStr);
                    equip.setName(nameStr);
                    equip.setId(nameStr);
                    equip.setuId(app.getUser().getId());
                    StringRequest addRequest = new StringRequest(Request.Method.POST, ADDEQUIP_URL, new Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            equips.add(equip);
                            adp.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MyEquipListActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            super.getParams();
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("id", equip.getId());
                            map.put("phone", equip.getPhone());
                            map.put("name", equip.getName());
                            map.put("uId", "" + equip.getuId());
                            return map;
                        }
                    };
                    queue.add(addRequest);
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            return builder.create();
        }
    }

    public class deleteEquipDialog extends DialogFragment {
        int position = 0;
        Context context;

        public deleteEquipDialog(int position, Context context) {
            super();
            this.position = position;
            this.context = context;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            super.onCreateDialog(savedInstanceState);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("删除设备");
            builder.setMessage("设备名:" + equips.get(position).getName() + "\n电话号码:" + equips.get(position).getPhone());
            builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //delete_device(position);
                    final Equipment equip = equips.get(position);

                }
            });
            builder.setNegativeButton("取消", null);
            return builder.create();
        }

    }

}
