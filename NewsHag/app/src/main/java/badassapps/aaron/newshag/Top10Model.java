package badassapps.aaron.newshag;

import android.widget.ArrayAdapter;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private final String CONSTRUCT = NYTIMES_SEARCH_ALL + API_KEY;

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

    public void doRequest(LinkedList response){
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(
                CONSTRUCT ,null,
                new JsonHttpResponseHandler(){
                    LinkedList<String> items;

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        {

                            try {
                                JSONObject jsonObject = response.getJSONObject("response");
                                JSONArray jsonArray = jsonObject.getJSONArray("results");
                                items = new LinkedList<>();


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject title = jsonArray.getJSONObject(i);
                                    if (!title.has("webUrl")) continue;
                                    items.add(title.getString("webUrl"));
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
        void handleResponse(LinkedList response);
    }
}
