package com.example.myrecipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.myrecipeapp.viewmodels.MainViewModelXML

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipeapp.repository.MainRepositoryImpl

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            MyRecipeAppTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    RecipeScreen(
//                        modifier = Modifier.padding(innerPadding),
//                    )
//                }
//            }
//        }
//    }
//}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MyRecipeAppTheme {
//        Greeting("Android")
//    }
//}



class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModelXML
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTextView: TextView
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_screen)

        progressBar = findViewById(R.id.progressBar)
        errorTextView = findViewById(R.id.errorTextView)
        recyclerView = findViewById(R.id.categoryRecyclerView)

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