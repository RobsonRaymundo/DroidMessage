package ray.droid.com.droidmessage.others;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import ray.droid.com.droidmessage.dbase.Persintencia;
import ray.droid.com.droidmessage.feature.Constantes;
import ray.droid.com.droidmessage.gdrive.CreateFileActivity;

/**
 * Created by Robson on 02/03/2016.
 */
public class Methods {

    public static String getDateTimeFormated()
    {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMdd_hhmmss");
        return simpleFormat.format( new Date( System.currentTimeMillis() ));
    }

    public static String getNameDevice(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return tm.getMmsUserAgent();
        }
        else return "Smartphone Padrao";
    }

    public static String getIDDevice(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return tm.getDeviceId();
       // }
       // else return "0000000000";
    }

    public static void showMessage(final Activity activity, String mensagem) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setMessage(mensagem);
        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // activity.finish();
            }
        });
        alerta.show();
    }

    public static boolean checkPlayServices(Activity activity) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(activity, resultCode, Constantes.PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Methods.showMessage(activity, "Dispositivo não suportado");
                Log.d(Constantes.TAG, "This device is not supported.");
                activity.finish();
            }
            return false;
        }
        return true;
    }

    public static int obtemQualidadeCamera(final Context context, Constantes.EnumTypeViewCam typeViewCam) {
        int qualid = 0; // QUALITY_LOW
        try {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            if (typeViewCam == Constantes.EnumTypeViewCam.FacingFront) {
                qualid = Integer.parseInt(sp.getString("ltp_qualidadeCameraFrontal", "0"));
            } else qualid = Integer.parseInt(sp.getString("ltp_qualidadeCameraTraseira", "0"));

        } catch (Exception ex) {
            Log.d("DroidVideo", ex.getMessage());
        }
        return qualid;

    }

    public static int obtemLocalGravacao(final Context context) {
        int local = 0; // Interno
        try {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            local = Integer.parseInt(sp.getString("ltp_localGravacaoVideo", "0"));
        } catch (Exception ex) {
            Log.d("DroidVideo", ex.getMessage());
        }
        return local;

    }

    public static boolean exibeTelaInicial(final Context context) {
        boolean spf = false;
        try {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            spf = sp.getBoolean("spf_exibeAoIniciar", true);
        } catch (Exception ex) {
            Log.d("DroidVideo", ex.getMessage());
        }
        return spf;

    }

    public static String obtemDescricaoPreferencias(final Context context, String valor_selecionado, int nome_lista, int lista_valor) {
        String nome_selecionado = "";

        String[] array_lista = context.getResources().getStringArray(nome_lista);
        String[] array_lista_valores = context.getResources().getStringArray(lista_valor);

        for (int i = 0; i < array_lista_valores.length; i++) {
            if (array_lista_valores[i].equals(valor_selecionado)) {
                nome_selecionado = array_lista[i].toString();
                break;
            }
        }
        return nome_selecionado;
    }

    public static String chamadaBroadCastPorComandoTexto(Intent intent) {
        String chamadaPorCmdTxt = "";
        try {

            chamadaPorCmdTxt = intent.getStringExtra(Constantes.CHAMADAPORCOMANDOTEXTO);

        } catch (Exception ex) {

        }
        return chamadaPorCmdTxt;
    }

    public static String GetPathStorage()
    {
        // SandBox
        return System.getenv("EXTERNAL_STORAGE");
    }


    public static void EnviaMsg(Context context) {
        Intent mIntent = new Intent(context, CreateFileActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Persintencia persintencia = new Persintencia(context);
        StringBuilder sb = persintencia.ObterMensagens();
        mIntent.putExtra(Constantes.MESSAGE, sb.toString());

        context.startActivity(mIntent);
    }

    public static void ApagarMsg(Context context)
    {
        Persintencia persintencia = new Persintencia(context);
        persintencia.ApagarMensagens();
    }


    public static void EnviaWhatsapp(Context context, String number)
    {
        try {
           /*
            String whatsAppMessage = "Hello!";
            Uri uri = Uri.parse("smsto:" + number);
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setPackage("com.whatsapp");
            //i.putExtra("sms_body", whatsAppMessage);
            i.putExtra(Intent.EXTRA_TEXT, whatsAppMessage);//
            context.startActivity(i);
            */

/*
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "testing message";
            waIntent.setPackage("com.whatsapp");
            if (waIntent != null) {
                waIntent.putExtra(Intent.EXTRA_TEXT, text);//
                waIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(waIntent, text));
            } else {
                Toast.makeText(context, "WhatsApp not found", Toast.LENGTH_SHORT)
                        .show();
            }
            */

            Uri uri = Uri.parse("smsto:" + number);
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.putExtra("sms_body", "TESTE");
            i.setPackage("com.whatsapp");
            context.startActivity(i);
        }
        catch (Exception ex)
        {
            Log.d("DroidMessage", ex.getMessage());
        }
    }


}

