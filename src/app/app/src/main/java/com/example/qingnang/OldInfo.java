package com.example.qingnang;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class OldInfo extends AppCompatActivity {

    private Button button2, button3;
    private EditText name, height, weight, age, room, phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oldinfo);
        name = (EditText) findViewById(R.id.name);
        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        age = (EditText) findViewById(R.id.age);
        room = (EditText) findViewById(R.id.room);
        phone = (EditText) findViewById(R.id.phone);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("User", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("age",age.getText().toString());
                editor.putString("height",height.getText().toString());
                editor.putString("name",name.getText().toString());
                editor.putString("weight",weight.getText().toString());
                editor.putString("phone",phone.getText().toString());
                editor.putString("room",room.getText().toString());
//                //通过editor对象写入数据
//                edit.putString("name", name.getText().toString().trim());
////                edit.putString("age", age.getText().toString().trim());
////                edit.putString("height", height.getText().toString().trim());
////                edit.putString("weight", weight.getText().toString().trim());
////                edit.putString("phone", phone.getText().toString().trim());
////                edit.putString("room", room.getText().toString().trim());
//                //提交数据存入到xml文件中
                editor.apply();
                finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("User", Context.MODE_PRIVATE);
                String value = sp.getString("age","Null");
                age.setText(value);
                String value1 = sp.getString("name","Null");
                name.setText(value1);
                String value2 = sp.getString("height","Null");
                weight.setText(value2);
                String value3 = sp.getString("weight","Null");
                height.setText(value3);
                String value4 = sp.getString("phone","Null");
                phone.setText(value4);
                String value5 = sp.getString("room","Null");
                room.setText(value5);
            }
        });
    }
}

//    }
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode){
//            case 1:
//                if (resultCode == RESULT_OK ){
//
//                    OldData olddata =(OldData) data.getSerializableExtra("olddata");
//                    name.setText(olddata.getName());
//                    height.setText(String.valueOf(olddata.getHeight()));
//                    weight.setText(String.valueOf(olddata.getWeight()));
//                    age.setText(olddata.getage());
//                    room.setText(olddata.getAddress());
//                    phone.setText(olddata.getNumber());
//
//
//                }
//        }
//    }



