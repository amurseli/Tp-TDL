package com.example.tdlcolecciones

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {

    var listOfCollections = mutableListOf<Collection>()
    lateinit var arrayAdapter: ArrayAdapter<*>
    private val db = FirebaseFirestore.getInstance()

    var email = ""

    private val responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                val newCollection =
                    activityResult.data?.getSerializableExtra("nombre") as Collection
                listOfCollections.add(newCollection)
            }
        }

    override fun onResume() {
        arrayAdapter.notifyDataSetChanged()
        super.onResume()
    }

    override fun onStop() {
        db.collection("users").document(email).set(
            hashMapOf("COLECCIONES" to listOfCollections)
        )
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        email = intent.extras?.get("email").toString()

        Toast.makeText(this, email, Toast.LENGTH_SHORT).show()

        db.collection("users").document(email).get().addOnSuccessListener {

           /* var hash = (it.get("COLECCIONES") as HashMap<String,String>)
            for ((k,v) in hash){
                var nuevaColeccion = Collection(v)
                Log.e(TAG, element.name)
                nuevaColeccion.listOfAttributes = element.listOfAttributes
                nuevaColeccion.listOfItems = element.listOfItems

                listOfCollections.add(nuevaColeccion)
            }

            */

        }

        val fab: View = findViewById(R.id.fab1)
        val list: ListView = findViewById(R.id.list1)

        arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, listOfCollections)
        list.adapter = arrayAdapter


        val btn: Button = findViewById(R.id.btn2)

        btn.setOnClickListener(View.OnClickListener {

        })

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
