package badassapps.aaron.newshag;

import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by aaron on 6/1/2016.
 */
public class Top10Model {
    private static Top10Model instance;
    private static ApiResponseHandler responseHandler;

    //Will query most popular news stories as by NYTimes API
    private static final String NYTIMES_SEARCH_ALL = "http://api.nytimes.com/svc/mostpopular/v2/mostviewed/all-sections/30.json?";

    private static final String API_KEY = "&api-key=";

//    private static final String FORMAT_STRING = "&nojsoncallback=1&format=json";

    private final String CONSTRUCT = NYTIMES_SEARCH_ALL + API_KEY + NYTimesAppData.API_KEY;

    //Empty constructor
    private Top10Model(){
    }


    //Creates our singleton
    public static Top10Model getInstance(ApiResponseHandler handler){
        responseHandler = handler;
        if(instance == null){
            instance = new Top10Model();
        }
        return instance;
    }

    public void doRequest(){
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(
                CONSTRUCT ,null,
                new JsonHttpResponseHandler(){
                    ArrayList<Article> items;

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        {

                            try {
                                JSONArray responseObject = response.getJSONArray("results");

//                                JSONObject url = responseObject.getJSONObject("url");
//                                JSONObject title = responseObject.getJSONObject("title");
//                                JSONObject media = responseObject.getJSONObject("media")
//                                        .getJSONArray("media-metadata").getJSONObject(1)
//                                        .getJSONObject("url");

                                items = new ArrayList<>();

                                for (int i = 0; i < responseObject.length(); i++) {
                                    JSONObject url = responseObject.getJSONObject(i);
                                        if (!url.has("url")) continue;
                                        items.add(url.getString("url"));
                                    JSONObject title = responseObject.getJSONObject(i);
                                        if (!title.has("title")) continue;
                                        items.add(url.getString("title"));
                                    JSONObject getMedia = responseObject.getJSONObject(i);
                                        if (!getMedia.has("media")) continue;
                                    JSONArray mediaMeta = getMedia.getJSONArray("media-metadata");
                                    JSONObject metaURL = mediaMeta.getJSONObject(0);
                                        if (!metaURL.has("url")) continue;
                                        items.add(url.getString("url"));
                                }
                                responseHandler.handleResponse(items);
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    public interface ApiResponseHandler{
        void handleResponse(ArrayList<Article> mList);
    }
}
