package com.fjodors.fullcontactapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Created by Fjodors on 2015.05.10..
 */

@Data
public class Company implements Parcelable {

    @Data
    public static class Organization implements Parcelable {

        public Organization(){
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

        @Data
        public static class Links implements Parcelable {
            private String url;

            public Links(){

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

        @Data
        public static class ContactInfo implements Parcelable {

            public ContactInfo(){

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

            @Data
            public static class EmailAddresses implements Parcelable {
                private String value;

                public EmailAddresses(){

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

            private List<EmailAddresses> emailAddresses;
        }

        private String name;
        private String approxEmployees;
        private String founded;
        private ContactInfo contactInfo;
        private List<Links> links;
    }


    @Data
    public static class SocialProfiles implements Parcelable {
        private String typeName;
        private String bio;

        public SocialProfiles(){
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
