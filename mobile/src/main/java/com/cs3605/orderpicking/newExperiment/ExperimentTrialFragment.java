package com.cs3605.orderpicking.newExperiment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs3605.orderpicking.R;
import com.cs3605.orderpicking.data.Trial;
import com.cs3605.orderpicking.util.TrialView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExperimentTrialFragment extends Fragment {

    private static final String ARG_TRIAL = "trial";

    @BindView(R.id.create_trial_trialview)
    TrialView trialView;

    private Trial trial;

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

        trial = (Trial) getArguments().getSerializable(ARG_TRIAL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trial_creation, container, false);
        ButterKnife.bind(this, view);

        trialView.setTrial(trial);
        trialView.setEnabled(false);

        return view;
    }
}
