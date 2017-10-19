package com.example.user.lab3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemDetail extends Activity {
    private TextView tv_item_name,tv_detail_price,tv_detail_message;
    private ImageButton btn_return,btn_shopping;
    private ImageView iv_item,iv_tag;
    private ListView ls_detail;
    private Boolean flag;
    private Intent result_intent;
    private int getId;
    private MainActivity m = new MainActivity();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);
        findbyid();
        result_intent = new Intent();
        getId = getIntent().getIntExtra("id",0);
        tv_item_name.setText(m.getbyid(getId).getItem_name());
        tv_detail_message.setText(m.getbyid(getId).getType()+" "+m.getbyid(getId).getMessage());
        tv_detail_price.setText(m.getbyid(getId).getPrice());
        iv_item.setImageResource(m.getbyid(getId).getItem_icon_id());

        flag = getIntent().getBooleanExtra("flag",false);
//        flag = m.getbyid(getId).getFlag();
        change_state(flag);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_text);
        adapter.add("一键下单");
        adapter.add("分享商品");
        adapter.add("不感兴趣");
        adapter.add("查看更多商品促销消息");
        ls_detail.setAdapter(adapter);

        btn_shopping.setOnClickListener(new View.OnClickListener() {
            int times = 0;
            @Override
            public void onClick(View v) {
                times ++;
                result_intent.putExtra("item",times);
                setResult(RESULT_OK, result_intent);
                Toast.makeText(ItemDetail.this,"商品已添加到购物车",Toast.LENGTH_SHORT).show();
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
                result_intent.putExtra("tag",flag);
                setResult(RESULT_OK, result_intent);
            }
        });

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
