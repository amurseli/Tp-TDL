package com.example.tdlcolecciones

import android.graphics.Typeface
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginLeft

class ItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        var columnaVertical : LinearLayout = findViewById(R.id.columnaVertical)

        var item = intent.getSerializableExtra("data") as Item


        val actionBar = supportActionBar
        actionBar!!.title = item.name
        actionBar.setDisplayHomeAsUpEnabled(true)

        for ((key,value) in item.dictionaryOfAttribute){
            var txt_dinamic = TextView(this)
            var tamanio_text = 27F
            txt_dinamic.setTypeface(null, Typeface.BOLD)
            txt_dinamic.marginLeft
            txt_dinamic.textSize = tamanio_text
            txt_dinamic.text = "${key} : ${value}"
            columnaVertical.addView(txt_dinamic)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}