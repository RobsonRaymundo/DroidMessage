package ray.droid.com.droidmessage.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import ray.droid.com.droidmessage.R;
import ray.droid.com.droidmessage.activitys.DroidMainActivity;
import ray.droid.com.droidmessage.dbase.Persintencia;
import ray.droid.com.droidmessage.feature.Constantes;
import ray.droid.com.droidmessage.others.Methods;


/**
 * Created by Robson on 03/02/2016.
 */

public class DroidNotification extends DroidBaseNotification {

    private boolean sentBroadcast = false;
    CharSequence tit;


    public void sendNotification(String msg) {

        try {


            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this);

//Create the intent that’ll fire when the user taps the notification//

            //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.uol.com.br"));
            //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            //mBuilder.setContentIntent(pendingIntent);


            mBuilder.setSmallIcon(R.drawable.common_google_signin_btn_icon_dark);
            mBuilder.setContentTitle(tit);
            mBuilder.setContentText(msg);

            NotificationManager mNotificationManager =

                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            mNotificationManager.notify(001, mBuilder.build());
        } catch (Exception ex) {
            Log.d("DroidMessage", "Erro : " + ex.getMessage());
        }
    }


    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        sentBroadcast = false;

        String msgNotification = getNotificationKitKat(sbn);

        Context context = getBaseContext();


        if (!msgNotification.isEmpty()) {

            if (msgNotification.contains("enviar")) {
                Methods.EnviaMsg(context);
            } else if (msgNotification.contains("apagar")) {
                Methods.ApagarMsg(context);
            } else {
                Persintencia persintencia = new Persintencia(context);
                persintencia.InserirMensagens(msgNotification);
                sendNotification(msgNotification);
            }

            cancelAllNotification(context);



        }

    }


    public static void cancelAllNotification(Context ctx) {
        try {
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
            nMgr.cancelAll();
        }
        catch (Exception ex)
        {
            Log.d("DroidMessage", ex.getMessage());
        }
    }

    private void SendBroadCast(String msgNotification) {
        Intent mIntent = new Intent();
        mIntent.setAction(Constantes.CHAVERECEIVER);
        mIntent.addCategory(Intent.CATEGORY_DEFAULT);
        mIntent.putExtra(Constantes.CHAVERECEIVER, msgNotification);
        sendBroadcast(mIntent);
        sentBroadcast = true;
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    private String getNotificationKitKat(StatusBarNotification mStatusBarNotification) {
        String pack = mStatusBarNotification.getPackageName();// Package Name
        String msg = "";
        tit = "";
        if (pack.contains("com.whatsapp")) {
            Bundle extras = mStatusBarNotification.getNotification().extras;
            tit = extras.getCharSequence(Notification.EXTRA_TITLE); // Title
            CharSequence desc = extras.getCharSequence(Notification.EXTRA_TEXT); // / Description

            try {
                Bundle bigExtras = mStatusBarNotification.getNotification().extras;
                CharSequence[] descArray = bigExtras.getCharSequenceArray(Notification.EXTRA_TEXT_LINES);
                msg = descArray[descArray.length - 1].toString();

            } catch (Exception ex) {

            }

            if (msg.isEmpty()) {
                msg = desc.toString();
            }


        }
        return msg;
    }

}
