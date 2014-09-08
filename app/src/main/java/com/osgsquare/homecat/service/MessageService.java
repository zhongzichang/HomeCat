package com.osgsquare.homecat.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osgsquare.homecat.model.ws.Message;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

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
    private WebSocketClient webSocketClient;
    private ObjectMapper mapper = new ObjectMapper();

    public MessageService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void send(Message message){
        Ln.d(this, message);
        try {
            webSocketClient.send(mapper.writeValueAsString(message));
        } catch (Exception e) {
            Ln.e(e);
        }
    }

    private void connectWebSocket() {

        URI uri;
        try {
            uri = new URI("ws://www.osgsquare.com:8080");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Ln.i(this, "Websocket Opened");
                webSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        TextView textView = (TextView) findViewById(R.id.messages);
//                        textView.setText(textView.getText() + "\n" + message);
//                    }
//                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Ln.i(this, "Websocket Closed %s " , s);
            }

            @Override
            public void onError(Exception e) {
                Ln.i(this, "Websocket Error %s" , e.getMessage());
            }
        };
        webSocketClient.connect();
    }


}
