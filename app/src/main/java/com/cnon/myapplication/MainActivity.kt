package com.cnon.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /*
           var request = StringRequest(Request.Method.GET,"https://www.google.com/",
            object : Response.Listener<String>
        {
            override fun onResponse(response: String?) {

            Log.e("Output",response.toString());

            //   Toast.makeText(applicationContext,"Response:"+response,Toast.LENGTH_SHORT)
            }

        }, object : Response.ErrorListener{
            override fun onErrorResponse(error: VolleyError?) {
                TODO("Not yet implemented")
            }
        }
        )
         */

      var url ="https://api.openweathermap.org/data/2.5/weather?id=311046&appid=8ee9d24845c9f9c3a95ed9485fb43da3&units=metric"
        var weatherObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            object : Response.Listener<JSONObject>, Response.ErrorListener {
                override fun onResponse(response: JSONObject?) {
                   Log.e("Output",response.toString());
                    Log.e("main",response?.getJSONObject("main").toString())
                    Log.e("coord",response?.getJSONObject("coord").toString())
                    Log.e("weather",response?.getJSONArray("weather").toString())

                    var sys = response?.getJSONObject("sys")
                    if (sys != null) {
                        Log.e("country",sys.getString("country"))
                    }
                    Log.e("name",response?.getString("name").toString())
                    Log.e("weather",response?.getJSONArray("weather").toString())

                    var weather = response?.getJSONArray("weather");
                    var description = weather?.getJSONObject(0);
                    Log.e("description",description?.getString("description").toString())

                  //  response?.getJSONArray("weather")?.let { Log.e("Output", it.getString(0)) };

             //Log.e("Output",response?.getJSONObject("city").toString());
                }

                override fun onErrorResponse(error: VolleyError?) {
                    TODO("Not yet implemented")
                }

            }, Response.ErrorListener {


            }
        )

       // var url = StringRequest(Request.Method.GET,"https://www.google.com/",

        MySingleton.getInstance(applicationContext).addToRequestQueue(weatherObjectRequest)



    }
}