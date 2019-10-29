package com.example.grabnotifications;

import android.app.Notification;
import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class NotificationListenerExampleService extends NotificationListenerService {

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn){

        if (sbn.getPackageName().equals("za.co.fnb.connect.itt")) {

            Retrofit retrofit2 = new Retrofit.Builder()
                    .baseUrl("https://nnuvzm1nn3.execute-api.eu-west-1.amazonaws.com/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

            StringPostAPI stringPostAPI2 = retrofit2.create(StringPostAPI.class);

            Call<String> call2 = stringPostAPI2.submit(sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TEXT).toString());

            call2.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                    return;
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                    return;
                }
            });
        }

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn){
        // Implement what you want here
    }

    @Override
    public void onDestroy(){

    }
}
