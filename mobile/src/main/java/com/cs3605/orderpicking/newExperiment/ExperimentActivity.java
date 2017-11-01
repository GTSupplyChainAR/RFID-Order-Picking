package com.cs3605.orderpicking.newExperiment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.cs3605.orderpicking.R;
import com.cs3605.orderpicking.bluetooth.GlassClientBluetoothInterface;
import com.cs3605.orderpicking.data.Experiment;
import com.cs3605.orderpicking.data.ExperimentDBHelper;
import com.cs3605.orderpicking.data.Trial;
import com.cs3605.orderpicking.util.MultiFragmentSwipeAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExperimentActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Cap this at 6 for memory concerns
     */
    private static final int MAX_EXPERIMENT_TRIAL_SIZE = 6;

    @BindView(R.id.experiment_viewpager)
    ViewPager viewPager;

    @BindView(R.id.previous_button)
    TextView previousButton;

    @BindView(R.id.send_to_glass_button)
    TextView sendButton;

    @BindView(R.id.next_button)
    TextView nextButton;

    private MultiFragmentSwipeAdapter adapter;
    private ArrayList<ExperimentTrialFragment> fragmentList = new ArrayList<>();
    private static final int INVALID_ID = -1;
    private static final String ARG_EXPERIMENT_ID = "experiment_id";

    private Experiment experiment;
    private GlassClientBluetoothInterface bluetoothInterface;

    public static Intent getIntent(Context context, int experimentId) {
        Bundle args = new Bundle();
        args.putInt(ARG_EXPERIMENT_ID, experimentId);

        Intent intent = new Intent(context, ExperimentActivity.class);
        intent.putExtras(args);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);
        ButterKnife.bind(this);

        int experimentId = getIntent().getIntExtra(ARG_EXPERIMENT_ID, INVALID_ID);

        if (experimentId == INVALID_ID) {
            finish();
        }

        experiment = new ExperimentDBHelper(this).getExperiment(experimentId);

        setupBT();
        setupViews();
    }

    private void setupBT() {
        bluetoothInterface = new GlassClientBluetoothInterface(this);
        bluetoothInterface.connectToGlass();
    }

    private void setupViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        for (Trial trial : experiment.getTrialList()) {
            ExperimentTrialFragment fragment = ExperimentTrialFragment.newInstance(trial);
            fragmentList.add(fragment);
        }

        adapter = new MultiFragmentSwipeAdapter<>(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(MAX_EXPERIMENT_TRIAL_SIZE);

        previousButton.setOnClickListener(this);
        sendButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.previous_button:
                if (viewPager.getCurrentItem() != 0) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                }
                break;
            case R.id.send_to_glass_button:
                bluetoothInterface.sendTrial(experiment.getTrialList().get(viewPager.getCurrentItem()));
                break;
            case R.id.next_button:
                if (viewPager.getCurrentItem() != adapter.getCount() - 1) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
                break;
        }
    }
}
