package com.cs3605.orderpicking.newExperiment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cs3605.orderpicking.R;
import com.cs3605.orderpicking.bluetooth.GlassClientBluetoothInterface;
import com.cs3605.orderpicking.bluetooth.XbandInterface;
import com.cs3605.orderpicking.data.Experiment;
import com.cs3605.orderpicking.data.ExperimentDBHelper;
import com.cs3605.orderpicking.data.Trial;
import com.cs3605.orderpicking.util.MultiFragmentSwipeAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.ubimax.xbandtest.xband.XBandEventListener;
import de.ubimax.xbandtest.xband.XBandIMUData;

public class ExperimentActivity extends AppCompatActivity implements View.OnClickListener, XBandEventListener, OnTrialUpdatedListener {

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
    private GlassClientBluetoothInterface glassInterface;
    private XbandInterface xbandInterface;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        glassInterface.stop();
        xbandInterface.disconnect();
    }

    private void setupBT() {
        glassInterface = new GlassClientBluetoothInterface(this);
        glassInterface.connectToGlass();

        xbandInterface = new XbandInterface(this);
        xbandInterface.connect();
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
                glassInterface.sendTrial(experiment.getTrialList().get(viewPager.getCurrentItem()));
                break;
            case R.id.next_button:
                if (viewPager.getCurrentItem() != adapter.getCount() - 1) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
                break;
        }
    }

    @Override
    public void onNewRFIDScan(final String rfidTag) {
        ((ExperimentTrialFragment) adapter.getItem(viewPager.getCurrentItem())).onRFIDScanned(rfidTag);
    }

    @Override
    public void onNewScanRSSI(short i) {
    }

    @Override
    public void onNewBatteryStatus(double v) {
    }

    @Override
    public void onTagWriteResponse(String s) {
    }

    @Override
    public void onNewIMUEntry(XBandIMUData xBandIMUData) {
    }

    @Override
    public void onTrialUpdated(final Trial trial) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                glassInterface.sendTrial(trial);
            }
        });
    }
}
