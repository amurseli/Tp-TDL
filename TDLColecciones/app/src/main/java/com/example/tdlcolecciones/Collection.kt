package com.example.tdlcolecciones

import java.io.Serializable

class Collection (var name:String) : Serializable {

    var listOfItems = mutableListOf<Item>()
    var listOfAttributes = mutableListOf<String>()

    class Collection(var listOfAttributes: MutableList<String>,var name: String, var listOfItems: MutableList<Item> ){

    }

    fun addAttribute(attribute: String){
        listOfAttributes.add(attribute)
    }

    fun addListOfAtribute(list : MutableList<String>){
        for (attribute in list){
            this.addAttribute(attribute)
        }
    }

    fun addItem(item: Item){
        listOfItems.add(item)
    }

    fun addListOfItems(list : MutableList<Item>){
        for (item in list){
            this.addItem(item)
        }
    }
}