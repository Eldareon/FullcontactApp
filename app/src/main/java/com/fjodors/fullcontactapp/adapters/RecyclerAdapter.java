package com.fjodors.fullcontactapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.fjodors.fullcontactapp.R;
import com.fjodors.fullcontactapp.app.AppController;
import com.fjodors.fullcontactapp.models.Company;

import java.util.ArrayList;

/**
 * Created by Fjodors on 2015.05.10..
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<Company> mDataset;

    private final int TYPE_COMPANY_MAIN_INFO = 0;
    private final int TYPE_COMPANY_URLS = 1;
    private final int TYPE_COMPANY_EMAILS = 2;
    private final int TYPE_COMPANY_BIOS = 3;

    public class ViewHolder extends RecyclerView.ViewHolder {

        //viewtype = TYPE_COMPANY_MAIN_INFO
        public NetworkImageView companyLogo;
        public TextView companyName;
        public TextView companyFoundedYear;
        public TextView companyEmployeeCount;
        public TextView companyWebsite;

        //viewtype = TYPE_COMPANY_URLS
        public TextView companyLinkUrl;

        //viewtype = TYPE_COMPANY_EMAIL
        public TextView companyEmail;

        //viewtype = TYPE_COMPANY_BIOS
        public TextView companyTypeName;
        public TextView companyBio;


        public ViewHolder(View v, int viewType) {
            super(v);
            if (viewType == TYPE_COMPANY_MAIN_INFO) {
                companyLogo = (NetworkImageView) v.findViewById(R.id.companyLogo);
                companyName = (TextView) v.findViewById(R.id.companyName);
                companyFoundedYear = (TextView) v.findViewById(R.id.foundedYear);
                companyEmployeeCount = (TextView) v.findViewById(R.id.employeeCount);
                companyWebsite = (TextView) v.findViewById(R.id.website);
            } else if (viewType == TYPE_COMPANY_URLS) {
                companyLinkUrl = (TextView) v.findViewById(R.id.companyUrl);
            } else if (viewType == TYPE_COMPANY_EMAILS) {
                companyEmail = (TextView) v.findViewById(R.id.companyEmail);
            } else if (viewType == TYPE_COMPANY_BIOS) {
                companyTypeName = (TextView) v.findViewById(R.id.companyTypeName);
                companyBio = (TextView) v.findViewById(R.id.companyBio);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v;
        ViewHolder vh;

        switch (viewType) {
            case TYPE_COMPANY_MAIN_INFO:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rec_view_company_main_info, parent, false);
                vh = new ViewHolder(v, viewType);
                return vh;
            case TYPE_COMPANY_URLS:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rec_view_company_url, parent, false);
                vh = new ViewHolder(v, viewType);
                return vh;

            case TYPE_COMPANY_EMAILS:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rec_view_company_email, parent, false);
                vh = new ViewHolder(v, viewType);
                return vh;

            case TYPE_COMPANY_BIOS:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rec_view_company_social_profile, parent, false);
                vh = new ViewHolder(v, viewType);
                return vh;

            default:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rec_view_company_main_info, parent, false);
                vh = new ViewHolder(v, viewType);

                return vh;
        }
    }


    @Override
    public int getItemViewType(int position) {

        if (mDataset.get(position).getName() != null)
            return TYPE_COMPANY_MAIN_INFO;

        if (mDataset.get(position).getLinkUrl() != null)
            return TYPE_COMPANY_URLS;

        if (mDataset.get(position).getEmail() != null)
            return TYPE_COMPANY_EMAILS;

        if (mDataset.get(position).getTypeName() != null)
            return TYPE_COMPANY_BIOS;

        return 0;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (position == TYPE_COMPANY_MAIN_INFO) {
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            holder.companyLogo.setImageUrl(mDataset.get(position).getLogoUrl(), imageLoader);
            holder.companyName.setText(mDataset.get(position).getName());
            holder.companyWebsite.setText(mDataset.get(position).getWebsite());

            if (!mDataset.get(position).getYearFounded().equalsIgnoreCase(""))
                holder.companyFoundedYear.setText(mDataset.get(position).getYearFounded());
            else
                holder.companyFoundedYear.setText(R.string.no_info);
            if (!mDataset.get(position).getEmployeeCount().equalsIgnoreCase(""))
                holder.companyEmployeeCount.setText(mDataset.get(position).getEmployeeCount());
            else
                holder.companyEmployeeCount.setText(R.string.no_info);
        }
        if (mDataset.get(position).getLinkUrl() != null) {
            holder.companyLinkUrl.setText(mDataset.get(position).getLinkUrl());
        }

        if (mDataset.get(position).getEmail() != null) {
            holder.companyEmail.setText(mDataset.get(position).getEmail());
        }

        if (mDataset.get(position).getTypeName() != null) {
            holder.companyTypeName.setText(mDataset.get(position).getTypeName());
            holder.companyBio.setText(mDataset.get(position).getBioText());
        }

    }

    public RecyclerAdapter(ArrayList<Company> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();

    }
}