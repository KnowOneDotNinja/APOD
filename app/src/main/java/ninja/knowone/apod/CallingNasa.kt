package ninja.knowone.apod

import android.app.Activity
import android.os.AsyncTask
import android.widget.Toast
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import android.app.DatePickerDialog.OnDateSetListener as OnDateSetListener1

class CallingNasa(private val activity: Activity) {

     fun picSnag(date:String = "", filePasser: (JSONObject) -> Unit) {
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
                    Toast.makeText(activity, "Response Unsuccessful", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}