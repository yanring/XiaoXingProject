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
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mygps.MyApplication;
import com.mygps.R;
import com.mygps.related_to_device.map.HttpRequest.GpsRequestThread;
import com.mygps.related_to_device.map.adapter.MyEquipListAdapter;
import com.mygps.related_to_device.map.model.Equip;
import com.mygps.related_to_device.map.service.MyEquipListService;
import com.mygps.unrelated_to_function.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by HowieWang on 2016/3/9.
 */
public class MyEquipListActivity extends AppCompatActivity {

    MyApplication app;
    MyEquipListService service;


    ListView equipList;
    MyEquipListAdapter adp;

    ProgressDialog pro;
    ArrayList<Equip> equips;

    SwitchCompat functionSwitch;

    FloatingActionButton FABAdd;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equiplist);
        //临时添加一个跳转
      /*  ImageButton button = (ImageButton)findViewById(R.id.service_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyEquipListActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });*/
        mContext = this;
        initView();

        //测试thread
        GpsRequestThread gpsRequestThread = new GpsRequestThread(this, "867967020452449");
        gpsRequestThread.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        adp.notifyDataSetChanged();
    }

    private void initView() {


        showPro();

        functionSwitch = (SwitchCompat) findViewById(R.id.function_switch);
        FABAdd = (FloatingActionButton) findViewById(R.id.activityEquiplistAdd);

        app = (MyApplication) getApplication();
        equips = app.getEquips();
        service = new MyEquipListService(this, equips);
        Context context = this;
        equipList = (ListView) findViewById(R.id.equiplist);
        equipList.setDividerHeight(0);

        adp = new MyEquipListAdapter(this, equips);
        equipList.setAdapter(adp);

        service.getEquips(app.getUser().getUsername());
        adp.notifyDataSetChanged();

        functionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    functionSwitch.setText("实时定位");
                } else {
                    functionSwitch.setText("历史轨迹");
                }
            }
        });
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
                Toast.makeText(MyEquipListActivity.this, "fw", Toast.LENGTH_SHORT).show();
                new deleteEquipDialog(position,MyEquipListActivity.this).show(getSupportFragmentManager(), null);
            }

            @Override
            public void onPenClick(int position) {
                startActivity(new Intent(MyEquipListActivity.this, MyEquipPenActivity.class));
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
                    String phoneStr =  phone.getText().toString();

                    /**
                     * 这里做号码和名称的检测
                     */

                    final Equip equip = new Equip(phoneStr, nameStr, app.getUser().getUsername());
                    equip.save(MyEquipListActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {

                            app.getEquips().add(equip);
                            adp.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            new AddEquipDialog().show(getSupportFragmentManager(), null);
                        }
                    });

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

    class deleteEquipDialog extends DialogFragment {
        int position = 0;
        Context context;
        public deleteEquipDialog(int position,Context context) {
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
            builder.setMessage("设备名:" + equips.get(position).getName() + "\n电话号码:" + equips.get(position).getPhoneID());
            builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //delete_device(position);
                    final Equip equip = equips.get(position);
                    equips.remove(position);
                    adp.notifyDataSetChanged();
                    BmobQuery<Equip> query = new BmobQuery<Equip>();
                    query.addWhereEqualTo("phoneID", equip.getPhoneID());
                    query.addWhereEqualTo("name", equip.getName());
                    query.addWhereEqualTo("username", equip.getUsername());
                    query.setLimit(1);
                    Log.i("Bomb1",mContext+"");
                    query.findObjects(mContext, new FindListener<Equip>() {
                        @Override
                        public void onSuccess(List<Equip> list) {



                            Log.i("Bomb","查询成功：共"+list.size()+"条数据。");
                            for(Equip equips: list)
                            {

                                Log.i("Bomb","删除数据id："+equips.getObjectId());
                                Log.i("Bomb","删除数据name："+equips.getName());
                                Equip DeleteEquips = new Equip(null,null,null);
                                DeleteEquips.setObjectId(equips.getObjectId());
                                DeleteEquips.delete(mContext, new DeleteListener() {
                                    @Override
                                    public void onSuccess() {
                                        Log.i("Bomb","成功删除一条数据");
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {
                                        Log.i("Bomb","删除失败："+s);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onError(int i, String s) {
                            Log.i("Bomb","查询失败："+s);
                            Log.i("Bomb2",mContext+"");
                        }
                    });
                }
            });
            builder.setNegativeButton("取消", null);
            return builder.create();
        }
//        public void delete_device(int position)
//        {
//            final Equip equip = equips.get(position);
//            equips.remove(position);
//            adp.notifyDataSetChanged();
//            BmobQuery<Equip> query = new BmobQuery<Equip>();
//            query.addWhereEqualTo("phoneID", equip.getPhoneID());
//            query.addWhereEqualTo("name", equip.getName());
//            query.addWhereEqualTo("username", equip.getUsername());
//            query.setLimit(1);
//            query.findObjects(getContext(), new FindListener<Equip>() {
//                @Override
//                public void onSuccess(List<Equip> list) {
//
//                    Context context = getContext();
//
//                    Log.i("Bomb","查询成功：共"+list.size()+"条数据。"+MyEquipListActivity.this);
//                    for(Equip equips: list)
//                    {
//
//                        Log.i("Bomb","删除数据id："+equips.getObjectId());
//                        equips.delete(context, new DeleteListener() {
//                            @Override
//                            public void onSuccess() {
//                                Log.i("Bomb","成功删除一条数据");
//                            }
//
//                            @Override
//                            public void onFailure(int i, String s) {
//                                Log.i("Bomb","删除失败："+s);
//                            }
//                        });
//                    }
//                }
//
//                @Override
//                public void onError(int i, String s) {
//                    Log.i("Bomb","查询失败："+s);
//                }
//            });
//        }
    }

}
