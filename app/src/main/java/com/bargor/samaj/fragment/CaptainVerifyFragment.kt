package com.bargor.samaj.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bargor.samaj.R
import com.bargor.samaj.common.RetrofitClient
import com.bargor.samaj.cons.Constants
import com.bargor.samaj.model.MyRes
import kotlinx.android.synthetic.main.fragment_captain_verify.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class CaptainVerifyFragment : Fragment() {


    var id: String? = null
    private lateinit var progressDialog: ProgressDialog
    private lateinit var sendOTPVerify: SendOTPVerify


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sendOTPVerify = RetrofitClient.getClient(Constants.BASE_URL).create(SendOTPVerify::class.java)

        if (arguments != null) {

            id = arguments?.getString("id")

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_captain_verify, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSendOTP.setOnClickListener {


            if (edtOtp.text.toString().trim().isNotEmpty() && id != null) {

                showProgressDialog()

                sendOTPVerify.sendOtp(id!!, edtOtp.text.toString().trim()).enqueue(
                        object : Callback<MyRes> {
                            override fun onFailure(call: Call<MyRes>, t: Throwable) {

                                if (activity != null && !progressDialog?.isShowing) {
                                    progressDialog.dismiss()
                                }


                                Toast.makeText(activity, "Error occurred", Toast.LENGTH_LONG).show()


                            }


                            override fun onResponse(call: Call<MyRes>, response: Response<MyRes>) {
                                if (activity != null && !progressDialog?.isShowing) {
                                    progressDialog.dismiss()
                                }


                                if (response!!.isSuccessful) {

                                    if (response!!.body()!!.msg.equals("true", true)) {
                                        Toast.makeText(activity, "Successfully Verified..", Toast.LENGTH_LONG).show()


                                    } else {
                                        Toast.makeText(activity, "Error occurred", Toast.LENGTH_LONG).show()

                                    }

                                } else {

                                    Toast.makeText(activity, "Error occurred", Toast.LENGTH_LONG).show()

                                }


                            }


                        }
                )


            }


        }


    }

    fun showProgressDialog() {
        progressDialog = ProgressDialog(activity)
        progressDialog.setTitle("Loading..")
        progressDialog.setCancelable(false)
        progressDialog.show()
    }


    interface SendOTPVerify {
        @POST("khelmahotsav/verify_team")
        @FormUrlEncoded
        fun sendOtp(@Field("id") id: String, @Field("code") code: String): Call<MyRes>
    }

}