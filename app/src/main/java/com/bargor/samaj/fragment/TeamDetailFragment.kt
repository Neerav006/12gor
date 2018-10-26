package com.bargor.samaj.fragment

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bargor.samaj.R
import com.bargor.samaj.common.RetrofitClient
import com.bargor.samaj.cons.Constants
import com.bargor.samaj.cons.Constants.WRITE_EXTERNAL_STORAGE
import com.bargor.samaj.model.TeamDetailList
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.android.synthetic.main.fragment_team_detail.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class TeamDetailFragment : Fragment() {

    private lateinit var getTeamDetailApi: GetTeamDetailApi
    private var id: String? = null
    private var game: String? = null
    private var team: String? = null
    private var playerList: ArrayList<TeamDetailList> = ArrayList()
    private val PDF_DIRECTORY = "/12Gor"
    private val FILE_NAME = "report"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.menu_pdf_report_view, menu)


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

                val paragraph = Paragraph()
                val fntSize: Float
                val lineSpacing: Float
                fntSize = 10f
                lineSpacing = 30f

                paragraph.spacingBefore = 20f
                paragraph.spacingAfter = 20f
                paragraph.alignment = Element.ALIGN_CENTER
                paragraph.add(Phrase(
                        lineSpacing, "12 GOR KADVA PATIDAR SAMAJ",
                        FontFactory.getFont(FontFactory.TIMES_BOLD, 20f)
                ))


                val teamNameParagraph = Paragraph()
                teamNameParagraph.spacingAfter = 10f
                teamNameParagraph.add(Phrase(
                        lineSpacing, "Team Name:-    ".plus(teamName.toUpperCase()),
                        FontFactory.getFont(FontFactory.TIMES_BOLD, 10f)
                ))


                val gameNameParagraph = Paragraph()
                gameNameParagraph.spacingAfter = 10f
                gameNameParagraph.add(Phrase(
                        lineSpacing, "Game Name:-    ".plus(gameName.toUpperCase()),
                        FontFactory.getFont(FontFactory.TIMES_BOLD, 10f)
                ))

                val teamDetailHeader = Paragraph()
                teamDetailHeader.spacingAfter = 20f
                teamDetailHeader.spacingBefore = 20f
                teamDetailHeader.add(Phrase(
                        lineSpacing, "Team Detail",
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
                                FontFactory.getFont(FontFactory.TIMES_BOLD, 10f)
                        )
                )

                theader.addCell(
                        Phrase(
                                lineSpacing, "Mem No",
                                FontFactory.getFont(FontFactory.TIMES_BOLD, 10f)
                        )
                )

                theader.addCell(
                        Phrase(
                                lineSpacing, "Name",
                                FontFactory.getFont(FontFactory.TIMES_BOLD, 10f)
                        )
                )



                theader.addCell(
                        Phrase(
                                lineSpacing, "T-Size",
                                FontFactory.getFont(FontFactory.TIMES_BOLD, 10f)
                        )
                )

                theader.addCell(
                        Phrase(
                                lineSpacing, "Sign",
                                FontFactory.getFont(FontFactory.TIMES_BOLD, 10f)
                        )
                )


                document.add(paragraph)
                document.add(teamNameParagraph)
                document.add(gameNameParagraph)
                document.add(teamDetailHeader)
                document.add(theader)


                for ((i, items) in playerList.withIndex()) {

                    val playerDetail = PdfPTable(5)
                    playerDetail.widthPercentage = 100f
                    playerDetail.setWidths(intArrayOf(1, 1, 3, 2 ,2))
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
                                    lineSpacing, items.tshirtSize,
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

}