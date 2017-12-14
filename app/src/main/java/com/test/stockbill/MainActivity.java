package com.test.stockbill;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int[] idarr = new int[]{R.id.tv1,R.id.tv2,R.id.tv3,R.id.tv4,R.id.tv5,R.id.tv6,R.id.tv7,R.id.tv8};
    private int[] colorarr = new int[]{0xFFFFFFFF,0xFFFFFFFF,0xFFFFFFFF,0xFFFFFFFF,0xFFFFFFFF,0xFFFFFFFF,0xFFFFFFFF,0xFFFFFFFF};
    private int[] bgarr = new int[]{0xFFFF6666,0xFF1e67c0,0xFFd47756,0xFF5a626f,0xFFee7434,0xFF3eadeb,0xFF0385fd,0xFF00a179};
    private String[] textarr = new String[]{"通讯电脑","生活厨卫","家用电器","日用百货","母婴玩具","户外体育","汽车配件","其它商品"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for(int i=0;i<idarr.length;i++){
            TextView tv = (TextView)findViewById(idarr[i]);
            tv.setText(textarr[i]);
            tv.setBackgroundColor(bgarr[i]);
            tv.setTextColor(colorarr[i]);
            tv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    TextView t = (TextView)v;
                    showMessage("您点击的是 : "+t.getText().toString());
                }
            });
        }
    }

    private void showMessage(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}