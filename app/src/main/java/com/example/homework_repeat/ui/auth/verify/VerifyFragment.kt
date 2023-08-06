package com.example.homework_repeat.ui.auth.verify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homework_repeat.R
import com.example.homework_repeat.databinding.FragmentVerifyBinding
import com.example.homework_repeat.ui.auth.phone.PhoneFragment.Companion.VERIFY_KEY
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider

class VerifyFragment : Fragment() {

    private lateinit var binding: FragmentVerifyBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVerifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val verId = arguments?.getString(VERIFY_KEY)
        auth = FirebaseAuth.getInstance()
        binding.btnCode.setOnClickListener {
            signInWithCred(verId)
        }
    }

    private fun signInWithCred(verId: String?) {
        val code = binding.etCode.text.toString()
        val credential = PhoneAuthProvider.getCredential(verId!!, code)
        auth.signInWithCredential(credential).addOnSuccessListener {
            findNavController().navigate(R.id.navigation_home)
        }.addOnFailureListener setOnClickListener@{
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            binding.etCode.error = "Code Required"
            return@setOnClickListener
        }
    }
}