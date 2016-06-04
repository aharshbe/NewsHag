package badassapps.aaron.newshag;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by austin on 6/3/16.
 */


public class SearchResultsMovies {
    @SerializedName("results")
    private ArrayList<Article> results;

    public ArrayList<Article> getResultsMovies() {
        return results;
    }

    public void setResultsMovies(ArrayList<Article> results) {
        this.results = results;
    }
}
