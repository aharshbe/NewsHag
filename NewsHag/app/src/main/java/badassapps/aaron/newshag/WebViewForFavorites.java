package badassapps.aaron.newshag;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewForFavorites extends AppCompatActivity {

    WebView webView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_for_favorites);


        webView = (WebView) findViewById(R.id.loadStory);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://www.nytimes.com/2016/06/02/world/europe/mikhail-gorbachev-interview-vladimir-putin.html?hp&action=click&pgtype=Homepage&clickSource=story-heading&module=second-column-region&region=top-news&WT.nav=top-news&_r=0");
    }
}
