package com.example.xlmp13.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.xlmp13.R
import com.example.xlmp13.database.InventoryApplication
import com.example.xlmp13.database.Item
import com.example.xlmp13.databinding.FragmentItemDetailBinding
import com.example.xlmp13.viewmodels.InventoryViewModel
import com.example.xlmp13.viewmodels.InventoryViewModelFactory
import kotlinx.coroutines.launch

class ItemDetailFragment : Fragment() {
    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao()
        )
    }

    private lateinit var binding: FragmentItemDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentItemDetailBinding = FragmentItemDetailBinding.inflate(
            inflater,
            container,
            false
        )
        fragmentItemDetailBinding.lifecycleOwner = this@ItemDetailFragment
        binding = fragmentItemDetailBinding
        return fragmentItemDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.itemDetailFragment = this@ItemDetailFragment
        val itemListListAdapter = ItemListListAdapter{
            val action = ItemDetailFragmentDirections
                .actionItemDetailFragmentToSellEditItemFragment(it.id)

            view.findNavController().navigate(action)
        }
        binding.itemRecyclerView.adapter = itemListListAdapter

        lifecycle.coroutineScope.launch{
            viewModel.getListItems().collect{
                itemListListAdapter.submitList(it)
            }
        }
    }

    fun goToAddItemFragment(){
        findNavController().navigate(R.id.action_itemDetailFragment_to_addItemFragment)
    }

}