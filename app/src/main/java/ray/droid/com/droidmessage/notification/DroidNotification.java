package ray.droid.com.droidmessage.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;

import ray.droid.com.droidmessage.dbase.Persintencia;
import ray.droid.com.droidmessage.feature.Constantes;
import ray.droid.com.droidmessage.others.Methods;


/**
 * Created by Robson on 03/02/2016.
 */

public class DroidNotification extends DroidBaseNotification {

    private boolean sentBroadcast = false;

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
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
            }
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        sentBroadcast = false;
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
        if (pack.contains("com.whatsapp")) {
            Bundle extras = mStatusBarNotification.getNotification().extras;
            CharSequence tit = extras.getCharSequence(Notification.EXTRA_TITLE); // Title
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
            if (!tit.toString().isEmpty())
            {
                msg = tit + ": " + msg;
            }
        }
        return msg;
    }

}
