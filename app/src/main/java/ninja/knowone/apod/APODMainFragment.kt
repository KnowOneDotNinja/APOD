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
import java.util.*

class APODMainFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_apod_main, container, false)
    }

    private fun clickApodDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(activity, R.style.DialogTheme, DatePickerDialog.OnDateSetListener {
                view,
                year,
                dayOfMonth,
                monthOfYear -> Toast.makeText(activity, "You chose ${dayOfMonth + 1}-$monthOfYear-$year", Toast.LENGTH_LONG).show()
        }, year, month, day)
        dpd.datePicker.maxDate = System.currentTimeMillis()
        //dpd.datePicker.minDate =
        dpd.show()
    }

    override fun onAttach(context: Context?) {

            try {
                CallingNasa(requireActivity()).picSnag { myThing ->
                    if (myThing.has("hdurl")) {
                        Glide.with(this).load(myThing.getString("url")).into(ivMain)
                        Glide.with(this).load(myThing.getString("hdurl")).into(ivMain)
                    } else Glide.with(this).load(context?.getDrawable(R.drawable.no_vid)).into(ivMain)
                    if (myThing.has("explanation")) {
                        tvMain.text = myThing.getString("explanation")
                    } else {
                        tvMain.text = R.string.no_explanation.toString()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            super.onAttach(context)
        }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnCal.setOnClickListener {
            clickApodDate()
        }
    }
    }