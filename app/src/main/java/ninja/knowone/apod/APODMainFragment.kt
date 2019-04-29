package ninja.knowone.apod

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_apod_main.*

class APODMainFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_apod_main, container, false)
    }

    override fun onAttach(context: Context?) {

        try {
            CallingNasa(requireActivity()).picSnag { myThing ->
                if (myThing.has("url")) {
                    Glide.with(this).load(myThing.getString("url")).into(ivMain)
                } else Glide.with(this).load(context?.getDrawable(android.R.drawable.ic_menu_close_clear_cancel)).into(ivMain)
                if (myThing.has("explanation")) {
                    tvMain.text = myThing.getString("explanation")
                } else {
                    tvMain.text = R.string.no_explanation.toString()
                }
            }
        } catch (e:Exception) {e.printStackTrace()}

        super.onAttach(context)
    }
}