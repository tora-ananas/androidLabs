package com.example.hellorobot

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class Cam : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Cam activity", "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cam)

        val button_back : Button = findViewById(R.id.button_back)!!
        var textView : TextView = findViewById(R.id.textView)!!
        var imageView: ImageView = findViewById(R.id.Image_update)!!

        Thread {
            Thread.sleep(1000)
            var byteArray = ByteArray(76032)
            Connection.inBytesCam?.read(byteArray)
            //textView.text = "ByteArray"
            var nrOfPixels = byteArray.size / 3 // Three bytes per pixel.
            var pixels = IntArray(nrOfPixels)
            for (i in 0 until nrOfPixels){
                var r = byteArray.get(3*i).toInt()
                var g = byteArray.get(3*i + 1).toInt()
                var b = byteArray.get(3*i + 2).toInt()
                pixels.set(i, Color.rgb(r, g, b))
            }
            var bitmap = Bitmap.createBitmap(pixels, 176, 144, Bitmap.Config.ARGB_8888)

            Thread.sleep(1000)
            textView.text = bitmap.height.toString()
            Thread.sleep(1000)
            textView.text = bitmap.width.toString()
            Thread.sleep(1000)
            textView.text = bitmap.config.toString()
            Thread.sleep(1000)
            textView.text = bitmap.byteCount.toString()
            Thread.sleep(1000)
            textView.text = bitmap.density.toString()
            Thread.sleep(1000)

            imageView.setImageBitmap(bitmap)

            Connection.outBuf!!.write("next\n")
            Connection.outBuf!!.flush()

        }.start()

        button_back.setOnClickListener {
            Thread {
                runOnUiThread {
                    val intent = Intent(this@Cam, ControlActivity::class.java)
                    startActivity(intent)
                }
            }.start()
        }

    }
}