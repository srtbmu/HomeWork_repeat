package com.example.homework_repeat.ui.auth.phone

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.homework_repeat.R
import com.example.homework_repeat.databinding.FragmentPhoneBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    private lateinit var binding: FragmentPhoneBinding
    private lateinit var auth: FirebaseAuth
    private var phoneNumber = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        clickPhoneNumber()
        val filterArray = arrayOf<InputFilter>(InputFilter.LengthFilter(13))
        binding.etPhone.filters = filterArray

    }

    private fun clickPhoneNumber() {
        binding.btnSend.setOnClickListener {
            if (binding.etPhone.toString().isNotEmpty()) {
                verifyNumber()
            } else {
                binding.etPhone.error = "Phone number"
                Toast.makeText(context, "null phone number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {}

        override fun onVerificationFailed(e: FirebaseException) {}

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            findNavController().navigate(
                R.id.verifyFragment,
                bundleOf(VERIFY_KEY to verificationId)
            )
        }
    }

    private fun verifyNumber() {
        phoneNumber = binding.etPhone.text.toString()
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    companion object {
        const val VERIFY_KEY = "ver_key"
    }
}