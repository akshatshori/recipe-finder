package com.example.a20201108.Fragments

import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.fragment_instructions.*


class InstructionsFragment : Fragment() {


    private val TAG = "MyActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_instructions, container, false)

    // populate the views now that the layout has been inflated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerView node initialized here

        val activity: MainActivity3? = activity as MainActivity3?
        var myDataFromActivity: String? = activity?.getMyData()
       // activity?.setappbarheightCollapse()

        myDataFromActivity = myDataFromActivity?.replace("[","")
        myDataFromActivity =  myDataFromActivity?.replace("]","")
       // myDataFromActivity = myDataFromActivity?.trim()
        val parts = myDataFromActivity?.split("Ingredient(image=", ", text=", ", weight=")
        Log.d(TAG, parts.toString())
        val instrucList = ArrayList<Instructions>()

        for (i in 1..(parts?.count()!!-1)step 3) {
            instrucList.add(Instructions(parts?.get(i)!!,parts?.get(i+1)!!))
        }
        //instrucList.add(Instructions(parts?.get(1)!!,parts?.get(2)!!))




        list_recycler_view.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = InstructionAdapter(instrucList)
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
    }

    companion object {
        fun newInstance(): InstructionsFragment = InstructionsFragment()
    }
}


