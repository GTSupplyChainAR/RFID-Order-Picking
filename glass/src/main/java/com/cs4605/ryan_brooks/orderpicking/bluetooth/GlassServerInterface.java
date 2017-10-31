package com.cs4605.ryan_brooks.orderpicking.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class GlassServerInterface {
    private interface MessageConstants {
        int MESSAGE_READ = 0;
        int MESSAGE_WRITE = 1;
        int MESSAGE_TOAST = 2;
    }

    private static final String PIXEL_XL_MAC_ADDRESS = "ac:37:43:4e:10:58";
    private static final String PIXEL_2_XL_MAC_ADDRESS = "10:f1:f2:ef:20:18";

    private static final String GLASS_UUID = "00001101-0000-1000-8000-00805f9b34fb";

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice pixelDevice;
    private Handler bluetoothHandler;

    private AcceptThread acceptThread;
    private ConnectedThread connectedThread;

    public GlassServerInterface(Handler handler) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothHandler = handler;

        getPixelDevice();
    }

    private void getPixelDevice() {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
            if (device.getAddress().toLowerCase().equals(PIXEL_2_XL_MAC_ADDRESS)) {
                pixelDevice = device;
                break;
            }
        }
    }

    public void stop() {
        if (acceptThread != null) {
            acceptThread.cancel();
            acceptThread = null;
        }

        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }

    }

    public void acceptConnection() {
        // TODO
        AcceptThread acceptThread = new AcceptThread();
        acceptThread.run();
    }

    private void connect(BluetoothSocket socket) {
        connectedThread = new ConnectedThread(socket);
        connectedThread.start();
    }

    private class AcceptThread extends Thread {
        private final BluetoothServerSocket serverSocket;

        AcceptThread() {
            BluetoothServerSocket socket = null;

            try {
                socket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(GLASS_UUID, UUID.fromString(GLASS_UUID));
                Log.d(TAG, "Server socket created successfully");
            } catch (IOException e) {
                Log.e(TAG, "Server Socket's listen() method failed", e);
                e.printStackTrace();
            }

            serverSocket = socket;
        }

        public void run() {
            BluetoothSocket socket = null;

            while (true) {
                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    Log.e(TAG, "Server Socket's accept() method failed", e);
                }

                if (socket != null) {
                    // TODO: Perform work with the socket in a separate thread
                    connect(socket);
                    Log.d(TAG, "Server socket connected");
                    return;
                }
            }
        }

        void cancel() {
            try {
                serverSocket.close();
                Log.d(TAG, "Server socket closed");
            } catch (IOException e) {
                Log.e(TAG, "Could not close server socket", e);
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
                    Message readMsg = bluetoothHandler.obtainMessage(
                            MessageConstants.MESSAGE_READ, numBytes, -1, buffer);
                    readMsg.sendToTarget();
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
                Message writtenMsg = bluetoothHandler.obtainMessage(
                        MessageConstants.MESSAGE_WRITE, -1, -1, buffer);
                writtenMsg.sendToTarget();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when sending data", e);

                // Send a failure message back to the activity.
                Message writeErrorMsg =
                        bluetoothHandler.obtainMessage(MessageConstants.MESSAGE_TOAST);
                Bundle bundle = new Bundle();
                bundle.putString("toast",
                        "Couldn't send data to the other device");
                writeErrorMsg.setData(bundle);
                bluetoothHandler.sendMessage(writeErrorMsg);
            }
        }

        // Call this method from the main activity to shut down the connection.
        public void cancel() {
            try {
                socket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the server connect socket", e);
            }
        }
    }
}
