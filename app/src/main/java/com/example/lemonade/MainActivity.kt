package com.example.lemonade

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var currentStep = 1
    private var pressCount = 0
    private var requiredPresses = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeApp()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LemonadeApp() {
        MaterialTheme {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Lemonade", color = Color.Black) },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Yellow)
                    )
                }
            ) { innerPadding ->
                MainContent(innerPadding)
            }
        }
    }

    @Composable
    fun MainContent(innerPadding: PaddingValues) {
        // Utilisation d'un Column pour centrer le contenu
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(), // Remplit l'espace disponible
            horizontalAlignment = Alignment.CenterHorizontally, // Centre horizontalement
            verticalArrangement = Arrangement.Center // Centre verticalement
        ) {
            val textAction = remember { mutableStateOf("") }
            val imageAction = remember { mutableStateOf(R.drawable.lemon_tree) }

            // Initialise l'affichage pour la première étape
            setStepContent(textAction, imageAction)

            // Image cliquable
            Image(
                painter = painterResource(id = imageAction.value),
                contentDescription = "Image Action",
                modifier = Modifier
                    .clickable {
                        handleClick(textAction, imageAction)
                    }
                    .padding(16.dp) // Ajout d'un padding pour l'image
            )

            // Texte centré
            Text(text = textAction.value, modifier = Modifier.padding(top = 16.dp))
        }
    }

    private fun handleClick(textAction: MutableState<String>, imageAction: MutableState<Int>) {
        when (currentStep) {
            1 -> {
                currentStep = 2
                requiredPresses = Random.nextInt(2, 5)  // Génère un nombre aléatoire entre 2 et 4
                pressCount = 0
            }
            2 -> {
                pressCount++
                if (pressCount >= requiredPresses) {
                    currentStep = 3
                }
            }
            3 -> currentStep = 4
            4 -> currentStep = 1
        }
        setStepContent(textAction, imageAction)
    }

    private fun setStepContent(textAction: MutableState<String>, imageAction: MutableState<Int>) {
        when (currentStep) {
            1 -> {
                textAction.value = getString(R.string.tap_lemon_tree)
                imageAction.value = R.drawable.lemon_tree
            }
            2 -> {
                textAction.value = getString(R.string.keep_tapping_lemon)
                imageAction.value = R.drawable.lemon_squeeze
            }
            3 -> {
                textAction.value = getString(R.string.tap_lemonade)
                imageAction.value = R.drawable.lemon_drink
            }
            4 -> {
                textAction.value = getString(R.string.tap_empty_glass)
                imageAction.value = R.drawable.lemon_restart
            }
        }
    }
}
