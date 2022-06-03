package com.example.tdlcolecciones

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class ItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        val txt: TextView = findViewById(R.id.infoItemTxt)

        var item = intent.getSerializableExtra("data") as Item

        val actionBar = supportActionBar
        actionBar!!.title = item.name
        actionBar.setDisplayHomeAsUpEnabled(true)

        txt.text = item.dictionaryOfAttribute.toString()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}