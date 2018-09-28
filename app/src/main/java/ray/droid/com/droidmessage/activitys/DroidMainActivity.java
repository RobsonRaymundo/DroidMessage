package ray.droid.com.droidmessage.activitys;
import android.content.Context;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ray.droid.com.droidmessage.R;
import ray.droid.com.droidmessage.others.Methods;


public class DroidMainActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_droid_main);
        context = getBaseContext();
        Methods.ShowListener(context);
        finish();

    }
}
