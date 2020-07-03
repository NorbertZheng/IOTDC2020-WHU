package com.example.qingnang;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.util.Calendar;
import java.util.Date;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class EditMedi extends SwipeBackActivity {

    private TextView date ;
    private TextInputEditText medi_name , dosage, time;
    private ImageButton button;
    private Medi medi;
    private static final String TAG = "EditMedi";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medi);
        init();
    }
    private void init(){
        setSwipeBackEnable(true);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("添加药品");
        setSupportActionBar(toolbar);

        date=(TextView)findViewById(R.id.date);
        medi_name = (TextInputEditText)findViewById(R.id.medi_name);
        dosage = (TextInputEditText)findViewById(R.id.dosage);
        time = (TextInputEditText)findViewById(R.id.time);
        button = (ImageButton)findViewById(R.id.ok);

        medi = new Medi();

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        medi.setYear(year);
        int month = calendar.get(Calendar.MONTH)+1;
        medi.setMonth(month);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        medi.setDay(day);
        date.setText(year+"/"+month+"/"+day);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (medi_name.getText().toString().trim().isEmpty()){
                    Toast.makeText(EditMedi.this,"请输入药品名称",Toast.LENGTH_SHORT).show();
                    return;
                }
                String t = dosage.getText().toString().trim();
                if (t.isEmpty()){
                    Toast.makeText(EditMedi.this,"请输入药品每顿服用数量",Toast.LENGTH_SHORT).show();
                    return;
                }
                String tt = time.getText().toString().trim();
                medi.setName(medi_name.getText().toString());
                medi.setDosage(Double.valueOf(t));
                medi.setTime(Integer.valueOf(tt));
                Intent intent = new Intent();
                intent.putExtra("medi",medi);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
    private void pickTime(){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()){
            inputMethodManager.hideSoftInputFromWindow(date.getApplicationWindowToken(),0);
        }
        TimePickerView timePickerView = new TimePickerBuilder(EditMedi.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date d, View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(d);
                int year = calendar.get(Calendar.YEAR);
                medi.setYear(year);
                int month = calendar.get(Calendar.MONTH)+1;
                medi.setMonth(month);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                medi.setDay(day);
                date.setText(year+"/"+month+"/"+day);
            }
        }).build();
        timePickerView.show();
    }
}
