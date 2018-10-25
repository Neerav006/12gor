package com.bargor.samaj.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bargor.samaj.R
import com.bargor.samaj.Utils.Utils
import com.bargor.samaj.common.RetrofitClient
import com.bargor.samaj.cons.Constants
import com.bargor.samaj.model.MyTeamList
import kotlinx.android.synthetic.main.fragment_team_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class TeamListFragment:Fragment() {

    private lateinit var myTeamListApi: MyTeamListApi
    private var id = ""
    private var teamList:ArrayList<MyTeamList> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myTeamListApi = RetrofitClient.getClient(Constants.BASE_URL).create(MyTeamListApi::class.java)

        val sharedPreferences = Utils.getSharedPreference(Constants.MY_PREF, activity!!)
        id = sharedPreferences.getString(Constants.ID, "")!!


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_list,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvList.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)

        myTeamListApi.getMyTeamList(id).enqueue(
                object :Callback<List<MyTeamList>>{
                    override fun onFailure(call: Call<List<MyTeamList>>, t: Throwable) {
                          progressBar.visibility = View.GONE
                         Toast.makeText(activity,"Error occurred",Toast.LENGTH_LONG).show()


                    }

                    override fun onResponse(call: Call<List<MyTeamList>>, response: Response<List<MyTeamList>>) {
                        progressBar.visibility = View.GONE

                        if (response!!.isSuccessful){

                            if (response.body()!!.isNotEmpty()){
                                teamList = response.body() as ArrayList<MyTeamList>
                                rvList.adapter = CustomAdapter(teamList)



                            }
                            else{
                                Toast.makeText(activity,"No team exists",Toast.LENGTH_LONG).show()


                            }



                        }
                        else{
                            Toast.makeText(activity,"Error occurred",Toast.LENGTH_LONG).show()

                        }


                    }


                }
        )




    }

    interface MyTeamListApi{
        @POST("khelmahotsav/TeamListApi")
        @FormUrlEncoded
        fun getMyTeamList(@Field("id") id:String):Call<List<MyTeamList>>

    }


  inner  class CustomAdapter(private val dataSet: ArrayList<MyTeamList>) :
            RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

        /**
         * Provide a reference to the type of views that you are using (custom ViewHolder)
         */
      inner  class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val tvMemNo: TextView
            val tvMemberName: TextView
            val tvSize: TextView
            val ivRemove: ImageView
            val tvMobile: TextView
            val tvRole: TextView

            init {
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener {

                    val fragment = TeamDetailFragment()
                    val bundle = Bundle()
                    bundle.putString("id",dataSet[adapterPosition].id)
                    bundle.putString("game",dataSet[adapterPosition].gameName)
                    bundle.putString("team",dataSet[adapterPosition].teamName)
                    fragment.arguments = bundle

                    fragmentManager!!.beginTransaction()
                            .add(R.id.container_my_team,fragment)
                            .hide(this@TeamListFragment)
                            .addToBackStack(null)
                            .commit()


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

            viewHolder.tvMemNo.text = "Team Name: ".plus(dataSet[position].teamName)
            viewHolder.tvSize.text = dataSet[position].gameName
            viewHolder.tvRole.text = "Caption:".plus(dataSet[position].capName)


            // Get element from your dataset at this position and replace the contents of the view
            // with that element

        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = dataSet.size


    }





}