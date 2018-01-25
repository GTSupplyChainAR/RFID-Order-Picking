package com.cs3605.orderpicking.bluetooth;

import android.content.Context;

import com.cs3605.orderpicking.newExperiment.ExperimentActivity;

import de.ubimax.xbandtest.xband.XBandConnectionHandler;
import de.ubimax.xbandtest.xband.XBandEventListener;
import de.ubimax.xbandtest.xband.XBandIMUData;

public class XbandInterface implements XBandEventListener {

    private static final String XBAND_1_ADDRESS = "B#E076D0916795";
    private static final String XBAND_2_ADDRESS = "B#E076D09162D7";

    private Context context;
    private XBandConnectionHandler xBandConnection = null;
    private XBandConnectionHandler xBandConnection2 = null;
    private static XbandInterface xbandInterface;

    public static XbandInterface getInstance(Context context) {
        if (xbandInterface == null) {
            xbandInterface = new XbandInterface(context);
        }
        xbandInterface.context = context;
        return xbandInterface;
    }

    public XbandInterface(Context context) {
        this.context = context;

        if (XBandConnectionHandler.checkDeviceBLESupport()) {
            xBandConnection = new XBandConnectionHandler(context);
            xBandConnection.setActivateIMUService(false);
            xBandConnection.setLastConfigMessage(xBandConnection.createConfigurationProperty());
            xBandConnection.registerEventListener(this);
            xBandConnection.setInitialReaderPower((byte) 3);
            xBandConnection.setTagTimeoutInSeconds("3");

            xBandConnection2 = new XBandConnectionHandler(context);
            xBandConnection2.setActivateIMUService(false);
            xBandConnection2.setLastConfigMessage(xBandConnection2.createConfigurationProperty());
            xBandConnection2.registerEventListener(this);
            xBandConnection2.setInitialReaderPower((byte) 3);
            xBandConnection2.setTagTimeoutInSeconds("3");
        }
    }

    public void connect() {
        if (xBandConnection == null) {
            return;
        }

        xBandConnection.connectDevice(XBAND_1_ADDRESS);

        if (xBandConnection2 == null) {
            return;
        }

        xBandConnection2.connectDevice(XBAND_2_ADDRESS);
    }

    public void disconnect() {
        if (xBandConnection == null) {
            return;
        }

        xBandConnection.disconnectDevice(XBAND_1_ADDRESS);

        if (xBandConnection2 == null) {
            return;
        }

        xBandConnection2.disconnectDevice(XBAND_2_ADDRESS);
    }

    @Override
    public void onNewRFIDScan(String s) {
        if (context instanceof ExperimentActivity) {
            ((XBandEventListener) context).onNewRFIDScan(s);
        }
    }

    @Override
    public void onNewScanRSSI(short i) {

    }

    @Override
    public void onNewBatteryStatus(double v) {

    }

    @Override
    public void onTagWriteResponse(String s) {

    }

    @Override
    public void onNewIMUEntry(XBandIMUData xBandIMUData) {

    }
}
