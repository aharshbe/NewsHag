package badassapps.aaron.newshag;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
<<<<<<< HEAD
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
||||||| merged common ancestors
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
=======
>>>>>>> b1c8a0489487e6ce915488d2708601ef03f422c1
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity{

    ArrayList<Article> mList;
    CustomAdapter adapter;
    ListView listView;
    LayoutInflater layoutInflater;

    // Constants
    // Content provider authority
    public static final String AUTHORITY = "badassapps.aaron.newshag.AppContentProvider";
    // Account type
    public static final String ACCOUNT_TYPE = "example.com";
    // Account
    public static final String ACCOUNT = "default_account";

    Account mAccount;

    // Global variables
    // A content resolver for accessing the provider
    ContentResolver mResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mAccount = createSyncAccount(this);
        Cursor cursor = getContentResolver().query(AppContentProvider.CONTENT_URI,null,null,null,
                null);

        mList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        adapter = new CustomAdapter(MainActivity.this, cursor, 0);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(MainActivity.this, Top10NewsD.class);
                myIntent.putExtra("position", position);
                Article article = mList.get(position);
                myIntent.putExtra("article", article);
                startActivity(myIntent);
            }
        });


        //Step 1 (for content resolver)
        //new Handler
        getContentResolver().registerContentObserver(AppContentProvider.CONTENT_URI,true,new
                NewsContentObserver
                (new Handler()));

        //Performs manual sync
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        /*
         * Request the sync for the default account, authority, and
         * manual sync settings
         */

        //REQUESTS A SYNC FOR THE ACCOUNT
        //i.e. if there's no cache, or app hasn't been used for several days...
        ContentResolver.requestSync(mAccount, AUTHORITY, settingsBundle);

        ContentResolver.setSyncAutomatically(mAccount,AUTHORITY,true);
        ContentResolver.addPeriodicSync(
                mAccount,
                AUTHORITY,
                Bundle.EMPTY,
                60);
    }

    public void clickingFavs(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, FavoritesD.class);
        startActivity(intent);
    }

    //CustomAdapter for our Cursor
    public class CustomAdapter extends CursorAdapter {
        private LayoutInflater cursorInflater;

        public CustomAdapter(Context context, Cursor cursor, int flags) {
            super(context, cursor, flags);
            cursorInflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.activity_main, parent, false);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {

                convertView = layoutInflater.inflate(R.layout.list_items, parent, false);
                TextView title = (TextView) convertView.findViewById(R.id.title);
                title.setText(mList.get(position).getTITLE());
                TextView url = (TextView) convertView.findViewById(R.id.url);
                url.setText(mList.get(position).getURL());
                TextView image = (TextView) convertView.findViewById(R.id.image);
                image.setText(mList.get(position).getID());
            }
            return convertView;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            // Find fields to populate in inflated template
            TextView title = (TextView) findViewById(R.id.title);
            TextView url = (TextView) findViewById(R.id.url);
            TextView image = (TextView) findViewById(R.id.image);

            // Extract properties from cursor
            String urlString = cursor.getString(cursor.getColumnIndexOrThrow("url"));

            // Populate fields with extracted properties
            url.setText(urlString);
        }
    }

    //Will inflate our menu's search functionality
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.search, menu);
        inflater.inflate(R.menu.access_db, menu);

//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    /**
     * Create a new dummy account for the sync adapter
     *
     * @param context The application context
     */
    public static Account createSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(
                ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }
        return newAccount;
    }

    public class NewsContentObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public NewsContentObserver(Handler handler) {
            super(handler);
        }


        @Override
        public void onChange(boolean selfChange, Uri uri) {
            //do stuff on UI thread
            adapter.swapCursor
                    (getContentResolver().query(AppContentProvider.CONTENT_URI, null, null,
                    null, null));
        }
    }
}

