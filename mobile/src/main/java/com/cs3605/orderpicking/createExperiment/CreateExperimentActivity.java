package com.cs3605.orderpicking.createExperiment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cs3605.orderpicking.R;
import com.cs3605.orderpicking.data.Experiment;
import com.cs3605.orderpicking.data.ExperimentDBHelper;
import com.cs3605.orderpicking.data.Trial;
import com.cs3605.orderpicking.util.MultiFragmentSwipeAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateExperimentActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Cap this at 6 for memory concerns
     */
    private static final int MAX_EXPERIMENT_TRIAL_SIZE = 6;

    @BindView(R.id.create_experiment_viewpager)
    ViewPager viewPager;

    @BindView(R.id.previous_button)
    TextView previousButton;

    @BindView(R.id.save_button)
    TextView saveButton;

    @BindView(R.id.next_button)
    TextView nextButton;

    @BindView(R.id.create_experiment_name_et)
    EditText experimentNameET;

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

        fragmentList.add(TrialCreationFragment.newInstance());

        adapter = new MultiFragmentSwipeAdapter<>(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(MAX_EXPERIMENT_TRIAL_SIZE);

        previousButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create_experiment, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                if (fragmentList.size() >= 6) {
                    // Don't allow user more than 5 trials
                    break;
                }
                fragmentList.add(TrialCreationFragment.newInstance());
                adapter.notifyDataSetChanged();
                viewPager.setCurrentItem(fragmentList.size() - 1);
                break;
            case R.id.action_delete:
                // TODO: Fix
                if (fragmentList.size() == 1) {
                    // Don't allow user to delete all fragments
                    break;
                }

                fragmentList.remove(viewPager.getCurrentItem());
                adapter.setFragmentList(fragmentList);
                adapter.notifyDataSetChanged();
                break;
        }
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
                if (experimentNameET.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "Experiment name is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create experiment and save
                ArrayList<Trial> trialList = new ArrayList<>();
                for (TrialCreationFragment fragment : fragmentList) {
                    trialList.add(fragment.getTrial());
                }

                trialList = addTrialsToDb(trialList);

                Experiment experiment = new Experiment();
                experiment.setExperimentName(experimentNameET.getText().toString().trim());
                experiment.setTrialList(trialList);

                ExperimentDBHelper experimentDBHelper = new ExperimentDBHelper(this);
                experimentDBHelper.addExperiment(experiment);
                finish();
                break;
            case R.id.next_button:
                if (viewPager.getCurrentItem() != adapter.getCount() - 1) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
                break;
        }
    }

    private ArrayList<Trial> addTrialsToDb(ArrayList<Trial> trialList) {
        ArrayList<Trial> updatedTrials = new ArrayList<>();
        ExperimentDBHelper experimentDBHelper = new ExperimentDBHelper(this);
        for (Trial trial : trialList) {
            long id = experimentDBHelper.addTrial(trial);
            trial.setId(id);
            updatedTrials.add(trial);
        }

        return updatedTrials;
    }
}
