package ninja.knowone.apod

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_apod_main.*
import java.text.SimpleDateFormat
import java.util.*

class APODMainFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_apod_main, container, false)
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
    }

    private fun setTheUi(date: String = "") {
        CallingNasa(requireActivity()).picSnag(date) { myThing ->
            when {
                myThing.has("hdurl") -> {
                    Glide.with(this).load(myThing.getString("url")).into(ivMain)
                }
                myThing.get("media_type") == "video" -> {
                    Toast.makeText(activity, "Video is not supported :(", Toast.LENGTH_LONG).show()
                }
                else -> Glide.with(this).load(context?.getDrawable(R.drawable.no_vid)).into(ivMain)
            }
            if (myThing.has("explanation")) {
                tvMain.text = myThing.getString("explanation")
            } else {
                tvMain.text = R.string.no_explanation.toString()
            }
        }
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
}