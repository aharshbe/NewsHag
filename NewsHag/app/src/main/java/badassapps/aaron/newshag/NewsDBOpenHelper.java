package badassapps.aaron.newshag;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tiannan.mcclanahan on 6/1/16.
 */

//Our favorites database (added by: Aaron on 6/2/2016)

public class NewsDBOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "News.db";
    public static final String NEWS_HAG_TABLE = "Saved_Stories";

    public static final String COL_ID = "_id";
    public static final String COL_URL = "URL";
    public static final String COL_FAV = "favorites";
    public static final String COL_TITLE = "title";
    public static final String COL_THUMBNAIL = "thumbnail_standard";
    public static final String COL_ABSTRACT = "abstract";

    public NewsDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int
            version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                NEWS_HAG_TABLE + "("
                + COL_ID + " INTEGER PRIMARY KEY," + COL_URL
                + " TEXT, "+ COL_FAV + " INTEGER," + COL_TITLE + " TEXT, " + COL_THUMBNAIL +
                " TEXT, " + COL_ABSTRACT + " TEXT)";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NEWS_HAG_TABLE);
        onCreate(db);
    }



    public long addArticle(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        long insertedRow = db.insert(NEWS_HAG_TABLE, null, values);
        return insertedRow;
    }



    public Cursor getAllArticles() {
        String[] projection = {COL_ID, COL_URL, COL_FAV, COL_TITLE, COL_THUMBNAIL, COL_ABSTRACT};

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(NEWS_HAG_TABLE,projection,null,null,null,null,null);
        return cursor;

    }

    public Cursor getFavorites(){
        String[] projection = {COL_ID, COL_URL, COL_FAV, COL_TITLE, COL_THUMBNAIL, COL_ABSTRACT};

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                NEWS_HAG_TABLE,projection,
                COL_FAV + "= ?" ,
                new String[]{"1"},
                null,null,null);
        return cursor;
    }



    public int deleteAllArticles() {
        SQLiteDatabase db = getWritableDatabase();

        int rowsDeleted = db.delete(NEWS_HAG_TABLE,null,null);
        return rowsDeleted;
    }

    @Override
    public synchronized void close() {
        super.close();

    }


}


