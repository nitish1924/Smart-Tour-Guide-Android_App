package com.example.nitis.smarttourapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import android.os.AsyncTask
import java.io.InputStreamReader
import com.example.nitis.smarttourapp.MainActivity.DownloadTask




class MainActivity : AppCompatActivity(){

    inner class DownloadTask : AsyncTask<String, Void, String>() {
        //pass a url as string and get html as string.
        override//some kind of function can be passed but excluding it by calling it void
        fun doInBackground(vararg urls: String): String {
            val json = JSONObject()
            json.put("username", "nitish1924@gmail.com")
            json.put("password","nitish")

            var result="hello"
            var urlConn: HttpURLConnection? = null
            try {
                val url: URL
                val printout: DataOutputStream
                val address = urls[0]
                url = URL(address)
                urlConn = url.openConnection() as HttpURLConnection
                urlConn.doInput = true
                urlConn.doOutput = true
                urlConn.useCaches = false
                urlConn.requestMethod = "POST"
                urlConn.setChunkedStreamingMode(100)
                urlConn.setRequestProperty("Content-Type","application/json")
                urlConn.connect()
                // Send POST output.
                printout = DataOutputStream(urlConn.outputStream)
                val output = URLEncoder.encode(json.toString(), "UTF-8")
                printout.writeUTF(output)
                printout.flush()



                var res = Integer.toString(urlConn.responseCode)
                result=res.toString()
                printout.close()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (urlConn != null)
                    urlConn.disconnect()
            }
            Log.i("response",result)
            return result
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var result = ""
        val username = "nitish1924@gmil.com"
        val password = "nitish"
        val task = DownloadTask()
        signin.setOnClickListener{
            result = task.execute("http://10.0.2.2:3000/signin").get();
        }
        Toast.makeText(this,result,Toast.LENGTH_SHORT).show()


    }

    private fun signIn(username: String, password: String):String {
        //Toast.makeText(this,username,Toast.LENGTH_SHORT).show()


        return "hellllllll"

    }
}
