package com.delta_inductions.delta_task_3.Model;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Breeds implements Parcelable {

    @SerializedName("weight")
    @Expose()
    private Weight weight;
    @SerializedName("height")
    @Expose()
    private Height height;
    @SerializedName("image")
    @Expose()
    private Image image;
    private Integer id;
    private String name;
    private String bred_for;
    private String breed_group;
    private String life_span;
    private String temperament;
    private String origin;
    private boolean fav;

    public Breeds(Weight weight, Height height, String name, String bred_for, String breed_group, String life_span, String temperament, String origin, Image image) {
        this.weight = weight;
        this.height = height;
        this.name = name;
        this.bred_for = bred_for;
        this.breed_group = breed_group;
        this.life_span = life_span;
        this.temperament = temperament;
        this.origin = origin;
        this.image = image;
    }

    protected Breeds(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        bred_for = in.readString();
        breed_group = in.readString();
        life_span = in.readString();
        temperament = in.readString();
        origin = in.readString();
        fav = in.readByte() != 0;
    }

    public static final Creator<Breeds> CREATOR = new Creator<Breeds>() {
        @Override
        public Breeds createFromParcel(Parcel in) {
            return new Breeds(in);
        }

        @Override
        public Breeds[] newArray(int size) {
            return new Breeds[size];
        }
    };

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public Weight getWeight() {
        return weight;
    }
    public Height getHeight() {
        return height;
    }
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getBred_for() {
        return bred_for;
    }
    public String getBreed_group() {
        return breed_group;
    }
    public String getLife_span() {
        return life_span;
    }
    public String getTemperament() {
        return temperament;
    }
    public String getOrigin() {
        return origin;
    }
    public Image getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(bred_for);
        dest.writeString(breed_group);
        dest.writeString(life_span);
        dest.writeString(temperament);
        dest.writeString(origin);
        dest.writeByte((byte) (fav ? 1 : 0));
    }
}
