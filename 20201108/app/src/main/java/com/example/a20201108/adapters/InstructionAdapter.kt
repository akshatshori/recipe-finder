package com.example.a20201108.adapters

import com.example.a20201108.Holder
import com.example.a20201108.MainActivity3
import com.example.a20201108.R



import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a20201108.model.Hit
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.instruction_listitem.view.*
import kotlinx.android.synthetic.main.item_view.view.*



class InstructionAdapter(private val list: List<Instructions>)
    : RecyclerView.Adapter<Holder1>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder1 {
        val inflater = LayoutInflater.from(parent.context)
        return Holder1(inflater, parent)
    }

    override fun onBindViewHolder(holder: Holder1, position: Int) {
        val movie: Instructions = list[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = list.size

}