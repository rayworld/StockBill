package com.test.stockbill;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Main2Activity extends Activity {

    public ListView mListView;
    public ArrayList<NewsBean> newsBeanArrayList = new ArrayList <NewsBean>();
    public JSONObject object;
    private final String URL = "http://www.imooc.com/api/teacher?type=4&num=30";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        init();
    }

    private void init(){
        newsBeanArrayList.clear();
        mListView = findViewById(R.id.lvStockBill);
        new Thread(new Runnable(){

            @Override
            public void run() {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url(URL).build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    String data = response.body().string();
                    //Log.d("sys",data);
                    JsonJx(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
                NewsBean newsBean = newsBeanArrayList.get(position);
                //Toast.makeText(getApplicationContext(),newsBean.newsTitle,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                Bundle bundle=new Bundle();
                bundle.putString("itle", newsBean.newsTitle);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void JsonJx(String data){
        //将字符串转换成jsonObject对象
        if(data != null) {
            try {
                JSONObject jsonObject = new JSONObject(data);
                //获取到json数据中里的activity数组内容
                JSONArray resultJsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < resultJsonArray.length(); i++) {
                    object = resultJsonArray.getJSONObject(i);
                    NewsBean newsBean = new NewsBean();
                    newsBean.newsIconUrl = object.getString("picSmall");
                    newsBean.newsTitle = object.getString("name");
                    newsBean.newsContent = object.getString("description");
                    newsBeanArrayList.add(newsBean);
                }
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
                //Log.d("size",newsBeanArrayList.size() + "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    //Handler运行在主线程中(UI线程中)，  它与子线程可以通过Message对象来传递数据
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    Mybaseadapter list_item=new Mybaseadapter();
                    mListView.setAdapter(list_item);
                    break;
            }
        }
    };
    //listview的适配器
    public class Mybaseadapter extends BaseAdapter {

        @Override
        public int getCount() { return newsBeanArrayList.size(); }

        @Override
        public Object getItem(int position) {
            return newsBeanArrayList.get(position);
        }

        @Override
        public long getItemId(int position) { return position; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();

                convertView = getLayoutInflater().inflate(R.layout.stockbilllist, null);
                viewHolder.vhBillTitle = (TextView) convertView.findViewById(R.id.tvBillTitle);
                viewHolder.vhBillContent = (TextView) convertView.findViewById(R.id.tvBillContent);
                viewHolder.vhBillIcon = (ImageView)convertView.findViewById(R.id.ivBillIcon);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.vhBillTitle.setText(newsBeanArrayList.get(position).newsTitle.toString());
            viewHolder.vhBillContent.setText(newsBeanArrayList.get(position).newsContent.toString());
            //viewHolder.vhBillIcon.setImageURI(Uri.parse(newsBeanArrayList.get(position).newsIconUrl.toString()));
            viewHolder.vhBillIcon.setImageResource(R.drawable.ic_launcher_background);
            return convertView;
        }

    }

    final static class ViewHolder {
        TextView vhBillTitle;
        TextView vhBillContent;
        ImageView vhBillIcon;
    }
}
