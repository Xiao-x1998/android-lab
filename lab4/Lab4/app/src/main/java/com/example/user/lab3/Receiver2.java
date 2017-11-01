package com.example.user.lab3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;


public class Receiver2 extends BroadcastReceiver {
    public NotificationManager nm;
    private int times = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
        MainActivity m = new MainActivity();

        nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context);
        PendingIntent pendingintent = PendingIntent.getBroadcast(context,0,new Intent(context,Receiver3.class)
                ,PendingIntent.FLAG_UPDATE_CURRENT);

        nBuilder.setContentIntent(pendingintent).setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT).setAutoCancel(true);
        nBuilder.setTicker("lab4").setContentTitle("马上下单")
                .setContentText(String.format("%s已添加到购物车",m.getbyid(intent.getIntExtra("id",0)).getItem_name()))
                .setSmallIcon(R.drawable.shoplist);

        Notification notification = nBuilder.build();
        nm.notify(times,notification);
        times ++;
    }
}
