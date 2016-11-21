/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.ryan.finalproject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryan.finalproject.DeviceListFragment.DeviceActionListener;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A fragment that manages a particular peer and allows interaction with device
 * i.e. setting up network connection and transferring data.
 */
public class DeviceDetailFragment extends Fragment implements ConnectionInfoListener {

    private static String TAG = "RPSTAG";
    public static final String IP_SERVER = "192.168.49.1";
    protected static final int CHOOSE_FILE_RESULT_CODE = 20;
    protected static final int NET_RESULT_RESULT_CODE = 40;
    private View mContentView = null;
    private WifiP2pDevice device;
    private WifiP2pInfo info;
    ProgressDialog progressDialog = null;
    private static String p1move = "";
    private static int p1wins = 0,p2wins = 0,draws = 0;
    private static boolean netRunning = false;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContentView = inflater.inflate(R.layout.device_detail, null);
        mContentView.findViewById(R.id.btn_connect).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = device.deviceAddress;
                config.wps.setup = WpsInfo.PBC;
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                progressDialog = ProgressDialog.show(getActivity(), "Press back to cancel",
                        "Connecting to :" + device.deviceAddress, true, true
//                        new DialogInterface.OnCancelListener() {
//
//                            @Override
//                            public void onCancel(DialogInterface dialog) {
//                                ((DeviceActionListener) getActivity()).cancelDisconnect();
//                            }
//                        }
                        );
                ((DeviceActionListener) getActivity()).connect(config);

            }
        });

        mContentView.findViewById(R.id.btn_disconnect).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        ((DeviceActionListener) getActivity()).disconnect();
                    }
                });

        mContentView.findViewById(R.id.btn_start_client).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // Allow user to pick an image from Gallery or other
                        // registered apps
                        //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        //intent.setType("image/*");
                        //startActivityForResult(intent, CHOOSE_FILE_RESULT_CODE);

                        String message = "start playing";
                        TextView statusText = (TextView) mContentView.findViewById(R.id.status_text);
                        statusText.setText("Sending: " + message);
                        Log.i(com.example.ryan.finalproject.WiFiDirectActivity.TAG, "Intent----------- " + message);
                        Intent serviceIntent = new Intent(getActivity(), com.example.ryan.finalproject.FileTransferService.class);
                        serviceIntent.setAction(com.example.ryan.finalproject.FileTransferService.ACTION_SEND_MOVE);
                        serviceIntent.putExtra(com.example.ryan.finalproject.FileTransferService.SEND_MESSAGE, message);
                        serviceIntent.putExtra(com.example.ryan.finalproject.FileTransferService.EXTRAS_ADDRESS, info.groupOwnerAddress.getHostAddress());
                        serviceIntent.putExtra(com.example.ryan.finalproject.FileTransferService.EXTRAS_PORT, 8988);
                        getActivity().startService(serviceIntent);

                        Intent intent = new Intent(getActivity().getApplicationContext(), com.example.ryan.finalproject.networkPlayer.class);
                        Log.i(com.example.ryan.finalproject.WiFiDirectActivity.TAG, "Starting network player from on click listener");
                        Log.i(com.example.ryan.finalproject.WiFiDirectActivity.TAG, "Starting async task for non group owner");
                        new FileServerAsyncTask(getActivity().getApplicationContext(), mContentView.findViewById(R.id.status_text), false).execute();
                        startActivityForResult(intent, CHOOSE_FILE_RESULT_CODE);
                    }
                });

        return mContentView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == CHOOSE_FILE_RESULT_CODE) {
            String localIP = com.example.ryan.finalproject.Utils.getLocalIPAddress();
            Log.i(com.example.ryan.finalproject.WiFiDirectActivity.TAG, "localIP: " + localIP);

            // User has picked an image. Transfer it to group owner i.e peer using
            // FileTransferService.
            p1move = data.getStringExtra("p1move");
            Toast.makeText(getActivity().getApplicationContext(), "You picked " + p1move, Toast.LENGTH_SHORT).show();
            TextView statusText = (TextView) mContentView.findViewById(R.id.status_text);
            statusText.setText("Sending: " + p1move);
            Log.i(com.example.ryan.finalproject.WiFiDirectActivity.TAG, "Intent----------- " + p1move);
            Intent serviceIntent = new Intent(getActivity(), com.example.ryan.finalproject.FileTransferService.class);
            serviceIntent.setAction(com.example.ryan.finalproject.FileTransferService.ACTION_SEND_MOVE);
            serviceIntent.putExtra(com.example.ryan.finalproject.FileTransferService.SEND_MESSAGE, p1move);

            if (localIP != null && localIP.equals(IP_SERVER)) {
                String client_mac_fixed = com.example.ryan.finalproject.Utils.fixMac(new String(device.deviceAddress));
                String clientIP = com.example.ryan.finalproject.Utils.getIPFromMac(client_mac_fixed);
                Log.i(com.example.ryan.finalproject.WiFiDirectActivity.TAG, "clientIP: " + clientIP);
                Log.i(com.example.ryan.finalproject.WiFiDirectActivity.TAG, "clientMac: " + client_mac_fixed);
                serviceIntent.putExtra(com.example.ryan.finalproject.FileTransferService.EXTRAS_ADDRESS, clientIP);
            } else {
                serviceIntent.putExtra(com.example.ryan.finalproject.FileTransferService.EXTRAS_ADDRESS, IP_SERVER);
            }
            //serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_ADDRESS,
            //        info.groupOwnerAddress.getHostAddress());
            serviceIntent.putExtra(com.example.ryan.finalproject.FileTransferService.EXTRAS_PORT, 8988);
            getActivity().startService(serviceIntent);
            /*if (localIP == null && !netRunning) {
                Log.i(WiFiDirectActivity.TAG, "Starting async task for non group owner");
                new FileServerAsyncTask(getActivity().getApplicationContext(), mContentView.findViewById(R.id.status_text), false).execute();
            } else if(!netRunning){
                Log.i(WiFiDirectActivity.TAG, "Starting async task for group owner");
                new FileServerAsyncTask(getActivity().getApplicationContext(), mContentView.findViewById(R.id.status_text),true).execute();
            }*/
        }
    }

    @Override
    public void onConnectionInfoAvailable(final WifiP2pInfo info) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        this.info = info;
        this.getView().setVisibility(View.VISIBLE);

        // The owner IP is now known.
        TextView view = (TextView) mContentView.findViewById(R.id.group_owner);
        view.setText(getResources().getString(R.string.group_owner_text)
                + ((info.isGroupOwner == true) ? getResources().getString(R.string.yes)
                        : getResources().getString(R.string.no)));

        // InetAddress from WifiP2pInfo struct.
        view = (TextView) mContentView.findViewById(R.id.device_info);
        view.setText("Group Owner IP - " + info.groupOwnerAddress.getHostAddress());

        // After the group negotiation, we assign the group owner as the file
        // server. The file server is single threaded, single connection server
        // socket.
        if (info.groupFormed && info.isGroupOwner) {
            if(!netRunning) {
                Log.i(com.example.ryan.finalproject.WiFiDirectActivity.TAG, "Starting async task for group owner");
                new FileServerAsyncTask(getActivity().getApplicationContext(), mContentView.findViewById(R.id.status_text), true)
                        .execute();
            }
        } else if (info.groupFormed) {
            // The other device acts as the client. In this case, we enable the
            // get file button.
            mContentView.findViewById(R.id.btn_start_client).setVisibility(View.VISIBLE);
            ((TextView) mContentView.findViewById(R.id.status_text)).setText(getResources()
                    .getString(R.string.client_text));
        }

        // hide the connect button
        mContentView.findViewById(R.id.btn_connect).setVisibility(View.GONE);
    }

    /**
     * Updates the UI with device data
     * 
     * @param device the device to be displayed
     */
    public void showDetails(WifiP2pDevice device) {
        this.device = device;
        this.getView().setVisibility(View.VISIBLE);
        TextView view = (TextView) mContentView.findViewById(R.id.device_address);
        view.setText(device.deviceAddress);
        view = (TextView) mContentView.findViewById(R.id.device_info);
        view.setText(device.toString());

    }

    /**
     * Clears the UI fields after a disconnect or direct mode disable operation.
     */
    public void resetViews() {
        mContentView.findViewById(R.id.btn_connect).setVisibility(View.VISIBLE);
        TextView view = (TextView) mContentView.findViewById(R.id.device_address);
        view.setText(R.string.empty);
        view = (TextView) mContentView.findViewById(R.id.device_info);
        view.setText(R.string.empty);
        view = (TextView) mContentView.findViewById(R.id.group_owner);
        view.setText(R.string.empty);
        view = (TextView) mContentView.findViewById(R.id.status_text);
        view.setText(R.string.empty);
        mContentView.findViewById(R.id.btn_start_client).setVisibility(View.GONE);
        this.getView().setVisibility(View.GONE);
    }

    /**
     * A simple server socket that accepts connection and writes some data on
     * the stream.
     */
    public class FileServerAsyncTask extends AsyncTask<Void, Void, String> {

        private Context context;
        private TextView statusText;
        private boolean isGroupOwner;
        /**
         * @param context
         * @param statusText
         */
        public FileServerAsyncTask(Context context, View statusText, boolean isGroupOwner) {
            this.context = context;
            this.statusText = (TextView) statusText;
            this.isGroupOwner = isGroupOwner;
        }

       @Override
        protected String doInBackground(Void... params) {
            netRunning = true;
            ServerSocket serverSocket = null;
            Socket client = null;
            DataInputStream inputstream = null;
            try {
                    serverSocket = new ServerSocket(8988);
                    client = serverSocket.accept();
                    inputstream = new DataInputStream(client.getInputStream());
                    String str = inputstream.readUTF();
                    serverSocket.close();
                    while(p1move.isEmpty() && !str.equals("start playing")){
                        //Log.i(WiFiDirectActivity.TAG,"Waiting for p1move");
                    }
                    return str;
            } catch (IOException e) {
                    Log.e(com.example.ryan.finalproject.WiFiDirectActivity.TAG, e.getMessage());
                    return null;
            }finally{
                    if(inputstream != null){
                         try{
                                inputstream.close();
                         } catch (IOException e) {
                                Log.e(com.example.ryan.finalproject.WiFiDirectActivity.TAG, e.getMessage());
                         }
                    }
                    if(client != null){
                         try{
                                client.close();
                         } catch (IOException e) {
                                Log.e(com.example.ryan.finalproject.WiFiDirectActivity.TAG, e.getMessage());
                         }
                    }
                     if(serverSocket != null){
                         try{
                                serverSocket.close();
                         } catch (IOException e) {
                                Log.e(com.example.ryan.finalproject.WiFiDirectActivity.TAG, e.getMessage());
                         }
                    }
            }
        }
        @Override
        protected void onPostExecute(final String result) {
            Toast.makeText(getActivity().getApplicationContext(),result,Toast.LENGTH_SHORT).show();
            netRunning = false;
            if (result != null) {
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                Log.i(com.example.ryan.finalproject.WiFiDirectActivity.TAG, "Entered on post execute with a result " + result);
                if (result.equals("start playing")) {
                    Intent intent = new Intent();
                    intent.setClass(context, com.example.ryan.finalproject.networkPlayer.class);
                    new FileServerAsyncTask(context,statusText,isGroupOwner).execute();
                    startActivityForResult(intent, CHOOSE_FILE_RESULT_CODE);
                } else if (isGroupOwner) {
                    /*Intent intent = new Intent();
                    intent.setClass(context, networkResult.class);
                    intent.putExtra("p1Move", p1move);
                    intent.putExtra("p2Move", result);
                    context.startActivity(intent);*/

                    // handler to wait for p1move before starting networkResults activity
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (p1move.isEmpty()) {
                                Log.i(com.example.ryan.finalproject.WiFiDirectActivity.TAG, "Waiting for p1");
                                handler.postDelayed(this, 1000);
                            } else {
                                Log.i(com.example.ryan.finalproject.WiFiDirectActivity.TAG, "Have a move for both players. p1: " + p1move + " p2: " + result);
                                Intent intent = new Intent();
                                intent.setClass(context, com.example.ryan.finalproject.networkResult.class);
                                intent.putExtra("p1Move", p1move);
                                intent.putExtra("p2Move", result);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                p1move = "";
                                context.startActivity(intent);
                            }
                        }
                    }, 1000);
                } else {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (p1move.isEmpty()) {
                                Log.i(com.example.ryan.finalproject.WiFiDirectActivity.TAG, "Waiting for p1");
                                handler.postDelayed(this, 1000);
                            } else {
                                Log.i(com.example.ryan.finalproject.WiFiDirectActivity.TAG, "P1: " + p1move + " P2: " + result);
                                Intent intent = new Intent();
                                intent.setClass(context, com.example.ryan.finalproject.networkResult.class);
                                intent.putExtra("p1Move", p1move);
                                intent.putExtra("p2Move", result);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                p1move = "";
                                context.startActivity(intent);
                            }

                        }
                    }, 1000);
                }
                statusText.setText("Closing the server socket");
            }
        }

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            statusText.setText("Opening a server socket");
        }

    }

    public static boolean copyFile(InputStream inputStream, OutputStream out) {
        byte buf[] = new byte[1024];
        int len;
        long startTime=System.currentTimeMillis();
        
        try {
            while ((len = inputStream.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            out.close();
            inputStream.close();
            long endTime=System.currentTimeMillis()-startTime;
            Log.v("","Time taken to transfer all bytes is : "+endTime);
            
        } catch (IOException e) {
            Log.d(com.example.ryan.finalproject.WiFiDirectActivity.TAG, e.toString());
            return false;
        }
        return true;
    }

}
