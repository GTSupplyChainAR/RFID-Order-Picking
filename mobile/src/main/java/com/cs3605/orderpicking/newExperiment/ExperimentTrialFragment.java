package com.cs3605.orderpicking.newExperiment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cs3605.orderpicking.R;
import com.cs3605.orderpicking.data.Trial;
import com.cs3605.orderpicking.util.PreferenceInterface;
import com.cs3605.orderpicking.util.TrialView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExperimentTrialFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_TRIAL = "trial";

    @BindView(R.id.create_trial_trialview)
    TrialView trialView;

    @BindView(R.id.reset_trial_button)
    Button resetTrialButton;

    private OnTrialUpdatedListener onTrialUpdatedListener;
    private Trial originalTrial;
    private Trial trial;

    private PreferenceInterface prefInterface;

    public static ExperimentTrialFragment newInstance(Trial trial) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRIAL, trial);

        ExperimentTrialFragment fragment = new ExperimentTrialFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        originalTrial = (Trial) getArguments().getSerializable(ARG_TRIAL);
        trial = originalTrial.copy();

        onTrialUpdatedListener = (OnTrialUpdatedListener) getActivity();
        prefInterface = new PreferenceInterface(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trial_creation, container, false);
        ButterKnife.bind(this, view);

        trialView.setTrial(trial);
        trialView.setEnabled(false);

        resetTrialButton.setOnClickListener(this);

        return view;
    }

    public void onRFIDScanned(String tagId) {
        PreferenceInterface.BIN_ID_FLAG binIdFlag = prefInterface.checkBinId(tagId);

        switch (binIdFlag) {
            case BIN11:
                if (trial.getR1Quantity() != 0) {
                    trial.setR1Quantity(0);
                }
                break;
            case BIN12:
                if (trial.getR2Quantity() != 0) {
                    trial.setR2Quantity(0);
                }
                break;
            case BIN13:
                if (trial.getR3Quantity() != 0) {
                    trial.setR3Quantity(0);
                }
                break;
            case BIN21:
                if (trial.getY1Quantity() != 0) {
                    trial.setY1Quantity(0);
                }
                break;
            case BIN22:
                if (trial.getY2Quantity() != 0) {
                    trial.setY2Quantity(0);
                }
                break;
            case BIN23:
                if (trial.getY3Quantity() != 0) {
                    trial.setY3Quantity(0);
                }
                break;
            case BIN31:
                if (trial.getG1Quantity() != 0) {
                    trial.setG1Quantity(0);
                }
                break;
            case BIN32:
                if (trial.getG2Quantity() != 0) {
                    trial.setG2Quantity(0);
                }
                break;
            case BIN33:
                if (trial.getG3Quantity() != 0) {
                    trial.setG3Quantity(0);
                }
                break;
            case BIN41:
                if (trial.getB1Quantity() != 0) {
                    trial.setB1Quantity(0);
                }
                break;
            case BIN42:
                if (trial.getB2Quantity() != 0) {
                    trial.setB2Quantity(0);
                }
                break;
            case BIN43:
                if (trial.getB3Quantity() != 0) {
                    trial.setB3Quantity(0);
                }
                break;
            //TODO: Cart
        }

        trialView.setTrial(trial);
        onTrialUpdatedListener.onTrialUpdated(trial);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset_trial_button:
                trial = originalTrial.copy();
                trialView.setTrial(trial);
                onTrialUpdatedListener.onTrialUpdated(trial);
                break;
        }
    }
}
