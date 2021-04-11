package com.example.a20201108

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.a20201108.Fragments.InstructionsFragment
import com.example.a20201108.Fragments.NutritionFragment
import com.example.a20201108.Fragments.ThirdFragment
import com.example.a20201108.adapters.Nutrition
import com.example.a20201108.adapters.ViewPagerAdapter
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main3.*


private const val TAG = "MyActivity"
lateinit var recipeIntstructions: String
lateinit var nutLists: String
lateinit var website: String
class MainActivity3 : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        //collapsing
        val toolbar: Toolbar = toolbar2 as Toolbar

        setSupportActionBar(toolbar)
        val coll_toolbar = toolbar_layout as CollapsingToolbarLayout

        coll_toolbar.setCollapsedTitleTextAppearance(R.style.coll_toolbar_title)
        coll_toolbar.setExpandedTitleTextAppearance(R.style.exp_toolbar_title)


        val bundle = Bundle()
        bundle.putString("edttext", "From Activity")
// set Fragmentclass Arguments
// set Fragmentclass Arguments
        val fragobj = InstructionsFragment()
        fragobj.setArguments(bundle)

        setUpTabs()

        //get data from intent
        val intent = intent

        val recipeImg = intent.getStringExtra("recipeImg")
       recipeIntstructions = intent.getStringExtra("intstructions")
       val RecipeLabel = intent.getStringExtra("label")
        coll_toolbar.title = RecipeLabel
         nutLists = intent.getStringExtra("nutlist")
        website = intent.getStringExtra("website")

       // val token: TypeToken<ArrayList<Nutrition?>?> = object : TypeToken<ArrayList<Nutrition?>?>() {}
      //  val nutLists: ArrayList<Nutrition> = Gson().fromJson(json, token.type)

       // Log.i("tt",nutLists[0].title)
        Picasso.get().load(recipeImg).into(logoImageView)


    }
    fun getMyData(): String? {

        val myString =recipeIntstructions
        return myString
    }

    fun getNutrients() :String?
    {


        return nutLists
    }

    fun setappbarheightExpand()
    {
        app_bar.setExpanded(false)
    }

    fun setappbarheightCollapse()
    {
        app_bar.setExpanded(true)
    }
    fun sendurl():String?
    {
        return website
    }

    private fun setUpTabs()
    {
        val adapter = ViewPagerAdapter(supportFragmentManager)
       adapter.addFragment(InstructionsFragment() ,"Ingredients")
        adapter.addFragment(NutritionFragment() ,"Nutrition")
        adapter.addFragment(ThirdFragment() ,"Recipe")
        viewpager.adapter = adapter
        tabs.setupWithViewPager(viewpager)
        tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_menu_book_24)
        tabs.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_food_bank_24)
        tabs.getTabAt(2)!!.setIcon(R.drawable.ic_baseline_timer_24)
    }





}




