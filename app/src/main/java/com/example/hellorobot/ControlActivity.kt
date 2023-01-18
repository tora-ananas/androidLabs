package com.example.hellorobot

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class ControlActivity : AppCompatActivity() {
    var cameraCheck: Boolean = true
    lateinit var webThread: Thread

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Control activity", "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        val button_up: Button = findViewById(R.id.button)!!
        val button_left: Button = findViewById(R.id.button_left)!!
        val button_right: Button = findViewById(R.id.button_right)!!
        val button_down: Button = findViewById(R.id.button_down)!!
        val imageView: ImageView = findViewById(R.id.image_cam)!!

        webThread = Thread {
            Log.d("Start thread", "go in start thread")
            Connection.outBuf!!.write("start\n")
            Connection.outBuf!!.flush()
            //textView.text = "Thread"
            cameraCheck = true
            while (cameraCheck) {
                var byteArray = ByteArray(230400)
                Connection.inBytesCam?.readFully(byteArray)
                var nrOfPixels = byteArray.size / 3 // Three bytes per pixel.
                var pixels = IntArray(nrOfPixels)
                for (i in 0 until nrOfPixels) {
                    var r = byteArray.get(3 * i).toInt()
                    var g = byteArray.get(3 * i + 1).toInt()
                    var b = byteArray.get(3 * i + 2).toInt()
                    pixels.set(i, Color.rgb(r, g, b))
                }
                var bitmap = Bitmap.createBitmap(pixels, 320, 240, Bitmap.Config.ARGB_8888)

                runOnUiThread {
                    imageView.setImageBitmap(bitmap)
                }
                Connection.outBuf!!.write("next\n")
                Connection.outBuf!!.flush()
            }
        }
        webThread.start()

        button_up.setOnClickListener {
            Thread {
                Connection.outBuf!!.write("button_up\n")
                Connection.outBuf!!.flush()
            }.start()
        }
        button_left.setOnClickListener {
            Thread {
                Connection.outBuf!!.write("button_left\n")
                Connection.outBuf!!.flush()
            }.start()
        }
        button_right.setOnClickListener {
            Thread {
                Connection.outBuf!!.write("button_right\n")
                Connection.outBuf!!.flush()
            }.start()
        }
        button_down.setOnClickListener {
            Thread {
                Connection.outBuf!!.write("button_down\n")
                Connection.outBuf!!.flush()
            }.start()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        cameraCheck = false
        webThread.join()
        val thread = Thread {
            Connection.outBuf!!.write("stop client\n")
            Connection.outBuf!!.flush()
        }
        thread.start()
        thread.join()
        Connection.closeConnection()
    }

}