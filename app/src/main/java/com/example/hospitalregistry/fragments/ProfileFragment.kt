package com.example.hospitalregistry.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.hospitalregistry.R
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.util.Objects

class ProfileFragment : Fragment() {
    private var imageView: ImageView? = null
    private var name: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        // Find the ImageView by its id
        val imageView: ImageView = rootView.findViewById(R.id.imageView)

        // Create a Drawable for the placeholder
        val placeholderDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.avatar)

        // Set the placeholder in ImageView
        imageView.setImageDrawable(placeholderDrawable)

        // Find the TextView for the name field
        val nameField: TextView = rootView.findViewById(R.id.nameField)

        // Get the current user's display name from FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser
        val displayName = currentUser?.displayName ?: "Anonymous"
        nameField.text = displayName

        // URL of your avatar
        val avatarUrl = "https://tommystinctures.com/wp-content/uploads/2020/10/avatar-icon-placeholder-1577909.jpg"

        // Load the image using Picasso and set it into ImageView
        if (placeholderDrawable != null) {
            Picasso.get()
                .load(avatarUrl)
                .placeholder(placeholderDrawable)
                .into(imageView, object : Callback {
                    override fun onSuccess() {
                        // Image loaded successfully
                    }

                    override fun onError(e: Exception?) {
                        // Handle error, if any
                        Log.e("Picasso", "Error loading image: $e")
                    }
                })
        }

        return rootView
    }

}