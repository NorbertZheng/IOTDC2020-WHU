package com.example.qingnang;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class login extends AppCompatActivity {
    private static final String TAG = "login";
    private String ip = "115.159.3.231:8000";
    EditText name;  //用户名
    EditText pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        name= findViewById(R.id.account);  //获取用户名
        pass= findViewById(R.id.pwd); //密码
        Button btn2 = findViewById(R.id.register);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });
        Button btn1 = findViewById(R.id.login);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log_in();
            }
        });

    }
    private void log_in(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("user_name", name.getText().toString())
                        .add("old_password", pass.getText().toString())
                        .add("fun", "login")
                        .build();
                Request request = new Request.Builder()
                        .url("http://"+ip+"/user/")
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    final String rd = response.body().string();
                    Log.d(TAG, "get response : "+rd);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (rd.equals("True")) {
                                Toast.makeText(login.this, "登录成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(login.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else
                                Toast.makeText(login.this,"密码错误，请重新尝试！",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(login.this,"登录异常，请检查网络",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
}


