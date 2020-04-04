package com.example.loginviaotp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.loginviaotp.Utils.getOtp
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

/**
 * BroadcastReceiver to wait for SMS messages. This can be registered either
 * in the AndroidManifest or at runtime.  Should filter Intents on
 * SmsRetriever.SMS_RETRIEVED_ACTION.
 */
class MySMSBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val extras = intent.extras
            val status = extras!![SmsRetriever.EXTRA_STATUS] as Status?
            when (status!!.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    val message = extras[SmsRetriever.EXTRA_SMS_MESSAGE] as String
                    Log.e(TAG, "message: $message")
                    val otp = getOtp(message)
                    Log.e(TAG, "OTP: $otp")
                    otpListener?.onOtpReceived(otp)
                }
                CommonStatusCodes.TIMEOUT -> {
                }
            }
        }
    }

    companion object {
        private val TAG = MySMSBroadcastReceiver::class.java.simpleName

        var otpListener: OtpListener? = null
    }
}

interface OtpListener {
    fun onOtpReceived(otp: String)
}