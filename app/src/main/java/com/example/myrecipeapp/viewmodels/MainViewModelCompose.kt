package com.example.myrecipeapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.Category
import com.example.myrecipeapp.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModelCompose(private val _repository: MainRepository) : ViewModel() {

    private val _categoriesState = mutableStateOf(RecipeState())

    val categoriesState: State<RecipeState> = _categoriesState


    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {

                val response = _repository.getCategories()
                _categoriesState.value = _categoriesState.value.copy(
                    loading = false,
                    categories = response.categories,
                    error = null,
                )

            } catch (e: Exception) {
                _categoriesState.value = _categoriesState.value.copy(
                    loading = false,
                    error = "Error fetching categories ${e.message}"
                )
            }
        }
    }


}

data class RecipeState(
    val loading: Boolean = true,
    val categories: List<Category> = emptyList(),
    val error: String? = null,
)