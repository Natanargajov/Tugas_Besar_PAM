package com.example.tugas_besar_pam

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EditProfileFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var nameEditText: TextView
    private lateinit var emailEditText: TextView
    private lateinit var saveButton: Button
    private lateinit var imageProfile: TextView
    private lateinit var usiaEditText: TextView
    private lateinit var jkEditText: TextView
    private lateinit var bioEditText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        db = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()

        saveButton = view.findViewById(R.id.simpanButton)
        imageProfile = view.findViewById(R.id.imageProfile)
        nameEditText = view.findViewById(R.id.nameEditText)
        emailEditText = view.findViewById(R.id.emailEditText)
        usiaEditText = view.findViewById(R.id.usiaEditText)
        jkEditText = view.findViewById(R.id.jkEditText)
        bioEditText = view.findViewById(R.id.bioEditText)

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
                        val intent = Intent(requireContext(), ProfileFragment::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener { exception ->
                        println("Error updating document: $exception")
                    }
            }
        }
        return view
    }
}