package com.example.tugas_besar_pam

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EditProfileActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var imageProfile: ImageView
    private lateinit var usiaEditText: EditText
    private lateinit var jkEditText: EditText
    private lateinit var bioEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        db = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()

        saveButton = findViewById(R.id.simpanButton)
        imageProfile = findViewById(R.id.imageProfile)
        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        usiaEditText = findViewById(R.id.usiaEditText)
        jkEditText = findViewById(R.id.jkEditText)
        bioEditText = findViewById(R.id.bioEditText)

        val back: ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
        }

        saveButton.setOnClickListener {

            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val usia = usiaEditText.text.toString()
            val jk = jkEditText.text.toString()
            val bio = bioEditText.text.toString()

            val currentUser = firebaseAuth.currentUser
            currentUser?.uid?.let { userId ->

                val updateUserData = hashMapOf(
                    "email" to email,
                    "name" to name,
                    "usia" to usia,
                    "jk" to jk,
                    "bio" to bio
                )

                db.collection("users").document(userId)
                    .set(updateUserData)
                    .addOnSuccessListener { _ ->
                        finish()
                    }
                    .addOnFailureListener { exception ->
                        println("Error updating document: $exception")
                    }
            }
        }
    }
}
