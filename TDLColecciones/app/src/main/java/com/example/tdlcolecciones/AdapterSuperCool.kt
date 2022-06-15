package com.example.tdlcolecciones

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import io.grpc.Context
import kotlinx.android.synthetic.main.item.view.*
import java.text.CollationElementIterator

class AdapterSuperCool(private val mContext: android.content.Context, private var list: MutableList<Collection>)
    : ArrayAdapter<Collection>(mContext,0,list){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.item,parent,false)

        val coleccion = list[position]
        layout.name.text = coleccion.name
        return layout
    }
}