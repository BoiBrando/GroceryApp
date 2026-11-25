package com.example.grocerymanagementapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.scale
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.grocerymanagementapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers

@Suppress("Deprecation")

class MainActivity : AppCompatActivity() {
    private lateinit var myButtonScanReceipt: Button
    private lateinit var myButtonTakePicture: Button
    private lateinit var myTextViewWelcome: TextView

    private lateinit var binding: ActivityMainBinding

    private lateinit var auth: FirebaseAuth

    // iv_image is the variable of the shown image
    private lateinit var ivImage : androidx.appcompat.widget.AppCompatImageView

    // The companion object is for the camera permissions
    companion object {
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
    }

    private var imageSize = 224

    private lateinit var masterDatabase: MasterInventory
    private lateinit var personalDatabase: PersonalDatabase


    override fun onCreate(savedInstanceState: Bundle?) {

        masterDatabase = MasterInventory.getDatabase(this)
        personalDatabase = PersonalDatabase.getDatabase(this)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        myButtonScanReceipt = findViewById(R.id.myButtonScanReceipt)
        myButtonTakePicture = findViewById(R.id.myButtonTakePicture)
        myTextViewWelcome = findViewById(R.id.myTextViewWelcome)

        myButtonScanReceipt.text = getString(R.string.button_text_scan_object)
        myButtonTakePicture.text = getString(R.string.button_text_refresh_database)
        myTextViewWelcome.text = getString(R.string.text_view_text)

        ivImage = findViewById(R.id.iv_image)

        binding.myButtonLogout.setOnClickListener {
            auth.signOut()
            Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }

        binding.deleteAccountButton.setOnClickListener {
            Toast.makeText(this, "Preparing to Delete Account", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, DeleteAccountActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }

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

        // Test listener for myButtonTakePicture
        // Made more changes to this chunk of code on 11/17/2026
        myButtonTakePicture.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                // Claimed it "wasn't used", so I commented it out
//                val uid = auth.currentUser!!.uid
                val masterItems = masterDatabase.dao.getMasterGroceryItems()
                val personalItems = personalDatabase.dao.getGroceryItems()
                Log.d("MASTER_DB", masterItems.toString())
                Log.d("PERSONAL_DB", personalItems.toString())
            }

        }

    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser == null) {
            val intent = Intent(
                this,
                LoginActivity::class.java
            ).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
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

                // All this does it put the just-taken
                // photo on the user's screen
//              ivImage.setImageBitmap(thumbnail)

                val image = thumbnail.scale(imageSize, imageSize, false)
                val str = cv.classifyImage(image, imageSize, this)

                // Check if the items that's about to be
                // added already exists in personal DB
                // Made more changes so that individual users can have their own separate
                // databases based off of their userid
                lifecycleScope.launch(Dispatchers.IO) {
                    val uid = auth.currentUser!!.uid
                    val toAdd = masterDatabase.dao.getMasterGroceryItemFromName(str)
                    toAdd.quantity = 1.0

                    if(personalDatabase.dao.checkIfItemExists(toAdd.itemName,uid)) {
                            personalDatabase.dao.updateItemQuantity(
                                toAdd.itemName,
                                uid,
                                1.0)
                    }
                    else {
                        val personalItem = GroceryItem(
                            itemId =  toAdd.itemID,
                            itemName = toAdd.itemName,
                            quantity = 1.0,
                            expirationDate = toAdd.expirationDate,
                            foodType = toAdd.foodType,
                            userId = uid
                        )
                        personalDatabase.dao.insert(personalItem)
                    }
                }

                Toast.makeText(this, "$str added to database!", Toast.LENGTH_LONG).show()


            }
        }
    }


}