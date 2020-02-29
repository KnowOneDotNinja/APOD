package ninja.knowone.apod

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler

class SplashActivity: AppCompatActivity() {

    private val splashTime = 3000L
    private lateinit var myHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        myHandler = Handler()

        myHandler.postDelayed({ goToMainFragment()
        }, splashTime)
    }

    private fun goToMainFragment() {

        val mainFragmentIntent = Intent(applicationContext, APODMainFragment::class.java)
        startActivity(mainFragmentIntent)
        finish()
    }
}