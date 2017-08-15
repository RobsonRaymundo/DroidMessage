package ray.droid.com.droidmessage.dbase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import java.io.File;

/**
 * Created by nalmir on 19/12/2015.
 */
public class Persintencia extends SQLiteOpenHelper {

    //public static final String BANCO = "/storage/extSdCard/BancoDados/contatosdbase.db3";
    public static final String BANCO = GetPathStorage() + "droidMessage.db3";

    public static final int VERSAO = 4;
    //
    public static final String TBLMENSAGENS = "tbl_mensagens";
    public static final String TOKEN = "token";

    public static final String MENSAGEM = "mensagem";

    public Persintencia(Context context) {
        super(context, BANCO, null, VERSAO);
    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageMediaMounted() {
        return (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()));
    }

    public static String GetPathStorage() {
        String strSDCardPath = "";
        String strDirectory = "";
        String strPaste = "/DBase/";
        try {
            if (isExternalStorageMediaMounted()) {
                strSDCardPath = System.getenv("SECONDARY_STORAGE");
                if ((null == strSDCardPath) || (strSDCardPath.length() == 0)) {
                    strSDCardPath = System.getenv("EXTERNAL_SDCARD_STORAGE");
                }
                strDirectory = CreateGetDirectory(strSDCardPath + strPaste);
            }
        } catch (Exception e) {
        } finally {
            if (strSDCardPath == "" || strDirectory == "") {
                strSDCardPath = Environment.getExternalStorageDirectory().toString();
                strDirectory = CreateGetDirectory(strSDCardPath + strPaste);
            }
        }
        return strDirectory;
    }

    private static void TimeSleep(Integer seg) {
        try {
            Thread.sleep(seg);
        } catch (Exception ex) {
        }
    }

    public static String CreateGetDirectory(String pathStorage) {
        String pathDirectory = "";

        try {

            File myNewFolder = new File(pathStorage);

            if (!myNewFolder.exists()) {
                myNewFolder.mkdir();
                TimeSleep(1000);
            }
            if (myNewFolder.exists()) {
                pathDirectory = pathStorage;
            }
        } catch (Exception e) {

        }
        return pathDirectory;

    }

    private void CreateTabelaMensagens(SQLiteDatabase db) {

        StringBuilder stringBuilder = new StringBuilder();       //

        stringBuilder.append("CREATE TABLE IF NOT EXISTS [" + TBLMENSAGENS + "] (\n" +
                "  [mensagem] CHAR(1024));\n" +
                " ); ");
        //
        db.execSQL(stringBuilder.toString());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CreateTabelaMensagens(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBLMENSAGENS + ";");
        //
        onCreate(db);
    }

    private boolean JaExisteMensagem( String mensagem)
    {
        boolean cadastrado = false;
        //
        Cursor cursor = null;
        //
        try {
            //

            String[] argumentos = new String[]{String.valueOf(mensagem)};
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM " + TBLMENSAGENS + " WHERE mensagem = ?");
            //
            cursor = getWritableDatabase().rawQuery(sb.toString(), argumentos);
            //

            cadastrado = cursor.getCount() > 0;

        } catch (Exception e) {
            Log.d("DBase", e.getMessage());

        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
        //
        return cadastrado;

    }

    public void InserirMensagens (String mensagem) {

        if (!JaExisteMensagem(mensagem)) {
            ContentValues cv = new ContentValues();
            //
            cv.put(MENSAGEM, mensagem);
            //
            getWritableDatabase().insert(TBLMENSAGENS, null, cv);
        }
    }

    public void ApagarMensagens()
    {
        try {
            getWritableDatabase().delete(TBLMENSAGENS, null, null);
        }
        catch (Exception ex)
        {
            Log.d("DroidMessage", ex.getMessage());
        }

    }

    public StringBuilder ObterMensagens() {
        StringBuilder sAux = new StringBuilder();
        //
        Cursor cursor = null;
        //
        try {

            //
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM " + TBLMENSAGENS );
            //
            cursor = getWritableDatabase().rawQuery(sb.toString(), null);
            //
            while (cursor.moveToNext()) {
                sAux.append(cursor.getString(cursor.getColumnIndex(MENSAGEM)));
                sAux.append("\n");
            }



        } catch (Exception e) {
            Log.d("DBase", e.getMessage());

        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
        //
        return sAux;
    }

}
