package com.osgsquare.homecat.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.osgsquare.homecat.model.ws.Message;

import roboguice.util.Ln;

public class MessageService extends Service {

    public class LocalBinder extends Binder {
        public MessageService getService() {
            // Return this instance of LocalService so clients can call public methods
            return MessageService.this;
        }
    }
    // Binder given to clients
    private final IBinder binder = new LocalBinder();

    public MessageService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void send(Message message){
        Ln.d(this, message);
    }


}
