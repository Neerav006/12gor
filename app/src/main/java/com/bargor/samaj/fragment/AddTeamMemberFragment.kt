package com.bargor.samaj.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatSpinner
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.bargor.samaj.R
import com.bargor.samaj.Utils.Utils
import com.bargor.samaj.common.RetrofitClient
import com.bargor.samaj.cons.Constants
import com.bargor.samaj.model.AllMember
import com.bargor.samaj.model.Memberlist
import com.bargor.samaj.model.VillageList
import kotlinx.android.synthetic.main.fragment_add_team_member.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.ArrayList

class AddTeamMemberFragment : Fragment() {

    var contxt: Context? = null
    private var memberlistArrayList: ArrayList<Memberlist>? = null
    private var searchMember: SearchMember? = null
    private var gor: String? = null

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
        searchMember = getSearchedMember(Constants.BASE_URL)
        memberlistArrayList = ArrayList()
        val sharedPreferences = Utils.getSharedPreference(Constants.MY_PREF, contxt)
        gor = sharedPreferences.getString(Constants.GOR, null)
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_team_member, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvList.layoutManager = LinearLayoutManager(contxt, LinearLayoutManager.VERTICAL, false)

        btnSearch.setOnClickListener {


            if (edtMemberNo.text.toString().trim().isNotEmpty()) {
                searchMember!!.getMemberDetail("1", edtMemberNo.text.toString().trim(), gor!!)
                        .enqueue(object : Callback<AllMember> {
                            override fun onResponse(call: Call<AllMember>, response: Response<AllMember>) {
                                btnSearch.isEnabled = true
                                progressBar.visibility = View.GONE
                                if (response.isSuccessful) {
                                    memberlistArrayList = response.body()!!.memberlist as ArrayList<Memberlist>

                                    if (memberlistArrayList!!.size > 0) {

                                        showPayDialog(memberlistArrayList!![0].mId,
                                                memberlistArrayList!![0].name)

                                    } else {

                                    }
                                }

                            }

                            override fun onFailure(call: Call<AllMember>, t: Throwable) {
                                btnSearch.isEnabled = true
                                progressBar.visibility = View.GONE

                            }
                        })

            }


        }


    }

    override fun onPause() {
        super.onPause()
    }


    //------------------API--------------------
    internal interface SearchMember {

        @POST("member/searchdetailsapi/")
        @FormUrlEncoded
        fun getMemberDetail(@Field("type") type: String, @Field("search") search: String, @Field("gor") gor: String): Call<AllMember>


    }

    internal fun getSearchedMember(baseUrl: String): SearchMember {
        return RetrofitClient.getClient(baseUrl).create(SearchMember::class.java)
    }


    //-------------------------------------------------------


    //-----------------------DIALOG-------------------------------


    fun showPayDialog(no: String, name: String) {

        val mDialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_add_team_member, null)

        val mBuilder = AlertDialog.Builder(activity!!)
                .setView(mDialogView)
                .setTitle("Add Player")


        val tvMemNo = mDialogView.findViewById<TextView>(R.id.tvMemNo)
        val tvMemberName = mDialogView.findViewById<TextView>(R.id.tvMemberName)
        val btnAdd = mDialogView.findViewById<Button>(R.id.btnAddMember)
        val spSize = mDialogView.findViewById<AppCompatSpinner>(R.id.spSize)

        tvMemNo.text = "Mem No: ".plus(no)
        tvMemberName.text = name


        val mAlertDialog = mBuilder.show()

        btnAdd.setOnClickListener {
           // TODO add in list

        }


    }


}

