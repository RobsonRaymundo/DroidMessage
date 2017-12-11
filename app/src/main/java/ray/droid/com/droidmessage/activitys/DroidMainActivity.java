package ray.droid.com.droidmessage.activitys;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.service.notification.StatusBarNotification;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ray.droid.com.droidmessage.R;
import ray.droid.com.droidmessage.dbase.Persintencia;
import ray.droid.com.droidmessage.feature.Constantes;
import ray.droid.com.droidmessage.gdrive.CreateFileActivity;
import ray.droid.com.droidmessage.others.Methods;

import static ray.droid.com.droidmessage.notification.DroidNotification.cancelAllNotification;


public class DroidMainActivity extends AppCompatActivity {

    private Button btnEnviaMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_droid_main);

        btnEnviaMsg = (Button) findViewById(R.id.btnEnviaMsg);

        btnEnviaMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             EnviaWhatsapp("5511977178109");
            }
        });

        cancelAllNotification(getApplicationContext());

        NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancelAll();

        Intent mIntent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mIntent);


        //finish();

    //    Methods.EnviaWhatsapp(getBaseContext(), "+5511977178109");

    }



    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    private void EnviaWhatsapp(String number)
    {
        try {

            String text = "TESTANDO";
            if(whatsappInstalledOrNot("com.whatsapp")){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("whatsapp://send?text="+text+"&phone="+number.toString()));
                startActivity(browserIntent);
            }else {
                Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                        .show();
            }


            /*

            Intent sendIntent = new Intent("android.intent.action.MAIN");
            //sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            sendIntent.putExtra("jid", number + "@s.whatsapp.net"); //phone number without "+" prefix
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);

            /*
            String whatsAppMessage = "Hello!";
            Uri uri = Uri.parse("smsto:" + number);
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.setPackage("com.whatsapp");
            //i.putExtra("sms_body", whatsAppMessage);
            i.putExtra(Intent.EXTRA_TEXT, whatsAppMessage);//
            startActivity(i);


/*
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "testing message";
            waIntent.setPackage("com.whatsapp");
            if (waIntent != null) {
                waIntent.putExtra(Intent.EXTRA_TEXT, text);//
                startActivity(Intent.createChooser(waIntent, text));
            } else {
                Toast.makeText(getBaseContext(), "WhatsApp not found", Toast.LENGTH_SHORT)
                        .show();
            }

/*

             Uri uri = Uri.parse("smsto:" + number);
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.putExtra("sms_body", "TESTE");
            i.setPackage("com.whatsapp");
            startActivity(i);
            */
        }
        catch (Exception ex)
        {
            Log.d("DroidMessage", ex.getMessage());
        }
    }

}
