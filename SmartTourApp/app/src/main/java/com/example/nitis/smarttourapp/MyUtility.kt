package com.example.nitis.smarttourapp

import android.content.Context
import org.json.JSONObject
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.util.Log
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.UnknownHostException


class MyUtility {
    companion object {
        // Download JSON data (string) using HTTP Get Request
        fun downloadJSONusingHTTPGetRequest(urlString: String): String? {
            var jsonString: String? = null

            try {
                val url = URL(urlString)

                val httpConnection = url.openConnection() as HttpURLConnection
                if (httpConnection.responseCode == HttpURLConnection.HTTP_OK) {
                    val stream = httpConnection.inputStream
                    jsonString = getStringfromStream(stream)
                }
                httpConnection.disconnect()

            } catch (e1: UnknownHostException) {
                Log.d("MyDebugMsg", "UnknownHostexception in downloadJSONusingHTTPGetRequest")
                e1.printStackTrace()
            } catch (ex: Exception) {
                Log.d("MyDebugMsg", "Exception in downloadJSONusingHTTPGetRequest")
                ex.printStackTrace()
            }

            return jsonString
        }

        // Get a string from an input stream
        private fun getStringfromStream(stream: InputStream?): String? {
            var line: String
            var jsonString: String? = null
            if (stream != null) {
                val reader = BufferedReader(InputStreamReader(stream))
                val out = StringBuilder()
                try {
                    while (true) {
                        line = reader.readLine() ?: break
                        out.append(line)
                    }
                    reader.close()
                    jsonString = out.toString()
                } catch (ex: IOException) {
                    Log.d("MyDebugMsg", "IOException in downloadJSON()")
                    ex.printStackTrace()
                }

            }
            return jsonString
        }

        // Send json data via HTTP POST Request
        fun sendHttPostRequest(urlString: String, param: String): String? {
            var httpConnection: HttpURLConnection? = null
            var json: String? = null
            try {
                val url = URL(urlString)
                httpConnection = url.openConnection() as HttpURLConnection
                if (httpConnection != null) {
                    httpConnection.setDoOutput(true)
                    httpConnection.setChunkedStreamingMode(0)
                    httpConnection.setRequestProperty("Content-Type", "application/json")

                    val out = OutputStreamWriter(httpConnection.outputStream)
                    out.write(param)
                    out.close()
                    Log.i("hello", "hello")
                    Log.i("response code", httpConnection.responseCode.toString())
                    if (httpConnection.responseCode == 200) {
                        val stream = httpConnection.inputStream
                        val reader = BufferedReader(InputStreamReader(stream))
                        var line: String
                        val msg = StringBuilder()
                        while (true) {
                            line = reader.readLine() ?: break
                            msg.append(line)
                            Log.d("MyDebugMsg:PostRequest", line)  // for debugging purpose
                        }

                        json = msg.toString()
                        Log.i("jsonstring", json)
                        reader.close()
                        // Log.d("MyDebugMsg:PostRequest", "POST request returns ok")
                    } else {
                        val stream = httpConnection.inputStream
                        val reader = BufferedReader(InputStreamReader(stream))
                        var line: String
                        val msg = StringBuilder()
                        while (true) {
                            line = reader.readLine() ?: break
                            msg.append(line)
                            Log.d("MyDebugMsg:PostRequest", line)  // for debugging purpose
                        }

                        json = msg.toString()
                        reader.close()
                    }
                    // Log.d("MyDebugMsg:PostRequest", "POST request returns error")
                }
            } catch (ex: Exception) {
                Log.d("MyDebugMsg", "Exception in sendHttpPostRequest")
                ex.printStackTrace()
            } finally {
                if (httpConnection != null)
                    httpConnection.disconnect()
            }
            return json
        }
    }
}