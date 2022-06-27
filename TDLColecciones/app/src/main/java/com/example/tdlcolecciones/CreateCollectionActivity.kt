package com.example.tdlcolecciones

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.service.autofill.SaveInfo
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.lang.IllegalArgumentException
import java.util.jar.Manifest

class CreateCollectionActivity : AppCompatActivity() {

    var listOfAttribute = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_collection)


        var editTxtName: EditText = findViewById(R.id.nameCollectionEdit)
        var editTxtAttribute: EditText = findViewById(R.id.attributeEdit)
        val addAttributeBtn:Button = findViewById(R.id.addAttributeBtn)
        val saveBtn: Button = findViewById(R.id.saveBtn)

        var listViewOfAttributes: ListView = findViewById(R.id.listOfAttributes)

        val arrayAdapter: ArrayAdapter<*> = ArrayAdapter(this, android.R.layout.simple_list_item_1, listOfAttribute)
        listViewOfAttributes.adapter = arrayAdapter


        saveBtn.setOnClickListener(View.OnClickListener {

            if (validarGuardado(editTxtAttribute,editTxtName)) {
                var newCollection = Collection(editTxtName.text.toString())
                newCollection.addListOfAtribute(listOfAttribute)
                saveInfo(newCollection)
            }
        })

        addAttributeBtn.setOnClickListener(View.OnClickListener {
            comprobarEntrada(editTxtAttribute)
            arrayAdapter.notifyDataSetChanged()
        })


    }



    fun saveInfo(newCollection: Collection){

        val intent = Intent()
        intent.putExtra("nombre", newCollection)
        setResult(RESULT_OK, intent)
        finish()
    }

    fun comprobarEntrada(editText:EditText){
        var newAtributte = editText.text.toString()
        try {
            if (newAtributte.isBlank()){
                throw IllegalArgumentException("Entrada invalida. El campo no puede ser vacio")
            }else{
                listOfAttribute.add(newAtributte)
                editText.setText("")
                editText.hint = "Agrega otro atributo"
            }
        }catch (e: IllegalArgumentException){
            editText.hint = "Completar campo!"
        }

    }

    fun validarGuardado(editTextAttribute: EditText, editTextName: EditText): Boolean {

        if (editTextName.text.isBlank()){
            editTextName.hint = "Agregar nombre!"
            return false
        }

        if(listOfAttribute.size == 0){
            editTextAttribute.hint = "Al menos un atributo!"
            return false
        }

        return true
    }


}