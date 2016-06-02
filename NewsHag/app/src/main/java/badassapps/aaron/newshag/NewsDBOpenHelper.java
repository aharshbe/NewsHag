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
public class NewsDBOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "News.db";
    public static final String NEWS_HAG_TABLE = "Saved_Stories";

    public static final String COL_ID = "_id";
    public static final String COL_URL = "URL";

    public static final String [] COLUMNS = {
            COL_ID, COL_URL};

    private static final String CREATE_NEWS_HAG_TABLE = "CREATE TABLE " + NEWS_HAG_TABLE + "(" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_URL + " TEXT)";

    public static NewsDBOpenHelper instance;


    public static NewsDBOpenHelper getInstance(Context context){
        if (instance == null){
            instance = new NewsDBOpenHelper(context, null, null, 1);
        }
        return instance;
    }
    public NewsDBOpenHelper(Context context, Object o, Object o1, int i) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NEWS_HAG_TABLE);
        addDefaultData(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NEWS_HAG_TABLE);
        onCreate(db);
    }

    private void addDefaultData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        //add data here
        
    }

    public boolean insertFavorite(String url){
        SQLiteDatabase database =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("url", url);
        database.insert("Saved Stories", null, values);
        return true;
    }


    public Cursor displayBookmarks(){
        SQLiteDatabase database = this.getReadableDatabase();

        //add more columns?
        String [] projection = new String[]{COL_ID, COL_URL};

        Cursor cursor = database.query(NEWS_HAG_TABLE, projection, null, null, null, null, null, null);
        DatabaseUtils.dumpCursor(cursor);
        return cursor;
    }


    public Cursor getAllArticles() {
        String[] projection = {COL_ID, COL_URL};

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(NEWS_HAG_TABLE, projection, null, null, null, null, null);
        return cursor;
    }


    public long addArticle(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        long insertedRow = db.insert(NEWS_HAG_TABLE, null, values);
        return insertedRow;
    }

    public int deleteAllArticles() {
        SQLiteDatabase db = getWritableDatabase();

        int rowsDeleted = db.delete(NEWS_HAG_TABLE,null,null);
        return rowsDeleted;
    }
}
