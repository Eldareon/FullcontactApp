package com.fjodors.fullcontactapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fjodors on 2015.05.10..
 */
public class Company implements Parcelable {


    private String name;
    private String logoUrl;
    private String employeeCount;
    private String yearFounded;
    private String linkUrl;
    private String email;
    private String website;
    private String typeName;
    private String bioText;

    public Company() {

    }

    public String getYearFounded() {
        return yearFounded;
    }

    public void setYearFounded(String yearFounded) {
        this.yearFounded = yearFounded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(String employeeCount) {
        this.employeeCount = employeeCount;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getBioText() {
        return bioText;
    }

    public void setBioText(String bioText) {
        this.bioText = bioText;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", employeeCount='" + employeeCount + '\'' +
                ", yearFounded='" + yearFounded + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", email='" + email + '\'' +
                ", website='" + website + '\'' +
                ", typeName='" + typeName + '\'' +
                ", bioText='" + bioText + '\'' +
                '}';
    }

    /* everything below here is for implementing Parcelable */

    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(logoUrl);
        out.writeString(employeeCount);
        out.writeString(yearFounded);
        out.writeString(linkUrl);
        out.writeString(email);
        out.writeString(website);
        out.writeString(typeName);
        out.writeString(bioText);
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
        name = in.readString();
        logoUrl = in.readString();
        employeeCount = in.readString();
        yearFounded = in.readString();
        linkUrl = in.readString();
        email = in.readString();
        website = in.readString();
        typeName = in.readString();
        bioText = in.readString();
    }


}
