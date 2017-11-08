package com.cs3605.orderpicking.savedExperiments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedExperimentsActivity extends AppCompatActivity {

    @BindView(R.id.saved_experiments_no_experiments_textview)
    TextView noExperimentsTextView;

    @BindView(R.id.saved_experiments_recyclerview)
    RecyclerView recyclerView;

    private ExperimentDBHelper dbHelper;
    private ExperimentRecyclerViewAdapter adapter;
    private ArrayList<Experiment> experimentList;

    public static Intent newIntent(Context context) {
        return new Intent(context, SavedExperimentsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_experiments);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new ExperimentDBHelper(this);
        experimentList = dbHelper.getAllExperiments();

        if (!experimentList.isEmpty()) {
            noExperimentsTextView.setVisibility(View.GONE);
            setupRecyclerView();
        }
    }

    private void setupRecyclerView() {
        adapter = new ExperimentRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void deleteExperimentPrompt(int experimentPosition) {
        final Experiment experiment = experimentList.get(experimentPosition);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Delete " + experiment.getExperimentName() + "?");
        dialog.setMessage("Are you sure you want to delete experiment " + experiment.getExperimentName() + "? This will remove it permanently!");
        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHelper.deleteExperiment(experiment.getId());
                experimentList = dbHelper.getAllExperiments();
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private class ExperimentRecyclerViewAdapter extends RecyclerView.Adapter<ExperimentViewHolder> {

        @Override
        public ExperimentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ExperimentViewHolder(LayoutInflater.from(SavedExperimentsActivity.this).inflate(R.layout.viewholder_experiment, parent, false));
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

        private TextView nameTextView;
        private TextView trialsTextView;

        public ExperimentViewHolder(View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    deleteExperimentPrompt(getAdapterPosition());
                    return true;
                }
            });
            nameTextView = itemView.findViewById(R.id.experiment_viewholder_name_textview);
            trialsTextView = itemView.findViewById(R.id.experiment_viewholder_trials_textview);
        }

        public void bind(Experiment experiment) {
            nameTextView.setText(experiment.getExperimentName());
            trialsTextView.setText(getResources().getQuantityString(R.plurals.trials_quantity, experiment.getTrialList().size(), experiment.getTrialList().size()));
        }
    }
}
