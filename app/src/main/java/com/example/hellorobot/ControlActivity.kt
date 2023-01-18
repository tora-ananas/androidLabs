package com.example.hellorobot

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ControlActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Control activity", "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        val button_up : Button = findViewById(R.id.button)!!
        val button_left : Button = findViewById(R.id.button_left)!!
        val button_right : Button = findViewById(R.id.button_right)!!
        val button_down : Button = findViewById(R.id.button_down)!!
        val button_cam : Button = findViewById(R.id.button_cam)!!
        val imageView : ImageView = findViewById(R.id.image_cam)!!
        val textView: TextView = findViewById(R.id.textView_cam)!!

        Thread {
            Log.d("Start thread", "go in start thread")
            Connection.outBuf!!.write("start\n")
            Connection.outBuf!!.flush()
            //textView.text = "Thread"
            /*while (true){
                //textView.text = "START"
                Thread.sleep(1000)
                var byteArray = ByteArray(76032)
                Connection.inBytes?.read(byteArray)
                //textView.text = "ByteArray"
                var bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                //imageView.setImageBitmap(bmp)
                textView.text = byteArray.decodeToString()
                Connection.outBuf!!.write("next\n")
                Connection.outBuf!!.flush()
            }*/
        }.start()
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
        button_cam.setOnClickListener {
            Thread {
                Connection.outBuf!!.write("cam\n")
                Connection.outBuf!!.flush()
                runOnUiThread {
                    val intent = Intent(this@ControlActivity, Cam::class.java)
                    startActivity(intent)
                }
            }.start()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Connection.closeConnection()
    }

}