package com.mygps.related_to_device.map;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.mygps.AppConf;
import com.mygps.MyApplication;
import com.mygps.R;
import com.mygps.related_to_device.map.HttpRequest.GpsRequestThread;
import com.mygps.related_to_device.map.adapter.MyEquipListAdapter;

import com.mygps.related_to_device.map.model.Equipment;
import com.mygps.related_to_device.map.provider.URIList;

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

    private static final String ADDEQUIP_URL = AppConf.ServerPath + "user/addEquip.do";
    private GpsRequestThread mGpsRequestThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equiplist);

        mContext = this;
        initView();

        queue = Volley.newRequestQueue(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        adp.notifyDataSetChanged();
    }


    private void initView() {


        FABAdd = (FloatingActionButton) findViewById(R.id.activityEquiplistAdd);

        app = (MyApplication) getApplication();
        equips = app.getEquips();
        for (Equipment euip : equips) {//获取数据
            mGpsRequestThread = new GpsRequestThread(this, euip.geteId());
            mGpsRequestThread.start();
        }

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
            final EditText eIdET=(EditText)view.findViewById(R.id.addequip_id);
            builder.setView(view);
            builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String nameStr = name.getText().toString();
                    String id = eIdET.getText().toString();
                    final int phoneInt=Integer.parseInt(phone.getText().toString());


                    /**
                     * 这里做号码和名称的检测
                     */
                    final Equipment equip = new Equipment();
                    //   equip.setPhone(phoneStr);
                    equip.setName(nameStr);
                    equip.seteId(id);

                    equip.setuId(app.getUser().getId());
                    StringRequest addRequest = new StringRequest(Request.Method.POST, ADDEQUIP_URL, new Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.toLowerCase().equals("0")) {
                                equips.add(equip);
                                adp.notifyDataSetChanged();
                            } else if (response.toLowerCase().equals(1)) {
                                Toast.makeText(MyEquipListActivity.this, "设备ID不存在。。", Toast.LENGTH_LONG).show();

                            } else if (response.toLowerCase().equals("2")) {
                                Toast.makeText(MyEquipListActivity.this, "手机号码错误。。", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MyEquipListActivity.this, "未知错误", Toast.LENGTH_LONG).show();
                            }
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
                            map.put("eId", equip.geteId());
                            map.put("phone", phoneInt+"");
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
            builder.setMessage("设备名:" + equips.get(position).getName() + "\nName:" + equips.get(position).getName());
            builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //delete_device(position);
                    final Equipment equip = equips.get(position);
                    deleteEquip();

                }
            });
            builder.setNegativeButton("取消", null);
            return builder.create();
        }

        private void deleteEquip() {
            String url = AppConf.ServerPath + "user/deleteEquip.do?id=" + equips.get(position).getId();
            Log.i("aa", equips.get(position).geteId());
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(MyEquipListActivity.this, "shen", Toast.LENGTH_LONG).show();
                    Log.i("aa", "get请求成功" + response);
                    equips.remove(equips.get(position));
                    adp.notifyDataSetChanged();
                    //adp.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MyEquipListActivity.this, "请求失败！", Toast.LENGTH_LONG).show();
                    Log.i("aa", "get请求失败" + error);
                    //deleteLocalEquips();
                    //adp.notifyDataSetChanged();
                }
            });
            queue.add(request);
        }

        private void deleteLocalEquips() {
            Log.d("deleteLocal", "删除了本地设备！");
            ContentResolver contentResolver = mContext.getContentResolver();
            Uri uri = Uri.parse(URIList.GPS_URI);
            if ((contentResolver.query(uri, null, "id=" + equips.get(position).geteId(), null, null).getCount() != 0)) {
                contentResolver.delete(uri, "id=" + "?", new String[]{equips.get(position).geteId()});
            }
            contentResolver.notifyChange(uri, null);
        }
    }
}
