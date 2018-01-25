package com.cs3605.orderpicking.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.cs3605.orderpicking.util.PreferenceInterface.BIN_ID_FLAG.BIN11;
import static com.cs3605.orderpicking.util.PreferenceInterface.BIN_ID_FLAG.BIN12;
import static com.cs3605.orderpicking.util.PreferenceInterface.BIN_ID_FLAG.BIN13;
import static com.cs3605.orderpicking.util.PreferenceInterface.BIN_ID_FLAG.BIN21;
import static com.cs3605.orderpicking.util.PreferenceInterface.BIN_ID_FLAG.BIN22;
import static com.cs3605.orderpicking.util.PreferenceInterface.BIN_ID_FLAG.BIN23;
import static com.cs3605.orderpicking.util.PreferenceInterface.BIN_ID_FLAG.BIN31;
import static com.cs3605.orderpicking.util.PreferenceInterface.BIN_ID_FLAG.BIN32;
import static com.cs3605.orderpicking.util.PreferenceInterface.BIN_ID_FLAG.BIN33;
import static com.cs3605.orderpicking.util.PreferenceInterface.BIN_ID_FLAG.BIN41;
import static com.cs3605.orderpicking.util.PreferenceInterface.BIN_ID_FLAG.BIN42;
import static com.cs3605.orderpicking.util.PreferenceInterface.BIN_ID_FLAG.BIN43;
import static com.cs3605.orderpicking.util.PreferenceInterface.BIN_ID_FLAG.CART1;
import static com.cs3605.orderpicking.util.PreferenceInterface.BIN_ID_FLAG.CART2;
import static com.cs3605.orderpicking.util.PreferenceInterface.BIN_ID_FLAG.CART3;

public class PreferenceInterface {

    public enum BIN_ID_FLAG {
        BIN11,
        BIN12,
        BIN13,
        BIN21,
        BIN22,
        BIN23,
        BIN31,
        BIN32,
        BIN33,
        BIN41,
        BIN42,
        BIN43,
        CART1,
        CART2,
        CART3
    }

    private static final String BIN_11_ID_KEY = "bin_11_id";
    private static final String BIN_12_ID_KEY = "bin_12_id";
    private static final String BIN_13_ID_KEY = "bin_13_id";
    private static final String BIN_21_ID_KEY = "bin_21_id";
    private static final String BIN_22_ID_KEY = "bin_22_id";
    private static final String BIN_23_ID_KEY = "bin_23_id";
    private static final String BIN_31_ID_KEY = "bin_31_id";
    private static final String BIN_32_ID_KEY = "bin_32_id";
    private static final String BIN_33_ID_KEY = "bin_33_id";
    private static final String BIN_41_ID_KEY = "bin_41_id";
    private static final String BIN_42_ID_KEY = "bin_42_id";
    private static final String BIN_43_ID_KEY = "bin_43_id";
    private static final String CART_1_ID_KEY = "cart_1_id";
    private static final String CART_2_ID_KEY = "cart_2_id";
    private static final String CART_3_ID_KEY = "cart_3_id";

    private SharedPreferences preferences;

    public PreferenceInterface(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public BIN_ID_FLAG checkBinId(String binId) {
        if (binId.equals(getBin11Id())) {
            return BIN11;
        } else if (binId.equals(getBin12Id())) {
            return BIN12;
        } else if (binId.equals(getBin13Id())) {
            return BIN13;
        } else if (binId.equals(getBin21Id())) {
            return BIN21;
        } else if (binId.equals(getBin22Id())) {
            return BIN22;
        } else if (binId.equals(getBin23Id())) {
            return BIN23;
        } else if (binId.equals(getBin31Id())) {
            return BIN31;
        } else if (binId.equals(getBin32Id())) {
            return BIN32;
        } else if (binId.equals(getBin33Id())) {
            return BIN33;
        } else if (binId.equals(getBin41Id())) {
            return BIN41;
        } else if (binId.equals(getBin42Id())) {
            return BIN42;
        } else if (binId.equals(getBin43Id())) {
            return BIN43;
        } else if (binId.equals(getCart1Id())) {
            return CART1;
        } else if (binId.equals(getCart2Id())) {
            return CART2;
        } else if (binId.equals(getCart3Id())) {
            return CART3;
        }
        return null;
    }

    public String getBin11Id() {
        return preferences.getString(BIN_11_ID_KEY, "");
    }

    public void setBin11Id(String binId) {
        preferences.edit().putString(BIN_11_ID_KEY, binId).apply();
    }

    public String getBin12Id() {
        return preferences.getString(BIN_12_ID_KEY, "");
    }

    public void setBin12Id(String binId) {
        preferences.edit().putString(BIN_12_ID_KEY, binId).apply();
    }

    public String getBin13Id() {
        return preferences.getString(BIN_13_ID_KEY, "");
    }

    public void setBin13Id(String binId) {
        preferences.edit().putString(BIN_13_ID_KEY, binId).apply();
    }

    public String getBin21Id() {
        return preferences.getString(BIN_21_ID_KEY, "");
    }

    public void setBin21Id(String binId) {
        preferences.edit().putString(BIN_21_ID_KEY, binId).apply();
    }

    public String getBin22Id() {
        return preferences.getString(BIN_22_ID_KEY, "");
    }

    public void setBin22Id(String binId) {
        preferences.edit().putString(BIN_22_ID_KEY, binId).apply();
    }

    public String getBin23Id() {
        return preferences.getString(BIN_23_ID_KEY, "");
    }

    public void setBin23Id(String binId) {
        preferences.edit().putString(BIN_23_ID_KEY, binId).apply();
    }

    public String getBin31Id() {
        return preferences.getString(BIN_31_ID_KEY, "");
    }

    public void setBin31Id(String binId) {
        preferences.edit().putString(BIN_31_ID_KEY, binId).apply();
    }

    public String getBin32Id() {
        return preferences.getString(BIN_32_ID_KEY, "");
    }

    public void setBin32Id(String binId) {
        preferences.edit().putString(BIN_32_ID_KEY, binId).apply();
    }

    public String getBin33Id() {
        return preferences.getString(BIN_33_ID_KEY, "");
    }

    public void setBin33Id(String binId) {
        preferences.edit().putString(BIN_33_ID_KEY, binId).apply();
    }

    public String getBin41Id() {
        return preferences.getString(BIN_41_ID_KEY, "");
    }

    public void setBin41Id(String binId) {
        preferences.edit().putString(BIN_41_ID_KEY, binId).apply();
    }

    public String getBin42Id() {
        return preferences.getString(BIN_42_ID_KEY, "");
    }

    public void setBin42Id(String binId) {
        preferences.edit().putString(BIN_42_ID_KEY, binId).apply();
    }

    public String getBin43Id() {
        return preferences.getString(BIN_43_ID_KEY, "");
    }

    public void setBin43Id(String binId) {
        preferences.edit().putString(BIN_43_ID_KEY, binId).apply();
    }

    public String getCart1Id() {
        return preferences.getString(CART_1_ID_KEY, "");
    }

    public void setCart1Id(String binId) {
        preferences.edit().putString(CART_1_ID_KEY, binId).apply();
    }

    public String getCart2Id() {
        return preferences.getString(CART_2_ID_KEY, "");
    }

    public void setCart2Id(String binId) {
        preferences.edit().putString(CART_2_ID_KEY, binId).apply();
    }

    public String getCart3Id() {
        return preferences.getString(CART_3_ID_KEY, "");
    }

    public void setCart3Id(String binId) {
        preferences.edit().putString(CART_3_ID_KEY, binId).apply();
    }
}
