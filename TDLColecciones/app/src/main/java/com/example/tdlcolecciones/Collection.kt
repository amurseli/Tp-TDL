package com.example.tdlcolecciones

import java.io.Serializable

class Collection (var name:String) : Serializable {

    var listOfItems = mutableListOf<Item>()
    var listOfNamesOfItems = mutableListOf<String>()
    var listOfAttributes = mutableListOf<String>()

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
        listOfNamesOfItems.add(item.name)
    }

    fun addListOfItems(list : MutableList<Item>){
        for (item in list){
            this.addItem(item)
        }
    }
}