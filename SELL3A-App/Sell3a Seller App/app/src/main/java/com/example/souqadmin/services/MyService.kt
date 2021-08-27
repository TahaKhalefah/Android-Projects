package com.example.souqadmin.services

import ahmed.adel.sleeem.clowyy.souq.notifications.Notifications
import android.R.id.message
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.souqadmin.pojo.OrderResponseItem
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONException
import org.json.JSONObject


class MyService : Service() {

    private lateinit var mySocket: Socket
    private lateinit var notification: Notifications
    var TAG = "MAINACTIVITY_Socket"
    private var data = listOf<OrderResponseItem>()

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()


        //Notification
        notification = Notifications(this)

        //Socket Io
        mySocket = IO.socket("https://souqitigraduationproj.herokuapp.com")
        mySocket.open()


        mySocket.on(Socket.EVENT_CONNECT, Emitter.Listener {
            Log.i(TAG, "onCreate: connected")
        });
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return super.onStartCommand(intent, flags, startId)
    }
}