package com.example.a20201108

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_main2.*


private const val TAG = "MyActivity"

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)



        searchInput.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode === KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                //do what you want on the press of 'done'
                imageButton.performClick()
                hidekeyboard()

            }
            false
        })






        imageButton.setOnClickListener{

          //  val userinput = searchInput.text.toString()
            //send data
           // val intent = Intent(this@MainActivity2,MainActivity::class.java)
           // intent.putExtra("user_input",userinput)
          //  startActivity(intent)

            val view = LayoutInflater.from(this@MainActivity2)
            val values = searchInput.text.toString()
            val lstValues: List<String> = values.split("and",",",ignoreCase = true ).map { it -> it.trim() }
            lstValues.forEach { it ->




                var chipcount =  chip_group.getChildCount()
                val chipsToRemove = ArrayList<Int>()
                for (i in 0..(chipcount-1))
                  {

                      val chip = chip_group.getChildAt(i) as Chip
                       if ("$it".trim().toUpperCase() == chip.getText().toString().trim().toUpperCase())

                       {
                           chipsToRemove.add(i)
                       }
                  }
                //remove duplicates
                for(i in chipsToRemove)
                {
                    val chip = chip_group.getChildAt(i) as Chip
                    chip_group.removeView(chip)
                }

                val chips = view.inflate(R.layout.chip_item,null,false) as Chip
                    chips.text = "$it"
                    // Set chip close icon click listener
                    chips.setOnCloseIconClickListener{
                        // Smoothly remove chip from chip group
                        TransitionManager.beginDelayedTransition(chip_group)
                        chip_group.removeView(chips)

                    }

                    // Finally, add the chip to chip group
                    chip_group.addView(chips)
                    searchInput.text.clear()

            }
               //hide the keybaord
            hidekeyboard()
        }


        search_button.setOnClickListener {

            var chipcount =  chip_group.getChildCount()

            var userinput = ""
            for (i in 0..(chipcount-1))
            {
                val chip = chip_group.getChildAt(i) as Chip
                userinput = userinput + " , " + chip.getText().toString().trim()
            }

            //val userinput = searchInput.text.toString()
            //send data
            val intent = Intent(this@MainActivity2,MainActivity::class.java)
            intent.putExtra("user_input",userinput)
            startActivity(intent)

        }





    }

    //hide keyboard

    fun hidekeyboard()
    {
        val view = this.currentFocus
        if (view !=null)
        {
            val hidme = getSystemService(Context.INPUT_METHOD_SERVICE ) as InputMethodManager
            hidme.hideSoftInputFromWindow(view.windowToken,0)

        }
        //else
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

    }




}