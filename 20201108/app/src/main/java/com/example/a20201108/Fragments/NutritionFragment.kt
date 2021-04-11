package com.example.a20201108.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a20201108.MainActivity3
import com.example.a20201108.R
import com.example.a20201108.adapters.InstructionAdapter
import com.example.a20201108.adapters.Instructions
import com.example.a20201108.adapters.Nutrition
import com.example.a20201108.adapters.NutritionAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ramotion.foldingcell.FoldingCell
import kotlinx.android.synthetic.main.activity_main3.*
import kotlinx.android.synthetic.main.fragment_instructions.*
import kotlinx.android.synthetic.main.fragment_nutrition.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NutritionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NutritionFragment : Fragment() {
    // TODO: Rename and change types of parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_nutrition, container, false)

    // populate the views now that the layout has been inflated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // get our folding cell
       val nutList = ArrayList<Nutrition>()



       // nutList.add(Nutrition("2","Recipe"))

        val activity: MainActivity3? = activity as MainActivity3?
        var json = activity?.getNutrients()
        //activity?.setappbarheightCollapse()

         val token: TypeToken<ArrayList<Nutrition?>?> = object : TypeToken<ArrayList<Nutrition?>?>() {}
         val nutlst: ArrayList<Nutrition> = Gson().fromJson(json, token.type)
        nutList.add(nutlst!![0])
        nutList.add(nutlst!![1])
        nutList.add(nutlst!![2])
        nutList.add(nutlst!![3])


        nutrition_list_recycler_view.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = NutritionAdapter(nutList)
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }



        // Inflate the layout for this fragment

    }


}