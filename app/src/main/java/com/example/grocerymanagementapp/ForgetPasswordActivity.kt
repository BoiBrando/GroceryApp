package com.example.grocerymanagementapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.grocerymanagementapp.databinding.ActivityForgetPasswordBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class ForgetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgetPasswordBinding

    private lateinit var auth: FirebaseAuth

    private var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.sendEmailBtn.setOnClickListener {
            binding.email.error = null
            email = binding.email.text.toString().trim()

            if(email.isEmpty()) {
                binding.email.error = "Enter email."
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.email.error = "Enter a valid email."
                return@setOnClickListener
            }

            binding.sendEmailBtn.isEnabled = false
            auth.sendPasswordResetEmail(email).addOnSuccessListener {
                Snackbar.make(
                    binding.root,
                    "Password reset email has been sent to $email.",
                    Snackbar.LENGTH_LONG
                ).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                binding.sendEmailBtn.isEnabled = true
                Snackbar.make(
                    binding.root,
                    "Unable to send password reset email. Please try again.",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }
}