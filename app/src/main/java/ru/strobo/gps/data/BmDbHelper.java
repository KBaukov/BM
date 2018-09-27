package ru.strobo.gps.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import ru.strobo.gps.data.BmContract.*;
import ru.strobo.gps.data.ent.*;
import android.content.ContentValues;


public class BmDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = BmDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "bm.db";
    private static final int DATABASE_VERSION = 1;

    public BmDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Вызывается при создании базы данных
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_CONFIG_TABLE = "CREATE TABLE " + ConfigEntry.TABLE_NAME + " ("
                + BmContract.ConfigEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ConfigEntry.SERVER_HOST + " TEXT NOT NULL, "
                + ConfigEntry.SERVER_PORT + " TEXT NOT NULL, "
                + ConfigEntry.DEVICE_ID + " TEXT NOT NULL);";

        String SQL_CREATE_POINTS_TABLE = "CREATE TABLE " + PointsEntry.TABLE_NAME + " ("
                + BmContract.PointsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PointsEntry.DATE_TIME + " TEXT NOT NULL, "
                + PointsEntry.POINT + " TEXT NOT NULL, "
                + PointsEntry.IS_SENF_FLAG + " INTEGER NOT NULL);";

        db.execSQL(SQL_CREATE_CONFIG_TABLE);
        db.execSQL(SQL_CREATE_POINTS_TABLE);
    }

    /**
     * Вызывается при обновлении схемы базы данных
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public Long seveConfig(String devId, String host, String port) {

        clearConfig();

        ContentValues cv = new ContentValues();

        cv.put(ConfigEntry.SERVER_HOST, host);
        cv.put(ConfigEntry.SERVER_PORT, port);
        cv.put(ConfigEntry.DEVICE_ID, devId);

        Long rowId = this.getWritableDatabase().insert(ConfigEntry.TABLE_NAME, null, cv);

        return rowId;
    }

    public Config getConfig() {
        Config cfg = new Config();
        Cursor c = this.getWritableDatabase().query(ConfigEntry.TABLE_NAME, null, null, null, null, null, null );

        if(c.moveToFirst()) {
            int devCidx = c.getColumnIndex(ConfigEntry.DEVICE_ID);
            int hostCidx = c.getColumnIndex(ConfigEntry.SERVER_HOST);
            int portCidx = c.getColumnIndex(ConfigEntry.SERVER_PORT);

            do {
                cfg.setDevId(c.getString(devCidx));
                cfg.setHost(c.getString(hostCidx));
                cfg.setPort(c.getString(portCidx));
            } while(c.moveToNext());

        } else {

        }

        return cfg;
    }

    public int clearConfig() {
        return this.getWritableDatabase().delete(ConfigEntry.TABLE_NAME, null, null);
    }


    public Long setPoint(Point p) {

        ContentValues cv = new ContentValues();

        cv.put(PointsEntry.DATE_TIME, p.getDateTime());
        cv.put(PointsEntry.POINT, p.getPoint());
        cv.put(PointsEntry.IS_SENF_FLAG, p.getIsSend());

        Long rowId = this.getWritableDatabase().insert(PointsEntry.TABLE_NAME, null, cv);

        return rowId;
    }

    public int getUnSendedPoinstCount() {

        //String[] cond = {"0"};

        Cursor c = this.getWritableDatabase()
                .query(PointsEntry.TABLE_NAME,
                        null,
                         null, //PointsEntry.IS_SENF_FLAG +" = ?",
                         null, //cond,
                        null,
                        null,
                        null
                );

        return c.getCount();
    }


}
