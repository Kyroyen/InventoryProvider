package com.example.xlmp13.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.xlmp13.database.InventoryApplication
import com.example.xlmp13.database.Item
import com.example.xlmp13.databinding.FragmentSingleItemBinding
import com.example.xlmp13.viewmodels.InventoryViewModel
import com.example.xlmp13.viewmodels.InventoryViewModelFactory

class SingleItemFragment : Fragment() {
    private lateinit var binding: FragmentSingleItemBinding

    private lateinit var item: Item
    private var itemId: Int = 0

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
        binding = FragmentSingleItemBinding.inflate(
            inflater,
            container,
            false
        )

        arguments?.let {
            itemId = it.getInt("itemId")
        }
        binding.singleItemFragment = this@SingleItemFragment
        return binding.root
    }

    private fun bind(item: Item){
        binding.apply {
            itemNameInput.setText(item.name)
            itemPriceInput.setText(item.price.toString())
            itemQuantityInput.setText(item.getQuantityString())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.retrieveItem(itemId).observe(this.viewLifecycleOwner){
            item = it
            bind(item)
        }
    }

    fun updateItem(){
        val price = binding.itemPriceInput.text.toString().toDouble()
        val quantity = binding.itemQuantityInput.text.toString().toInt()
        val name = binding.itemNameInput.text.toString()
        try{
            viewModel.updateItem(
                itemId,
                name,
                price,
                quantity
            )
            Toast.makeText(context, "Changes saved successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }catch (e: Exception){
            Toast.makeText(context, "An Error Occurred", Toast.LENGTH_SHORT).show()
        }

    }

}