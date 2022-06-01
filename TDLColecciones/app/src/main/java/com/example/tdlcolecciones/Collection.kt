package com.example.tdlcolecciones

import java.io.Serializable

class Collection (var name:String) : Serializable {

    var listOfItems = mutableListOf<Item>()
    var listOfAttributes = mutableListOf<String>()



    @JvmName("getName1")
    fun getName(): String {
        return name
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


}