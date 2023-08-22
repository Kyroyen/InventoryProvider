package com.example.xlmp13.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.xlmp13.R
import com.example.xlmp13.database.InventoryApplication
import com.example.xlmp13.databinding.FragmentAddItemBinding
import com.example.xlmp13.databinding.ItemListItemBinding
import com.example.xlmp13.viewmodels.InventoryViewModel
import com.example.xlmp13.viewmodels.InventoryViewModelFactory

class AddItemFragment : Fragment() {
    private lateinit var binding: FragmentAddItemBinding

    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddItemBinding.inflate(
            inflater,
            container,
            false
        )
        binding.addItemFragment = this@AddItemFragment
        binding.lifecycleOwner = this@AddItemFragment
        return binding.root
    }

    fun submitData(){
        val name = binding.itemNameInput.text.toString()
        val price = binding.itemPriceInput.text.toString()
        val quantity = binding.itemQuantityInput.text.toString()
        if(name.isBlank() || price.isBlank() || quantity.isBlank()){
            if(name.isBlank()) binding.itemNameInput.error = "Name can't be left empty"
            if(price.isBlank()) binding.itemPriceInput.error = "Price can't be left empty"
            if(quantity.isBlank()) binding.itemQuantityInput.error = "Quantity can't be left empty"
            return
        }
        viewModel.addNewItem(name, price, quantity)
        goToPreviousScreen()
    }

    private fun goToPreviousScreen(){
        findNavController().navigate(R.id.action_addItemFragment_to_itemDetailFragment)
    }
}