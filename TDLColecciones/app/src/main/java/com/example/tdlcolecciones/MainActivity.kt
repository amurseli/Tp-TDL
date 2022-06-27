package com.example.tdlcolecciones

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {

    var listOfCollections = mutableListOf<Collection>()
    lateinit var arrayAdapter: AdapterSuperCool

    lateinit var mAuth:FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    var email = ""

    private val responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                val newCollection =
                    activityResult.data?.getSerializableExtra("nombre") as Collection
                listOfCollections.add(newCollection)
                saveInfo()
            }
        }

    private val responseLauncher2 =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                val updateCollection = activityResult.data?.getSerializableExtra("updateCollection") as Collection
                val position = activityResult.data?.extras?.get("position") as Int
                listOfCollections.set(position,updateCollection)
                saveInfo()
            }
        }
    override fun onResume() {
        arrayAdapter.notifyDataSetChanged()
        super.onResume()
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar!!.title = "Tus Colecciones"

        email = intent.extras?.get("email").toString()

        //Toast.makeText(this,"Hola " + email, Toast.LENGTH_SHORT).show()

        val fab: View = findViewById(R.id.fab1)
        val list: ListView = findViewById(R.id.list1)


        arrayAdapter = AdapterSuperCool(this, listOfCollections)
        list.adapter = arrayAdapter


        getInfo()


        fab.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, CreateCollectionActivity::class.java)
            responseLauncher.launch(intent)
        })


        list.setOnItemClickListener { _, _, position, _ ->
            val i = Intent(this, CollectionActivity::class.java)
            i.putExtra("coleccion", listOfCollections[position])
            i.putExtra("position", position)
            responseLauncher2.launch(i)
        }


        list.setOnItemLongClickListener { _, _, position, _ ->
            Toast.makeText(this,"Borraste: ${listOfCollections[position].name}",Toast.LENGTH_SHORT).show()
            listOfCollections.removeAt(position)
            arrayAdapter.notifyDataSetChanged()
            saveInfo()
            return@setOnItemLongClickListener true

        }

    super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.logout -> logout()
        }
        return super.onOptionsItemSelected(item)
    }

    fun logout(){
        mAuth = FirebaseAuth.getInstance()
        mAuth.signOut()


        val intent = Intent(applicationContext, LogInActivity::class.java)
        intent.putExtra("logout",true)
        startActivity(intent)
    }


    private fun getInfo() {
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

                                            arrayAdapter.notifyDataSetChanged()
                                        }

                                }

                            }

                            listOfCollections.add(nuevaColeccion)
                            arrayAdapter.notifyDataSetChanged()

                        }
                }
            }
        }
    }


    private fun saveInfo() {
        var i = 0
        var j = 0
        db.collection("users").document(email).set(
            hashMapOf("TAMANIO" to listOfCollections.size )
        )

        for (coleccion in listOfCollections) {
            db.collection("users").document(email).collection(i.toString()).document("data")
                .set(
                    hashMapOf(
                        "NOMBRE" to coleccion.name,
                        "LISTA DE ATRIBUTOS" to coleccion.listOfAttributes,
                        "CANTIDAD DE ITEMS" to coleccion.listOfItems.size
                    )
                )
            for (item in coleccion.listOfItems) {

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

        //Toast.makeText(this,"Cambios guardados",Toast.LENGTH_SHORT).show()

    }

}
