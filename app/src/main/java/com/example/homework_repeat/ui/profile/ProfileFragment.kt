package com.example.homework_repeat.ui.profile

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homework_repeat.R
import com.example.homework_repeat.data.local.Pref
import com.example.homework_repeat.databinding.FragmentProfileBinding
import com.example.homework_repeat.utils.loadImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val pref: Pref by lazy {
        Pref(requireContext())
    }

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val imageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val image = result.data?.data
                pref.saveImage(image.toString())
                binding.imageView.loadImage(image.toString())
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        insertName()
        openGallery()
        exitAcount()
    }

    private fun exitAcount() {
        binding.btnExit.setOnClickListener {
            signOutFromGoogle()
        }
    }

    private fun signOutFromGoogle() {
        val currentUser = auth.currentUser
        if (currentUser != null && currentUser.providerData.any {
                it.providerId == GoogleAuthProvider.PROVIDER_ID
            }) {
            AlertDialog.Builder(requireContext())
                .setTitle("Delete acount google")
                .setPositiveButton("Yes") { _, _ ->
                    auth.signOut()
                    findNavController().navigate(R.id.chooseRegisterFragment)
                    Toast.makeText(
                        context,
                        "You are signed out of your Google account",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .setNegativeButton("No") { dialog, _ -> dialog?.dismiss() }.create().show()
        } else {
            Toast.makeText(context, "You don't have accounts", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openGallery() {
        binding.imageView.loadImage(pref.getImage())
        binding.imageView.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            imageLauncher.launch(intent)
        }
    }

    private fun insertName() {
        binding.etProfile.setText(pref.getName())
        binding.etProfile.addTextChangedListener {
            pref.saveName(binding.etProfile.text.toString())
        }
    }
}