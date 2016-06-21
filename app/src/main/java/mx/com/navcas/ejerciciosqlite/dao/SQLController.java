package mx.com.navcas.ejerciciosqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Pablo Navarrete on 18/06/2016.
 */
public class SQLController {

    public SQLController(Context c){
        context = c;
    }

    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public SQLController openDataBase() throws SQLException{
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void closeDataBase(){
        dbHelper.close();
    }

    public void insertData(String name){
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.TABLE_FIELD_NAME, name);
        database.insert(DBHelper.TABLE_NAME, null, cv);
    }

    public boolean updatetData(long id, String name){
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.TABLE_FIELD_NAME, name);
        return database.update(DBHelper.TABLE_NAME,cv,DBHelper.TABLE_FIELD_ID + " = " + id, null) > 0;
    }

    public boolean deleteData(long id){
        return database.delete(DBHelper.TABLE_NAME, DBHelper.TABLE_FIELD_ID + " = " + id, null) > 0;
    }

    public Cursor getAllData(){


        Cursor c = database.query(DBHelper.TABLE_NAME, getAllColumns(), null, null, null, null, null);
        if(c != null){
            c.moveToFirst();
        }

        return c;
    }

    public Cursor getCustomSentence(String customQuery){
        return  database.rawQuery(customQuery,null);
    }

    public String[] getAllColumns (){
        return new String[]{
                DBHelper.TABLE_FIELD_ID,
                DBHelper.TABLE_FIELD_NAME
        };
    }
}
