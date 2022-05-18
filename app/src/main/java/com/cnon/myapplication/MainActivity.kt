package com.cnon.myapplication

import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.graphics.alpha
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.cnon.myapplication.R.drawable

import com.cnon.myapplication.databinding.ActivityMainBinding
import org.json.JSONObject
import java.sql.SQLOutput
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




       binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding!!.getRoot()
       setContentView(view)
       // setContentView(R.layout.activity_main)



      //var cityList = arrayOf("Izmir","Tokyo","Sydney","Miami");
       // var cityList = arrayOf("Berlin","London","Paris","Rome");

        val hashMap:HashMap<String,String> = HashMap<String,String>() //define empty hashmap
        hashMap.put("Izmir","311046")
        hashMap.put("Tokyo","1850147")
        hashMap.put("Sydney","2147714")
        hashMap.put("Miami","4164138")
        hashMap.put("Los Angeles","5344994")
        hashMap.put("London","2643743")
        hashMap.put("Berlin","2885657")
        hashMap.put("Dubai","292223")
        hashMap.put("Mumbai","1275339")

        hashMap.keys
        Log.e("Hashmap keys",hashMap.keys.toString())
        var cityList = hashMap.keys.toTypedArray()


        binding!!.spinner.adapter = ArrayAdapter<String>(applicationContext,R.layout.spinnerlistitem,cityList )

        binding!!.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: View?,
                p2: Int,
                p3: Long
            ) {

                getData(hashMap[cityList.get(p2)].toString())
                //   getData(cityList.get(p2))
                //     Log.e("Spinner", cityList.get(p2))
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


        //izmir 311046
 // Tokyo 1850147

        getData("311046")


    }





    fun getData( cityId :String)
    {

        var url ="https://api.openweathermap.org/data/2.5/weather?id="+cityId+"&appid=8ee9d24845c9f9c3a95ed9485fb43da3&units=metric"
        var weatherObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            object : Response.Listener<JSONObject>, Response.ErrorListener {
                @RequiresApi(Build.VERSION_CODES.M)
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
                    var name = response?.getString("name").toString()
                    Log.e("weather",response?.getJSONArray("weather").toString())

                    var main = response?.getJSONObject("main");
                    var weather = response?.getJSONArray("weather");
                    var description = weather?.getJSONObject(0);


                    var temp = main?.getString("temp")
                    //Log.e("Temp",temp.toString())
                    var tempStr = temp.toString()
                    var tempFlt =  tempStr.toFloat()
                    var tempInt = tempFlt.toInt()
                    // Log.e("TempInt",tempInt.toString())

                    binding!!.tvTempt.setText(""+tempInt.toString())

                    var description2 = description?.getString("description").toString()
                    binding!!.tvDescription.setText(""+description2)

                    var cal  = Calendar.getInstance();
                    var dateFormat = SimpleDateFormat("EEEE,MMMM yyyy",Locale("en"));
                    // dateFormat.applyPattern("dd,mm,yy")
                    var Date2 = dateFormat.format(cal.time)

                    // var Date = cal.get(5).toString()+"/"+cal.get(10).toString()+"/"+cal.get(1).toString()
                    binding!!.tvTarih.setText(""+Date2)
                    var icon = description?.getString("icon").toString()
                    Log.e("icon", icon.toString())
                    var imgFileName = resources.getIdentifier("a"+icon,"drawable",packageName)
                    binding!!.imageView.setImageResource(imgFileName)



                    val hashMap:HashMap<String,String> = HashMap<String,String>() //define empty hashmap
                    hashMap.put("Izmir","311046")
                    hashMap.put("Tokyo","1850147")
                    hashMap.put("Sydney","2147714")
                    hashMap.put("Miami","4164138")
                    hashMap.put("Los Angeles","5344994")
                    hashMap.put("London","2643743")
                    hashMap.put("Berlin","2885657")
                    hashMap.put("Dubai","292223")
                    hashMap.put("Mumbai","1275339")

                    hashMap.keys
                    Log.e("Hashmap keys",hashMap.keys.toString())
                    var cityList = hashMap.keys.toTypedArray()


                    if(icon.last() =='d')
                    {


                      binding!!.backGround.background = getDrawable(drawable.day)
                        binding!!.spinner.setPopupBackgroundResource(R.color.dark_yellow)


                       // binding!!.spinner.adapter = ArrayAdapter<String>(applicationContext,R.layout.spinnerlistitem,cityList )


                        //tvSpinner.setBackgroundColor(getColor(R.color.dark_yellow))

                    }
                    else
                    {
                        binding!!.backGround.background = getDrawable(drawable.night)
                        binding!!.spinner.setPopupBackgroundResource(R.color.dark_blue)

                    //    binding!!.spinner.adapter = ArrayAdapter<String>(applicationContext,R.layout.spinnerlistitem,cityList )
                        //binding!!.spinner.background=getDrawable(drawable.night) //getColor(R.color.dark_yellow
                        //tvSpinner.setBackgroundColor(getColor(R.color.dark_blue))
                    }









                }

                override fun onErrorResponse(error: VolleyError?) {
                    TODO("Not yet implemented")
                }

            }, Response.ErrorListener {



            }
        )


        MySingleton.getInstance(applicationContext).addToRequestQueue(weatherObjectRequest)


    }

}