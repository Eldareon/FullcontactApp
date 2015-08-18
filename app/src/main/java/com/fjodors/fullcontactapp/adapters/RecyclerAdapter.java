package com.fjodors.fullcontactapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fjodors.fullcontactapp.R;
import com.fjodors.fullcontactapp.models.Company;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Fjodors on 2015.05.10..
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Company company;
    private Context context;

    private final int TYPE_COMPANY_MAIN_INFO = 0;
    private final int TYPE_COMPANY_URLS = 1;
    private final int TYPE_COMPANY_EMAILS = 2;
    private final int TYPE_COMPANY_BIOS = 3;

    private final int COMPANY_MAIN_INFO_SIZE = 1;

    private int linkListSize = 0;
    private int emailListSize = 0;
    private int bioListSize = 0;

    private List linkList = Collections.EMPTY_LIST;
    private List emailList = Collections.EMPTY_LIST;
    private List bioList = Collections.EMPTY_LIST;


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ViewHolderMainInfo extends ViewHolder {

        @Bind(R.id.companyLogo)
        ImageView companyLogo;
        @Bind(R.id.companyName)
        TextView companyName;
        @Bind(R.id.foundedYear)
        TextView companyFoundedYear;
        @Bind(R.id.employeeCount)
        TextView companyEmployeeCount;
        @Bind(R.id.website)
        TextView companyWebsite;

        public ViewHolderMainInfo(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class ViewHolderUrls extends ViewHolder {

        @Bind(R.id.companyUrl)
        TextView companyLinkUrl;

        public ViewHolderUrls(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class ViewHolderEmails extends ViewHolder {

        @Bind(R.id.companyEmail)
        TextView companyEmail;

        public ViewHolderEmails(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class ViewHolderBios extends ViewHolder {

        @Bind(R.id.companyTypeName)
        TextView companyTypeName;
        @Bind(R.id.companyBio)
        TextView companyBio;

        public ViewHolderBios(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (position < COMPANY_MAIN_INFO_SIZE)
            return TYPE_COMPANY_MAIN_INFO;

        else if (position < linkListSize + COMPANY_MAIN_INFO_SIZE)
            return TYPE_COMPANY_URLS;

        else if (position < emailListSize + linkListSize + COMPANY_MAIN_INFO_SIZE)
            return TYPE_COMPANY_EMAILS;

        else if (position < bioListSize + emailListSize + linkListSize + COMPANY_MAIN_INFO_SIZE)
            return TYPE_COMPANY_BIOS;

        return 0;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v;

        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case TYPE_COMPANY_MAIN_INFO:
                v = mInflater.inflate(R.layout.rec_view_company_main_info, parent, false);
                return new ViewHolderMainInfo(v);

            case TYPE_COMPANY_URLS:
                v = mInflater.inflate(R.layout.rec_view_company_url, parent, false);
                return new ViewHolderUrls(v);

            case TYPE_COMPANY_EMAILS:
                v = mInflater.inflate(R.layout.rec_view_company_email, parent, false);
                return new ViewHolderEmails(v);

            case TYPE_COMPANY_BIOS:
                v = mInflater.inflate(R.layout.rec_view_company_social_profile, parent, false);
                return new ViewHolderBios(v);

            default:
                return null;
        }

    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {

            case TYPE_COMPANY_MAIN_INFO:

                ViewHolderMainInfo viewHolderMainInfo = (ViewHolderMainInfo) viewHolder;

                if (company != null) {

                    if (company.getLogo() != null)
                        Picasso.with(context)
                                .load(company.getLogo())
                                .into(viewHolderMainInfo.companyLogo);

                    if (company.getWebsite() != null)
                        viewHolderMainInfo.companyWebsite.setText(company.getWebsite());

                    if (company.getOrganization() != null) {

                        if (company.getOrganization().getName() != null)
                            viewHolderMainInfo.companyName.setText(company.getOrganization().getName());

                        if (company.getOrganization().getFounded() != null) {
                            if (!company.getOrganization().getFounded().equalsIgnoreCase(""))
                                viewHolderMainInfo.companyFoundedYear.setText(company.getOrganization().getFounded());
                            else
                                viewHolderMainInfo.companyFoundedYear.setText(R.string.no_info);
                        } else {
                            viewHolderMainInfo.companyFoundedYear.setText(R.string.no_info);
                        }

                        if (company.getOrganization().getApproxEmployees() != null) {
                            if (!company.getOrganization().getApproxEmployees().equalsIgnoreCase(""))
                                viewHolderMainInfo.companyEmployeeCount.setText(company.getOrganization().getApproxEmployees());
                            else
                                viewHolderMainInfo.companyEmployeeCount.setText(R.string.no_info);
                        } else {
                            viewHolderMainInfo.companyEmployeeCount.setText(R.string.no_info);
                        }
                    }
                }

                break;

            case TYPE_COMPANY_URLS:

                ViewHolderUrls viewHolderUrls = (ViewHolderUrls) viewHolder;

                viewHolderUrls.companyLinkUrl.setText(company.getOrganization().getLinks().get(position - COMPANY_MAIN_INFO_SIZE).getUrl());
                break;

            case TYPE_COMPANY_EMAILS:

                ViewHolderEmails viewHolderEmails = (ViewHolderEmails) viewHolder;

                viewHolderEmails.companyEmail.setText(company.getOrganization().getContactInfo().getEmailAddresses().get(position - linkListSize - COMPANY_MAIN_INFO_SIZE).getValue());
                break;

            case TYPE_COMPANY_BIOS:

                ViewHolderBios viewHolderBios = (ViewHolderBios) viewHolder;

                viewHolderBios.companyTypeName.setText(company.getSocialProfiles().get(position - emailListSize - linkListSize - COMPANY_MAIN_INFO_SIZE).getTypeName());
                viewHolderBios.companyBio.setText(company.getSocialProfiles().get(position - emailListSize - linkListSize - COMPANY_MAIN_INFO_SIZE).getBio());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return COMPANY_MAIN_INFO_SIZE + linkListSize + emailListSize + bioListSize;
    }

    public RecyclerAdapter(Company mCompany, Context mContext) {
        company = mCompany;
        context = mContext;
        initDataLists();
    }


    public void initDataLists() {
        //check for null to avoid NPE in future for lists
        if (company.getOrganization() != null) {
            if (company.getOrganization().getLinks() != null)
                linkList = company.getOrganization().getLinks();
            if (company.getOrganization().getContactInfo() != null) {
                if (company.getOrganization().getContactInfo().getEmailAddresses() != null)
                    emailList = company.getOrganization().getContactInfo().getEmailAddresses();
            }
            if (company.getSocialProfiles() != null)
                bioList = company.getSocialProfiles();
        }

        linkListSize = linkList.size();
        emailListSize = emailList.size();
        bioListSize = bioList.size();
    }


}
