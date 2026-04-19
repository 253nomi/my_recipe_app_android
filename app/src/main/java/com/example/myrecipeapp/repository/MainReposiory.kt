package com.example.myrecipeapp.repository

import com.example.myrecipeapp.ApiService
import com.example.myrecipeapp.CategoriesResponse

interface MainRepository{
    suspend fun getCategories(): CategoriesResponse
}


class MainRepositoryImpl( private val apiService: ApiService) : MainRepository {

    override suspend fun getCategories(): CategoriesResponse {
        return apiService.getCategories()
    }
}