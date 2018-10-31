package com.bargor.samaj.fragment

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatSpinner
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.*
import com.bargor.samaj.R
import com.bargor.samaj.Utils.Utils
import com.bargor.samaj.common.RetrofitClient
import com.bargor.samaj.cons.Constants
import com.bargor.samaj.cons.Constants.WRITE_EXTERNAL_STORAGE
import com.bargor.samaj.model.*
import com.google.gson.Gson
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfLabColor
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.android.synthetic.main.fragment_team_detail.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class TeamDetailFragment : Fragment() {

    private lateinit var getTeamDetailApi: GetTeamDetailApi
    private var id: String? = null
    private var game: String? = null
    private var team: String? = null
    private var game_id: String? = null
    private var cap_id: String? = null
    private var playerList: ArrayList<TeamDetailList> = ArrayList()
    private val PDF_DIRECTORY = "/12Gor"
    private val FILE_NAME = "report"
    private var captain: String? = null
    private var isEditMode = false
    private var t_shirt_size: String? = null
    private var team_size: String? = null
    private var reg_date: String? = null
    private var memberlistArrayList: java.util.ArrayList<Memberlist>? = null
    private var selectedPlayerList: java.util.ArrayList<Memberlist> = java.util.ArrayList()
    private var searchMember: SearchMember? = null
    private lateinit var addTeamPlayersapi: AddTeamPlayersapi
    private lateinit var progressDialog: ProgressDialog
    private var gor: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        searchMember = getSearchedMember(Constants.BASE_URL)
        memberlistArrayList = java.util.ArrayList()
        val sharedPreferences = Utils.getSharedPreference(Constants.MY_PREF, activity)
        gor = sharedPreferences.getString(Constants.GOR, null)
        if (arguments != null) {
            id = arguments!!.getString("id")
            game = arguments!!.getString("game")
            team = arguments!!.getString("team")
            captain = arguments!!.getString("captain")
            game_id = arguments!!.getString("game_id")
            cap_id = arguments!!.getString("member_id")
            team_size = arguments!!.getString("team_size")
            t_shirt_size = arguments!!.getString("t_size")
            reg_date = arguments!!.getString("reg_date")
        }




        addTeamPlayersapi = RetrofitClient.getClient(Constants.BASE_URL).create(AddTeamPlayersapi::class.java)

        getTeamDetailApi = RetrofitClient.getClient(Constants.BASE_URL).create(GetTeamDetailApi::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_team_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvGameName.text = game
        tvTeamName.text = team

        ivAddTeamPlayer.setOnClickListener {

            if (edtMemberNo.text.toString().trim().isNotEmpty()) {

                progressBar.visibility = View.VISIBLE

                if (edtMemberNo.text.toString().trim().isNotEmpty()) {
                    searchMember!!.getMemberDetail("1", edtMemberNo.text.toString().trim(), gor!!)
                            .enqueue(object : Callback<AllMember> {
                                override fun onResponse(call: Call<AllMember>, response: Response<AllMember>) {

                                    progressBar.visibility = View.GONE
                                    if (response.isSuccessful) {
                                        memberlistArrayList = response.body()!!.memberlist as java.util.ArrayList<Memberlist>

                                        if (memberlistArrayList!!.size > 0) {

                                            val teamPlayer = TeamDetailList()
                                            teamPlayer.memberId = memberlistArrayList!![0].id
                                            teamPlayer.palyerName = memberlistArrayList!![0].name






                                            showSizeDialog(teamPlayer, memberlistArrayList!![0].id,
                                                    memberlistArrayList!![0].name)

                                        } else {

                                        }
                                    }

                                }

                                override fun onFailure(call: Call<AllMember>, t: Throwable) {

                                    progressBar.visibility = View.GONE

                                }
                            })

                }


            }
        }


        btnEditTeam.setOnClickListener {

            if (playerList.size.toInt() < team_size!!.toInt()) {

                // add player api

                val addTeamPlayer = AddTeamPlayer()
                addTeamPlayer.cId = cap_id
                addTeamPlayer.cName = captain
                addTeamPlayer.cSize = t_shirt_size
                addTeamPlayer.teamId = id

                val midList = java.util.ArrayList<MId>()



                for (items in playerList) {

                    if (!items.isCaptain) {

                        val mid = MId()
                        mid.id = items.memberId
                        mid.name = items.palyerName
                        mid.size = items.size
                        midList.add(mid)
                    }

                }

                addTeamPlayer.mId = midList

                Log.e("player list", Gson().toJson(addTeamPlayer))

                showProgressDialog()

                addTeamPlayersapi.addPlayer(addTeamPlayer).enqueue(
                        object : Callback<MyRes> {
                            override fun onFailure(call: Call<MyRes>, t: Throwable) {

                                if (activity != null && progressDialog.isShowing)
                                    progressDialog.dismiss()

                                Toast.makeText(activity, "Error occurred", Toast.LENGTH_LONG).show()

                            }

                            override fun onResponse(call: Call<MyRes>, response: Response<MyRes>) {
                                if (activity != null && progressDialog.isShowing)
                                    progressDialog.dismiss()


                                if (response!!.isSuccessful) {

                                    if (response.body()!!.msg.equals("true", true)) {

                                        Toast.makeText(activity, "Successfully Team added..", Toast.LENGTH_LONG).show()
                                        activity?.finish()


                                    } else {
                                        Toast.makeText(activity, "Error occurred", Toast.LENGTH_LONG).show()

                                    }


                                } else {
                                    Toast.makeText(activity, "Error occurred", Toast.LENGTH_LONG).show()

                                }

                            }


                        }
                )


            } else {
                Toast.makeText(activity, "Total Player must be less or equal to ${team_size}", Toast.LENGTH_LONG).show()

            }


        }



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

                            tvRemainingCounter.text = "Remaining: ${team_size.toString().toInt() - playerList.size}"

                            for (items in playerList) {
                                if (items.palyerName == captain) {
                                    items.palyerName = captain.plus(" (C) ")
                                    break
                                }
                            }

                            for (items in playerList) {
                                if (items.memberId == cap_id) {
                                    items.isCaptain = true
                                    break
                                }
                            }



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

                if (isEditMode) {
                    ivRemove.visibility = View.VISIBLE

                    ivRemove.setOnClickListener {

                        dataSet.remove(dataSet[adapterPosition])
                        notifyItemRemoved(adapterPosition)
                        notifyDataSetChanged()
                        tvRemainingCounter.text = "Remaining: ${team_size.toString().toInt() - playerList.size}"


                    }
                } else {
                    ivRemove.visibility = View.GONE
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

            viewHolder.tvMemNo.text = "Mem No:".plus(dataSet[position].memberId)
            viewHolder.tvSize.text = "T Shirt Size: ".plus(dataSet[position].size)
            viewHolder.tvMemberName.text = dataSet[position].palyerName

            if (isEditMode && dataSet[position].isCaptain) {
                viewHolder.ivRemove.visibility = View.GONE
            } else if (isEditMode && !dataSet[position].isCaptain) {
                viewHolder.ivRemove.visibility = View.VISIBLE
            } else {
                viewHolder.ivRemove.visibility = View.GONE
            }

            // viewHolder.tvRole.text = "Caption:".plus(dataSet[position].capName)


            // Get element from your dataset at this position and replace the contents of the view
            // with that element

        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = dataSet.size


    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.menu_pdf_report_view, menu)

        val item = menu?.findItem(R.id.myswitch)

        val actionView = item?.actionView as RelativeLayout

        val switch = actionView.findViewById<Switch>(R.id.switchForActionBar)

        switch.setOnCheckedChangeListener { buttonView, isChecked ->

            isEditMode = isChecked

            if (isChecked) {

                btnEditTeam.visibility = View.VISIBLE
                ivAddTeamPlayer.visibility = View.VISIBLE
                txtInMemberNo.visibility = View.VISIBLE
                rvList.adapter = CustomAdapter(playerList)


            } else {
                txtInMemberNo.visibility = View.GONE
                btnEditTeam.visibility = View.GONE
                ivAddTeamPlayer.visibility = View.GONE
                rvList.adapter = CustomAdapter(playerList)


            }


        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item!!.itemId == R.id.action_menu_pdf_view) {

            if (playerList.isNotEmpty()) {
                requestWriteExternalPermission()
                return true
            }

        }

        return false
    }


    @AfterPermissionGranted(WRITE_EXTERNAL_STORAGE)
    fun requestWriteExternalPermission() {

        val perms = arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (EasyPermissions.hasPermissions(activity!!, *perms)) {

            generateReportPdf(team!!, game!!)

        } else {
            EasyPermissions.requestPermissions(
                    PermissionRequest.Builder(activity!!, WRITE_EXTERNAL_STORAGE, *perms)
                            .setPositiveButtonText("Ok")
                            .setNegativeButtonText("cancel")
                            .setRationale("Storage permission required..")
                            .setTheme(R.style.AppTheme)
                            .build()
            )
        }

    }


    // --------------  Generate PDF -------------------

    fun generateReportPdf(teamName: String, gameName: String) {


        val file = getOutputMediaFile()

        if (file != null) {

            val document = Document(PageSize.A4)

            try {
                PdfWriter.getInstance(document, FileOutputStream(file.absolutePath))

                document.open()

                val rect = Rectangle(10f, 10f, 585f, 832f)


                rect.borderColor = BaseColor.BLACK
                rect.borderWidth = 2f

                rect.border = Rectangle.BOX

                val paragraph = Paragraph()
                val fntSize: Float
                val lineSpacing: Float
                fntSize = 10f
                lineSpacing = 30f

                paragraph.spacingBefore = 20f
                paragraph.spacingAfter = 20f
                paragraph.alignment = Element.ALIGN_CENTER
                paragraph.add(Phrase(
                        lineSpacing, "12 GOR KADVA PATIDAR SAMAJ\n",
                        FontFactory.getFont(FontFactory.TIMES_BOLD, 20f)
                ))
                paragraph.add(Chunk("\n"))
                paragraph.add(Phrase(
                        lineSpacing, "Daramali, Sabarkantha, Gujarat\n",
                        FontFactory.getFont(FontFactory.TIMES_ROMAN, 10f)
                ))


                try {
                    // get input stream
                    val stream = ByteArrayOutputStream()
                    val ims = context!!.getAssets().open("umiyama.png")
                    val bmp = BitmapFactory.decodeStream(ims)

                    bmp.compress(Bitmap.CompressFormat.PNG, 50, stream)


                    val headerTable = PdfPTable(2)
                    headerTable.widthPercentage = 100f
                    headerTable.setWidths(intArrayOf(1, 4))
                    headerTable.defaultCell.fixedHeight = 100f
                    headerTable.defaultCell.border = Rectangle.NO_BORDER
                    headerTable.defaultCell.isUseAscender = true
                    headerTable.defaultCell.verticalAlignment = Element.ALIGN_MIDDLE
                    headerTable.defaultCell.paddingTop = 5f
                    headerTable.defaultCell.paddingBottom = 5f

                    val i = Image.getInstance(stream.toByteArray())
                    i.scalePercent(100f)
                    headerTable.addCell(i)

                    headerTable.addCell(paragraph)

                    document.add(headerTable)


                } catch (ex: IOException) {

                }


//                val teamNameParagraph = Paragraph()
//                teamNameParagraph.spacingAfter = 10f
//                teamNameParagraph.add(Phrase(
//                        lineSpacing, "Team Name:-    ".plus(teamName.toUpperCase()),
//                        FontFactory.getFont(FontFactory.TIMES_BOLD, 10f)
//                ))
//
//
//                val gameNameParagraph = Paragraph()
//                gameNameParagraph.spacingAfter = 10f
//                gameNameParagraph.add(Phrase(
//                        lineSpacing, "Game Name:-    ".plus(gameName.toUpperCase()),
//                        FontFactory.getFont(FontFactory.TIMES_BOLD, 10f)
//                ))

                val teamDetailHeader = Paragraph()
                teamDetailHeader.spacingAfter = 10f
                teamDetailHeader.spacingBefore = 10f
                teamDetailHeader.alignment = Element.ALIGN_CENTER
                teamDetailHeader.add(Phrase(
                        lineSpacing, "Team Detail",
                        FontFactory.getFont(FontFactory.TIMES_BOLD, 15f)
                ))


                val titleHeader = PdfPTable(5)
                titleHeader.widthPercentage = 100f
                titleHeader.setWidths(intArrayOf(1, 2, 1, 2,1))
                titleHeader.horizontalAlignment = Element.ALIGN_CENTER
                titleHeader.defaultCell.horizontalAlignment = Element.ALIGN_CENTER
                titleHeader.defaultCell.paddingTop = 5f
                titleHeader.defaultCell.paddingBottom = 5f
                titleHeader.addCell(
                        Phrase(
                                lineSpacing, "Team No",
                                FontFactory.getFont(FontFactory.TIMES_ROMAN, 10f)
                        )
                )

                titleHeader.addCell(
                        Phrase(
                                lineSpacing, "Team Name",
                                FontFactory.getFont(FontFactory.TIMES_ROMAN, 10f)
                        )
                )

                titleHeader.addCell(
                        Phrase(
                                lineSpacing, "Game Name",
                                FontFactory.getFont(FontFactory.TIMES_ROMAN, 10f)
                        )
                )

                titleHeader.addCell(
                        Phrase(
                                lineSpacing, "Registration Date",
                                FontFactory.getFont(FontFactory.TIMES_ROMAN, 10f)
                        )
                )



                titleHeader.addCell(
                        Phrase(
                                lineSpacing, "Team Size",
                                FontFactory.getFont(FontFactory.TIMES_ROMAN, 10f)
                        )
                )


                val titleValue = PdfPTable(5)
                titleValue.widthPercentage = 100f
                titleValue.setWidths(intArrayOf(1,2, 1, 2, 1))
                titleValue.horizontalAlignment = Element.ALIGN_CENTER
                titleValue.defaultCell.horizontalAlignment = Element.ALIGN_CENTER
                titleValue.defaultCell.paddingTop = 5f
                titleValue.defaultCell.paddingBottom = 5f

                titleValue.addCell(
                        Phrase(
                                lineSpacing,id ,
                                FontFactory.getFont(FontFactory.TIMES_BOLD, 10f)
                        )
                )

                titleValue.addCell(
                        Phrase(
                                lineSpacing, teamName,
                                FontFactory.getFont(FontFactory.TIMES_BOLD, 10f)
                        )
                )

                titleValue.addCell(
                        Phrase(
                                lineSpacing, gameName,
                                FontFactory.getFont(FontFactory.TIMES_BOLD, 10f)
                        )
                )

                titleValue.addCell(
                        Phrase(
                                lineSpacing, reg_date,
                                FontFactory.getFont(FontFactory.TIMES_BOLD, 10f)
                        )
                )



                titleValue.addCell(
                        Phrase(
                                lineSpacing, team_size,
                                FontFactory.getFont(FontFactory.TIMES_BOLD, 10f)
                        )
                )


                val playerHeader = Paragraph()
                playerHeader.spacingAfter = 10f
                playerHeader.spacingBefore = 10f
                playerHeader.alignment = Element.ALIGN_CENTER
                playerHeader.add(Phrase(
                        lineSpacing, "Player Detail",
                        FontFactory.getFont(FontFactory.TIMES_BOLD, 15f)
                ))


                val theader = PdfPTable(5)
                theader.widthPercentage = 100f
                theader.setWidths(intArrayOf(1, 1, 3, 2, 2))
                theader.horizontalAlignment = Element.ALIGN_CENTER
                theader.defaultCell.horizontalAlignment = Element.ALIGN_CENTER
                theader.defaultCell.paddingTop = 5f
                theader.defaultCell.paddingBottom = 5f


                theader.addCell(
                        Phrase(
                                lineSpacing, "Sr No",
                                FontFactory.getFont(FontFactory.TIMES_ROMAN, 10f)
                        )
                )

                theader.addCell(
                        Phrase(
                                lineSpacing, "Mem No",
                                FontFactory.getFont(FontFactory.TIMES_ROMAN, 10f)
                        )
                )

                theader.addCell(
                        Phrase(
                                lineSpacing, "Name",
                                FontFactory.getFont(FontFactory.TIMES_ROMAN, 10f)
                        )
                )



                theader.addCell(
                        Phrase(
                                lineSpacing, "T-Size",
                                FontFactory.getFont(FontFactory.TIMES_ROMAN, 10f)
                        )
                )

                theader.addCell(
                        Phrase(
                                lineSpacing, "Sign",
                                FontFactory.getFont(FontFactory.TIMES_ROMAN, 10f)
                        )
                )


                document.add(rect)
//                document.add(teamNameParagraph)
//                document.add(gameNameParagraph)
                document.add(teamDetailHeader)
                document.add(titleHeader)
                document.add(titleValue)
                document.add(playerHeader)

                document.add(theader)


                for ((i, items) in playerList.withIndex()) {

                    val playerDetail = PdfPTable(5)
                    playerDetail.widthPercentage = 100f
                    playerDetail.setWidths(intArrayOf(1, 1, 3, 2, 2))
                    playerDetail.horizontalAlignment = Element.ALIGN_CENTER
                    playerDetail.defaultCell.horizontalAlignment = Element.ALIGN_CENTER
                    playerDetail.defaultCell.paddingTop = 8f
                    playerDetail.defaultCell.paddingBottom = 8f

                    playerDetail.addCell(
                            Phrase(
                                    lineSpacing, (i + 1).toString(),
                                    FontFactory.getFont(FontFactory.TIMES_BOLD, 10f)
                            )
                    )

                    playerDetail.addCell(
                            Phrase(
                                    lineSpacing, items.memberId,
                                    FontFactory.getFont(FontFactory.TIMES_BOLD, 10f)
                            )
                    )

                    playerDetail.addCell(
                            Phrase(
                                    lineSpacing, items.palyerName,
                                    FontFactory.getFont(FontFactory.TIMES_BOLD, 10f)
                            )
                    )




                    playerDetail.addCell(
                            Phrase(
                                    lineSpacing, items.size,
                                    FontFactory.getFont(FontFactory.TIMES_BOLD, 10f)
                            )
                    )

                    playerDetail.addCell(
                            Phrase(
                                    lineSpacing, "",
                                    FontFactory.getFont(FontFactory.TIMES_BOLD, 10f)
                            )
                    )


                    document.add(playerDetail)

                }

                val officeUseHeader = Paragraph()
                officeUseHeader.spacingAfter = 10f
                officeUseHeader.spacingBefore = 10f
                officeUseHeader.add(Phrase(
                        lineSpacing, "For office use only",
                        FontFactory.getFont(FontFactory.TIMES_BOLD, 15f)
                ))


                val footer1 = PdfPTable(1)
                footer1.widthPercentage = 100f
                footer1.defaultCell.horizontalAlignment = Element.ALIGN_CENTER
                footer1.defaultCell.paddingTop = 5f
                footer1.defaultCell.paddingBottom = 5f
                footer1.spacingBefore = 20f

                footer1.addCell(
                        Phrase(
                                lineSpacing, "Design & Developed By Codefuel Technology Pvt. Ltd. ",
                                FontFactory.getFont(FontFactory.TIMES_BOLD, 10f)
                        )
                )


                val footer2 = PdfPTable(1)
                footer2.widthPercentage = 100f
                footer2.defaultCell.horizontalAlignment = Element.ALIGN_CENTER
                footer2.defaultCell.paddingTop = 5f
                footer2.defaultCell.paddingBottom = 5f

                footer2.addCell(
                        Phrase(
                                lineSpacing, "Mobile :- 9427745635 E-Mail :- info@codefuelindia.com\n" +
                                "F-1, Ashwamegh City Center, Opp. Medical College, Polytechnic-Gadhoda Road, Motipura, Himmatnagar, Gujarat\n" +
                                "383001",
                                FontFactory.getFont(FontFactory.TIMES_BOLD, 10f)
                        )
                )


                val receiverTable = PdfPTable(3)
                receiverTable.widthPercentage = 100f
                receiverTable.setWidths(intArrayOf(1, 1, 2))
                receiverTable.defaultCell.paddingTop = 15f
                receiverTable.defaultCell.paddingBottom = 15f


                receiverTable.addCell(
                        Phrase(
                                lineSpacing, "Receive Date ____________",
                                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8f)
                        )
                )

                receiverTable.addCell(
                        Phrase(
                                lineSpacing, "Receive Amount ___________",
                                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8f)
                        )
                )

                receiverTable.addCell(
                        Phrase(
                                lineSpacing, "Receiver Sign And Name_________________________",
                                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8f)
                        )
                )


                val gameCoordinate = PdfPTable(2)
                gameCoordinate.widthPercentage = 100f
                gameCoordinate.spacingBefore = 10f
                gameCoordinate.spacingAfter = 10f
                gameCoordinate.setWidths(intArrayOf(1, 1))
                gameCoordinate.defaultCell.paddingTop = 15f
                gameCoordinate.defaultCell.paddingBottom = 15f

                gameCoordinate.addCell(
                        Phrase(
                                lineSpacing, "President Sign ________________________",
                                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8f)
                        )
                )

                gameCoordinate.addCell(
                        Phrase(
                                lineSpacing, "Game Coordinator Sign ________________________________",
                                FontFactory.getFont(FontFactory.TIMES_ROMAN, 8f)
                        )
                )




                document.add(officeUseHeader)
                document.add(receiverTable)
                document.add(gameCoordinate)

                document.add(footer1)
                document.add(footer2)

                document.close()



                try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                    var uri: Uri? = null
                    // So you have to use Provider
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        uri = FileProvider.getUriForFile(activity!!, activity!!.applicationContext.packageName + ".fileprovider", file)

                        // Add in case of if We get Uri from fileProvider.
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    } else {
                        uri = Uri.fromFile(file)
                    }

                    intent.setDataAndType(uri, "application/pdf")
                    startActivity(intent)
                } catch (e: RuntimeException) {

                }


            } catch (e: DocumentException) {

                Log.e("exception", e.message)

            }


        }


    }


    // ------------------ FILE classes ----------------

    private fun getOutputMediaFile(): File? {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        //        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
        //                Environment.DIRECTORY_PICTURES), PDF_DIRECTORY);

        deleteDir(File(Environment.getExternalStorageDirectory(), PDF_DIRECTORY))

        val mediaStorageDir = File(
                Environment.getExternalStorageDirectory(), PDF_DIRECTORY
        )
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(PDF_DIRECTORY, "failed to create directory")
                return null
            }
        }

        // Create a media file name

        var mediaFile: File? = null


        try {
            mediaFile = File.createTempFile(FILE_NAME, ".pdf", mediaStorageDir)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return mediaFile
    }

    fun deleteDir(dir: File): Boolean {
        if (dir.isDirectory) {
            val children = dir.list()
            for (i in children!!.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete()
    }


    //-----------------------DIALOG-------------------------------


    fun showSizeDialog(member: TeamDetailList, no: String, name: String) {

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

            for (items in playerList) {

                if (items.memberId == member.memberId) {
                    isAdded = true
                    break
                }


            }

            if (!isAdded && playerList.size.toInt() < team_size!!.toInt()) {
                member.size = spSize.selectedItem.toString()
                playerList.add(member)
                rvList.adapter = CustomAdapter(playerList)
                tvRemainingCounter.text = "Remaining: ${team_size.toString().toInt() - playerList.size}"


            }

            mAlertDialog.cancel()


        }


    }


    interface AddTeamPlayersapi {
        @POST("khelmahotsav/addTeamMemberApi")
        fun addPlayer(@Body player: AddTeamPlayer): Call<MyRes>

    }


    fun showProgressDialog() {
        progressDialog = ProgressDialog(activity)
        progressDialog.setTitle("Loading..")
        progressDialog.setCancelable(false)
        progressDialog.show()
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

}