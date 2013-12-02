package com.adk.touchevent;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class SendTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        try {
            Socket s = new Socket("107.102.183.117", 9999);
            DataOutputStream dataOutputStream = new DataOutputStream(s.getOutputStream());
            dataOutputStream.writeBytes(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}
