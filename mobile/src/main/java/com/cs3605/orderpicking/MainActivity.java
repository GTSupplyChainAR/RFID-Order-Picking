package com.cs3605.orderpicking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cs3605.orderpicking.bluetooth.GlassClientBluetoothInterface;
import com.cs3605.orderpicking.createExperiment.CreateExperimentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.new_experiment_button)
    Button newExpButton;

    @BindView(R.id.create_experiment_button)
    Button createExperimentsButton;

    @BindView(R.id.view_saved_experiments_button)
    Button viewSavedExperimentsButton;

    // TODO: Remove
    @BindView(R.id.send_to_glass_et)
    EditText glassMessageET;

    @BindView(R.id.send_to_glass_button)
    Button sendToGlassButton;

    private GlassClientBluetoothInterface btInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupViews();

        setupBT();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        btInterface.stop();
    }

    private void setupViews() {
        newExpButton.setOnClickListener(this);
        createExperimentsButton.setOnClickListener(this);
        viewSavedExperimentsButton.setOnClickListener(this);
        sendToGlassButton.setOnClickListener(this);
    }

    private void setupBT() {
        btInterface = new GlassClientBluetoothInterface(this);
        btInterface.connectToGlass();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_experiment_button:
                // TODO: New alert dialog with list of created experiments
                break;
            case R.id.create_experiment_button:
                // TODO: Diaong to create, view history
                startActivity(CreateExperimentActivity.newIntent(this));
                break;
            case R.id.view_saved_experiments_button:
                // TODO: Launch activity to view/edit experiments
                break;

            // TODO: REmove
            case R.id.send_to_glass_button:
                // TODO: Send message to Glass
                btInterface.sendString(glassMessageET.getText().toString().trim());
                break;
        }
    }
}
