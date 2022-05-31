package com.example.tdlcolecciones

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.service.autofill.SaveInfo
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class CreateCollectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_collection)


        var editTxtName: EditText = findViewById(R.id.nameCollectionEdit)
        val saveBtn: Button = findViewById(R.id.saveBtn)


        saveBtn.setOnClickListener(View.OnClickListener {

            var nameCollection = editTxtName.text.toString()
            var newCollection = Collection(nameCollection)
            saveInfo(newCollection)
        })


    }

    fun saveInfo(newCollection: Collection){
        val intent = Intent()
        intent.putExtra("nombre", newCollection)
        setResult(RESULT_OK, intent)
        finish()
    }

}