package com.example.hellorobot

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val enterText: EditText = findViewById(R.id.editText_enterId)
        val clickImage: ImageView = findViewById(R.id.imageView_connect)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        clickImage.setOnClickListener {
            //10.0.2.2
            Connection.mHost = enterText.text.toString()
            Connection.camHost = enterText.text.toString()
            enterText.setText(Connection.mHost)
            Connection.mPort =  8080
            Connection.camPort = 8081

            val thread = Thread {
                val res = Connection.openConnection()
                Connection.outBuf!!.write("SQUIRREL\n")
                Connection.outBuf!!.flush()
                if (res) {
                    Log.d("Connection true","catch res")
                    runOnUiThread {
                        val intent = Intent(this@MainActivity, ControlActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
            thread.start()
        }
    }
}