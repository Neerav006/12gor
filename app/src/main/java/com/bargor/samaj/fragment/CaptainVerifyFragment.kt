package com.bargor.samaj.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bargor.samaj.R
import kotlinx.android.synthetic.main.fragment_captain_verify.*

class CaptainVerifyFragment :Fragment() {


    var id:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){

            id = arguments?.getString("id")

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_captain_verify,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSendOTP.setOnClickListener {


            if (edtOtp.text.toString().trim().isNotEmpty() && id!=null){





            }



        }


    }




}