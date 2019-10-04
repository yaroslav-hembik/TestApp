package it.hembik.primatest.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import java.net.URL

class ImageLoader(private var completion: (Bitmap) -> Unit): AsyncTask<String, Void, Bitmap>() {
    override fun doInBackground(vararg urls: String): Bitmap? {
        val code = urls[0]

        val url = "https://www.countryflags.io/$code/flat/64.png"
        var bitmap: Bitmap? = null
        try {
            val inputStream = URL(url).openStream()
            bitmap = BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            Log.d("Error", e.stackTrace.toString())

        }

        return bitmap
    }

    override fun onPostExecute(result: Bitmap) {
        completion(result)
    }
}