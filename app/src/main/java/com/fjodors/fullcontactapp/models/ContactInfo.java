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
public class ContactInfo implements Parcelable {

    public ContactInfo() {

    }

    protected ContactInfo(Parcel in) {
        emailAddresses = new ArrayList();
        in.readTypedList(emailAddresses, EmailAddresses.CREATOR);
    }

    public static final Creator<ContactInfo> CREATOR = new Creator<ContactInfo>() {
        @Override
        public ContactInfo createFromParcel(Parcel in) {
            return new ContactInfo(in);
        }

        @Override
        public ContactInfo[] newArray(int size) {
            return new ContactInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(emailAddresses);
    }



    private List<EmailAddresses> emailAddresses;
}