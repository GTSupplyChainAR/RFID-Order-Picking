package com.cs3605.orderpicking.newExperiment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cs3605.orderpicking.R;
import com.cs3605.orderpicking.data.Experiment;
import com.cs3605.orderpicking.data.ExperimentDBHelper;

import java.util.ArrayList;

public class NewExperimentDialogFragment extends DialogFragment {

    private RecyclerView recyclerView;
    private NewExperimentClickListener newExperimentClickListener;
    private ArrayList<Experiment> experimentList;

    public static NewExperimentDialogFragment newInstance() {
        return new NewExperimentDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Click listener will be MainActivity
        newExperimentClickListener = (NewExperimentClickListener) getContext();
        experimentList = new ExperimentDBHelper(getContext()).getAllExperiments();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.new_experiment_dialog_layout, null);
        recyclerView = view.findViewById(R.id.new_experiment_recyclerview);

        setupRecyclerView();

        return new AlertDialog.Builder(getContext())
                .setTitle("Select Experiment")
                .setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .create();
    }

    private void setupRecyclerView() {
        recyclerView.setAdapter(new ExperimentRecyclerAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private class ExperimentRecyclerAdapter extends RecyclerView.Adapter<ExperimentViewHolder> {

        @Override
        public ExperimentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ExperimentViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.viewholder_new_experiment_dialog, parent, false));
        }

        @Override
        public void onBindViewHolder(ExperimentViewHolder holder, int position) {
            holder.bind(experimentList.get(position));
        }

        @Override
        public int getItemCount() {
            return experimentList.size();
        }
    }

    private class ExperimentViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private TextView experimentNameTextView;

        public ExperimentViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            experimentNameTextView = itemView.findViewById(R.id.experiment_viewholder_name_textview);
        }

        public void bind(final Experiment experiment) {
            experimentNameTextView.setText(experiment.getExperimentName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newExperimentClickListener.onClick(experiment.getId());
                }
            });

        }
    }
}
