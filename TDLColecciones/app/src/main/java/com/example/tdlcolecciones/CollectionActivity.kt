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
        var position = intent.extras?.get("position") as Int

        arrayAdapter = AdapterSuperCoolItem(this,collection.listOfItems)
        list.adapter = arrayAdapter


        val actionBar = supportActionBar
        actionBar!!.title = collection.name
        actionBar.setDisplayHomeAsUpEnabled(true)



        val responseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activityResult->
            if(activityResult.resultCode == RESULT_OK){
                val newItem = activityResult.data?.getSerializableExtra("item") as Item
                collection.addItem(newItem)
                saveInfo(collection,position)
            }
        }

        fab.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, CreateItemActivity::class.java)
            intent.putExtra("ATRIBUTTES", ArrayList(collection.listOfAttributes) )
            responseLauncher.launch(intent)

        })

        list.setOnItemClickListener { _, _, position, _ ->
            val i = Intent(this, ItemActivity::class.java)
            i.putExtra("data", collection.listOfItems[position])
            startActivity(i)
        }

        list.setOnItemLongClickListener { _, _, position, _ ->
            Toast.makeText(this,"HOLA ${position}",Toast.LENGTH_SHORT).show()
            collection.deleteItem(position)
            arrayAdapter.notifyDataSetChanged()
            return@setOnItemLongClickListener true

        }

    }

    fun saveInfo(updateCollection: com.example.tdlcolecciones.Collection, position: Int){

        val intent = Intent()
        intent.putExtra("updateCollection", (updateCollection))
        intent.putExtra("position", position)
        setResult(RESULT_OK, intent)
    }
    override fun onSupportNavigateUp(): Boolean {
        //saveInfo(collection, position)
        onBackPressed()
        return true
    }

}