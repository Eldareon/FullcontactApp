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

import butterknife.Bind;
import butterknife.ButterKnife;

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
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ViewHolderMainInfo extends ViewHolder {

        @Bind(R.id.companyLogo)
        NetworkImageView companyLogo;
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
                v = mInflater.inflate(R.layout.rec_view_company_main_info, parent, false);
                return new ViewHolderMainInfo(v);
        }

    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {

            case TYPE_COMPANY_MAIN_INFO:

                ViewHolderMainInfo viewHolderMainInfo = (ViewHolderMainInfo) viewHolder;

                ImageLoader imageLoader = AppController.getInstance().getImageLoader();
                viewHolderMainInfo.companyLogo.setImageUrl(mDataset.get(position).getLogoUrl(), imageLoader);
                viewHolderMainInfo.companyName.setText(mDataset.get(position).getName());
                viewHolderMainInfo.companyWebsite.setText(mDataset.get(position).getWebsite());

                if (!mDataset.get(position).getYearFounded().equalsIgnoreCase(""))
                    viewHolderMainInfo.companyFoundedYear.setText(mDataset.get(position).getYearFounded());
                else
                    viewHolderMainInfo.companyFoundedYear.setText(R.string.no_info);

                if (!mDataset.get(position).getEmployeeCount().equalsIgnoreCase(""))
                    viewHolderMainInfo.companyEmployeeCount.setText(mDataset.get(position).getEmployeeCount());
                else
                    viewHolderMainInfo.companyEmployeeCount.setText(R.string.no_info);
                break;

            case TYPE_COMPANY_URLS:

                ViewHolderUrls viewHolderUrls = (ViewHolderUrls) viewHolder;

                viewHolderUrls.companyLinkUrl.setText(mDataset.get(position).getLinkUrl());
                break;

            case TYPE_COMPANY_EMAILS:

                ViewHolderEmails viewHolderEmails = (ViewHolderEmails) viewHolder;

                viewHolderEmails.companyEmail.setText(mDataset.get(position).getEmail());
                break;

            case TYPE_COMPANY_BIOS:

                ViewHolderBios viewHolderBios = (ViewHolderBios) viewHolder;

                viewHolderBios.companyTypeName.setText(mDataset.get(position).getTypeName());
                viewHolderBios.companyBio.setText(mDataset.get(position).getBioText());
                break;

        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();

    }

    public RecyclerAdapter(ArrayList<Company> myDataset) {
        mDataset = myDataset;
    }
}