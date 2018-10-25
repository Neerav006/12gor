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
import com.bargor.samaj.model.Memberlist
import com.bargor.samaj.model.MyRes
import com.bargor.samaj.model.ResGameList
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
    private var c_id: String? = null
    private var c_name: String? = null
    private var c_size: String? = null
    private var captain: Memberlist? = null
    private var game: ResGameList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sendOTPVerify = RetrofitClient.getClient(Constants.BASE_URL).create(SendOTPVerify::class.java)

        if (arguments != null) {

            id = arguments?.getString("id")
            c_id = arguments?.getString("c_id")
            c_name = arguments?.getString("c_name")
            c_size = arguments?.getString("c_size")
            captain = arguments?.getParcelable("data")
            game = arguments?.getParcelable("game")

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

                sendOTPVerify.sendOtp(c_id!!,id!!, edtOtp.text.toString().trim()).enqueue(
                        object : Callback<MyRes> {
                            override fun onFailure(call: Call<MyRes>, t: Throwable) {

                                if (activity != null && progressDialog?.isShowing) {
                                    progressDialog.dismiss()
                                }


                                Toast.makeText(activity, "Error occurred", Toast.LENGTH_LONG).show()


                            }


                            override fun onResponse(call: Call<MyRes>, response: Response<MyRes>) {
                                if (activity != null && progressDialog?.isShowing) {
                                    progressDialog.dismiss()
                                }


                                if (response!!.isSuccessful) {

                                    if (response!!.body()!!.msg.equals("true", true)) {
                                        Toast.makeText(activity, "Successfully Verified..", Toast.LENGTH_LONG).show()

                                        val fragment = AddTeamMemberFragment()
                                        val bundle = Bundle()
                                        bundle.putString("id", id)
                                        bundle.putString("c_id", c_id)
                                        bundle.putString("c_name", c_name)
                                        bundle.putString("c_size", c_size)
                                        bundle.putParcelable("data", captain)
                                        bundle.putParcelable("game", game)
                                        fragment.arguments = bundle



                                        fragmentManager!!.beginTransaction()
                                                .add(R.id.content_activity_ramat, fragment)
                                                .hide(this@CaptainVerifyFragment)
                                                .addToBackStack(null)
                                                .commit()


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
        fun sendOtp(@Field("id") id: String,
                    @Field("t_id") t_id:String,
                    @Field("code") code: String): Call<MyRes>
    }

}