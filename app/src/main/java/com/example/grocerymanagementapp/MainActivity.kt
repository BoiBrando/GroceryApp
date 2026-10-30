package com.example.grocerymanagementapp

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var myButtonScanReceipt: Button
    private lateinit var myButtonTakePicture: Button
    private lateinit var myTextViewWelcome: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myButtonScanReceipt = findViewById(R.id.myButtonScanReceipt)
        myButtonTakePicture = findViewById(R.id.myButtonTakePicture)
        myTextViewWelcome = findViewById(R.id.myTextViewWelcome)

        myButtonScanReceipt.text = getString(R.string.button_text_scan_receipt)
        myButtonTakePicture.text = getString(R.string.button_text_take_picture)
        myTextViewWelcome.text = getString(R.string.text_view_text)



    }
}