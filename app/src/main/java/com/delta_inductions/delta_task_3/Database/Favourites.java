package com.delta_inductions.delta_task_3.Database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourites_table")
public class Favourites implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    private String detailtext;
    private String imageURL;
    private String breedname;

    public Favourites(String detailtext, String imageURL, String breedname) {
        this.detailtext = detailtext;
        this.imageURL = imageURL;
        this.breedname = breedname;
    }

    protected Favourites(Parcel in) {
        Id = in.readInt();
        detailtext = in.readString();
        imageURL = in.readString();
        breedname = in.readString();
    }

    public static final Creator<Favourites> CREATOR = new Creator<Favourites>() {
        @Override
        public Favourites createFromParcel(Parcel in) {
            return new Favourites(in);
        }

        @Override
        public Favourites[] newArray(int size) {
            return new Favourites[size];
        }
    };

    public int getId() {
        return Id;
    }

    public String getDetailtext() {
        return detailtext;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getBreedname() {
        return breedname;
    }

    public void setId(int id) {
        Id = id;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(detailtext);
        dest.writeString(imageURL);
        dest.writeString(breedname);
    }
}
