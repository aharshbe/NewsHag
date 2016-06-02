package badassapps.aaron.newshag;


import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.SearchView;

import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements Top10Model.ApiResponseHandler{

    ArrayList<Article> mList;
    CustomAdapter adapter;
    ListView listView;
    LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkFirstRun();


        mList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        adapter = new CustomAdapter(this, R.layout.list_items, mList);
        listView.setAdapter(adapter);


        Article article = new Article("www.google.com", "This is the image", "This is the title");
        Article article2 = new Article("www.yahoo.com", "This is the image", "This is the title");
        Article article3 = new Article("www.yahoo.com", "This is the image", "This is the title");
        Article article4 = new Article("www.yahoo.com", "This is the image", "This is the title");
        Article article5 = new Article("www.yahoo.com", "This is the image", "This is the title");
        Article article6 = new Article("www.yahoo.com", "This is the image", "This is the title");


        mList.add(article);

        mList.add(article2);

        mList.add(article3);

        mList.add(article4);

        mList.add(article5);

        mList.add(article6);

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


    }

    private void NOTIFICATIONisAllowed() {

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        //If there is internet connection then the user will be presented with a notification that displays the top story
        if (networkInfo != null && networkInfo.isConnected()) {
            Intent intent1 = new Intent(MainActivity.this, MainActivity.class);

            PendingIntent pendingIntent1 = PendingIntent.getActivity(MainActivity.this, (int) System.currentTimeMillis(), intent1, 0);

            NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
            bigPictureStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.news)).build();
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this);
            mBuilder.setSmallIcon(R.drawable.ic_chrome_reader_mode_black_24dp);
            mBuilder.setContentTitle("BREAKING NEWS!");
            mBuilder.setContentText("The News Hag team: Check out the latest story!");
            mBuilder.setContentIntent(pendingIntent1);
            mBuilder.setPriority(Notification.PRIORITY_MAX);
            mBuilder.setStyle(bigPictureStyle);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, bigPictureStyle.build());
            notificationManager.cancel(6);


        } else {
            //If there is no internet connection present, the user is presented with a notification that lasts 30 seconds with the option to go into settings and turn it on via click
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            Intent intent1 = new Intent(Settings.ACTION_WIFI_SETTINGS);

            PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, (int) System.currentTimeMillis(), intent, 0);
            PendingIntent pendingIntent1 = PendingIntent.getActivity(MainActivity.this, (int) System.currentTimeMillis(), intent1, 0);


            NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this);
            mBuilder.setSmallIcon(android.R.drawable.stat_sys_warning);
            mBuilder.setContentTitle("No internet connection!");
            mBuilder.setContentText("To use the app, please enable WIFI, Thanks!");
            mBuilder.setContentIntent(pendingIntent);
            mBuilder.setPriority(Notification.PRIORITY_MAX);
            mBuilder.setStyle(bigTextStyle);
            mBuilder.addAction(android.R.drawable.ic_menu_info_details, "Connect WIFI", pendingIntent1);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(6, bigTextStyle.build());
            notificationManager.cancel(1);
        }

        mList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        adapter = new CustomAdapter(this, R.layout.list_items, mList);
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

        mList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        adapter = new CustomAdapter(this, R.layout.list_items, mList);
        listView.setAdapter(adapter);


        Article article = new Article("www.google.com", "This is the image", "This is the title");
        Article article2 = new Article("www.yahoo.com", "This is the image", "This is the title");
        Article article3 = new Article("www.yahoo.com", "This is the image", "This is the title");
        Article article4 = new Article("www.yahoo.com", "This is the image", "This is the title");
        Article article5 = new Article("www.yahoo.com", "This is the image", "This is the title");
        Article article6 = new Article("www.yahoo.com", "This is the image", "This is the title");


        mList.add(article);

        mList.add(article2);

        mList.add(article3);

        mList.add(article4);

        mList.add(article5);

        mList.add(article6);

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

    }

    public void clickingFavs(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, FavoritesD.class);
        startActivity(intent);
    }

    public class CustomAdapter extends ArrayAdapter {

        Context mContext;
        int mLayoutResource;
        ArrayList<Article> mList;

        public CustomAdapter(Context context, int layoutResource, ArrayList<Article> list) {
            super(context, layoutResource, list);
            mContext = context;
            mLayoutResource = layoutResource;
            mList = list;
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
    }


    //Will inflate our menu's search functionality


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getSupportActionBar().setIcon(R.drawable.newhag);
        inflater.inflate(R.menu.search, menu);
        inflater.inflate(R.menu.access_db, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }


    public void checkFirstRun() {
        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        if (isFirstRun) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Would you like to recieve the latest news from News Hag?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes please!",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(MainActivity.this, "Great you'll recieve stuff!", Toast.LENGTH_SHORT).show();
                            dialog.cancel();

                            NOTIFICATIONisAllowed();

                            return;


                        }
                    });

            builder1.setNegativeButton(
                    "No thanks.",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(MainActivity.this, "Oh okay, nothing then", Toast.LENGTH_SHORT).show();
                            dialog.cancel();


                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();


            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .edit()
                    .putBoolean("isFirstRun", false)
                    .apply();
        }
    }

    @Override
    public void handleResponse(ArrayList<Article> response) {
        mList = response;
        adapter.clear();
        adapter.notifyDataSetChanged();
        adapter.addAll(mList);
    }
}

