package com.example.tdlcolecciones

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
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

        val typeface = ResourcesCompat.getFont(this.applicationContext, R.font.google_sans_regular)
        val tamanioText = 27F




        for ((key,value) in item.dictionaryOfAttribute){

            val txt_dinamic = TextView(this)
            txt_dinamic.typeface = typeface
            txt_dinamic.setTextColor(Color.BLACK)
            txt_dinamic.textAlignment = View.TEXT_ALIGNMENT_CENTER
            txt_dinamic.setBackgroundResource(R.drawable.gradient_1)
            txt_dinamic.textSize = tamanioText
            txt_dinamic.text = "${key} : ${value}"

            columnaVertical.addView(txt_dinamic)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}