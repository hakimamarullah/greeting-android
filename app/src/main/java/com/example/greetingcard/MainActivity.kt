package com.example.greetingcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.greetingcard.ui.theme.DiceRollerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerTheme {
                DiceRollerApp()
            }
        }
    }
}

@Composable
fun DiceRollerApp(
    modifier: Modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
) {
    var result by remember { mutableStateOf((1..6).random()) }
    var shake by remember { mutableStateOf(false) }  // Change to false initially
    val rotation = remember { Animatable(0f) }

    // Define the dice image based on the result
    val imageResource: Int = when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

    // When `shake` changes, trigger the animation and then reset it
    LaunchedEffect(shake) {
        if (shake) {
            rotation.snapTo(0f) // Reset rotation before starting the animation
            rotation.animateTo(
                targetValue = 360f,
                animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
            )
            // After the rotation animation ends, update the dice result
            result = (1..6).random()
            rotation.snapTo(0f) // Reset the angle after the roll
            shake = false // Reset `shake` to allow the next roll
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Image with rotation animation
        Dice(
            dice = painterResource(id = imageResource),
            rotationAngle = rotation.value
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Roll button to trigger the dice roll
        RollButton {
            shake = true // Set shake to true to trigger the animation
        }
    }
}

@Composable
fun Dice(dice: Painter, rotationAngle: Float, modifier: Modifier = Modifier) {
    // Apply rotation animation to the dice image
    Image(
        painter = dice,
        contentDescription = "Dice",
        modifier = modifier
            .graphicsLayer(rotationZ = rotationAngle) // Apply rotation here
            .size(150.dp)
    )
}

@Composable
fun RollButton(onRoll: () -> Unit) {
    Button(onClick = onRoll) {
        Text(text = "Roll")
    }
}