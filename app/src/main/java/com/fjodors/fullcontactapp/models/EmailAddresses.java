package com.fjodors.fullcontactapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

/**
 * Created by Fjodors on 2015.08.03..
 */
@Data
public class EmailAddresses implements Parcelable {
    private String value;

    public EmailAddresses() {

    }

    protected EmailAddresses(Parcel in) {
        value = in.readString();

    }

    public static final Creator<EmailAddresses> CREATOR = new Creator<EmailAddresses>() {
        @Override
        public EmailAddresses createFromParcel(Parcel in) {
            return new EmailAddresses(in);
        }

        @Override
        public EmailAddresses[] newArray(int size) {
            return new EmailAddresses[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(value);
    }
}