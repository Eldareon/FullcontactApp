package com.fjodors.fullcontactapp.activities;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.fjodors.fullcontactapp.R;
import com.fjodors.fullcontactapp.adapters.RecyclerAdapter;
import com.fjodors.fullcontactapp.models.Company;

import java.util.ArrayList;

/**
 * Created by Fjodors on 2015.05.08..
 */
public class ResultActivity extends MenuActivity {

    protected static final String TAG = "Fullcontact-ResultScreen";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        actionBar.setDisplayHomeAsUpEnabled(true);

        ArrayList<Company> companyData =  getIntent().getParcelableArrayListExtra("companyData");

        mRecyclerView = (RecyclerView) findViewById(R.id.companyData);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerAdapter(companyData);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
