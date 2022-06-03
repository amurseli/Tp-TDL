package com.example.tdlcolecciones

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import kotlin.collections.ArrayList
import kotlin.collections.Collection

class CollectionActivity : AppCompatActivity() {

    lateinit var arrayAdapter : ArrayAdapter<*>

    override fun onResume() {
        arrayAdapter.notifyDataSetChanged()
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)

        val fab: View = findViewById(R.id.fab2)
        val list: ListView = findViewById(R.id.list2)

        var collection = intent.getSerializableExtra("coleccion") as com.example.tdlcolecciones.Collection


        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,collection.listOfItems)
        list.adapter = arrayAdapter


        val actionBar = supportActionBar
        actionBar!!.title = collection.name
        actionBar.setDisplayHomeAsUpEnabled(true)

        val responseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activityResult->
            if(activityResult.resultCode == RESULT_OK){
                val newItem = activityResult.data?.getSerializableExtra("item") as Item
                collection.addItem(newItem)
            }
        }

        fab.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, CreateItemActivity::class.java)
            intent.putExtra("ATRIBUTTES", ArrayList(collection.listOfAttributes) )
            responseLauncher.launch(intent)

        })

        list.setOnItemClickListener { parent, view, position, id ->
            val i = Intent(this, ItemActivity::class.java)
            i.putExtra("data", collection.listOfItems[position])
            startActivity(i)
        }



    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}