package com.example.tdlcolecciones

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    var listOfCollections = mutableListOf<Collection>()
    lateinit var arrayAdapter : ArrayAdapter<*>

    private val responseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activityResult->
        if(activityResult.resultCode == RESULT_OK){
            val newCollection = activityResult.data?.getSerializableExtra("nombre") as Collection
            listOfCollections.add(newCollection)
        }
    }

    override fun onResume() {
        arrayAdapter.notifyDataSetChanged()
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var email = intent.extras?.get("email")

        Toast.makeText(this,email.toString(), Toast.LENGTH_SHORT).show()

        val fab: View = findViewById(R.id.fab1)
        val list: ListView = findViewById(R.id.list1)

        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,listOfCollections)
        list.adapter = arrayAdapter


        fab.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, CreateCollectionActivity::class.java)
            responseLauncher.launch(intent)
        })


        list.setOnItemClickListener { parent, view, position, id ->
            val i = Intent(this, CollectionActivity::class.java)
            i.putExtra("coleccion", listOfCollections[position])
            startActivity(i)
        }

    }


}