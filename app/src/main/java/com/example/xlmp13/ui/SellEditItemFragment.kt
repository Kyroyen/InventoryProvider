package com.example.xlmp13.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.xlmp13.R
import com.example.xlmp13.database.InventoryApplication
import com.example.xlmp13.database.Item
import com.example.xlmp13.databinding.FragmentSellEditItemBinding
import com.example.xlmp13.viewmodels.InventoryViewModel
import com.example.xlmp13.viewmodels.InventoryViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SellEditItemFragment : Fragment() {
    private lateinit var binding : FragmentSellEditItemBinding

    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao()
        )
    }

    lateinit var item: Item
    private var itemId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSellEditItemBinding.inflate(
            inflater,
            container,
            false
        )
        arguments?.let {
            itemId = it.getInt("itemId")
        }
        return binding.root
    }

    private fun bind(item:Item){
        binding.apply {
            singleItemName.text = item.name
            singleItemPrice.text = item.getPriceString()
            singleItemQuantity.text = "Quantity : ${item.getQuantityString()}"
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.retrieveItem(itemId).observe(this.viewLifecycleOwner){
            item = it
            bind(item)
        }
        binding.sellEditItemFragment = this@SellEditItemFragment
    }

    fun showConfirmationDialog(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)){_,_->
            }
            .setPositiveButton(getString(R.string.yes)){_,_->
                itemDeleter()
            }
            .show()
    }

    private fun itemDeleter(){
        viewModel.deleteItem(item)
        findNavController().navigateUp()
    }

    fun sellItem(){
        if(viewModel.sellItem(item)){
            Toast.makeText(context, "Can't Sell Now!!", Toast.LENGTH_SHORT).show()
        }
    }

    fun moveToEditFragment(){
        val action = SellEditItemFragmentDirections
            .actionSellEditItemFragmentToSingleItemFragment(itemId)

        findNavController().navigate(action)
    }

}