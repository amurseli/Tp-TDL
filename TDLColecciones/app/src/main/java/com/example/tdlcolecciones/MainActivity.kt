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
import java.text.CollationElementIterator


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


    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(R.layout.activity_main)


        email = intent.extras?.get("email").toString()

        Toast.makeText(this, email, Toast.LENGTH_SHORT).show()


        val fab: View = findViewById(R.id.fab1)
        val list: ListView = findViewById(R.id.list1)

        //----------------ARRAY ADAPTER----------------------

        arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, listOfCollections)
        list.adapter = arrayAdapter

        //-------------------GET INFO FROM FIRESRTORE--------------------

        db.collection("users").document(email).get().addOnSuccessListener {
            var tamanio = it.get("TAMANIO")

            if (tamanio != null){
                tamanio = tamanio as Long
                for (i in 0 until tamanio.toInt()) {

                    db.collection("users").document(email).collection(i.toString()).document("data").get()
                    .addOnSuccessListener {

                        var nuevaColeccion = Collection(it.get("NOMBRE").toString())
                        nuevaColeccion.addListOfAtribute(it.get("LISTA DE ATRIBUTOS") as MutableList<String>)
                        nuevaColeccion.addListOfItems(it.get("LISTA DE ITEMS") as MutableList<Item>)

                        listOfCollections.add(nuevaColeccion)
                        arrayAdapter.notifyDataSetChanged()

                    }
                }
            }
        }



        //----------------------SAVE INFO FIRESTORE-------------------

        val btn: Button = findViewById(R.id.btn2)

        btn.setOnClickListener(View.OnClickListener {
            var i = 0
            db.collection("users").document(email).set(
                hashMapOf("TAMANIO" to listOfCollections.size )
            )

            for (coleccion in listOfCollections){
                db.collection("users").document(email).collection(i.toString()).document("data").set(
                    hashMapOf("NOMBRE" to coleccion.name,"LISTA DE ITEMS" to coleccion.listOfItems, "LISTA DE ATRIBUTOS" to coleccion.listOfAttributes)
                )
                i++
            }

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

    super.onCreate(savedInstanceState)
    }

}
