package com.fjodors.fullcontactapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Data;

/**
 * Created by Fjodors on 2015.05.10..
 */

@Data
public class Company implements Serializable {
    private String logo;
    private String website;
    private Organization organization;
    private List<SocialProfiles> socialProfiles;

    public Company() {

    }

    @Data
    public static class Organization implements Serializable {
        private String name;
        private String approxEmployees;
        private String founded;
        private ContactInfo contactInfo;
        private List<Links> links;

        public Organization() {

        }

        @Data
        public static class Links implements Serializable {
            private String url;

            public Links() {

            }
        }

        @Data
        public static class ContactInfo implements Serializable {
            private List<EmailAddresses> emailAddresses;

            public ContactInfo() {
            }
        }

        @Data
        public static class EmailAddresses implements Serializable {
            private String value;

            public EmailAddresses() {

            }
        }
    }

    @Data
    public static class SocialProfiles implements Serializable {
        private String typeName;
        private String bio;

        public SocialProfiles() {

        }
    }
}
