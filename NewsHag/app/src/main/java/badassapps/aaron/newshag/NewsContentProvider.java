package badassapps.aaron.newshag;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by tiannan.mcclanahan on 6/2/16.
 */
public class NewsContentProvider extends ContentProvider {

    private NewsDBOpenHelper myDB;
    private static final String AUTHORITY = "badassapps.aaron.newshag.NewsContentProvider";
    private static final String ARTICLES_TABLE = "articles";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + ARTICLES_TABLE);
    public static final int ARTICLES = 1;
    public static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {sURIMatcher.addURI(AUTHORITY, ARTICLES_TABLE, ARTICLES);}

    @Override
    public boolean onCreate(){
        myDB = new NewsDBOpenHelper(getContext(), null, null, 1);
        return false;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {
        int uriType = sURIMatcher.match(uri);
        Cursor cursor = null;

        switch (uriType) {
            case ARTICLES:
                cursor = myDB.getAllArticles();
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values){
        int uriType = sURIMatcher.match(uri);

        long id = 0;
        switch (uriType) {
            case ARTICLES:
                id = myDB.addArticle(values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(ARTICLES_TABLE + "/" + id);
    }


    @Override
    public int delete (Uri uri,String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        int rowsDeleted = 0;

        switch (uriType) {
            case ARTICLES:
                rowsDeleted = myDB.deleteAllArticles();
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }
    public int update(
            Uri uri,
            ContentValues values,
            String selection,
            String[] selectionArgs) {
        return 0;
    }

}
