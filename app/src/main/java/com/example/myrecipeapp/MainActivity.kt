package com.example.myrecipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.myrecipeapp.viewmodels.MainViewModelXML
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipeapp.repository.MainRepositoryImpl
import com.example.myrecipeapp.ui.theme.MyRecipeAppTheme
import com.example.myrecipeapp.viewmodels.MainViewModelCompose

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModelXML

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val useCompose: Boolean = true

        if (useCompose) {
            setupComposeUI()
        } else {
            setupXmlUI()
        }
    }

    // ------------------ COMPOSE UI ------------------
    private fun setupComposeUI() {
        enableEdgeToEdge()
        setContent {
            MyRecipeAppTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize(),  contentWindowInsets = WindowInsets.safeDrawing) { innerPadding ->
                    ComposeRecipeApp(navController, Modifier.padding(innerPadding),)
                }
            }
        }
    }

    // ------------------ XML UI ------------------
    private fun setupXmlUI() {
        setContentView(R.layout.recipe_screen)

        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        val errorTextView: TextView = findViewById(R.id.errorTextView)
        val recyclerView: RecyclerView = findViewById(R.id.categoryRecyclerView)

        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Initialize repository
        val repository = MainRepositoryImpl(receipeService)

        // Initialize ViewModel (simple constructor, can use Factory if needed)
        viewModel = MainViewModelXML(repository)

        // Observe state
        viewModel.categoriesState.observe(this) { state ->
            // Show/hide progress bar
            progressBar.visibility = if (state.loading) View.VISIBLE else View.GONE

            // Show/hide error
            errorTextView.visibility = if (state.error != null) View.VISIBLE else View.GONE
            errorTextView.text = state.error

            // Populate RecyclerView
            if (!state.loading && state.error == null) {
                recyclerView.adapter = CategoryAdapter(state.categories)
            }
        }
    }
}