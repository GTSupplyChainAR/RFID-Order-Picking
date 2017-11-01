package com.cs3605.orderpicking.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs3605.orderpicking.R;
import com.cs3605.orderpicking.data.Trial;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrialView extends RelativeLayout implements View.OnClickListener, View.OnLongClickListener {

    @BindView(R.id.rack_id_textview)
    TextView rackIdTextView;
    @BindView(R.id.bin_11)
    LinearLayout bin11Layout;
    @BindView(R.id.bin_11_textview)
    TextView bin11TextView;
    @BindView(R.id.bin_12)
    LinearLayout bin12Layout;
    @BindView(R.id.bin_12_textview)
    TextView bin12TextView;
    @BindView(R.id.bin_13)
    LinearLayout bin13Layout;
    @BindView(R.id.bin_13_textview)
    TextView bin13TextView;
    @BindView(R.id.bin_21)
    LinearLayout bin21Layout;
    @BindView(R.id.bin_21_textview)
    TextView bin21TextView;
    @BindView(R.id.bin_22)
    LinearLayout bin22Layout;
    @BindView(R.id.bin_22_textview)
    TextView bin22TextView;
    @BindView(R.id.bin_23)
    LinearLayout bin23Layout;
    @BindView(R.id.bin_23_textview)
    TextView bin23TextView;
    @BindView(R.id.bin_31)
    LinearLayout bin31Layout;
    @BindView(R.id.bin_31_textview)
    TextView bin31TextView;
    @BindView(R.id.bin_32)
    LinearLayout bin32Layout;
    @BindView(R.id.bin_32_textview)
    TextView bin32TextView;
    @BindView(R.id.bin_33)
    LinearLayout bin33Layout;
    @BindView(R.id.bin_33_textview)
    TextView bin33TextView;
    @BindView(R.id.bin_41)
    LinearLayout bin41Layout;
    @BindView(R.id.bin_41_textview)
    TextView bin41TextView;
    @BindView(R.id.bin_42)
    LinearLayout bin42Layout;
    @BindView(R.id.bin_42_textview)
    TextView bin42TextView;
    @BindView(R.id.bin_43)
    LinearLayout bin43Layout;
    @BindView(R.id.bin_43_textview)
    TextView bin43TextView;
    @BindView(R.id.cart_bin_1)
    LinearLayout cartBin1Layout;
    @BindView(R.id.cart_bin_2)
    LinearLayout cartBin2Layout;
    @BindView(R.id.cart_bin_3)
    LinearLayout cartBin3Layout;

    private Trial trial;

    public TrialView(Context context) {
        super(context);
        init();
    }

    public TrialView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TrialView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_trial, this, true);
        ButterKnife.bind(this);

        setupClickListeners();
        setupInitialTrial();
    }

    private void setupClickListeners() {
        bin11Layout.setOnClickListener(this);
        bin11Layout.setOnLongClickListener(this);
        bin12Layout.setOnClickListener(this);
        bin12Layout.setOnLongClickListener(this);
        bin13Layout.setOnClickListener(this);
        bin13Layout.setOnLongClickListener(this);
        bin21Layout.setOnClickListener(this);
        bin21Layout.setOnLongClickListener(this);
        bin22Layout.setOnClickListener(this);
        bin22Layout.setOnLongClickListener(this);
        bin23Layout.setOnClickListener(this);
        bin23Layout.setOnLongClickListener(this);
        bin31Layout.setOnClickListener(this);
        bin31Layout.setOnLongClickListener(this);
        bin32Layout.setOnClickListener(this);
        bin32Layout.setOnLongClickListener(this);
        bin33Layout.setOnClickListener(this);
        bin33Layout.setOnLongClickListener(this);
        bin41Layout.setOnClickListener(this);
        bin41Layout.setOnLongClickListener(this);
        bin42Layout.setOnClickListener(this);
        bin42Layout.setOnLongClickListener(this);
        bin43Layout.setOnClickListener(this);
        bin43Layout.setOnLongClickListener(this);

        cartBin1Layout.setOnClickListener(this);
        cartBin2Layout.setOnClickListener(this);
        cartBin3Layout.setOnClickListener(this);
    }

    private void setupInitialTrial() {
        trial = new Trial();
        trial.setRackId("");
        trial.setR1Quantity(0);
        trial.setR2Quantity(0);
        trial.setR3Quantity(0);
        trial.setY1Quantity(0);
        trial.setY2Quantity(0);
        trial.setY3Quantity(0);
        trial.setG1Quantity(0);
        trial.setG2Quantity(0);
        trial.setG3Quantity(0);
        trial.setB1Quantity(0);
        trial.setB2Quantity(0);
        trial.setB3Quantity(0);
        trial.setCartPos(0);
        setupViews();
    }

    private void setupViews() {
        rackIdTextView.setText(trial.getRackId().isEmpty() ? "Tap to change rack name." : trial.getRackId());

        setRedBackground(bin11Layout, trial.getR1Quantity());
        bin11TextView.setText(Integer.toString(trial.getR1Quantity()));
        setRedBackground(bin12Layout, trial.getR2Quantity());
        bin12TextView.setText(Integer.toString(trial.getR2Quantity()));
        setRedBackground(bin13Layout, trial.getR3Quantity());
        bin13TextView.setText(Integer.toString(trial.getR3Quantity()));

        setYellowBackground(bin21Layout, trial.getY1Quantity());
        bin21TextView.setText(Integer.toString(trial.getY1Quantity()));
        setYellowBackground(bin22Layout, trial.getY2Quantity());
        bin22TextView.setText(Integer.toString(trial.getY2Quantity()));
        setYellowBackground(bin23Layout, trial.getY3Quantity());
        bin23TextView.setText(Integer.toString(trial.getY3Quantity()));

        setGreenBackground(bin31Layout, trial.getG1Quantity());
        bin31TextView.setText(Integer.toString(trial.getG1Quantity()));
        setGreenBackground(bin32Layout, trial.getG2Quantity());
        bin32TextView.setText(Integer.toString(trial.getG2Quantity()));
        setGreenBackground(bin33Layout, trial.getG3Quantity());
        bin33TextView.setText(Integer.toString(trial.getG3Quantity()));

        setBlueBackground(bin41Layout, trial.getB1Quantity());
        bin41TextView.setText(Integer.toString(trial.getB1Quantity()));
        setBlueBackground(bin42Layout, trial.getB2Quantity());
        bin42TextView.setText(Integer.toString(trial.getB2Quantity()));
        setBlueBackground(bin43Layout, trial.getB3Quantity());
        bin43TextView.setText(Integer.toString(trial.getB3Quantity()));

        setGrayBackground(cartBin1Layout, trial.getCartPos() == 0);
        setGrayBackground(cartBin2Layout, trial.getCartPos() == 1);
        setGrayBackground(cartBin3Layout, trial.getCartPos() == 2);
    }

    public void setTrial(Trial trial) {
        this.trial = trial;
        setupViews();
    }

    public Trial getTrial() {
        return trial;
    }

    private void setRedBackground(LinearLayout layout, int quantity) {
        if (quantity == 0) {
            layout.setBackground(getResources().getDrawable(R.drawable.background_red_empty));
        } else {
            layout.setBackgroundColor(getResources().getColor(R.color.red));
        }
    }

    private void setYellowBackground(LinearLayout layout, int quantity) {
        if (quantity == 0) {
            layout.setBackground(getResources().getDrawable(R.drawable.background_yellow_empty));
        } else {
            layout.setBackgroundColor(getResources().getColor(R.color.yellow));
        }
    }

    private void setGreenBackground(LinearLayout layout, int quantity) {
        if (quantity == 0) {
            layout.setBackground(getResources().getDrawable(R.drawable.background_green_empty));
        } else {
            layout.setBackgroundColor(getResources().getColor(R.color.green));
        }
    }

    private void setBlueBackground(LinearLayout layout, int quantity) {
        if (quantity == 0) {
            layout.setBackground(getResources().getDrawable(R.drawable.background_blue_empty));
        } else {
            layout.setBackgroundColor(getResources().getColor(R.color.blue));
        }
    }

    private void setGrayBackground(LinearLayout layout, boolean isSelected) {
        if (!isSelected) {
            layout.setBackground(getResources().getDrawable(R.drawable.background_gray_empty));
        } else {
            layout.setBackgroundColor(getResources().getColor(R.color.gray));
        }
    }

    @Override
    public void onClick(View v) {
        if (isEnabled()) {
            switch (v.getId()) {
                case R.id.rack_id_textview:
                    // TODO: Launch dialog to change name
                    break;
                case R.id.bin_11:
                    trial.setR1Quantity(trial.getR1Quantity() + 1);
                    break;
                case R.id.bin_12:
                    trial.setR2Quantity(trial.getR2Quantity() + 1);
                    break;
                case R.id.bin_13:
                    trial.setR3Quantity(trial.getR3Quantity() + 1);
                    break;
                case R.id.bin_21:
                    trial.setY1Quantity(trial.getY1Quantity() + 1);
                    break;
                case R.id.bin_22:
                    trial.setY2Quantity(trial.getY2Quantity() + 1);
                    break;
                case R.id.bin_23:
                    trial.setY3Quantity(trial.getY3Quantity() + 1);
                    break;
                case R.id.bin_31:
                    trial.setG1Quantity(trial.getG1Quantity() + 1);
                    break;
                case R.id.bin_32:
                    trial.setG2Quantity(trial.getG2Quantity() + 1);
                    break;
                case R.id.bin_33:
                    trial.setG3Quantity(trial.getG3Quantity() + 1);
                    break;
                case R.id.bin_41:
                    trial.setB1Quantity(trial.getB1Quantity() + 1);
                    break;
                case R.id.bin_42:
                    trial.setB2Quantity(trial.getB2Quantity() + 1);
                    break;
                case R.id.bin_43:
                    trial.setB3Quantity(trial.getB3Quantity() + 1);
                    break;
                case R.id.cart_bin_1:
                    trial.setCartPos(0);
                    break;
                case R.id.cart_bin_2:
                    trial.setCartPos(1);
                    break;
                case R.id.cart_bin_3:
                    trial.setCartPos(2);
                    break;
            }
        }
        setupViews();
    }

    @Override
    public boolean onLongClick(View v) {
        if (isEnabled()) {
            switch (v.getId()) {
                case R.id.bin_11:
                    trial.setR1Quantity(0);
                    break;
                case R.id.bin_12:
                    trial.setR2Quantity(0);
                    break;
                case R.id.bin_13:
                    trial.setR3Quantity(0);
                    break;
                case R.id.bin_21:
                    trial.setY1Quantity(0);
                    break;
                case R.id.bin_22:
                    trial.setY2Quantity(0);
                    break;
                case R.id.bin_23:
                    trial.setY3Quantity(0);
                    break;
                case R.id.bin_31:
                    trial.setG1Quantity(0);
                    break;
                case R.id.bin_32:
                    trial.setG2Quantity(0);
                    break;
                case R.id.bin_33:
                    trial.setG3Quantity(0);
                    break;
                case R.id.bin_41:
                    trial.setB1Quantity(0);
                    break;
                case R.id.bin_42:
                    trial.setB2Quantity(0);
                    break;
                case R.id.bin_43:
                    trial.setB3Quantity(0);
                    break;
            }
        }
        setupViews();
        return true;
    }
}
