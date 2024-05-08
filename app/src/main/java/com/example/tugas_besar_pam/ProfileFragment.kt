package com.example.tugas_besar_pam

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class ProfileFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var buttonLogout: Button
    private lateinit var buttonEdit: Button
    private lateinit var imageProfile: TextView
    private lateinit var usiaTextView: TextView
    private lateinit var jkTextView: TextView
    private lateinit var bioTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        db = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()

        buttonLogout = view.findViewById(R.id.logoutButton)
        buttonEdit = view.findViewById(R.id.editButton)
        imageProfile = view.findViewById(R.id.imageProfile)
        nameTextView = view.findViewById(R.id.nameTextView)
        emailTextView = view.findViewById(R.id.emailTextView)
        usiaTextView = view.findViewById(R.id.usiaTextView)
        jkTextView = view.findViewById(R.id.jkTextView)
        bioTextView = view.findViewById(R.id.bioTextView)


        buttonLogout.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        buttonEdit.setOnClickListener {
            val intent = Intent(requireContext(), EditProfileFragment::class.java)
            startActivity(intent)
        }

        val currentUser = firebaseAuth.currentUser
        currentUser?.uid?.let { userId ->
            db.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val name = document.getString("name") ?: "Default Name"
                        val email = document.getString("email") ?: "Default Email"
                        val usia = document.getString("usia") ?: "Default Usia"
                        val jk = document.getString("jk") ?: "Default JK"
                        val bio = document.getString("bio") ?: "Default Bio"

                        nameTextView.text = name
                        emailTextView.text = email
                        usiaTextView.text = usia
                        jkTextView.text = jk
                        bioTextView.text = bio

                        val nameParts = name.split(" ")
                        val initials = if (nameParts.isNotEmpty()) {
                            nameParts[0].firstOrNull()?.toString() ?: ""
                        } else {
                            ""
                        }
                        imageProfile.text = initials

                    } else {
                        println("Data not found")
                    }
                }
                .addOnFailureListener { exception ->
                    println("Error: ${exception.message}")
                }
        }
        return view
    }
}