package badassapps.aaron.newshag;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FavoritesD extends AppCompatActivity {

    ArrayList<Article> mList;
    CustomAdapter adapter;
    ListView listView;
    LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_d);

        mList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView2);
        adapter = new CustomAdapter(this, R.layout.fav_items, mList);
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



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mList.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(FavoritesD.this, WebViewForFavorites.class);
                myIntent.putExtra("position", position);
                String nameOfListPrior = mList.get(position).toString();
                myIntent.putExtra("nameOfListPrior", nameOfListPrior);
                startActivity(myIntent);
            }
        });

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

                convertView = layoutInflater.inflate(R.layout.fav_items, parent, false);
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



}
