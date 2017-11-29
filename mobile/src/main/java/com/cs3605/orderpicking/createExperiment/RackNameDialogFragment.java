package com.cs3605.orderpicking.createExperiment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cs3605.orderpicking.R;

public class RackNameDialogFragment extends DialogFragment {

    private RackNameChangeListener rackNameChangeListener;
    private EditText editText;
    private TextView warningText;

    public static RackNameDialogFragment newInstance(RackNameChangeListener rackNameChangeListener) {
        RackNameDialogFragment dialogFragment = new RackNameDialogFragment();
        dialogFragment.rackNameChangeListener = rackNameChangeListener;
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.rack_name_dialog_view, null);
        editText = view.findViewById(R.id.rack_name_edit_text);
        warningText = view.findViewById(R.id.rack_name_warning_text);

        return new AlertDialog.Builder(getContext())
                .setTitle("Change Rack Name")
                .setView(view)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String rackName = editText.getText().toString().trim();
                        if (rackName.length() == 5) {
                            rackNameChangeListener.onRackNameChange(rackName);
                            dismiss();
                        } else {
                            warningText.setText("Rack ID must be 5 characters!");
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .create();
    }
}
