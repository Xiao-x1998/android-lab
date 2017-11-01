package com.example.user.lab3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private CellData[] Data = new CellData[]
            {
                    new CellData("¥ 5.00","Enchated Forest","作者","Johanna",R.drawable.img_enchatedforest,0),
                    new CellData("¥ 59.00","BasfordArla Milk","产地","德国",R.drawable.img_arla,1),
                    new CellData("¥ 79.00","Devondale Milk","产地","澳大利亚",R.drawable.img_devondale,2),
                    new CellData("¥ 2399.00","Kindle Oasis","版本","8GB",R.drawable.kindle,3),
                    new CellData("¥ 179.00","waitrose 早餐麦片","重量","2Kg",R.drawable.waitrose,4),
                    new CellData("¥ 14.90","Mcvitie's 饼干","产地","英国",R.drawable.img_mcvitie,5),
                    new CellData("¥ 132.59","Ferrero Rocher","重量","300g",R.drawable.img_ferrero,6),
                    new CellData("¥ 141.43","Maltesers","重量","118g",R.drawable.img_maltesers,7),
                    new CellData("¥ 139.43","Lindt","重量","249g",R.drawable.img_lindt,8),
                    new CellData("¥ 28.90","Borggreve","重量","640g",R.drawable.img_borggreve,9),
            };

    private RecyclerView recycler_view;
    private CellData[] allData = new CellData[]{};
    private CellData[] myData =  new CellData[]{};
    private HomeAdapter homeAdapter;
    private FloatingActionButton fab;
    private BaseAdapter baseAdapter;
    private ListView lv;
    private Boolean flag = true;
    private FrameLayout framelayout;
    private LinearLayout linear_list;

    private ScaleAnimation creat =
            new ScaleAnimation(0,1,0,1, Animation.RELATIVE_TO_PARENT,1,Animation.RELATIVE_TO_PARENT,1);
    private ScaleAnimation destroy =
            new ScaleAnimation(1,1,1,0, Animation.RELATIVE_TO_PARENT,0,Animation.RELATIVE_TO_PARENT,1);

    private Boolean create_view = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        creat.setDuration(2000);
        destroy.setDuration(2000);
        framelayout = (FrameLayout) findViewById(R.id.framelayout);
        linear_list = (LinearLayout) findViewById(R.id.linler_list);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        fab = (FloatingActionButton) findViewById(R.id.main_icon);

        Intent intent = new Intent("StartByStatic");
        sendBroadcast(intent);

        homeAdapter = new HomeAdapter();
        setBaseAdapter(myData);
        for(int i = 0;i < Data.length;i ++) AddAllData(i);

        framelayout.removeView(recycler_view);
        initial();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = !flag;
                initial();
                create_view = false;
            }
        });
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowMessageEvent(MessageEvent messageEvent) {
        if(!messageEvent.getFlag() && flag)
        {
            flag = messageEvent.getFlag();
            initial();
        }
        if(messageEvent.getTimes() == -1);
        else if(messageEvent.getTimes() == -2) Data[messageEvent.getId()].setFlag(!Data[messageEvent.getId()].getFlag());
        else
            for(int i = 0;i < messageEvent.getTimes();i ++)
                AddMyData(messageEvent.getId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initial()
    {
        if(flag)
        {
            framelayout.removeView(linear_list);
            framelayout.addView(recycler_view);
            recycler_view.setLayoutManager(new LinearLayoutManager(this));
            recycler_view.setAdapter(homeAdapter);
            framelayout.removeView(fab);
            framelayout.addView(fab);
            fab.setIcon(R.drawable.shoplist);
        }
        else
        {
            framelayout.removeView(recycler_view);
            framelayout.addView(linear_list);
            framelayout.removeView(fab);
            framelayout.addView(fab);
            fab.setIcon(R.drawable.mainpage);
            lv = (ListView) findViewById(R.id.list_view);
            setBaseAdapter(myData);
            lv.setAdapter(baseAdapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CellData data = (CellData) baseAdapter.getItem(position);
                    Intent intent = new Intent(MainActivity.this,ItemDetail.class);
                    intent.putExtra("id",data.getId());
                    intent.putExtra("flag",data.getFlag());
                    intent.putExtra("now",flag);
                    startActivity(intent);
                }
            });
            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    CellData onedata = (CellData) baseAdapter.getItem(position);
                    AlertDialog.Builder alterDialog = new AlertDialog.Builder(MainActivity.this);
                    alterDialog.setTitle("移除商品").setMessage(String.format("从购物车移除%s？",onedata.getItem_name()))
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DeleteMyData(position);
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                    return false;
                }
            });

            try {
                for(int i = 0;i < getIntent().getIntExtra("item",0);i ++)
                    AddMyData(getIntent().getIntExtra("item",0));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void AddAllData(int id)
    {
        CellData[] temp = new CellData[allData.length+1];
        for(int i = 0;i < allData.length;i ++) temp[i] = allData[i];
        temp[allData.length] = Data[id];
        allData = temp;
    }

    public void DeleteAllData(int position)
    {
        CellData[] temp = new CellData[allData.length-1];
        for(int i = 0;i < position;i ++) temp[i] = allData[i];
        for(int i = position+1;i < allData.length;i ++) temp[i-1] = allData[i];
        allData = temp;
        homeAdapter = new HomeAdapter();
        recycler_view.setAdapter(homeAdapter);
    }
    public void AddMyData(int id)
    {
        CellData[] temp = new CellData[myData.length+1];
        for(int i = 0;i < myData.length;i ++) temp[i] = myData[i];
        temp[myData.length] = Data[id];
        myData = temp;
        if(!flag)
        {
            setBaseAdapter(myData);
            lv.setAdapter(baseAdapter);
        }
    }

    public void DeleteMyData(int position)
    {
        CellData[] temp = new CellData[myData.length-1];
        for(int i = 0;i < position;i ++) temp[i] = myData[i];
        for(int i = position+1;i < myData.length;i ++) temp[i-1] = myData[i];
        myData = temp;
        setBaseAdapter(myData);
        lv.setAdapter(baseAdapter);
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    MainActivity.this).inflate(R.layout.item_maincell, parent, false));
            return holder;
        }


        public CellData getItem(int position)
        {
            return allData[position];
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            holder.tv_main_FirstLetter.setText(allData[position].getItem_name().toUpperCase().substring(0,1));
            holder.tv_main_Name.setText(allData[position].getItem_name());
            holder.id = allData[position].getId();
            holder.position = position;
        }

        @Override
        public int getItemCount()
        {
            return allData.length;
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {

            TextView tv_main_FirstLetter,tv_main_Name;
            int id,position;

            public MyViewHolder(View view)
            {
                super(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,ItemDetail.class);
                        intent.putExtra("id",id);
                        intent.putExtra("flag",Data[id].getFlag());
                        startActivityForResult(intent,id);
                    }
                });
                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {;
                        v.startAnimation(destroy);
                        create_view = false;
                        DeleteAllData(position);
                        Toast.makeText(MainActivity.this,"移除第"+position+"个商品",Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                tv_main_FirstLetter = (TextView) view.findViewById(R.id.tv_main_FirstLetter);
                tv_main_Name = (TextView) view.findViewById(R.id.tv_main_Name);

                if(create_view) view.startAnimation(creat);
            }
        }
    }

    private void setBaseAdapter(final CellData[] new_data)
    {
        baseAdapter = new BaseAdapter() {
            private CellData[] data = new_data;
            @Override
            public int getCount() {
                return data.length;
            }

            @Override
            public CellData getItem(int position) {
                return data[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                LinearLayout ll = null;
                if(convertView != null) ll = (LinearLayout)convertView;
                else ll = (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(R.layout.item_celldata,null);

                CellData data = getItem(position);

                TextView FirstLetter = (TextView) ll.findViewById(R.id.tv_FirstLetter);
                TextView Name = (TextView)ll.findViewById(R.id.tv_Name);
                TextView Price = (TextView)ll.findViewById(R.id.tv_Price);

                FirstLetter.setText(data.getItem_name().toUpperCase().substring(0,1));
                Name.setText(data.getItem_name());
                Price.setText(data.getPrice());

                return ll;
            }
        };
    }

    public CellData getbyid(int id)
    {
        return Data[id];
    }

}