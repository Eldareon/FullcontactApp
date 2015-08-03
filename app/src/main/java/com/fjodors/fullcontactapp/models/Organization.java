package com.fjodors.fullcontactapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Created by Fjodors on 2015.08.03..
 */
@Data
public class Organization implements Parcelable {

    public Organization() {
    }

    protected Organization(Parcel in) {
        name = in.readString();
        approxEmployees = in.readString();
        founded = in.readString();
        contactInfo = in.readParcelable(ContactInfo.class.getClassLoader());
        links = new ArrayList();
        in.readTypedList(links, Links.CREATOR);
    }

    public static final Creator<Organization> CREATOR = new Creator<Organization>() {
        @Override
        public Organization createFromParcel(Parcel in) {
            return new Organization(in);
        }

        @Override
        public Organization[] newArray(int size) {
            return new Organization[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(approxEmployees);
        dest.writeString(founded);
        dest.writeParcelable(contactInfo, flags);
        dest.writeTypedList(links);
    }


    private String name;
    private String approxEmployees;
    private String founded;
    private ContactInfo contactInfo;
    private List<Links> links;
}