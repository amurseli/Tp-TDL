package com.example.tdl_colecciones

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.navigation.fragment.findNavController
import com.example.tdl_colecciones.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val collectionsAdapater:ArrayAdapter<*>
        val collectionsList = binding.root.findViewById<ListView>(R.id.collectionList)
        val collectionsArray = mutableListOf("Libros","Monedas")

        collectionsAdapater = ArrayAdapter(this.requireContext(), android.R.layout.simple_list_item_1,collectionsArray)
        collectionsList.adapter = collectionsAdapater

        collectionsList.setOnItemClickListener(){parent,view,position,id->
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}