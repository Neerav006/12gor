package com.bargor.samaj.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatSpinner
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
    private var selectedPlayerList: ArrayList<Memberlist> = ArrayList()
    private var searchMember: SearchMember? = null
    private var gor: String? = null
    private var id: String? = null
    var captainMember: Memberlist? = null

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
        id = sharedPreferences.getString(Constants.ID, "")
        captainMember = arguments?.getParcelable("data")
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_team_member, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // default

        //edtMemberNo.setText(id!!)

        progressBar.visibility = View.VISIBLE


        progressBar.visibility = View.GONE

//        if (edtMemberNo.text.toString().trim().isNotEmpty()) {
//            searchMember!!.getMemberDetail("1", id!!, gor!!)
//                    .enqueue(object : Callback<AllMember> {
//                        override fun onResponse(call: Call<AllMember>, response: Response<AllMember>) {
//                            btnSearch.isEnabled = true
//                            progressBar.visibility = View.GONE
//                            if (response.isSuccessful) {
//                                memberlistArrayList = response.body()!!.memberlist as ArrayList<Memberlist>
//
//                                if (memberlistArrayList!!.size > 0) {
//
//                                    showSizeDialog(memberlistArrayList!![0], memberlistArrayList!![0].id,
//                                            memberlistArrayList!![0].name)
//
//                                } else {
//
//                                }
//                            }
//
//                        }
//
//                        override fun onFailure(call: Call<AllMember>, t: Throwable) {
//                            btnSearch.isEnabled = true
//                            progressBar.visibility = View.GONE
//
//                        }
//                    })
//
//        }


        rvList.layoutManager = LinearLayoutManager(contxt, LinearLayoutManager.VERTICAL, false)


        if (captainMember != null) {

            captainMember!!.isCaptain = true
            selectedPlayerList.add(captainMember!!)
            rvList.adapter = CustomAdapter(selectedPlayerList)

        }
        tvTotalPlayerCount.text = "Total Player: ".plus(selectedPlayerList.size.toString())


        btnSearch.setOnClickListener {

            progressBar.visibility = View.VISIBLE

            if (edtMemberNo.text.toString().trim().isNotEmpty()) {
                searchMember!!.getMemberDetail("1", edtMemberNo.text.toString().trim(), gor!!)
                        .enqueue(object : Callback<AllMember> {
                            override fun onResponse(call: Call<AllMember>, response: Response<AllMember>) {
                                btnSearch.isEnabled = true
                                progressBar.visibility = View.GONE
                                if (response.isSuccessful) {
                                    memberlistArrayList = response.body()!!.memberlist as ArrayList<Memberlist>

                                    if (memberlistArrayList!!.size > 0) {

                                        showSizeDialog(memberlistArrayList!![0], memberlistArrayList!![0].id,
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


    fun showSizeDialog(member: Memberlist, no: String, name: String) {

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
            Utils.hideSoftKeyBoard(edtMemberNo, activity)

            var isAdded = false

            for (items in selectedPlayerList) {

                if (items.id == member.id) {
                    isAdded = true
                    break
                }


            }

            if (!isAdded) {
                member.size = spSize.selectedItem.toString()
                selectedPlayerList.add(member)
                rvList.adapter = CustomAdapter(selectedPlayerList)
                tvTotalPlayerCount.text = "Total Player: ".plus(selectedPlayerList.size.toString())


            }

            mAlertDialog.cancel()


        }


    }

    // ------------------------ adapter ------------------------------------

    /**
     * Provide views to RecyclerView with data from dataSet.
     *
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    inner class CustomAdapter(private val dataSet: ArrayList<Memberlist>) :
            RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

        /**
         * Provide a reference to the type of views that you are using (custom ViewHolder)
         */
        inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val tvMemNo: TextView
            val tvMemberName: TextView
            val tvSize: TextView
            val ivRemove: ImageView
            val tvMobile: TextView
            val tvRole:TextView

            init {
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener {

                }
                tvMemNo = v.findViewById(R.id.tvMemNo)
                tvMemberName = v.findViewById(R.id.tvMemberName)
                tvSize = v.findViewById(R.id.tvSize)
                ivRemove = v.findViewById(R.id.ivRemove)
                tvMobile = v.findViewById(R.id.tvMobile)
                tvRole = v.findViewById(R.id.tvRole)

                ivRemove.setOnClickListener {

                    dataSet.remove(dataSet[adapterPosition])
                    notifyItemRemoved(adapterPosition)
                    notifyDataSetChanged()
                    tvTotalPlayerCount.text = "Total Player: ".plus(selectedPlayerList.size.toString())


                }
            }
        }

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            // Create a new view.
            val v = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.row_add_member_list, viewGroup, false)

            return ViewHolder(v)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

            viewHolder.tvMemNo.text = "Mem No: ".plus(dataSet[position].id)
            viewHolder.tvSize.text = dataSet[position].size
            viewHolder.tvMemberName.text = dataSet[position].name
            viewHolder.tvMobile.text = dataSet[position].mobile

            if (dataSet[position].isCaptain) {
                viewHolder.ivRemove.visibility = View.GONE
                viewHolder.tvRole.text = "Captain"
            } else {
                viewHolder.ivRemove.visibility = View.VISIBLE
                viewHolder.tvRole.text = " "
            }

            // Get element from your dataset at this position and replace the contents of the view
            // with that element

        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = dataSet.size


    }


}

