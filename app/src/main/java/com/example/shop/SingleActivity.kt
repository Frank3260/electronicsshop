package com.example.shop

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONObject

class SingleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)

        val prefs : SharedPreferences = this.getSharedPreferences("shop",Context.MODE_PRIVATE)

        val prodname = findViewById(R.id.p_name)as TextView
        val prodesk = findViewById(R.id.p_desc) as TextView
        val prodcost = findViewById(R.id.p_cost) as TextView
        val img = findViewById(R.id.img_url ) as ImageView

        val flashname = prefs.getString("prod_name","")
        val flashdesc = prefs.getString("prod_desc","")
        val flashcost = prefs.getString("prod_cost","")
        val flashimg = prefs.getString("img_url","")

        prodname.text = flashname
        prodesk.text = flashdesc
        prodcost.text = flashcost
        Glide.with(applicationContext).load(flashimg)
            .apply(RequestOptions().centerCrop())
            .into(img)



    val progressbar = findViewById(R.id.progressbar)as ProgressBar
    progressbar.visibility =View.GONE
    val phone = findViewById<EditText>(R.id.phone)
    val pay = findViewById<Button>(R.id.pay)

    pay.setOnClickListener {
        progressbar.visibility = View.GONE
        val client = AsyncHttpClient(true,88,443)
        val json = JSONObject()
        json.put("amount","1")
        json.put("phone",phone.text.toString())
        val body = StringEntity(json.toString())
        val post= client.post(
            this,
            "https://modcom.pythonanywhere.com/mpesa_payment",
            body,
            "application/json",
            object : JsonHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONObject?
                ) {
                    Toast.makeText(applicationContext, "paid Succesfully", Toast.LENGTH_LONG).show()
                    progressbar.visibility = View.GONE
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    throwable: Throwable?,
                    errorResponse: JSONObject?

                ) {
                    Toast.makeText(applicationContext, "Error During payment", Toast.LENGTH_LONG)
                        .show()
                    progressbar.visibility = View.GONE
                }


            }
        )
    }
    }
}