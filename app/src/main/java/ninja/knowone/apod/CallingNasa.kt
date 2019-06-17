package ninja.knowone.apod

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_apod_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import android.app.DatePickerDialog.OnDateSetListener as OnDateSetListener1

class CallingNasa(private val activity: Activity) {

    private fun hasNetwork(): Boolean? {
        val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting
    }


     fun picSnag(date:String = "", filePasser: (JSONObject) -> Unit) {
         if(hasNetwork() == true) {
            val client = OkHttpClient()
            var url = "https://api.nasa.gov/planetary/apod?api_key=${activity.getString(R.string.api_key)}&hd=true"
            if (!date.isBlank()) {
             url += "&date=$date"
            }
            val request: Request = Request.Builder().url(url).build()

            AsyncTask.execute {

            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val myThing = JSONObject(response.body()?.string())
                activity.runOnUiThread {
                        filePasser(myThing)
                }
            } else {
                activity.runOnUiThread {
                    Glide.with(activity).load(R.drawable.no_network).into(activity.ivMain)
                    activity.tvMain.text = R.string.no_network.toString()
                }
            }
        }
    } else {Toast.makeText(activity, "No network! :(", Toast.LENGTH_LONG).show()}
  }
}