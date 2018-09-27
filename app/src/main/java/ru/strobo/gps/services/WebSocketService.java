package ru.strobo.gps.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.InvalidHandshakeException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ClientHandshakeBuilder;
import org.java_websocket.handshake.HandshakeBuilder;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WebSocketService extends Service {

    private static final String TAG = "WebSocketService";

    private static final String ACTION_CONNECT_WEB_SOCKET = "ACTION_CONNECT_WEB_SOCKET";
    private static final String ACTION_SEND_MESSAGE = "ACTION_SEND_MESSAGE";

    private WebSocketClient mWebSocketClient;

    private static final String HOST = "strobo.ddns.net";
    private static final String PORT = "8085";

    final String wsuri = "http://" + HOST + ":" + PORT+ "/ws";

    public static void startWebSocket(Context context) {
        Log.e(TAG, "startWebSocket");
        Intent intent = new Intent(context, WebSocketService.class);
        intent.setAction(ACTION_CONNECT_WEB_SOCKET);
        context.startService(intent);
    }

    public static void startSendMessage(Context context) {
        Log.e(TAG, "startSendMessage");
        Intent intent = new Intent(context, WebSocketService.class);
        intent.setAction(ACTION_SEND_MESSAGE);
        context.startService(intent);
    }

    //@Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    final String LOG_TAG = "myLogs";

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");
        final String action = intent.getAction();
        switch (action) {
            case ACTION_CONNECT_WEB_SOCKET:
                Log.d(TAG, "WebSocketService.ACTION_CONNECT_WEB_SOCKET");
                connectToWebSocket();
                break;
            case ACTION_SEND_MESSAGE:
                Log.d(TAG, "WebSocketService.ACTION_SEND_MESSAGE");
                sendMessage();
                break;

        }
        return super.onStartCommand(intent, flags, startId);
    }


    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }


    void connectToWebSocket() {

        URI uri;
        Draft draft;
        Map<String, String> headers = new HashMap<String, String>();
        int timeout = 30000;

        try {
            uri = new URI(wsuri);
            draft = new Draft() {
                @Override
                public HandshakeState acceptHandshakeAsClient(ClientHandshake clientHandshake, ServerHandshake serverHandshake) throws InvalidHandshakeException {
                    return null;
                }

                @Override
                public HandshakeState acceptHandshakeAsServer(ClientHandshake clientHandshake) throws InvalidHandshakeException {
                    return null;
                }

                @Override
                public ByteBuffer createBinaryFrame(Framedata framedata) {
                    return null;
                }

                @Override
                public List<Framedata> createFrames(ByteBuffer byteBuffer, boolean b) {
                    return null;
                }

                @Override
                public List<Framedata> createFrames(String s, boolean b) {
                    return null;
                }

                @Override
                public void reset() {

                }

                @Override
                public ClientHandshakeBuilder postProcessHandshakeRequestAsClient(ClientHandshakeBuilder clientHandshakeBuilder) throws InvalidHandshakeException {
                    return null;
                }

                @Override
                public HandshakeBuilder postProcessHandshakeResponseAsServer(ClientHandshake clientHandshake, ServerHandshakeBuilder serverHandshakeBuilder) throws InvalidHandshakeException {
                    return null;
                }

                @Override
                public List<Framedata> translateFrame(ByteBuffer byteBuffer) throws InvalidDataException {
                    return null;
                }

                @Override
                public CloseHandshakeType getCloseHandshakeType() {
                    return null;
                }

                @Override
                public Draft copyInstance() {
                    return null;
                }
            };

            headers.put("deviceid", "StroboPhone");



        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri, draft, headers, timeout ) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
                mWebSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                Log.d(TAG, message);
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };

        mWebSocketClient.connect();
    }

    private void sendMessage() {
        mWebSocketClient.send("websocket service message");
    }
}