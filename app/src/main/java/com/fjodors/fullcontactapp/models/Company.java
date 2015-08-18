package com.fjodors.fullcontactapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Created by Fjodors on 2015.05.10..
 */

@Data
public class Company implements Parcelable {

    private String logo;
    private String website;
    private Organization organization;
    private List<SocialProfiles> socialProfiles;

    public Company() {
    }

    /* everything below here is for implementing Parcelable */

    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(logo);
        dest.writeString(website);
        dest.writeParcelable(organization, flags);
        dest.writeTypedList(socialProfiles);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Company> CREATOR = new Parcelable.Creator<Company>() {
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        public Company[] newArray(int size) {
            return new Company[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Company(Parcel in) {
        logo = in.readString();
        website = in.readString();
        organization = in.readParcelable(Organization.class.getClassLoader());
        socialProfiles = new ArrayList();
        in.readTypedList(socialProfiles, SocialProfiles.CREATOR);
    }


}
