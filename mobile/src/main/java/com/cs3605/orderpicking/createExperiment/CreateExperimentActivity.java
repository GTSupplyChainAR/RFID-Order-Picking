package com.cs3605.orderpicking.createExperiment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.cs3605.orderpicking.R;
import com.cs3605.orderpicking.util.MultiFragmentSwipeAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateExperimentActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.create_experiment_viewpager)
    ViewPager viewPager;

    @BindView(R.id.previous_button)
    TextView previousButton;

    @BindView(R.id.save_button)
    TextView saveButton;

    @BindView(R.id.next_button)
    TextView nextButton;

    private MultiFragmentSwipeAdapter adapter;
    private ArrayList<TrialCreationFragment> fragmentList = new ArrayList<>();

    public static Intent newIntent(Context context) {
        return new Intent(context, CreateExperimentActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_experiment);
        ButterKnife.bind(this);

        setupViews();
    }

    private void setupViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TrialCreationFragment initialFragment = TrialCreationFragment.newInstance();
        fragmentList.add(initialFragment);
        fragmentList.add(TrialCreationFragment.newInstance());

        adapter = new MultiFragmentSwipeAdapter<>(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);

        previousButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO: Add another (with button)
        // TODO: Remove (with button)
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.previous_button:
                if (viewPager.getCurrentItem() != 0) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                }
                break;
            case R.id.save_button:
                // Create experiment and save

                break;
            case R.id.next_button:
                if (viewPager.getCurrentItem() != adapter.getCount() - 1) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
                break;
        }
    }
}
