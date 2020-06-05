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

public class register extends AppCompatActivity {
    EditText name_input;  //用户名
    EditText pass_input;
    private static final String TAG = "Qingnang";
    private String ip = "115.159.3.231:8000";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        name_input=(EditText) findViewById(R.id.account_input);  //获取用户名
        pass_input=(EditText) findViewById(R.id.password_input); //密码

        Button btn1 = findViewById(R.id.registerr);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerr();
            }
        });
        Button btn2 = findViewById(R.id.exit);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                添加注册，加上成功就跳转的判断
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
            }
        });


    }
    private void registerr(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("user_name", name_input.getText().toString())
                        .add("old_password", pass_input.getText().toString())
                        .add("fun", "register")
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
                            if (rd.equals("Registration Success!")) {
                                Toast.makeText(register.this, "注册成功！", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(register.this, login.class);
                                startActivity(intent);
                            }
                            else
                                Toast.makeText(register.this,"用户名重复，请换一个试试~",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(register.this,"注册异常，请检查网络",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
}
