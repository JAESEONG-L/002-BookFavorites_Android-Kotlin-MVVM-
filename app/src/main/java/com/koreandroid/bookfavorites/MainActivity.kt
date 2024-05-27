package com.koreandroid.bookfavorites

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.koreandroid.bookfavorites.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            with(insets.getInsets(WindowInsetsCompat.Type.systemBars())) {
                v.setPadding(left, v.paddingTop, right, bottom)
            }

            insets
        }

        // Getting the NavController of the NavHostFragment connected with @id/fcv_main widget.
        val navController =
            (supportFragmentManager.findFragmentById(binding.fcvMain.id) as NavHostFragment).navController

        setupNavigationBar(navController)
    }

    private fun setupNavigationBar(navController: NavController) {
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}