package com.example.qingnang;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MedicineInfo extends AppCompatActivity {

    private static final String TAG = "Qingnang";
    private List<Medi> medis = getDataFromDB();
    private RecyclerView recyclerView;
    private MediAdapter adapter;
    private FloatingActionButton fab;
    private int mod = 0;
    private String ip = "115.159.3.231:8000";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        sync();
    }

    private void init(){

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView= findViewById(R.id.recycler_view);
        adapter = new MediAdapter(medis);
        adapter.setOnItemClickListener(new MediAdapter.OnItemOnClickListener() {
            @Override
            public void onItemOnClick(View view, int pos) {
                Toast.makeText(MedicineInfo.this,"长按可删除哦~",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongOnClick(View view, int pos) {
                showPopMenu(view,pos);
            }
        });
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //进入编辑页面：
                Intent intent = new Intent(MedicineInfo.this,EditMedi.class);
                startActivityForResult(intent,1);
            }
        });

        TextView sum = (TextView) findViewById(R.id.sum);
        sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mod==0)
                    mod=1;
                else
                    mod = 0;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK ){
                    final Medi medi =(Medi) data.getSerializableExtra("medi");
                    medi.setUploaded(false);
                    medi.save();

                    medis.add(0,medi);
                    adapter.notifyItemInserted(0);
                    recyclerView.scrollToPosition(0);
                    addMedi(medi,1);

                    Snackbar.make(fab,"添加成功",Snackbar.LENGTH_LONG)
                            .setAction("撤销", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    deleteMedi(medi,0);
                                }
                            })
                            .show();

                }
        }
    }
    private void sync(){
        List<Medi> medis = LitePal.where("uploaded=?","0").find(Medi.class);
        for (Medi medi:medis){
            addMedi(medi,0);
        }
    }
    private void addMedi(final Medi medi,final int n){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("user_name", "LiHua")
                        .add("password", "123456")
                        .add("fun", "create")
                        .add("aged_name", "LiDahua")
                        .add("medicament_name",String.valueOf(medi.getName()))
                        .add("dosage", String.valueOf(medi.getDosage()))
                        .add("unit", "piece")
                        .add("freq",String.valueOf(medi.gettime()))
                        .build();
                Request request = new Request.Builder()
                        .url("http://"+ip+"/medicament/")
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    final String rd = response.body().string();
                    Log.d(TAG, "get response : "+rd);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            medi.setUploaded(true);
                            medi.save();
                            if (n==1)
                                Toast.makeText(MedicineInfo.this,rd,Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (n==1)
                                Toast.makeText(MedicineInfo.this,"添加失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    private void deleteMedi(final Medi medi,final int pos){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("user_name", "LiHua")
                        .add("password", "123456")
                        .add("fun", "delete")
                        .add("aged_name", "LiDahua")
                        .add("medicament_name",String.valueOf(medi.getName()))
                        .build();
                Request request = new Request.Builder()
                        .url("http://"+ip+"/medicament/")
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    final String rd1 = response.body().string();
                    Log.d(TAG, "get response : "+ rd1);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            medis.remove(pos);
                            adapter.notifyItemRemoved(pos);
                            recyclerView.scrollToPosition(pos);
                            Toast.makeText(MedicineInfo.this,rd1,Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MedicineInfo.this,"删除失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    private List<Medi> getDataFromDB(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        List<Medi> medis = LitePal.where("year=?",String.valueOf(year))
                .where("month=?",String.valueOf(month))
                .find(Medi.class);
        Collections.reverse(medis);
        return medis;
    }

    public void showPopMenu(View view,final int pos){
        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_item,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Medi pa = medis.get(pos);
                deleteMedi(pa,pos);
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
            }
        });
        popupMenu.show();
    }
}
