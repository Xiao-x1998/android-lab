package com.example.user.lab3;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.Random;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,int rd,Intent intent) {
        MainActivity m = new MainActivity();
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        PendingIntent pi;
        if(rd == -2)
        {
            views.setTextViewText(R.id.appwidget_text, context.getString(R.string.message_widget));
            views.setImageViewResource(R.id.appwidget_image, R.drawable.shoplist);
            pi = PendingIntent.getActivity(context,-1
                    ,new Intent(context, MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
        }
        else if(rd == -1)
        {
            String str = String.format("%s已添加到购物车",m.getbyid(intent.getIntExtra("id",0)).getItem_name());
            int icon_id = m.getbyid(intent.getIntExtra("id",0)).getItem_icon_id();
            views.setTextViewText(R.id.appwidget_text, str);
            views.setImageViewResource(R.id.appwidget_image, icon_id);
            pi = PendingIntent.getBroadcast(context,-1,new Intent(context,Receiver3.class)
                    ,PendingIntent.FLAG_UPDATE_CURRENT);
        }
        else
        {
            String str = String.format("%s仅售%s!",m.getbyid(rd).getItem_name(),m.getbyid(rd).getPrice());
            int icon_id = m.getbyid(rd).getItem_icon_id();
            views.setTextViewText(R.id.appwidget_text, str);
            views.setImageViewResource(R.id.appwidget_image, icon_id);
            pi = PendingIntent.getActivity(context,-1
                    ,new Intent(context,ItemDetail.class).putExtra("id",rd)
                    ,PendingIntent.FLAG_UPDATE_CURRENT);
        }
        views.setOnClickPendingIntent(R.id.ll,pi);

        // Instruct the widget manager to update the widget
        ComponentName cn = new ComponentName(context,NewAppWidget.class);
        appWidgetManager.updateAppWidget(cn, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        updateAppWidget(context, appWidgetManager, -2, new Intent());
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if(intent.getAction().equals("StartByStatic"))
        {
            int rd = new Random().nextInt(10);
            updateAppWidget(context, AppWidgetManager.getInstance(context), rd, intent);
        }
        else if(intent.getAction().equals("StartByDynamic"))
        {
            updateAppWidget(context, AppWidgetManager.getInstance(context), -1, intent);
        }
        else if(intent.getAction().equals("END"))
        {
            updateAppWidget(context, AppWidgetManager.getInstance(context), -2, intent);
        }
    }
}

