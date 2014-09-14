package com.osgsquare.homecat.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osgsquare.homecat.model.ws.WsMessage;

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
    private URI uri = null;

    public MessageService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Ln.i("onBind");
        attempConnectWebSocket();
        return binder;
    }

    public void send(WsMessage msg) {
        try {
            Ln.d(mapper.writeValueAsString(msg));
            webSocketClient.send(mapper.writeValueAsString(msg));
        } catch (Exception e) {
            Ln.e(e);
        }
    }

    private void onReceive(String s){
        Ln.i(s);
    }

    private void attempConnectWebSocket() {

        if (uri == null)
            try {
                uri = new URI("ws://www.osgsquare.com:7778");
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return;
            }

        if (webSocketClient == null) {
            webSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    Ln.i("Websocket Opened");
                    webSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
                }

                @Override
                public void onMessage(String s) {
                    final String message = s;
                    MessageService.this.onReceive(s);
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
                    Ln.i("Websocket Closed %s ", s);
                }

                @Override
                public void onError(Exception e) {
                    Ln.i("Websocket Error %s", e.getMessage());
                }
            };
            webSocketClient.connect();
        } else if (webSocketClient.getConnection().isClosed()) {
            Ln.i("Websocket is Closed");
        } else if (webSocketClient.getConnection().isClosing()) {
            Ln.i("Websocket is Closing");
        } else if (webSocketClient.getConnection().isFlushAndClose()) {
            Ln.i("Websocket is FlushAndClose");
        } else if (webSocketClient.getConnection().isOpen()) {
            Ln.i("Websocket is Open");
        }
    }


}
