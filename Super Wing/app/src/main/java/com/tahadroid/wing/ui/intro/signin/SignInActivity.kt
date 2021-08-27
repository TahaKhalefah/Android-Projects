package com.tahadroid.wing.ui.intro.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.tahadroid.wing.R
import com.tahadroid.wing.ui.intro.welcome.WelcomeActivity
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_verification.*
import kotlinx.android.synthetic.main.activity_flip.*
import java.util.concurrent.TimeUnit

class SignInActivity : AppCompatActivity() {

    private lateinit var sendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var verificationId: String
    private lateinit var completePhoneNumber: String
    val mAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flip)

        phoneTextView.setOnClickListener {
            val phoneNumber = editTextPhoneNumber.text.toString().trim()
            if (phoneNumber.isEmpty()) {
                editTextPhoneNumber.error = "Enter a valid phone"
                editTextPhoneNumber.requestFocus()
                return@setOnClickListener
            }
            completePhoneNumber = '+' + ccp.selectedCountryCode + phoneNumber
            senCode(completePhoneNumber)
            progressBarCheckPhone.visibility =View.VISIBLE

        }
        nextButton.setOnClickListener {

            val code = otp_view.text.toString().trim()
            if (code.isEmpty()) {
                otp_view.error = "Code Required"
                otp_view.requestFocus()
                return@setOnClickListener
            } else if (code.length != 6) {
                otp_view.error = "Invalid Code"
                otp_view.requestFocus()
                return@setOnClickListener
            }
            progressBar.visibility = View.VISIBLE
            val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
            signInWithPhoneAuthCredential(credential)
        }

        textViewResend.setOnClickListener {
            reSenCode(completePhoneNumber, sendToken)
            progressBarCheckPhone.visibility =View.VISIBLE
        }
    }



    private fun senCode(phoneNumber: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,               // Phone number to verify
            60,                            // Timeout duration
            TimeUnit.SECONDS,              // Unit of timeout
            TaskExecutors.MAIN_THREAD,     // Activity (for callback binding)
            callbacks
        )                                  // OnVerificationStateChangedCallbacks

    }

    private fun reSenCode(phoneNumber: String, sendToken: PhoneAuthProvider.ForceResendingToken) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,               // Phone number to verify
            60,                            // Timeout duration
            TimeUnit.SECONDS,              // Unit of timeout
            TaskExecutors.MAIN_THREAD,     // Activity (for callback binding)
            mCallbacks,
            sendToken
        )                                  // OnVerificationStateChangedCallbacks

    }

    private var mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
            otp_view.setText(phoneAuthCredential.smsCode)
            signInWithPhoneAuthCredential(phoneAuthCredential)
        }

        override fun onVerificationFailed(p0: FirebaseException) {
        }

    }

    private var callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
            otp_view.setText(phoneAuthCredential.smsCode)
            signInWithPhoneAuthCredential(phoneAuthCredential)
            progressBarCheckPhone.visibility =View.GONE
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(this@SignInActivity, "Invalid Number", Toast.LENGTH_SHORT).show()
            progressBarCheckPhone.visibility =View.GONE
        }

        override fun onCodeSent(
            verificationCodeBySystem: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(verificationCodeBySystem, token)
            this@SignInActivity.verificationId = verificationCodeBySystem
            sendToken = token
            viewFlipperPhone.showNext()
            phoneNuTextView.text = completePhoneNumber
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, "Phone Added", Toast.LENGTH_SHORT).show()
                   val intentToWelcom = Intent(this@SignInActivity, WelcomeActivity::class.java)
                    intentToWelcom.putExtra("phoneNumber",completePhoneNumber)
                    startActivity(intentToWelcom)

                    finish()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        progressBar.visibility = View.GONE
                        Toast.makeText(
                            this,
                            "This code is invalid please enter the correct code",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }
}