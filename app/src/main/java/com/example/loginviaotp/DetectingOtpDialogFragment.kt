@file:Suppress("UNUSED_ANONYMOUS_PARAMETER", "MemberVisibilityCanBePrivate")

package com.example.loginviaotp

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.loginviaotp.databinding.FragmentDetectingOtpDialogBinding

class DetectingOtpDialogFragment : DialogFragment() {
    private val phoneNo: String
        get() = arguments!!.getString("phoneNo")!!

    private lateinit var binding: FragmentDetectingOtpDialogBinding

    companion object {
        @JvmField
        val TAG = DetectingOtpDialogFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(phoneNo: String) = DetectingOtpDialogFragment().apply {
            arguments = Bundle().apply {
                putString("phoneNo", phoneNo)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detecting_otp_dialog,
            container,
            false
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        initControl()
    }

    fun init() {
        isCancelable = false
        binding.tvPhoneNo.text = phoneNo
    }

    fun initControl() {
        binding.ivCross.setOnClickListener {
            dialog?.cancel()
        }

        binding.tvChange.setOnClickListener {
            dialog?.cancel()
        }

        binding.tvResendOtp.setOnClickListener {
            Toast.makeText(context!!, "OTP resend.", Toast.LENGTH_LONG).show()
        }

        binding.etOtp1.doOnTextChanged { text, start, count, after ->
            if (after == 1) {
                binding.etOtp2.requestFocus()
            }
        }

        binding.etOtp1.setOnKeyListener { v, keyCode, event ->
            event?.let {
                if (keyCode == KeyEvent.KEYCODE_DEL || keyCode.isNumeric()) {
                    updateInvalidState(false)
                }
                if (it.action == KeyEvent.ACTION_DOWN) {
                    if (keyCode.isNumeric() && !binding.etOtp1.text.isNullOrEmpty()) {
                        binding.etOtp1.text?.clear()
                    }
                }
            }
            return@setOnKeyListener false
        }

        binding.etOtp2.doOnTextChanged { text, start, count, after ->
            if (after == 1) {
                binding.etOtp3.requestFocus()
            }
        }

        binding.etOtp2.setOnKeyListener { v, keyCode, event ->
            event?.let {

                if (it.action == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DEL || keyCode.isNumeric()) {
                        updateInvalidState(false)
                    }
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (binding.etOtp2.text.isNullOrEmpty()) {
                            binding.etOtp1.requestFocus()
                            return@setOnKeyListener true
                        }
                    } else {
                        if (keyCode.isNumeric() && !binding.etOtp2.text.isNullOrEmpty()) {
                            binding.etOtp2.text?.clear()
                        }
                    }
                }
            }
            return@setOnKeyListener false
        }

        binding.etOtp3.doOnTextChanged { text, start, count, after ->
            if (after == 1) {
                binding.etOtp4.requestFocus()
            }
        }

        binding.etOtp3.setOnKeyListener { v, keyCode, event ->
            event?.let {
                if (it.action == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DEL || keyCode.isNumeric()) {
                        updateInvalidState(false)
                    }
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (binding.etOtp3.text.isNullOrEmpty()) {
                            binding.etOtp2.requestFocus()
                            return@setOnKeyListener true
                        }
                    } else {
                        if (keyCode.isNumeric() && !binding.etOtp3.text.isNullOrEmpty()) {
                            binding.etOtp3.text?.clear()
                        }
                    }
                }
            }
            return@setOnKeyListener false
        }

        binding.etOtp4.doOnTextChanged { text, start, count, after ->
            if (binding.etOtp1.text.isNullOrEmpty()
                || binding.etOtp2.text.isNullOrEmpty()
                || binding.etOtp3.text.isNullOrEmpty()
                || binding.etOtp4.text.isNullOrEmpty()
            ) {
                return@doOnTextChanged
            }

            Utils.hideKeyboard(context as Activity)
            if (binding.etOtp1.text.toString()
                + binding.etOtp2.text.toString()
                + binding.etOtp3.text.toString()
                + binding.etOtp4.text.toString()
                == "1234"
            ) {
                onVerifiedSuccessfully()
            } else {
                updateInvalidState(true)
            }
        }

        binding.etOtp4.setOnKeyListener { v, keyCode, event ->
            event?.let {
                if (it.action == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DEL || keyCode.isNumeric()) {
                        updateInvalidState(false)
                    }
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (binding.etOtp4.text.isNullOrEmpty()) {
                            binding.etOtp3.requestFocus()
                            return@setOnKeyListener true
                        }
                    } else {
                        if (keyCode.isNumeric() && !binding.etOtp4.text.isNullOrEmpty()) {
                            binding.etOtp4.text?.clear()
                        }
                    }
                }
            }
            return@setOnKeyListener false
        }

    }

    private fun updateInvalidState(isInvalid: Boolean) {
        val background =
            if (isInvalid) R.drawable.bg_item_otp_view_error else R.drawable.bg_item_otp_view
        binding.etOtp1.background = ContextCompat.getDrawable(context!!, background)
        binding.etOtp2.background = ContextCompat.getDrawable(context!!, background)
        binding.etOtp3.background = ContextCompat.getDrawable(context!!, background)
        binding.etOtp4.background = ContextCompat.getDrawable(context!!, background)
        binding.tvInvalidOtp.visibility = if (isInvalid) View.VISIBLE else View.GONE
    }

    fun onVerifiedSuccessfully() {
        dialog?.cancel()
        Toast.makeText(context!!, "OTP Verified Successfully.", Toast.LENGTH_LONG).show()
    }

}