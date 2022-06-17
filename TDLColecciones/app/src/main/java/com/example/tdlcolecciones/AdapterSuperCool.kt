package com.example.tdlcolecciones

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.collection.view.*

class AdapterSuperCool(private val mContext: android.content.Context, private var list: MutableList<Collection>)
    : ArrayAdapter<Collection>(mContext,0,list){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.collection,parent,false)

        val coleccion = list[position]
        layout.name.text = coleccion.name
        layout.cantidad_items.text = "Cantidad de items: " + coleccion.listOfItems.size.toString()
        return layout
    }
}