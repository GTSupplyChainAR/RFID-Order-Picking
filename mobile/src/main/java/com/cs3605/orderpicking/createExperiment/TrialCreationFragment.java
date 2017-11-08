package com.cs3605.orderpicking.createExperiment;

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

public class TrialCreationFragment extends Fragment {

    @BindView(R.id.create_trial_trialview)
    TrialView trialView;

    public static TrialCreationFragment newInstance() {
        return new TrialCreationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trial_creation, container, false);
        ButterKnife.bind(this, view);

        // TODO: Ability to edit rack name
        return view;
    }

    public Trial getTrial() {
        return trialView.getTrial();
    }
}
