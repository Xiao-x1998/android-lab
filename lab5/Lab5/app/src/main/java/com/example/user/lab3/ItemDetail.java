package com.example.user.lab3;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

public class ItemDetail extends Activity {
    private TextView tv_item_name,tv_detail_price,tv_detail_message;
    private ImageButton btn_return,btn_shopping;
    private ImageView iv_item,iv_tag;
    private ListView ls_detail;
    private Boolean flag;
    private int getId;
    private MainActivity m = new MainActivity();
    private Receiver2 rc = new Receiver2();
    private NewAppWidget nw = new NewAppWidget();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);
        findbyid();
        getId = getIntent().getIntExtra("id",0);
        tv_item_name.setText(m.getbyid(getId).getItem_name());
        tv_detail_message.setText(m.getbyid(getId).getType()+" "+m.getbyid(getId).getMessage());
        tv_detail_price.setText(m.getbyid(getId).getPrice());
        iv_item.setImageResource(m.getbyid(getId).getItem_icon_id());

        flag = getIntent().getBooleanExtra("flag",false);
        change_state(flag);

        IntentFilter filter = new IntentFilter();
        filter.addAction("StartByDynamic");
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        registerReceiver(nw,filter);
        registerReceiver(rc,filter);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_text);
        adapter.add("一键下单");
        adapter.add("分享商品");
        adapter.add("不感兴趣");
        adapter.add("查看更多商品促销消息");
        ls_detail.setAdapter(adapter);

        btn_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ItemDetail.this,"商品已添加到购物车",Toast.LENGTH_SHORT).show();

                EventBus.getDefault().post(new MessageEvent(getId,1,true));
                Intent intent = new Intent("StartByDynamic");
                intent.putExtra("id",getId);
                sendBroadcast(intent);
            }
        });

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        iv_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = !flag;
                change_state(flag);

                EventBus.getDefault().post(new MessageEvent(getId,-2,true));
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(rc);
        unregisterReceiver(nw);
    }

    private void findbyid()
    {
        tv_detail_message = (TextView) findViewById(R.id.tv_detail_message);
        tv_detail_price = (TextView) findViewById(R.id.tv_detail_price);
        tv_item_name = (TextView) findViewById(R.id.tv_item_name);

        btn_return = (ImageButton) findViewById(R.id.btn_return);
        btn_shopping = (ImageButton) findViewById(R.id.btn_shopping);

        iv_item = (ImageView) findViewById(R.id.iv_item);
        iv_tag = (ImageView) findViewById(R.id.iv_tag);

        ls_detail = (ListView) findViewById(R.id.ls_detail);
    }

    private void change_state(boolean flag)
    {
        if(flag) iv_tag.setImageResource(R.drawable.full_star);
        else iv_tag.setImageResource(R.drawable.empty_star);
    }
}
