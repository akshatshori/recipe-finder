package com.example.a20201108

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.a20201108.model.Hit
import com.example.a20201108.model.Recipe
import com.example.a20201108.model.edamam
import kotlinx.android.synthetic.main.activity_main.*

//https://api.edamam.com/search?q=' + ingredients + '&app_id=b8fa8ec0&app_key=2e99e135530eaed01cb9620b24c1f1c0'

class MainActivity : AppCompatActivity() {


private val dataList: MutableList<Hit> = mutableListOf()
    private lateinit var  myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //get data from intent
        val intent = intent
        val userinput = intent.getStringExtra("user_input")

          myAdapter = MyAdapter(dataList)


           my_recycler_view.layoutManager = LinearLayoutManager(this)
           my_recycler_view.addItemDecoration(DividerItemDecoration( this, OrientationHelper.VERTICAL))
        my_recycler_view.adapter = myAdapter
        //setup android networking
        AndroidNetworking.get("https://api.edamam.com/search?q="+userinput+"&app_id=680d684a&app_key=7632b30ad84aedab1ea687e8dbb8762e&to=20")
            .build()
            .getAsObject(edamam::class.java, object : ParsedRequestListener<edamam> {
                override fun onResponse(response: edamam) {

                    dataList.addAll(response.hits)
                    myAdapter.notifyDataSetChanged()
                }

                override fun onError(anError: ANError?) {
                    TODO("Not yet implemented")
                }

            })

    }
}