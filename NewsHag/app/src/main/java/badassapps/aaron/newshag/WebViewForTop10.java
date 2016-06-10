package badassapps.aaron.newshag;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class WebViewForTop10 extends AppCompatActivity {

    WebView webView;
    private String detailID;
    private String detailURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_for_top10);

        String detailUrl = getIntent().getStringExtra("url");
        detailURL = getIntent().getStringExtra("url");
        detailID = getIntent().getStringExtra("id");

        webView = (WebView) findViewById(R.id.loadStory);
        final ProgressDialog pd = ProgressDialog.show(this, "", "Loading...", true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().getLoadsImagesAutomatically();
        webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webView.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pd.setMessage("Loading, just one second more...");
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }
        });

        webView.loadUrl(detailUrl);

    }

    public void clickingFavsToAdd(MenuItem item) {


        Toast.makeText(WebViewForTop10.this, "Added the story to your favorites!", Toast.LENGTH_SHORT).show();
        insertFavorite();

    }

    public void clickingShare(MenuItem item) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, getIntent().getStringExtra("url"));
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.send_intent_title)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shared_icon, menu);
        inflater.inflate(R.menu.making_fav, menu);
//        inflater.inflate(R.menu.infobutton, menu);
        return true;
    }

//    public void clickingminus(View view) {
//        webView.zoomOut();
//        Toast.makeText(WebViewForTop10.this, "you clicked zoom out", Toast.LENGTH_SHORT).show();
//
//    }
//
//    public void clickingplus(View view) {
//        Toast.makeText(WebViewForTop10.this, "you clicked zoom in", Toast.LENGTH_SHORT).show();
//    }

    public void insertFavorite() {

        //Create our db object
        NewsDBOpenHelper helpMe = new NewsDBOpenHelper(this, "favorites", null, 1);

        //Insert values using writable db
        SQLiteDatabase db = helpMe.getWritableDatabase();

        //Receive our values from our class and pass them through here!
        ContentValues cv = new ContentValues();

        cv.put(NewsDBOpenHelper.COL_FAV, "1");
        cv.put(NewsDBOpenHelper.COL_TITLE, getIntent().getStringExtra("title"));
        cv.put(NewsDBOpenHelper.COL_URL, detailURL);
        cv.put(NewsDBOpenHelper.COL_THUMBNAIL, getIntent().getStringExtra("thumbnail"));
        cv.put(NewsDBOpenHelper.COL_ABSTRACT, getIntent().getStringExtra("abstract"));

        String id = detailID;  //is the id

        long insertColumn = db.insert(NewsDBOpenHelper.FAVS_HAG_TABLE, null, cv);
        long insertColumnValue = db.update(NewsDBOpenHelper.FAVS_HAG_TABLE, cv, NewsDBOpenHelper.COL_ID + " = ?", new String[]{id});
        db.close();
//        Toast.makeText(NavDDetailView.this, "Insert into columnID " + insertColumn, Toast.LENGTH_SHORT).show();
//        Toast.makeText(NavDDetailView.this, "Inserted data into columnID " + insertColumnValue, Toast.LENGTH_SHORT).show();

    }

}
