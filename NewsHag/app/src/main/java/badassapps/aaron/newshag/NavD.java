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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.SearchView;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class NavD extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_d);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checkFirstRun();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_top10) {
            // Handle the camera action
        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void clickingTopTen(MenuItem item) {
        Intent intent = new Intent(NavD.this, MainActivity.class);
        startActivity(intent);
    }

    public void clickingShareNav(MenuItem item) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "https://github.com/aartea/NewsHag");
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.send_intent_title)));
    }

    private void NOTIFICATIONisAllowed() {

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        //If there is internet connection then the user will be presented with a notification that displays the top story
        if (networkInfo != null && networkInfo.isConnected()) {
            Intent intent1 = new Intent(NavD.this, MainActivity.class);

            PendingIntent pendingIntent1 = PendingIntent.getActivity(NavD.this, (int) System.currentTimeMillis(), intent1, 0);

            NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
            bigPictureStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.news)).build();
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(NavD.this);
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
            Intent intent = new Intent(NavD.this, MainActivity.class);
            Intent intent1 = new Intent(Settings.ACTION_WIFI_SETTINGS);

            PendingIntent pendingIntent = PendingIntent.getActivity(NavD.this, (int) System.currentTimeMillis(), intent, 0);
            PendingIntent pendingIntent1 = PendingIntent.getActivity(NavD.this, (int) System.currentTimeMillis(), intent1, 0);


            NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(NavD.this);
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
    }

    public void checkFirstRun() {
        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        if (isFirstRun) {

            MainDialogue();


            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .edit()
                    .putBoolean("isFirstRun", false)
                    .apply();

        }


    }

    public void clickingSettings(MenuItem item) {
        Intent intent = new Intent(NavD.this, SettingsActivity.class);
        startActivity(intent);
    }

    public void firstDialogue() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("To search for the latest news based on your favorite topic, click the magnifying glass in the top right-hand corner and enter your topic!");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        secondDialogue();

                        return;


                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();

    }


    public void secondDialogue() {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setMessage("To view the Top 10 most popular stories, swipe from the left or click the 3 lines in the top-left");
        builder2.setCancelable(true);

        builder2.setPositiveButton(
                "Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        return;


                    }
                });


        AlertDialog alert12 = builder2.create();
        alert12.show();
    }

    public void ThirdDialogue() {
        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
        builder3.setMessage("Would you like tips on how to use the app?");
        builder3.setCancelable(true);

        builder3.setPositiveButton(
                "Yes please!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        firstDialogue();

                        return;


                    }
                });

        builder3.setNegativeButton(
                "No thanks.",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(NavD.this, "Oh okay, nothing then, you pro user you", Toast.LENGTH_LONG).show();
                        dialog.cancel();


                    }
                });


        AlertDialog alert13 = builder3.create();
        alert13.show();
    }

    public void MainDialogue() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Would you like to recieve the latest news from News Hag?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes please!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(NavD.this, "Great you'll recieve stuff!", Toast.LENGTH_SHORT).show();
                        dialog.cancel();

                        NOTIFICATIONisAllowed();
                        ThirdDialogue();


                        return;


                    }
                });

        builder1.setNegativeButton(
                "No thanks.",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(NavD.this, "Oh okay, nothing then", Toast.LENGTH_SHORT).show();
                        dialog.cancel();

                        ThirdDialogue();


                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
