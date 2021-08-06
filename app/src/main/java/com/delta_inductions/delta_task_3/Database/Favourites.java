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

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(detailtext);
        dest.writeString(imageURL);
        dest.writeString(breedname);
    }
}
