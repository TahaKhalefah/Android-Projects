package com.tahadroid.wing.ui.fragments.home.addcard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.tahadroid.wing.R
import kotlinx.android.synthetic.main.fragment_add_card.*

class AddCardFragment : Fragment(R.layout.fragment_add_card), View.OnClickListener, TextWatcher {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusColor()
        imageViewFinish.setOnClickListener(this)

        editTextCardNumber.addTextChangedListener(this)
        editTextCVV.addTextChangedListener(this)
        editTextDate.addTextChangedListener(this)
    }

    private fun setStatusColor() {
        val window = requireActivity().window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.white)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imageViewFinish -> {
                findNavController().popBackStack()
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        imageViewcredit.setImageResource(R.drawable.ic_mastercard)
        buttonSaveCard.isEnabled =
            editTextCardNumber.text.trim().isNotBlank() && editTextCVV.text.trim().isNotBlank()
                    && editTextDate.text.trim().isNotBlank()
    }
}