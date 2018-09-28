package ray.droid.com.droidmessage.activitys;
import android.content.Context;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ray.droid.com.droidmessage.R;
import ray.droid.com.droidmessage.others.Methods;

import static ray.droid.com.droidmessage.notification.DroidNotification.cancelAllNotification;


public class DroidMainActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
      //  setContentView(R.layout.activity_droid_main);
        context = getBaseContext();
        Methods.ShowListener(context);
        finish();
=======
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
>>>>>>> 0304361416a1602b00d47064256474bd034b9515

    }
}
