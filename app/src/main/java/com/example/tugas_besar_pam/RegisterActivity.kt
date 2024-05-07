package com.example.tugas_besar_pam

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tugas_besar_pam.databinding.ActivityRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        val db = Firebase.firestore

        binding.loginTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.registerButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            if (password == confirmPassword) {
                if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { authTask ->
                        if (authTask.isSuccessful) {
                            val user = authTask.result?.user
                            val userId = user?.uid

                            userId?.let {  uid ->
                                val userData = hashMapOf(
                                    "email" to email,
                                    "name" to name,
                                    "uid" to uid
                                )

                                db.collection("users").document(uid)
                                    .set(userData)
                                    .addOnSuccessListener { documentReference -> android.util.Log.d(TAG, "DocumentSnapshot added with ID:") }
                                    .addOnFailureListener { e -> android.util.Log.w(TAG, "Error adding document", e) }

                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            }
                        } else {
                            Toast.makeText(this, authTask.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Harap memasukkan data profile!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Harap memasukkan password yang benar!", Toast.LENGTH_SHORT).show()
            }


        }
    }
}