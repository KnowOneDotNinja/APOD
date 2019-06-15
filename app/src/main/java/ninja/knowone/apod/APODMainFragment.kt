package ninja.knowone.apod

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.fragment_apod_main.*
import java.text.SimpleDateFormat
import java.util.*

class APODMainFragment: Fragment() {

    lateinit var exoPlayer: ExoPlayer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_apod_main, container, false)
    }

    private fun clickApodDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val formatter = SimpleDateFormat("yyyy/MM/dd")
        val min = formatter.parse("1995/06/16")
        val minLong = min.time

        val dpd = DatePickerDialog(activity, R.style.DialogTheme, DatePickerDialog.OnDateSetListener {
                view,
                year,
                monthOfYear,
                dayOfMonth-> setTheUi("$year-${monthOfYear + 1}-$dayOfMonth")
        }, year, month, day)
        dpd.datePicker.maxDate = System.currentTimeMillis()
        dpd.datePicker.minDate = minLong
        dpd.show()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setTheUi()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnCal.setOnClickListener {
            clickApodDate()
        }
        initializePlayer()
    }

    private fun setTheUi(date: String = "") {
        CallingNasa(requireActivity()).picSnag(date) { myThing ->
            if (myThing.has("hdurl")) {
                Glide.with(this).load(myThing.getString("url")).into(ivMain)
                Glide.with(this).load(myThing.getString("hdurl")).into(ivMain)
            } else if(myThing.get("media_type") == "video") {
                playerView.visibility = VISIBLE
                playVideo(myThing.getString("url"), playerView)
            } else Glide.with(this).load(context?.getDrawable(R.drawable.no_vid)).into(ivMain)
            if (myThing.has("explanation")) {
                tvMain.text = myThing.getString("explanation")
            } else {
                tvMain.text = R.string.no_explanation.toString()
            }
        }
    }

    private fun initializePlayer() {

        val trackSelector = DefaultTrackSelector()
        val loadControl = DefaultLoadControl()
        val renderersFactory = DefaultRenderersFactory(context)

        exoPlayer = ExoPlayerFactory.newSimpleInstance( requireContext(),
            renderersFactory, trackSelector, loadControl)
    }


    fun playVideo(url: String, view: View) {

        context?.let {
            val userAgent = Util.getUserAgent(it, it.getString(R.string.app_name))
            val mediaSource = ProgressiveMediaSource
                .Factory(DefaultDataSourceFactory(it, userAgent))
                .createMediaSource(Uri.parse(url))
            exoPlayer.prepare(mediaSource)
            exoPlayer.playWhenReady = true
        }

    }

}