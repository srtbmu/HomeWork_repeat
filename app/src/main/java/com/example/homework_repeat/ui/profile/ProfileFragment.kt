package com.example.homework_repeat.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.homework_repeat.data.local.Pref
import com.example.homework_repeat.databinding.FragmentProfileBinding
import com.example.homework_repeat.utils.loadImage

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val pref: Pref by lazy {
        Pref(requireContext())
    }

    private val imageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val image = result.data?.data
                pref.saveImage(image.toString())
                binding.imageView.loadImage(image.toString())
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        insertName()
        openGallery()
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