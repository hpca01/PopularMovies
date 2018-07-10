package com.example.hiren_pc_hp.popularmovies.network.json_schema;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Videos implements Parcelable
{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("results")
    @Expose
    private List<Video> results = null;
    public final static Parcelable.Creator<Videos> CREATOR = new Creator<Videos>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Videos createFromParcel(Parcel in) {
            return new Videos(in);
        }

        public Videos[] newArray(int size) {
            return (new Videos[size]);
        }

    }
            ;

    protected Videos(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.results, (com.example.hiren_pc_hp.popularmovies.network.json_schema.Video.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Videos() {
    }

    /**
     *
     * @param id
     * @param results
     */
    public Videos(int id, List<Video> results) {
        super();
        this.id = id;
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Videos withId(int id) {
        this.id = id;
        return this;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }

    public Videos withResults(List<Video> results) {
        this.results = results;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(results);
    }

    public int describeContents() {
        return  0;
    }

}
