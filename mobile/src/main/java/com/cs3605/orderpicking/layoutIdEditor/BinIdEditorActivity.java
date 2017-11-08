package com.cs3605.orderpicking.layoutIdEditor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cs3605.orderpicking.R;
import com.cs3605.orderpicking.util.PreferenceInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BinIdEditorActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.bin_11_id_edittext)
    EditText bin11Edittext;
    @BindView(R.id.bin_12_id_edittext)
    EditText bin12Edittext;
    @BindView(R.id.bin_13_id_edittext)
    EditText bin13Edittext;
    @BindView(R.id.bin_21_id_edittext)
    EditText bin21Edittext;
    @BindView(R.id.bin_22_id_edittext)
    EditText bin22Edittext;
    @BindView(R.id.bin_23_id_edittext)
    EditText bin23Edittext;
    @BindView(R.id.bin_31_id_edittext)
    EditText bin31Edittext;
    @BindView(R.id.bin_32_id_edittext)
    EditText bin32Edittext;
    @BindView(R.id.bin_33_id_edittext)
    EditText bin33Edittext;
    @BindView(R.id.bin_41_id_edittext)
    EditText bin41Edittext;
    @BindView(R.id.bin_42_id_edittext)
    EditText bin42Edittext;
    @BindView(R.id.bin_43_id_edittext)
    EditText bin43Edittext;
    @BindView(R.id.cart_1_id_edittext)
    EditText cart1Edittext;
    @BindView(R.id.cart_2_id_edittext)
    EditText cart2Edittext;
    @BindView(R.id.cart_3_id_edittext)
    EditText cart3Edittext;
    @BindView(R.id.bin_id_save_button)
    LinearLayout saveButton;

    private PreferenceInterface prefInterface;

    public static Intent newIntent(Context context) {
        return new Intent(context, BinIdEditorActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_id_editor);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initPrefs();
        setupViews();
    }

    private void initPrefs() {
        prefInterface = new PreferenceInterface(this);
    }

    private void setupViews() {
        saveButton.setOnClickListener(this);

        bin11Edittext.setText(prefInterface.getBin11Id());
        bin12Edittext.setText(prefInterface.getBin12Id());
        bin13Edittext.setText(prefInterface.getBin13Id());
        bin21Edittext.setText(prefInterface.getBin21Id());
        bin22Edittext.setText(prefInterface.getBin22Id());
        bin23Edittext.setText(prefInterface.getBin23Id());
        bin31Edittext.setText(prefInterface.getBin31Id());
        bin32Edittext.setText(prefInterface.getBin32Id());
        bin33Edittext.setText(prefInterface.getBin33Id());
        bin41Edittext.setText(prefInterface.getBin41Id());
        bin42Edittext.setText(prefInterface.getBin42Id());
        bin43Edittext.setText(prefInterface.getBin43Id());

        cart1Edittext.setText(prefInterface.getCart1Id());
        cart2Edittext.setText(prefInterface.getCart2Id());
        cart3Edittext.setText(prefInterface.getCart3Id());
    }

    @Override
    public void onClick(View v) {
        prefInterface.setBin11Id(bin11Edittext.getText().toString().trim());
        prefInterface.setBin12Id(bin12Edittext.getText().toString().trim());
        prefInterface.setBin13Id(bin13Edittext.getText().toString().trim());
        prefInterface.setBin21Id(bin21Edittext.getText().toString().trim());
        prefInterface.setBin22Id(bin22Edittext.getText().toString().trim());
        prefInterface.setBin23Id(bin23Edittext.getText().toString().trim());
        prefInterface.setBin31Id(bin31Edittext.getText().toString().trim());
        prefInterface.setBin32Id(bin32Edittext.getText().toString().trim());
        prefInterface.setBin33Id(bin33Edittext.getText().toString().trim());
        prefInterface.setBin41Id(bin41Edittext.getText().toString().trim());
        prefInterface.setBin42Id(bin42Edittext.getText().toString().trim());
        prefInterface.setBin43Id(bin43Edittext.getText().toString().trim());

        prefInterface.setCart1Id(cart1Edittext.getText().toString().trim());
        prefInterface.setCart2Id(cart2Edittext.getText().toString().trim());
        prefInterface.setCart3Id(cart3Edittext.getText().toString().trim());

        finish();
    }
}
