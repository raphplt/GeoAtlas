package com.example.kotlin101

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    // Création de l'activité, initialisation de la vue avec le layout activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Rechargement du navHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Configuration de la barre d'action
        setupActionBarWithNavController(navController)
    }
    // Quand l'utilisateur appuie sur le bouton retour (retour à l'écran précédent)
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() ||   super.onSupportNavigateUp()
    }
}