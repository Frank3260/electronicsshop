package com.example.shop

import adapters.RecyclerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import models.Product
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var productList: ArrayList<Product>
    lateinit var recyclerAdapter: RecyclerAdapter
    lateinit var progress:ProgressBar
    lateinit var  recyclerView: RecyclerView






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView = findViewById(R.id.recycler)
        progress= findViewById(R.id.progress)
        progress.visibility= View.VISIBLE




        val client = AsyncHttpClient(true,80,443)
        recyclerAdapter=RecyclerAdapter(applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)


        client.get(this,"https://modcom.pythonanywhere.com/api/all",
        null,
            "application/json",
            object :JsonHttpResponseHandler(){
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONArray?
                ) {
                    val gson = GsonBuilder().create()
                    val list = gson.fromJson(response.toString(),
                    Array<Product>::class.java).toList()
                    recyclerAdapter.setProductListItems(list)
                    progress.visibility = View.GONE


                }
                //on failure
                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    throwable: Throwable?,
                    errorResponse: JSONArray?
                ) {
                    Toast.makeText(applicationContext, "no products on sale", Toast.LENGTH_LONG).show()
                    progress.visibility = View.GONE
                }


            }

            )

        recyclerView.adapter =recyclerAdapter













    }
}