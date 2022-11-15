package com.example.hellorobot

import android.content.Intent
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstText: TextView = findViewById(R.id.text_first)
        val secondText: TextView = findViewById(R.id.text_second)
        val thirdText: TextView = findViewById(R.id.text_third)
        val bottomImage: ImageView = findViewById(R.id.image_bottom)
        val clickImage: ImageView = findViewById(R.id.imageView10)

        bottomImage.setOnClickListener {
            val phrases = listOf(
                "Рыбов хочется",
                "Вставай, нам нужны рыбы",
                "Продайте рыбов",
                "Рыбы рыбы",
                "Наташ, мы все рыбы уронили!")

            val shuffledList = phrases.shuffled()

            firstText.text = shuffledList[0]
            secondText.text = shuffledList[1]
            thirdText.text = shuffledList[2]
        }

        clickImage.setOnClickListener {
            val intent = Intent(this@MainActivity, AboutActivity::class.java)
            startActivity(intent)
        }

    }
}