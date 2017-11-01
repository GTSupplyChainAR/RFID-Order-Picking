package com.cs4605.ryan_brooks.orderpicking.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs4605.ryan_brooks.orderpicking.R;

public class TrialView extends RelativeLayout {

    TextView rackIdTextView;
    LinearLayout bin11Layout;
    TextView bin11TextView;
    LinearLayout bin12Layout;
    TextView bin12TextView;
    LinearLayout bin13Layout;
    TextView bin13TextView;
    LinearLayout bin21Layout;
    TextView bin21TextView;
    LinearLayout bin22Layout;
    TextView bin22TextView;
    LinearLayout bin23Layout;
    TextView bin23TextView;
    LinearLayout bin31Layout;
    TextView bin31TextView;
    LinearLayout bin32Layout;
    TextView bin32TextView;
    LinearLayout bin33Layout;
    TextView bin33TextView;
    LinearLayout bin41Layout;
    TextView bin41TextView;
    LinearLayout bin42Layout;
    TextView bin42TextView;
    LinearLayout bin43Layout;
    TextView bin43TextView;
    LinearLayout cartBin1Layout;
    LinearLayout cartBin2Layout;
    LinearLayout cartBin3Layout;

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
        LayoutInflater.from(getContext()).inflate(R.layout.trial_view, this, true);

        bindViews();
    }

    private void bindViews() {
        rackIdTextView = (TextView) findViewById(R.id.rack_id_textview);

        bin11Layout = (LinearLayout) findViewById(R.id.bin_11);
        bin11TextView = (TextView) findViewById(R.id.bin_11_textview);
        bin12Layout = (LinearLayout) findViewById(R.id.bin_12);
        bin12TextView = (TextView) findViewById(R.id.bin_12_textview);
        bin13Layout = (LinearLayout) findViewById(R.id.bin_13);
        bin13TextView = (TextView) findViewById(R.id.bin_13_textview);
        bin21Layout = (LinearLayout) findViewById(R.id.bin_21);
        bin21TextView = (TextView) findViewById(R.id.bin_21_textview);
        bin22Layout = (LinearLayout) findViewById(R.id.bin_22);
        bin22TextView = (TextView) findViewById(R.id.bin_22_textview);
        bin23Layout = (LinearLayout) findViewById(R.id.bin_23);
        bin23TextView = (TextView) findViewById(R.id.bin_23_textview);
        bin31Layout = (LinearLayout) findViewById(R.id.bin_31);
        bin31TextView = (TextView) findViewById(R.id.bin_31_textview);
        bin32Layout = (LinearLayout) findViewById(R.id.bin_32);
        bin32TextView = (TextView) findViewById(R.id.bin_32_textview);
        bin33Layout = (LinearLayout) findViewById(R.id.bin_33);
        bin33TextView = (TextView) findViewById(R.id.bin_33_textview);
        bin41Layout = (LinearLayout) findViewById(R.id.bin_41);
        bin41TextView = (TextView) findViewById(R.id.bin_41_textview);
        bin42Layout = (LinearLayout) findViewById(R.id.bin_42);
        bin42TextView = (TextView) findViewById(R.id.bin_42_textview);
        bin43Layout = (LinearLayout) findViewById(R.id.bin_43);
        bin43TextView = (TextView) findViewById(R.id.bin_43_textview);

        cartBin1Layout = (LinearLayout) findViewById(R.id.cart_bin_1);
        cartBin2Layout = (LinearLayout) findViewById(R.id.cart_bin_2);
        cartBin3Layout = (LinearLayout) findViewById(R.id.cart_bin_3);

    }

    public void setupViews(byte[] bytes) {
        rackIdTextView.setText("TODO");

        setRedBackground(bin11Layout, bytes[0]);
        bin11TextView.setText(Byte.toString(bytes[0]));
        setRedBackground(bin12Layout, bytes[1]);
        bin12TextView.setText(Byte.toString(bytes[1]));
        setRedBackground(bin13Layout, bytes[2]);
        bin13TextView.setText(Byte.toString(bytes[2]));

        setYellowBackground(bin21Layout, bytes[3]);
        bin21TextView.setText(Byte.toString(bytes[3]));
        setYellowBackground(bin22Layout, bytes[4]);
        bin22TextView.setText(Byte.toString(bytes[4]));
        setYellowBackground(bin23Layout, bytes[5]);
        bin23TextView.setText(Byte.toString(bytes[5]));

        setGreenBackground(bin31Layout, bytes[6]);
        bin31TextView.setText(Byte.toString(bytes[6]));
        setGreenBackground(bin32Layout, bytes[7]);
        bin32TextView.setText(Byte.toString(bytes[7]));
        setGreenBackground(bin33Layout, bytes[8]);
        bin33TextView.setText(Byte.toString(bytes[8]));

        setBlueBackground(bin41Layout, bytes[9]);
        bin41TextView.setText(Byte.toString(bytes[9]));
        setBlueBackground(bin42Layout, bytes[10]);
        bin42TextView.setText(Byte.toString(bytes[10]));
        setBlueBackground(bin43Layout, bytes[11]);
        bin43TextView.setText(Byte.toString(bytes[11]));

        // TODO: Visibility of cart text
        setGrayBackground(cartBin1Layout, bytes[12] == 0);
        setGrayBackground(cartBin2Layout, bytes[12] == 1);
        setGrayBackground(cartBin3Layout, bytes[12] == 2);
    }

    private void setRedBackground(LinearLayout layout, byte quantity) {
        if (quantity == 0) {
            layout.setBackground(getResources().getDrawable(R.drawable.background_red_empty));
        } else {
            layout.setBackgroundColor(getResources().getColor(R.color.red));
        }
    }

    private void setYellowBackground(LinearLayout layout, byte quantity) {
        if (quantity == 0) {
            layout.setBackground(getResources().getDrawable(R.drawable.background_yellow_empty));
        } else {
            layout.setBackgroundColor(getResources().getColor(R.color.yellow));
        }
    }

    private void setGreenBackground(LinearLayout layout, byte quantity) {
        if (quantity == 0) {
            layout.setBackground(getResources().getDrawable(R.drawable.background_green_empty));
        } else {
            layout.setBackgroundColor(getResources().getColor(R.color.green));
        }
    }

    private void setBlueBackground(LinearLayout layout, byte quantity) {
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
}
