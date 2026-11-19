package com.example.grocerymanagementapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.grocerymanagementapp.databinding.ActivityDeleteAccountBinding
import com.example.grocerymanagementapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeleteAccountActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityDeleteAccountBinding

    private lateinit var personalDatabase: PersonalDatabase


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDeleteAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        personalDatabase = PersonalDatabase.getDatabase(this)

        binding.goBackButton.setOnClickListener {
            Toast.makeText(this, "Returned Successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }
        binding.deleteButton.setOnClickListener {
            val user = auth.currentUser

            if (user == null) {
                Toast.makeText(this, "No user is currently signed in", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val uid = user.uid

            lifecycleScope.launch(Dispatchers.IO) {

                personalDatabase.dao.deleteUserGroceries(uid)
                user.delete().addOnSuccessListener {
                    runOnUiThread { Toast.makeText(this@DeleteAccountActivity,"Account Deleted",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@DeleteAccountActivity, SignupActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            }
                        startActivity(intent)
                    }
                }
                    .addOnFailureListener { exception ->
                        runOnUiThread { Toast.makeText(this@DeleteAccountActivity,exception.message,Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }
}