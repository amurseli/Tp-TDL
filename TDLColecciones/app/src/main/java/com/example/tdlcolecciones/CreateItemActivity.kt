package com.example.tdlcolecciones

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlin.collections.Collection

class CreateItemActivity : AppCompatActivity() {

    var dictionaryOfAttributes = linkedMapOf<String,String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_item)


        val editAttribute: EditText = findViewById(R.id.editAttribute2)
        val editName : EditText = findViewById(R.id.editName2)
        val btnNext: Button = findViewById(R.id.nextBtn2)
        val btnSave: Button = findViewById(R.id.saveBtn2)


        var arrayOfAtributtes = intent.getStringArrayListExtra("ATRIBUTTES")


        var i = 0

        if (arrayOfAtributtes != null) {
            editAttribute.hint = arrayOfAtributtes.get(i).toString()
        }

        btnNext.setOnClickListener(View.OnClickListener {
            dictionaryOfAttributes[editAttribute.hint.toString()] = editAttribute.text.toString()

            i++

            if (arrayOfAtributtes!!.size == i){

                    editAttribute.visibility = View.INVISIBLE
                    btnNext.visibility = View.INVISIBLE
                    btnSave.visibility = View.VISIBLE
            }
            else {

                editAttribute.setText("")
                editAttribute.hint = arrayOfAtributtes.get(i)
            }
        })

        btnSave.setOnClickListener(View.OnClickListener {
            var item = Item(editName.text.toString())
            item.dictionaryOfAttribute = dictionaryOfAttributes
            saveInfo(item)
        })

    }

    fun saveInfo(newItem: Item){

        val intent = Intent()
        intent.putExtra("item", newItem)
        setResult(RESULT_OK, intent)
        finish()
    }
}