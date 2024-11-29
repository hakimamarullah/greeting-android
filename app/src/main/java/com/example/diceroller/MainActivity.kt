package com.example.diceroller

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.greetingcard.R
import com.example.diceroller.sensors.ShakeDetector
import com.example.diceroller.ui.theme.DiceRollerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
) {
    var result by remember { mutableStateOf((1..6).random()) }
    var shake by remember { mutableStateOf(false) }  // Initial state of shake
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
    val localContext = LocalContext.current
    LaunchedEffect(shake) {
        if (shake) {
            rotation.snapTo(0f) // Reset rotation before starting the animation

            val mediaPlayer =
                MediaPlayer.create(localContext, R.raw.dice_roll) // Point to your MP3 file
            mediaPlayer.start() // Start the sound

            // Randomize dice image during the rotation animation
            val imageUpdateJob = launch {
                repeat(10) { // Change the image 10 times during the rotation
                    delay(100) // Wait 100ms before changing the image
                    result = (1..6).random()  // Randomize dice image
                }
            }

            // Animate the dice rotation
            rotation.animateTo(
                targetValue = 360f,
                animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
            )

            // After the rotation animation ends, finalize the dice result
            result = (1..6).random()  // Final dice result after full rotation

            // Ensure the image update job is completed
            imageUpdateJob.join()

            mediaPlayer.release() // Stop and release the media player

            rotation.snapTo(0f) // Reset the rotation angle after the roll
            shake = false // Reset shake to allow the next roll
        }
    }

    // Start shake detection
    DisposableEffect(Unit) {
        val shakeDetector = ShakeDetector(localContext) {
            shake = true  // Modify the shake flag when a shake is detected
        }
        onDispose {
            shakeDetector.unregister()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Image with rotation animation
        Dice(
            dice = painterResource(id = imageResource),
            rotationAngle = rotation.value
        )

        Spacer(modifier = Modifier.height(16.dp))
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