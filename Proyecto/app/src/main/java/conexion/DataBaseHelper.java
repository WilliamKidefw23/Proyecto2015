package conexion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import dao.ProyectoData;

public class DataBaseHelper extends SQLiteAssetHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Proyecto.db";
    private static CursorFactory factory = null;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    /*
    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(ProyectoData.CREATE_USUARIO_SCRIPT);
            db.execSQL(ProyectoData.INSERT_USUARIO_SCRIPT);
    }
    */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ProyectoData.TABLE_NAME_USUARIO);
        onCreate(db);

      //  db.execSQL("DROP TABLE IF EXISTS " + ProyectoData.TABLE_NAME_CICLO);
       // onCreate(db);
    }


}
