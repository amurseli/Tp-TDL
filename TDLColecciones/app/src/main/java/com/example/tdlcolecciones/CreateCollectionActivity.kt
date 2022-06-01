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

    var listOfAttribute = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_collection)


        var editTxtName: EditText = findViewById(R.id.nameCollectionEdit)
        var editTxtAttribute: EditText = findViewById(R.id.attributeEdit)
        val addAttributeBtn:Button = findViewById(R.id.addAttributeBtn)
        val saveBtn: Button = findViewById(R.id.saveBtn)


        saveBtn.setOnClickListener(View.OnClickListener {
            var nameCollection = editTxtName.text.toString()
            var newCollection = Collection(nameCollection)
            newCollection.addListOfAtribute(listOfAttribute)
            saveInfo(newCollection)
        })

        addAttributeBtn.setOnClickListener(View.OnClickListener {
            var newAtributte = editTxtAttribute.text.toString()
            listOfAttribute.add(newAtributte)
            editTxtAttribute.setText("")
        })

    }

    fun saveInfo(newCollection: Collection){

        val intent = Intent()
        intent.putExtra("nombre", newCollection)
        setResult(RESULT_OK, intent)
        finish()
    }


}