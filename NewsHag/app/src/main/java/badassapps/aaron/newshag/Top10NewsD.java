package badassapps.aaron.newshag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

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
}
