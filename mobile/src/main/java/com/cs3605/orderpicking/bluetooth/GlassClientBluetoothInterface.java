package com.cs3605.orderpicking.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class GlassClientBluetoothInterface {

    private interface MessageConstants {
        int MESSAGE_READ = 0;
        int MESSAGE_WRITE = 1;
        int MESSAGE_TOAST = 2;
    }

    private static final String GLASS_MAC_ADDRESS = "f4:f5:e8:12:02:4d";

    private static final String GLASS_UUID = "00001101-0000-1000-8000-00805f9b34fb";

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice glassDevice;
//    private Handler bluetoothHandler;

    private ConnectThread connectThread;
    private ConnectedThread connectedThread;

    public GlassClientBluetoothInterface(Context context) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        bluetoothHandler = handler;

        getGlassDevice();
    }

    private void getGlassDevice() {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
            if (device.getAddress().toLowerCase().equals(GLASS_MAC_ADDRESS)) {
                glassDevice = device;
                break;
            }
        }
    }

    public void stop() {
        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }

        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }
    }

    public void connectToGlass() {
        connectThread = new ConnectThread();
        if (glassDevice == null) {
            return;
        }

        connectThread.run();
    }


    public void sendString(String string) {
        connectedThread.write(string.getBytes());
    }

    private void connect(BluetoothSocket socket) {
        connectedThread = new ConnectedThread(socket);
        connectedThread.start();
    }

    private class ConnectThread extends Thread {
        private final BluetoothSocket clientSocket;

        ConnectThread() {
            BluetoothSocket socket = null;

            try {
                socket = glassDevice.createRfcommSocketToServiceRecord(UUID.fromString(GLASS_UUID));
            } catch (IOException e) {
                Log.e(TAG, "Client Socket's create() method failed", e);
            }

            clientSocket = socket;
        }

        public void run() {
            bluetoothAdapter.cancelDiscovery();

            try {
                clientSocket.connect();
                Log.d(TAG, "Client socket connected");
            } catch (IOException connectException) {
                try {
                    clientSocket.close();
                    Log.e(TAG, "Client socket closed in run()", connectException);
                } catch (IOException closeException) {
                    Log.e(TAG, "Could not close the client socket", closeException);
                }
            }

            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
//            manageMyConnectedSocket(mmSocket); TODO
            connect(clientSocket);
        }

        // Closes the client socket and causes the thread to finish.
        void cancel() {
            try {
                clientSocket.close();
                Log.d(TAG, "Client socket closed");
            } catch (IOException e) {
                Log.e(TAG, "Could not close the client socket", e);
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket socket;
        private final InputStream connectedInputStream;
        private final OutputStream connectedOutputStream;
        private byte[] buffer; // mmBuffer store for the stream

        ConnectedThread(BluetoothSocket socket) {
            this.socket = socket;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                inputStream = socket.getInputStream();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when creating client input stream", e);
            }
            try {
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when creating client output stream", e);
            }

            connectedInputStream = inputStream;
            connectedOutputStream = outputStream;
        }

        public void run() {
            buffer = new byte[1024];
            int numBytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs.
            while (true) {
                try {
                    // Read from the InputStream.
                    numBytes = connectedInputStream.read(buffer);
                    // Send the obtained bytes to the UI activity.
//                    Message readMsg = bluetoothHandler.obtainMessage(
//                            MessageConstants.MESSAGE_READ, numBytes, -1, buffer);
//                    readMsg.sendToTarget();
                } catch (IOException e) {
                    Log.d(TAG, "Input stream was disconnected", e);
                    break;
                }
            }
        }

        public void write(byte[] bytes) {
            try {
                connectedOutputStream.write(bytes);

                // Share the sent message with the UI activity.
//                Message writtenMsg = bluetoothHandler.obtainMessage(
//                        MessageConstants.MESSAGE_WRITE, -1, -1, buffer);
//                writtenMsg.sendToTarget();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when sending data", e);

                // Send a failure message back to the activity.
//                Message writeErrorMsg =
//                        bluetoothHandler.obtainMessage(MessageConstants.MESSAGE_TOAST);
//                Bundle bundle = new Bundle();
//                bundle.putString("toast",
//                        "Couldn't send data to the other device");
//                writeErrorMsg.setData(bundle);
//                bluetoothHandler.sendMessage(writeErrorMsg);
            }
        }

        public void cancel() {
            try {
                socket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of client connect socket failed", e);
            }
        }
    }
}
