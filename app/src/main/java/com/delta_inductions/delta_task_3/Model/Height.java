package com.delta_inductions.delta_task_3.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Height implements Parcelable {
    private String metric;
    private String imperial;

    protected Height(Parcel in) {
        metric = in.readString();
        imperial = in.readString();
    }

    public static final Creator<Height> CREATOR = new Creator<Height>() {
        @Override
        public Height createFromParcel(Parcel in) {
            return new Height(in);
        }

        @Override
        public Height[] newArray(int size) {
            return new Height[size];
        }
    };

    public String getMetric() {
        return metric;
    }
    public String getImperial() {
        return imperial;
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
        dest.writeString(metric);
        dest.writeString(imperial);
    }
}
