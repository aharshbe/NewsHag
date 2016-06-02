package badassapps.aaron.newshag;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Top10NewsD extends AppCompatActivity {
    TextView body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top10_news_d);



        body = (TextView) findViewById(R.id.body);
        body.setMovementMethod(new ScrollingMovementMethod());

    }

    public void clickingReadOn(View view) {
        Intent intent = new Intent(Top10NewsD.this, WebViewForTop10.class);
        startActivity(intent);
    }

    public void clickingFavsToAdd(MenuItem item) {
        Toast.makeText(Top10NewsD.this, "Added the story to your favorites!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shared_icon, menu);
        inflater.inflate(R.menu.making_fav, menu);


        return true;
    }

    public void clickingShare(MenuItem item) {
        Toast.makeText(Top10NewsD.this, "You clicked share", Toast.LENGTH_SHORT).show();
    }
}
