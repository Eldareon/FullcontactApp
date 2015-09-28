package com.fjodors.fullcontactapp.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.fjodors.fullcontactapp.App;
import com.fjodors.fullcontactapp.R;
import com.google.common.base.Optional;

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
        socialProfiles = Collections.EMPTY_LIST;
    }

    public String getOrganizationName() {
        return Optional.fromNullable(getOrganization().getName()).or("");
    }

    public String getOrganizationFounded() {
        return Optional.fromNullable(getOrganization().getFounded()).or(App.getContext().getResources().getString(R.string.no_info));
    }

    public String getOrganizationApproxEmployees() {
        return Optional.fromNullable(getOrganization().getApproxEmployees()).or(App.getContext().getResources().getString(R.string.no_info));
    }

    public List<Organization.Links> getOrganizationLinkList() {
        return getOrganization().getLinks();
    }

    public Organization.Links getOrganizationLink(int position) {
        return Optional.fromNullable(getOrganizationLinkList().get(position)).or(new Organization.Links());
    }

    public String getOrganizationUrl(int position) {
        return Optional.fromNullable(getOrganizationLink(position).getUrl()).or("");
    }

    public Organization.ContactInfo getOrganizationContactInfo() {
        return Optional.fromNullable(getOrganization().getContactInfo()).or(new Organization.ContactInfo());
    }

    public List<Organization.EmailAddress> getOrganizationEmailList() {
        return getOrganizationContactInfo().getEmailAddresses();
    }

    public Organization.EmailAddress getOrganizationEmail(int position) {
        return Optional.fromNullable(getOrganizationEmailList().get(position)).or(new Organization.EmailAddress());
    }

    public String getOrganizationEmailValue(int position) {
        return Optional.fromNullable(getOrganizationEmail(position).getValue()).or("");
    }

    public String getSocialProfileTypeName(int position) {
        return Optional.fromNullable(getSocialProfiles().get(position).getTypeName()).or("");
    }

    public String getSocialProfileBio(int position) {
        return Optional.fromNullable(getSocialProfiles().get(position).getBio()).or("");
    }


    @Data
    public static class Organization implements Serializable {
        private String name;
        private String approxEmployees;
        private String founded;
        private ContactInfo contactInfo;
        private List<Links> links;

        public Organization() {
            links = Collections.EMPTY_LIST;
        }


        @Data
        public static class Links implements Serializable {
            private String url;

            public Links() {

            }
        }

        @Data
        public static class ContactInfo implements Serializable {
            private List<EmailAddress> emailAddresses;

            public ContactInfo() {
                emailAddresses = Collections.EMPTY_LIST;
            }
        }

        @Data
        public static class EmailAddress implements Serializable {
            private String value;

            public EmailAddress() {

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
