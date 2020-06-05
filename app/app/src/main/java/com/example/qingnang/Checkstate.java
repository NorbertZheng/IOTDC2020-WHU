package com.example.qingnang;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import android.util.Log;
import java.io.IOException;

public class Checkstate extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkinfo);
        getRequest();
    }

    private void getRequest() {
        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
//        Request request = new Request.Builder().url("http://www.baidu.com").method("GET",null).build();
        Request request = new Request.Builder().url("http://115.159.3.231:8000/hello").method("GET",null).build();
        //3.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);
        //4.请求加入调度，重写回调方法
        call.enqueue(new Callback() {
            //请求失败执行的方法
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(Checkstate.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            //请求成功执行的方法
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                Log.d("response",data);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Checkstate.this, "查询服务器中", Toast.LENGTH_SHORT).show();
                        Toast.makeText(Checkstate.this, "成功接收", Toast.LENGTH_SHORT).show();
                        Intent intent = getIntent();
                        String[] parts = data.split("-");
                        String part1 = parts[0];
                        String part2 = parts[1];
                        String part3 = parts[2];
                        String part4 = parts[3];
                        String part5 = parts[4];
                        TextView text1 = findViewById(R.id.medi1);
                        text1.setText(part1);
                        TextView text2 = findViewById(R.id.medi2);
                        text2.setText(part2);
                        TextView text3 = findViewById(R.id.medi3);
                        text3.setText(part3);
                        TextView text4 = findViewById(R.id.medi4);
                        text4.setText(part4);
                        TextView text5 = findViewById(R.id.tf);
                        text5.setText(part5);
                    }
                });
            }
        });
    }
}
