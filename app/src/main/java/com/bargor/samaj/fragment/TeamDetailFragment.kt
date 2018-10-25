package com.bargor.samaj.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bargor.samaj.R
import com.bargor.samaj.common.RetrofitClient
import com.bargor.samaj.cons.Constants
import com.bargor.samaj.model.TeamDetailList
import kotlinx.android.synthetic.main.fragment_team_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class TeamDetailFragment : Fragment() {

    private lateinit var getTeamDetailApi: GetTeamDetailApi
    private var id: String? = null
    private var game: String? = null
    private var team: String? = null
    private var playerList: ArrayList<TeamDetailList> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            id = arguments!!.getString("id")
            game = arguments!!.getString("game")
            team = arguments!!.getString("team")
        }


        getTeamDetailApi = RetrofitClient.getClient(Constants.BASE_URL).create(GetTeamDetailApi::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvGameName.text = game
        tvTeamName.text = team

        rvList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        getTeamDetailApi.getteamDetails(id!!).enqueue(
                object : Callback<List<TeamDetailList>> {
                    override fun onFailure(call: Call<List<TeamDetailList>>, t: Throwable) {
                        progressBar.visibility = View.GONE
                        Toast.makeText(activity, "Error occurred", Toast.LENGTH_LONG).show()


                    }

                    override fun onResponse(call: Call<List<TeamDetailList>>, response: Response<List<TeamDetailList>>) {
                        progressBar.visibility = View.GONE

                        if (response.isSuccessful) {

                            playerList = response.body() as ArrayList<TeamDetailList>
                            rvList.adapter = CustomAdapter(playerList)


                        } else {
                            Toast.makeText(activity, "Error occurred", Toast.LENGTH_LONG).show()


                        }


                    }


                }
        )


    }


    interface GetTeamDetailApi {
        @POST("khelmahotsav/teamMemberList")
        @FormUrlEncoded
        fun getteamDetails(@Field("id") id: String): Call<List<TeamDetailList>>

    }


    inner class CustomAdapter(private val dataSet: ArrayList<TeamDetailList>) :
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
            val tvRole: TextView

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

                ivRemove.visibility = View.GONE

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

            viewHolder.tvMemNo.text = "Mem No:".plus(dataSet[position].memberId)
            viewHolder.tvSize.text = "T Shirt Size: ".plus(dataSet[position].tshirtSize)
            viewHolder.tvMemberName.text = dataSet[position].palyerName

            // viewHolder.tvRole.text = "Caption:".plus(dataSet[position].capName)


            // Get element from your dataset at this position and replace the contents of the view
            // with that element

        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = dataSet.size


    }


}