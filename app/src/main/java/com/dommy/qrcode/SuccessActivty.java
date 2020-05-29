package com.dommy.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dommy.qrcode.util.NetUtil;

public class SuccessActivty extends AppCompatActivity implements View.OnClickListener {

    Button tfcg;//投放成功按钮
    TextView name,leixing;//投放人姓名，垃圾类型 控件
    String lx,news;//垃圾类型 ， 二维码信息
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        name=findViewById(R.id.name);
        leixing=findViewById(R.id.lx);

        tfcg = findViewById(R.id.tfcg);
        tfcg.setOnClickListener(this);

        Intent intent = getIntent();//接收垃圾类型与二维码信息
        lx = intent.getStringExtra("lx");//垃圾类型
        news = intent.getStringExtra("news");//二维码信息

        new Thread(new Runnable() {
            @Override
            public void run() {
                final String state= NetUtil.LoginOfPost(lx,news);
                //执行在主线程上
                runOnUiThread(new Runnable() {
                    public void run() {
                        //就是在主线程上操作,弹出结果

                       if(state.trim().equals("error")){
                           Intent intent1 = new Intent(SuccessActivty.this,MainActivity.class);
                           finish();
                           Toast.makeText(SuccessActivty.this,"二维码无效",Toast.LENGTH_LONG).show();
                           startActivity(intent1);

                        }else{
                           name.setText(state);

                        }
                    }
                });
            }
        }).start();

        switch (lx){
            case "1":
                leixing.setText("餐厨垃圾");
                break;
            case "2":
                leixing.setText("可回收垃圾");
                break;
            case "3":
                leixing.setText("有害垃圾");
                break;
            case "4":
                leixing.setText("其他垃圾");
                break;
        }
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tfcg:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
