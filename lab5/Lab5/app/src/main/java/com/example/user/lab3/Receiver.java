package com.example.user.lab3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import java.util.Random;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Receiver extends BroadcastReceiver {
    public NotificationManager nm;
    @Override
    public void onReceive(Context context, Intent intent) {
        MainActivity m = new MainActivity();

        nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context);

        int rd = new Random().nextInt(10);
        PendingIntent pendingintent = PendingIntent.getActivity(context,0
                ,new Intent(context,ItemDetail.class).putExtra("id",rd)
                ,PendingIntent.FLAG_UPDATE_CURRENT);
        nBuilder.setContentIntent(pendingintent).setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT).setAutoCancel(true);

        Bitmap bm = BitmapFactory.decodeResource(context.getResources(),R.drawable.shoplist);
        nBuilder.setTicker("lab4").setContentTitle("新商品热卖")
                .setContentText(String.format("%s仅售%s!",m.getbyid(rd).getItem_name(),m.getbyid(rd).getPrice()))
                .setSmallIcon(m.getbyid(intent.getIntExtra("id",0)).getItem_icon_id())
                .setLargeIcon(bm);

        Notification notification = nBuilder.build();
        nm.notify(R.layout.activity_main,notification);
    }
}
