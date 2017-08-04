package ray.droid.com.droidmessage.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ray.droid.com.droidmessage.R;
import ray.droid.com.droidmessage.dbase.Persintencia;
import ray.droid.com.droidmessage.feature.Constantes;
import ray.droid.com.droidmessage.gdrive.CreateFileActivity;


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
                EnviaMsg();
            }
        });

        Intent mIntent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mIntent);


    }

    private void EnviaMsg() {
        Intent mIntent = new Intent(getBaseContext(), CreateFileActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Persintencia persintencia = new Persintencia(getBaseContext());
        StringBuilder sb = persintencia.ObterMensagens();
        mIntent.putExtra(Constantes.MESSAGE, sb.toString());

        startActivity(mIntent);
    }
}
