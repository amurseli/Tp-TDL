package com.example.tdlcolecciones

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class CollectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)

        val exitBtn: Button = findViewById(R.id.exitBtn)
        var txtCollectionName : TextView = findViewById(R.id.txtNameCollection)

        var collection = intent.getSerializableExtra("coleccion") as Collection
        txtCollectionName.text = "Nombre: ${collection.name}"

        exitBtn.setOnClickListener(View.OnClickListener {
            finish()
        })

    }


}