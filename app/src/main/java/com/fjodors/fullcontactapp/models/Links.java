package com.fjodors.fullcontactapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

/**
 * Created by Fjodors on 2015.08.03..
 */
@Data
public class Links implements Parcelable {
    private String url;

    public Links() {

    }

    protected Links(Parcel in) {
        url = in.readString();
    }

    public static final Creator<Links> CREATOR = new Creator<Links>() {
        @Override
        public Links createFromParcel(Parcel in) {
            return new Links(in);
        }

        @Override
        public Links[] newArray(int size) {
            return new Links[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
    }
}