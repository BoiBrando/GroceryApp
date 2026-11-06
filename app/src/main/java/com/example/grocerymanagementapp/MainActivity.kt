package com.example.grocerymanagementapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.scale

@Suppress("Deprecation")

class MainActivity : AppCompatActivity() {
    private lateinit var myButtonScanReceipt: Button
    private lateinit var myButtonTakePicture: Button
    private lateinit var myTextViewWelcome: TextView

    // iv_image is the variable of the shown image
    private lateinit var ivImage : androidx.appcompat.widget.AppCompatImageView

    // The companion object is for the camera permissions
    companion object {
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
    }

    private var imageSize = 224

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myButtonScanReceipt = findViewById(R.id.myButtonScanReceipt)
        myButtonTakePicture = findViewById(R.id.myButtonTakePicture)
        myTextViewWelcome = findViewById(R.id.myTextViewWelcome)

        myButtonScanReceipt.text = getString(R.string.button_text_scan_receipt)
        myButtonTakePicture.text = getString(R.string.button_text_take_picture)
        myTextViewWelcome.text = getString(R.string.text_view_text)

        ivImage = findViewById(R.id.iv_image)

        // Listener for myButtonScanReceipt
        myButtonScanReceipt.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            }
            else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
            }
        }

    }

    // Setting the camera permissions for the app
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == CAMERA_PERMISSION_CODE) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            }
            else {
                Toast.makeText(this, "Camera Permission Denied...", Toast.LENGTH_SHORT).show()
            }
        }
    }



    // Once the button is clicked, this function opens the camera
    // and allows the user to take the photo
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK) {
            if(requestCode == CAMERA_REQUEST_CODE) {

                val thumbnail: Bitmap = data!!.extras!!.get("data") as Bitmap

                val cv = ComputerVision()

                ivImage.setImageBitmap(thumbnail)

                val image = thumbnail.scale(imageSize, imageSize, false)
                val str = cv.classifyImage(image, imageSize, this)
                Toast.makeText(this, str, Toast.LENGTH_LONG).show()


            }
        }
    }


}