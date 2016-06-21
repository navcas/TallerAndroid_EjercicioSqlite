package mx.com.navcas.ejerciciosqlite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Pablo Navarrete on 18/06/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME ="DBLOCAL";
    private static int DB_VERSION = 1;

    public static final String TABLE_NAME = "curso";
    public static final String TABLE_FIELD_ID = "_id";
    public static final String TABLE_FIELD_NAME = "nombre";

    private static final String CREATE_TABLE = "create table "+ TABLE_NAME
            + " ("+ TABLE_FIELD_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TABLE_FIELD_NAME + " TEXT NOT NULL);";


    public DBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
