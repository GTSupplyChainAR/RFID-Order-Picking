package com.cs3605.orderpicking.bluetooth;

import android.content.Context;

import de.ubimax.xbandtest.xband.XBandConnectionHandler;
import de.ubimax.xbandtest.xband.XBandEventListener;

public class XbandInterface {

    private static final String XBAND_ADDRESS = "B#E076D0916795";

    private Context context;
    private XBandConnectionHandler xBandConnection = null;

    public XbandInterface(Context context) {
        this.context = context;

        if (XBandConnectionHandler.checkDeviceBLESupport()) {
            xBandConnection = new XBandConnectionHandler(context);
            xBandConnection.setActivateIMUService(true);
            xBandConnection.setLastConfigMessage(xBandConnection.createConfigurationProperty());
            xBandConnection.registerEventListener((XBandEventListener) context);
        }
    }

    public void connect() {
        if (xBandConnection == null) {
            return;
        }

        xBandConnection.connectDevice(XBAND_ADDRESS);
    }

    public void disconnect() {
        if (xBandConnection == null) {
            return;
        }

        xBandConnection.disconnectDevice(XBAND_ADDRESS);
    }
}
