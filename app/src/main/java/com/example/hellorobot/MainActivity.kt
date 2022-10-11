package com.example.hellorobot

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    private lateinit var mHelloTextView: TextView
    private lateinit var editText: EditText
    private var counter: Int = 0

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        mHelloTextView = findViewById(R.id.textView)
        editText = findViewById(R.id.editText)


        var imageView: ImageView = findViewById(R.id.imageView)
        val textView: TextView = findViewById(R.id.textView)
        val rootLayout: ConstraintLayout = findViewById(R.id.root_layout)
        val button_red: Button = findViewById(R.id.button_red)
        val button_orange: Button = findViewById(R.id.button_orange)
        val button_green: Button = findViewById(R.id.button_green)

        button_red.setOnClickListener{
            textView.text = resources.getText(R.id.button_red)
            rootLayout.setBackgroundColor(resources.getColor(R.color.red, null))
        }

        button_orange.setOnClickListener{
            textView.text = "ORANGE!"
            rootLayout.setBackgroundColor(resources.getColor(R.color.orange, null))
        }

        button_green.setOnClickListener{
            textView.text = "GREEN!"
            rootLayout.setBackgroundColor(resources.getColor(R.color.green, null))
        }

        imageView.setOnClickListener {
            if (editText.text.isEmpty()) {
                mHelloTextView.text = "Hello Robot!"
            } else {
                mHelloTextView.text = "Hello, " + editText.text
            }
        }
    }
}