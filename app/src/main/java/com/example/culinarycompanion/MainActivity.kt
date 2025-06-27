// app/src/main/java/com/example/culinarycompanion/MainActivity.kt
package com.example.culinarycompanion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.culinarycompanion.data.RecipeDatabase
import com.example.culinarycompanion.data.RecipeRepository
import com.example.culinarycompanion.viewmodel.RecipeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Start Koin *before* calling super.onCreate()
        startKoin {
            androidContext(this@MainActivity)
            modules(module {
                single {
                    // provide RecipeDao
                    RecipeDatabase.getInstance(this@MainActivity).recipeDao()
                }
                single { RecipeRepository(get()) }
                viewModel { RecipeViewModel(get()) }
            })
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}