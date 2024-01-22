package com.example.proiectquantumdam.websocket;


import android.util.Log;

import org.json.JSONObject;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public final class JobStreamWebSocketListener extends WebSocketListener {

    OnMessageReceivedCallback callback;

    public JobStreamWebSocketListener(OnMessageReceivedCallback callback) {
        this.callback = callback;
    }

    @Override public void onOpen(WebSocket webSocket, Response response) {
        Log.i("Y", "YAHOO");
    }

    @Override public void onMessage(WebSocket webSocket, String text) {
        System.out.println("MESSAGE: " + text);
        Log.i("UPDATE", "UPDATE");

        try {
            JSONObject jsonObject = new JSONObject(text);
            String jobId = jsonObject.getString("id");
            String status = jsonObject.getString("status");
            callback.onStatusUpdateReceivedCallback(status);
        }
        catch (Exception e){
            Log.e("E", e.getMessage());
        }

    }

    @Override public void onMessage(WebSocket webSocket, ByteString bytes) {
        Log.i("UPDATE", "UPDATE");
    }

    @Override public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(1000, null);
        System.out.println("CLOSE: " + code + " " + reason);
    }

    @Override public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        t.printStackTrace();
    }

}

