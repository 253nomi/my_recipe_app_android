package com.example.myrecipeapp.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myrecipeapp.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModelXML(private val repository: MainRepository) : ViewModel() {

    private val _categoriesState = MutableLiveData(RecipeState())
    val categoriesState: LiveData<RecipeState> = _categoriesState

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = repository.getCategories()
                _categoriesState.value = RecipeState(
                    loading = false,
                    categories = response.categories,
                    error = null
                )
            } catch (e: Exception) {
                _categoriesState.value = RecipeState(
                    loading = false,
                    categories = emptyList(),
                    error = "Error fetching categories: ${e.message}"
                )
            }
        }
    }
}