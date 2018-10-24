package com.bargor.samaj.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bargor.samaj.R

class AddTeamMemberFragment :Fragment() {

    var contxt: Context?=null

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        this.contxt = activity?.applicationContext
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.contxt = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_team_member,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
    }
}