package com.example.a20201108.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.a20201108.MainActivity3
import com.example.a20201108.R
import kotlinx.android.synthetic.main.fragment_third.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [ThirdFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThirdFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater?.inflate(R.layout.fragment_third,
                container, false)
        val activity: MainActivity3? = activity as MainActivity3?
        // activity?.setappbarheightExpand()


        val webview: WebView? = view?.findViewById(R.id.webview)
        webview?.settings?.setJavaScriptEnabled(true)

        webview?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        webview?.loadUrl(activity?.sendurl())



        return view
    }



}