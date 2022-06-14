package com.example.tdlcolecciones

import java.io.Serializable

class Item (var name:String) : Serializable {

    var dictionaryOfAttribute = hashMapOf<String,String>()
    //el linked hash map es un diccionario que garantiza el orden en el cual se agregan las cosas

}