package badassapps.aaron.newshag;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by austin on 6/1/16.
 */
public class Article implements Parcelable {

    String URL;
    String ID;
    String TITLE;

    public Article(String URL, String ID, String TITLE) {
        this.URL = URL;
        this.ID = ID;
        this.TITLE = TITLE;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.URL);
        dest.writeString(this.ID);
        dest.writeString(this.TITLE);
    }

    protected Article(Parcel in) {
        this.URL = in.readString();
        this.ID = in.readString();
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
