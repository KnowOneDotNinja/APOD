package ninja.knowone.apod

import android.app.Activity
import android.os.AsyncTask
import android.support.annotation.MainThread
import android.widget.TextView
import android.widget.Toast
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class CallingNasa(private val activity: Activity) {

    fun picSnag(addyPasser: (String) -> Unit): String {
        val client = OkHttpClient()
        val url = "https://api.nasa.gov/planetary/apod?api_key=${R.string.api_key}&hd=true"
        val request: Request = Request.Builder().url(url).build()

        AsyncTask.execute {

            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                activity.runOnUiThread {
                    //val myText= response.body().toString()
                    val myThing = JSONObject(response.body().toString())
                    val addy = myThing.getString("hdurl")
                    addyPasser(addy)
                }
            } else {
                Toast.makeText(activity, "Response Unsuccessful", Toast.LENGTH_LONG).show()
            }
        }

        //val myThing: JSONObject =

    }
}