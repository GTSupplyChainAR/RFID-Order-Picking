package com.cs3605.orderpicking.createExperiment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs3605.orderpicking.R;
import com.cs3605.orderpicking.data.Trial;

public class TrialCreationFragment extends Fragment {

    private Trial trial;

    public static TrialCreationFragment newInstance() {
        return new TrialCreationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trial = new Trial();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trial_creation, container, false);

        // TODO: Configure view

        return view;
    }

    public Trial getTrial() {
        return trial;
    }
}
