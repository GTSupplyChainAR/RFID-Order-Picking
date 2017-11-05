package com.cs3605.orderpicking.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import com.cs3605.orderpicking.data.Trial;

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
        if (glassDevice == null) {
            return;
        }

        connectThread = new ConnectThread();
        connectThread.run();
    }


    public void sendString(String string) {
        connectedThread.write(string.getBytes());
    }

    public void sendTrial(Trial trial) {
        byte[] bytes = new byte[1024];
        byte r1Quantity = (byte) (0xff & trial.getR1Quantity());
        byte r2Quantity = (byte) (0xff & trial.getR2Quantity());
        byte r3Quantity = (byte) (0xff & trial.getR3Quantity());
        byte y1Quantity = (byte) (0xff & trial.getY1Quantity());
        byte y2Quantity = (byte) (0xff & trial.getY2Quantity());
        byte y3Quantity = (byte) (0xff & trial.getY3Quantity());
        byte g1Quantity = (byte) (0xff & trial.getG1Quantity());
        byte g2Quantity = (byte) (0xff & trial.getG2Quantity());
        byte g3Quantity = (byte) (0xff & trial.getG3Quantity());
        byte b1Quantity = (byte) (0xff & trial.getB1Quantity());
        byte b2Quantity = (byte) (0xff & trial.getB2Quantity());
        byte b3Quantity = (byte) (0xff & trial.getB3Quantity());
        byte cartPos = (byte) (0xff & trial.getCartPos());

        bytes[0] = r1Quantity;
        bytes[1] = r2Quantity;
        bytes[2] = r3Quantity;
        bytes[3] = y1Quantity;
        bytes[4] = y2Quantity;
        bytes[5] = y3Quantity;
        bytes[6] = g1Quantity;
        bytes[7] = g2Quantity;
        bytes[8] = g3Quantity;
        bytes[9] = b1Quantity;
        bytes[10] = b2Quantity;
        bytes[11] = b3Quantity;
        bytes[12] = cartPos;

        connectedThread.write(bytes);
    }

    private void connect(BluetoothSocket socket) {
        connectedThread = new ConnectedThread(socket);
        connectedThread.start();
    }

    // TODO: Progressbar
    private class ConnectThread extends Thread {
        private BluetoothSocket clientSocket;

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

                    clientSocket = (BluetoothSocket) glassDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(glassDevice, 1);
                    clientSocket.connect();
                    Log.e(TAG, "Client socket closed in run()", connectException);
                } catch (Exception e) {
                    Log.e(TAG, "Could not close the client socket", e);
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
                    // TODO Handle this
                    Log.d(TAG, "Input stream was disconnected", e);
                    cancel();
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
                connectedInputStream.close();
                connectedOutputStream.close();
                socket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of client connect socket failed", e);
            }
        }
    }
}
