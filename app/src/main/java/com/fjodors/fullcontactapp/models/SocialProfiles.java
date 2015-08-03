package com.fjodors.fullcontactapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

/**
 * Created by Fjodors on 2015.08.03..
 */
@Data
public class SocialProfiles implements Parcelable {
    private String typeName;
    private String bio;

    public SocialProfiles() {
    }

    protected SocialProfiles(Parcel in) {
        typeName = in.readString();
        bio = in.readString();
    }

    public static final Creator<SocialProfiles> CREATOR = new Creator<SocialProfiles>() {
        @Override
        public SocialProfiles createFromParcel(Parcel in) {
            return new SocialProfiles(in);
        }

        @Override
        public SocialProfiles[] newArray(int size) {
            return new SocialProfiles[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(typeName);
        dest.writeString(bio);
    }
}
