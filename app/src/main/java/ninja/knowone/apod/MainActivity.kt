package ninja.knowone.apod

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            CallingNasa(this).picSnag { myThing ->
                Glide.with(this).load(myThing.getString("hdurl")).into(ivMain)
                tvMain.text = myThing.getString("explanation")
            }
        } catch (e:Exception) {e.printStackTrace()}
    }
}
