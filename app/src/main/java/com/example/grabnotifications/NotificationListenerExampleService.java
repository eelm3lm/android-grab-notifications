package com.example.grabnotifications;

import android.app.Notification;
import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
//import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class NotificationListenerExampleService extends NotificationListenerService {

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn){
        //Intent intent = new  Intent("com.example.grabnotifications.notificationlistenerexample");
        //sendBroadcast(intent);
        if (sbn.getPackageName().equals("za.co.fnb.connect.itt")) {
            //Log.i("noti", sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TEXT).toString());

            URL url = null;
            try { url = new URL("https://nnuvzm1nn3.execute-api.eu-west-1.amazonaws.com/v1/fnb");
            } catch (MalformedURLException e) {}

            HttpURLConnection con = null;
            try { con = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {}

            try { con.setRequestMethod("POST");
            } catch (ProtocolException e) {}

            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            String postBody = sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TEXT).toString();

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = postBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            } catch (IOException e) {}

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            } catch (IOException e) {}
        }

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn){
        // Implement what you want here
    }
}
