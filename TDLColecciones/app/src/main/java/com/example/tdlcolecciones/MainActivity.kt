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
import java.util.HashMap


class MainActivity : AppCompatActivity() {

    var listOfCollections = mutableListOf<Collection>()
    var listOfNamesOfCollection = mutableListOf<String>()
    lateinit var arrayAdapter: AdapterSuperCool

    private val db = FirebaseFirestore.getInstance()

    var email = ""

    private val responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                val newCollection =
                    activityResult.data?.getSerializableExtra("nombre") as Collection
                listOfCollections.add(newCollection)
                listOfNamesOfCollection.add(newCollection.name)
            }
        }

    private val responseLauncher2 =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                val updateCollection = activityResult.data?.getSerializableExtra("updateCollection") as Collection
                val position = activityResult.data?.extras?.get("position") as Int
                listOfCollections.set(position,updateCollection)
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
            AdapterSuperCool(this, listOfCollections)
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
                       var cantidadDeItems = it.get("CANTIDAD DE ITEMS")

                        if (cantidadDeItems != null){
                            cantidadDeItems = cantidadDeItems as Long
                            for (j in 0 until cantidadDeItems.toInt()){
                                db.collection("users").document(email).collection(i.toString())
                                    .document("data").collection(j.toString()).document("data2")
                                    .get().addOnSuccessListener {
                                        var nuevoItem = Item(it.get("NOMBRE ITEM").toString())
                                        nuevoItem.dictionaryOfAttribute = it.get("ATRIBUTOS ITEM") as HashMap<String,String>
                                        nuevaColeccion.addItem(nuevoItem)
                                    }

                            }
                        }

                        listOfCollections.add(nuevaColeccion)
                        listOfNamesOfCollection.add(nuevaColeccion.name)
                        arrayAdapter.notifyDataSetChanged()

                    }
                }
            }
        }



        //----------------------SAVE INFO FIRESTORE-------------------

        val btn: Button = findViewById(R.id.btn2)

        btn.setOnClickListener(View.OnClickListener {
            var i = 0
            var j = 0
            db.collection("users").document(email).set(
                hashMapOf("TAMANIO" to listOfCollections.size )
            )

            for (coleccion in listOfCollections) {
                for (item in coleccion.listOfItems) {
                    db.collection("users").document(email).collection(i.toString()).document("data")
                        .set(
                            hashMapOf(
                                "NOMBRE" to coleccion.name,
                                "LISTA DE ATRIBUTOS" to coleccion.listOfAttributes,
                                "CANTIDAD DE ITEMS" to coleccion.listOfItems.size
                            )


                        )
                    db.collection("users").document(email).collection(i.toString())
                        .document("data").collection(j.toString()).document("data2")
                        .set(
                            linkedMapOf("NOMBRE ITEM" to item.name, "ATRIBUTOS ITEM" to item.dictionaryOfAttribute)
                        )
                    j++
                }
                j = 0
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
            i.putExtra("position", position)
            responseLauncher2.launch(i)
        }


        list.setOnItemLongClickListener { _, _, position, _ ->
            Toast.makeText(this,"HOLA ${position}",Toast.LENGTH_SHORT).show()
            listOfCollections.removeAt(position)
            listOfNamesOfCollection.removeAt(position)
            arrayAdapter.notifyDataSetChanged()
            return@setOnItemLongClickListener true

        }

    super.onCreate(savedInstanceState)
    }

}
