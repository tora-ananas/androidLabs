package com.example.hellorobot

import android.system.Os.socket
import android.util.Log
import java.io.*
import java.net.Socket


object Connection {
    private var mSocket: Socket? = null
    var mHost: String? = null
    var mPort = 0
    var inBuf: BufferedReader? = null
    var outBuf: BufferedWriter? = null

    private var camSocket: Socket? = null
    var camHost: String? = null
    var camPort = 0
    var inBytesCam: DataInputStream? = null
    var outBufCam: BufferedWriter? = null

    val LOG_TAG = "SOCKET"

    // Метод открытия сокета
    @Throws(Exception::class)
    fun openConnection(): Boolean {
        // Если сокет уже открыт, то он закрывается
        var result = false
        var resultCam = false
        closeConnection()
        try {
            // Создание сокета
            mSocket = Socket(mHost, mPort)
            inBuf = BufferedReader(InputStreamReader(mSocket!!.getInputStream()))
            outBuf = BufferedWriter(OutputStreamWriter(mSocket!!.getOutputStream()))
            result = true
            //Создание второго сокета для передачи фото
            camSocket = Socket(camHost, camPort)
            inBytesCam = DataInputStream(camSocket!!.getInputStream())
            outBufCam = BufferedWriter(OutputStreamWriter(mSocket!!.getOutputStream()))
            resultCam = true
        } catch (e: IOException) {
            Log.e("SOCKET ERROR",
                "Невозможно создать сокет: "
                        + e.message
                        + " " + mHost + " " + mPort
            )
        }
        return result && resultCam
    }

    /**
     * Метод закрытия сокета
     */
    fun closeConnection() {
        if (mSocket != null && !mSocket!!.isClosed) {
            try {
                mSocket!!.close()
            } catch (e: IOException) {
                Log.e(
                    LOG_TAG, ("Ошибка при закрытии сокета :"
                            + e.message)
                )
            } finally {
                mSocket = null
            }
        }
        mSocket = null
        inBuf = null
        outBuf = null
    }

    fun isClosed(): Boolean {
        if (mSocket == null) return true
        return mSocket!!.isClosed
    }
}