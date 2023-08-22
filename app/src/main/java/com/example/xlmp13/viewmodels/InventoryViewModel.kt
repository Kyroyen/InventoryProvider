package com.example.xlmp13.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.xlmp13.database.Item
import com.example.xlmp13.database.ItemDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class InventoryViewModel(
    private val itemDao: ItemDao
) : ViewModel() {

    fun getListItems(): Flow<List<Item>> = itemDao.getAllItems()

    fun retrieveItem(id: Int): LiveData<Item> = itemDao.getItem(id).asLiveData()

    fun updateItem(
        itemId: Int,
        itemName: String,
        itemPrice: Double,
        itemCount: Int,
    ){
        val item = Item(
            itemId,
            itemName,
            itemPrice,
            itemCount,
        )
        updateItem(item)
    }

    private fun updateItem(item:Item){
        viewModelScope.launch {
            itemDao.update(item)
        }
    }

    private fun insertItem(item: Item) {
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }

    private fun getNewItemEntry(
        itemName: String,
        itemPrice: String,
        itemCount: String,
    ): Item {
        return Item(
            name = itemName,
            price = itemPrice.toDouble(),
            quantity = itemCount.toInt(),
        )
    }

    fun addNewItem(
        itemName: String,
        itemPrice: String,
        itemCount: String,
    ) {
        insertItem(getNewItemEntry(itemName, itemPrice, itemCount))
    }

    fun sellItem(item:Item) : Boolean{
        return if (item.quantity>0){
            updateItem(
                item.copy(quantity = item.quantity-1)
            )
            false
        }else{
            true
        }
    }
}

class InventoryViewModelFactory(
    private val itemDao: ItemDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}