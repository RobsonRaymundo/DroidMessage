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
    String msg;

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Context context = getBaseContext();
        getNotificationKitKat(sbn);


        if (!tit.toString().isEmpty())
        {

        }


    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void getNotificationKitKat(StatusBarNotification mStatusBarNotification) {
        String pack = mStatusBarNotification.getPackageName();// Package Name
        msg = "";
        tit = "";
        if (pack.contains("com.whatsapp") ||
                pack.contains("com.android.mms") ||
                pack.contains("com.facebook.orca")) {
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

            if (msg.equals("procurando novas mensagens"))
            {
                tit = "";
                msg = "";
            }

        }
    }

}
