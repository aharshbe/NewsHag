package badassapps.aaron.newshag;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by austin on 6/3/16.
 */


public class Movie extends SearchResult implements Parcelable {

    @SerializedName("url")
    String URL;
    @SerializedName("display_title")
    String TITLE;
    @SerializedName("summary_short")
    String SUMMARY;
    @SerializedName("src")
    String BOOKCOVER;

    public Movie(String URL, String TITLE, String SUMMARY, String BOOKCOVER) {
        this.URL = URL;
        this.TITLE = TITLE;
        this.SUMMARY = SUMMARY;
        this.BOOKCOVER = BOOKCOVER;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getSUMMARY() {
        return SUMMARY;
    }

    public void setSUMMARY(String SUMMARY) {
        this.SUMMARY = SUMMARY;
    }

    public String getBOOKCOVER() {
        return BOOKCOVER;
    }

    public void setBOOKCOVER(String BOOKCOVER) {
        this.BOOKCOVER = BOOKCOVER;
    }

    public static Creator<Article> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.URL);
        dest.writeString(this.TITLE);
    }

    protected Movie(Parcel in) {
        this.URL = in.readString();
        this.TITLE = in.readString();
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
