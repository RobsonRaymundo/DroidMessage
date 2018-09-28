package ray.droid.com.droidmessage.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.content.Context;

import android.os.Build;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;



/**
 * Created by Robson on 03/02/2016.
 */

public class DroidNotification extends DroidBaseNotification {
    CharSequence tit;
<<<<<<< HEAD
    String msg;
=======


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

>>>>>>> 0304361416a1602b00d47064256474bd034b9515

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Context context = getBaseContext();
        getNotificationKitKat(sbn);


        if (!tit.toString().isEmpty())
        {

            cancelAllNotification(context);



        }


<<<<<<< HEAD
=======

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
>>>>>>> 0304361416a1602b00d47064256474bd034b9515
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void getNotificationKitKat(StatusBarNotification mStatusBarNotification) {
        String pack = mStatusBarNotification.getPackageName();// Package Name
        msg = "";
        tit = "";
<<<<<<< HEAD
        if (pack.contains("com.whatsapp") ||
                pack.contains("com.android.mms") ||
                pack.contains("com.facebook.orca")) {
=======
        if (pack.contains("com.whatsapp")) {
>>>>>>> 0304361416a1602b00d47064256474bd034b9515
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

<<<<<<< HEAD
            if (msg.equals("procurando novas mensagens"))
            {
                tit = "";
                msg = "";
            }
=======
>>>>>>> 0304361416a1602b00d47064256474bd034b9515

        }
    }

}
