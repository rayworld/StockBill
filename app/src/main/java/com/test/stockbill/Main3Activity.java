package com.test.stockbill;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Main3Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Bundle bundle=this.getIntent().getExtras();
        Toast.makeText(this, bundle.get("title").toString(),Toast.LENGTH_SHORT).show();
    }

    public void back(View v){
        Intent intent=new Intent();
        //intent.setClassName("com.example.lession12_activity", "com.example.lession12_activity.MainActivity");
        ComponentName component=new ComponentName("com.test.stockbill", "com.test.stockbill.Main2Activity");
        intent.setComponent(component);
        startActivity(intent);
    }
}
